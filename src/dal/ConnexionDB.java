package dal;

import java.sql.*;

public class ConnexionDB {
    public Connection cn;
    public Statement st;
    public ResultSet rs;

    public ConnexionDB() throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        this.cn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/stock","postgres", "postgres");
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
