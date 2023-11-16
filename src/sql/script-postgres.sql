-- Suppression des tables
DROP TABLE IF EXISTS Produits CASCADE;
DROP TABLE IF EXISTS Categories CASCADE;
DROP TABLE IF EXISTS Catalogues CASCADE;

-- Suppression des séquences
DROP SEQUENCE IF EXISTS seqProduit CASCADE;
DROP SEQUENCE IF EXISTS seqCategorie CASCADE;
DROP SEQUENCE IF EXISTS seqCatalogue CASCADE;

-- Création des séquences
CREATE SEQUENCE seqCatalogue START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seqCategorie START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seqProduit START WITH 1 INCREMENT BY 1;

-- Création de la table Catalogues
CREATE TABLE Catalogues (
    idCatalogue SERIAL PRIMARY KEY,
    nomCatalogue VARCHAR(255) NOT NULL UNIQUE
);

-- Création de la table Categories
CREATE TABLE Categories (
    idCategorie SERIAL PRIMARY KEY,
    nomCategorie VARCHAR(255) NOT NULL UNIQUE,
    tauxTVA NUMERIC NOT NULL
);

-- Création de la table Produits
CREATE TABLE Produits (
    idProduit SERIAL PRIMARY KEY,
    nomProduit VARCHAR(255) NOT NULL,
    prixProduitHT NUMERIC NOT NULL,
    quantiteStockProduits INTEGER NOT NULL,
    idCatalogue INTEGER REFERENCES Catalogues(idCatalogue) ON DELETE CASCADE,
    idCategorie INTEGER REFERENCES Categories(idCategorie),
    CONSTRAINT ck_prixProduitHT_Produits CHECK (prixProduitHT > 0),
    CONSTRAINT ck_qteStockProduits_Produits CHECK (quantiteStockProduits >= 0)
);

-- Création des procédures
CREATE OR REPLACE FUNCTION nouveauCatalogue(p_nomCatalogue VARCHAR(255)) RETURNS VOID AS $$
BEGIN
    INSERT INTO Catalogues (idCatalogue, nomCatalogue)
    VALUES (nextval('seqCatalogue'), p_nomCatalogue);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION supprimerCatalogue(p_nomCatalogue VARCHAR(255)) RETURNS VOID AS $$
BEGIN
    DELETE FROM Catalogues
    WHERE nomCatalogue = p_nomCatalogue;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION nouvelleCategorie(p_nomCategorie VARCHAR(255), p_tauxTVA NUMERIC) RETURNS VOID AS $$
BEGIN
    INSERT INTO Categories (idCategorie, nomCategorie, tauxTVA)
    VALUES (nextval('seqCategorie'), p_nomCategorie, p_tauxTVA);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION supprimerCategorie(p_nomCategorie VARCHAR(255)) RETURNS VOID AS $$
DECLARE
    v_exist INTEGER;
BEGIN
    SELECT COUNT(*) INTO v_exist
    FROM Produits
    WHERE idCategorie IN (
        SELECT idCategorie
        FROM Categories
        WHERE nomCategorie = p_nomCategorie
    );

    IF v_exist = 0 THEN
        DELETE FROM Categories
        WHERE nomCategorie = p_nomCategorie;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION nouveauProduit(
    p_nomProduit VARCHAR(255),
    p_prixProduitHT NUMERIC,
    p_quantiteStockProduits INTEGER,
    p_idCatalogue INTEGER,
    p_idCategorie INTEGER
) RETURNS VOID AS $$
DECLARE
    v_disponible INTEGER;
BEGIN
    SELECT COUNT(idProduit) INTO v_disponible
    FROM Produits p
    WHERE p.nomProduit = p_nomProduit AND p.idCatalogue = p_idCatalogue;

    IF v_disponible = 0 THEN
        INSERT INTO Produits (idProduit, nomProduit, prixProduitHT, quantiteStockProduits, idCatalogue, idCategorie)
        VALUES (nextval('seqProduit'), p_nomProduit, p_prixProduitHT, p_quantiteStockProduits, p_idCatalogue, p_idCategorie);
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION modifierProduit(
    p_nomProduit VARCHAR(255),
    p_prixProduitHT NUMERIC,
    p_quantiteStockProduits INTEGER,
    p_idCatalogue INTEGER
) RETURNS VOID AS $$
BEGIN
    IF p_quantiteStockProduits IS NOT NULL AND p_prixProduitHT IS NOT NULL THEN
        UPDATE Produits
        SET quantiteStockProduits = p_quantiteStockProduits, prixProduitHT = p_prixProduitHT
        WHERE nomProduit = p_nomProduit AND idCatalogue = p_idCatalogue;
    ELSIF p_quantiteStockProduits IS NOT NULL THEN
        UPDATE Produits
        SET quantiteStockProduits = p_quantiteStockProduits
        WHERE nomProduit = p_nomProduit AND idCatalogue = p_idCatalogue;
    ELSIF p_prixProduitHT IS NOT NULL THEN
        UPDATE Produits
        SET prixProduitHT = p_prixProduitHT
        WHERE nomProduit = p_nomProduit AND idCatalogue = p_idCatalogue;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION supprimerProduit(p_nomProduit VARCHAR(255), p_idCatalogue INTEGER) RETURNS VOID AS $$
BEGIN
    DELETE FROM Produits
    WHERE nomProduit = p_nomProduit AND idCatalogue = p_idCatalogue;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION vendreProduit(p_nomProduit VARCHAR(255), p_quantiteStockProduits INTEGER) RETURNS VOID AS $$
BEGIN
    UPDATE Produits
    SET quantiteStockProduits = quantiteStockProduits - p_quantiteStockProduits
    WHERE nomProduit = p_nomProduit;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION acheterProduit(p_nomProduit VARCHAR(255), p_quantiteStockProduits INTEGER) RETURNS VOID AS $$
BEGIN
    UPDATE Produits
    SET quantiteStockProduits = quantiteStockProduits + p_quantiteStockProduits
    WHERE nomProduit = p_nomProduit;
END;
$$ LANGUAGE plpgsql;
