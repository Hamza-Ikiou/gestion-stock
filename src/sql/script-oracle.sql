-- Suppression des tables
DROP TABLE Categories CASCADE CONSTRAINT;
DROP TABLE Produits CASCADE CONSTRAINT;
DROP TABLE Catalogues CASCADE CONSTRAINT;

-- Suppression des séquences
DROP SEQUENCE seqCategorie;
DROP SEQUENCE seqProduit;
DROP SEQUENCE seqCatalogue;

-- Création de la table Catalogues
CREATE TABLE Catalogues(
    idCatalogue NUMBER, nomCatalogue VARCHAR(255),
    CONSTRAINT pk_Catalogues PRIMARY KEY (idCatalogue),
    CONSTRAINT nn_nomCatalogue_Catalogue CHECK (nomCatalogue IS NOT NULL),
    CONSTRAINT un_nomCatalogue_Catalogue UNIQUE (nomCatalogue)
);

-- Création de la table Categories
CREATE TABLE Categories(
    idCategorie NUMBER, nomCategorie VARCHAR(255), tauxTVA NUMBER,
    CONSTRAINT pk_Categorie PRIMARY KEY (idCategorie),
    CONSTRAINT nn_nomCategorie_Categorie CHECK (nomCategorie IS NOT NULL),
    CONSTRAINT nn_tauxTVA_Categorie CHECK (tauxTVA IS NOT NULL),
    CONSTRAINT un_nomCategorie_Categorie UNIQUE (nomCategorie)
);

-- Création de la table Produits
CREATE TABLE Produits(
    idProduit NUMBER, nomProduit VARCHAR(255), prixProduitHT NUMBER, quantiteStockProduits INTEGER, idCatalogue NUMBER, idCategorie NUMBER,

    CONSTRAINT pk_Produits PRIMARY KEY (idProduit),

    CONSTRAINT fk_Produits_Catalogue FOREIGN KEY (idCatalogue) REFERENCES Catalogues(idCatalogue) ON DELETE CASCADE,
    CONSTRAINT fk_Produits_Categorie FOREIGN KEY (idCategorie) REFERENCES Categories(idCategorie),

    CONSTRAINT nn_nomProduit_Produits CHECK (nomProduit IS NOT NULL),
    CONSTRAINT nn_prixProduitHT_Produits CHECK (prixProduitHT IS NOT NULL),
    CONSTRAINT nn_qteStockProduits_Produits CHECK (quantiteStockProduits IS NOT NULL),

    CONSTRAINT ck_prixProduitHT_Produits CHECK (prixProduitHT > 0),
    CONSTRAINT ck_qteStockProduits_Produits CHECK (quantiteStockProduits >= 0)
);

-- Création des séquences
CREATE SEQUENCE seqCatalogue START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seqProduit START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seqCategorie START WITH 1 INCREMENT BY 1;

-- Création des procédures
CREATE OR REPLACE PROCEDURE nouveauCatalogue(p_nomCatalogue IN Catalogues.nomCatalogue%TYPE) IS
BEGIN
    INSERT INTO Catalogues(idCatalogue, nomCatalogue)
    VALUES (seqCatalogue.NEXTVAL, p_nomCatalogue);
END;
/
CREATE OR REPLACE PROCEDURE supprimerCatalogue(p_nomCatalogue IN Catalogues.nomCatalogue%TYPE) IS
BEGIN
    DELETE FROM Catalogues
    WHERE nomCatalogue = p_nomCatalogue;
END;
/
CREATE OR REPLACE PROCEDURE nouvelleCategorie(p_nomCategorie IN Categories.nomCategorie%TYPE, p_tauxTVA IN Categories.tauxTVA%TYPE) IS
BEGIN
    INSERT INTO Categories(idCategorie, nomCategorie, tauxTVA)
    VALUES (seqCategorie.NEXTVAL, p_nomCategorie, p_tauxTVA);
END;
/
CREATE OR REPLACE PROCEDURE supprimerCategorie(p_nomCategorie IN Categories.nomCategorie%TYPE) IS
v_exist NUMBER;
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
    WHERE p_nomCategorie = p_nomCategorie;
    END IF;

END;
/
CREATE OR REPLACE PROCEDURE nouveauProduit(
    p_nomProduit IN Produits.nomProduit%TYPE,
    p_prixProduitHT IN Produits.prixProduitHT%TYPE,
    p_quantiteStockProduits IN Produits.quantiteStockProduits%TYPE,
    p_idCatalogue IN Catalogues.idCatalogue%TYPE,
    p_idCategorie IN Categories.idCategorie%TYPE
) IS
v_disponible NUMBER;
BEGIN
    SELECT COUNT(idProduit) INTO v_disponible
    FROM Produits p
    WHERE p.nomProduit = p_nomProduit AND p.idCatalogue = p_idCatalogue;

    IF v_disponible = 0 THEN
            INSERT INTO Produits(idProduit, nomProduit, prixProduitHT, quantiteStockProduits, idCatalogue, idCategorie)
            VALUES (seqProduit.NEXTVAL, p_nomProduit, p_prixProduitHT, p_quantiteStockProduits, p_idCatalogue, p_idCategorie);
    END IF;
END;
/
CREATE OR REPLACE PROCEDURE modifierProduit(
    p_nomProduit IN Produits.nomProduit%TYPE,
    p_prixProduitHT IN Produits.prixProduitHT%TYPE,
    p_quantiteStockProduits IN Produits.quantiteStockProduits%TYPE,
    p_idCatalogue IN Catalogues.idCatalogue%TYPE
) IS
BEGIN
    IF p_quantiteStockProduits IS NULL AND p_prixProduitHT IS NOT NULL THEN
        UPDATE Produits
        SET prixProduitHT = p_prixProduitHT
        WHERE nomProduit = p_nomProduit AND idCatalogue = p_idCatalogue;
    END IF;

    IF p_quantiteStockProduits IS NOT NULL AND p_prixProduitHT IS NULL THEN
        UPDATE Produits
        SET quantiteStockProduits = p_quantiteStockProduits
        WHERE nomProduit = p_nomProduit AND idCatalogue = p_idCatalogue;
    END IF;

    IF p_quantiteStockProduits IS NOT NULL AND p_prixProduitHT IS NOT NULL THEN
        UPDATE Produits
        SET quantiteStockProduits = p_quantiteStockProduits, prixProduitHT = p_prixProduitHT
        WHERE nomProduit = p_nomProduit AND idCatalogue = p_idCatalogue;
    END IF;
END;
/
CREATE OR REPLACE PROCEDURE supprimerProduit(p_nomProduit IN Produits.nomProduit%TYPE, p_idCatalogue IN Catalogues.idCatalogue%TYPE) IS
BEGIN
    DELETE FROM Produits
    WHERE nomProduit = p_nomProduit AND idCatalogue = p_idCatalogue;
END;
/
CREATE OR REPLACE PROCEDURE vendreProduit(p_nomProduit IN Produits.nomProduit%TYPE, p_quantiteStockProduits IN Produits.quantiteStockProduits%TYPE) IS
BEGIN
    UPDATE Produits
    SET quantiteStockProduits = quantiteStockProduits - p_quantiteStockProduits
    WHERE nomProduit = p_nomProduit;
END;
/
CREATE OR REPLACE PROCEDURE acheterProduit(p_nomProduit IN Produits.nomProduit%TYPE, p_quantiteStockProduits IN Produits.quantiteStockProduits%TYPE) IS
BEGIN
    UPDATE Produits
    SET quantiteStockProduits = quantiteStockProduits + p_quantiteStockProduits
    WHERE nomProduit = p_nomProduit;
END;