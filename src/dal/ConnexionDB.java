package dal;

import java.sql.*;

public class ConnexionDB {
    // Les 5 lignes suivantes sont à configurer en fonction de votre installation de PostgreSQL
    private final String HOST_NAME = "localhost"; //exemple : localhost
    private final String PORT = "5432"; //exemple : 5432
    private final String DATA_BASE_NAME = "stock"; //exemple : stock
    private final String USER = "postgres"; //exemple : postgres
    private final String PASSWORD = "postgres"; //exemple : postgres


    private final String URL = "jdbc:postgresql://";
    private final String COMPLETE_URL = URL + HOST_NAME + ":" + PORT + "/" + DATA_BASE_NAME;

    public Connection cn;
    public Statement st;
    public ResultSet rs;

    public ConnexionDB() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        this.cn = DriverManager.getConnection(COMPLETE_URL, USER, PASSWORD);
        this.st = this.cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    public void deconnexion() {
        try {
            this.rs.close();
            this.st.close();
            this.cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
