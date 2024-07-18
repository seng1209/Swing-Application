import java.sql.*;

public class GetConnection {

    static Connection getConnection(){
        try{
            Connection connection;
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/Testing","root","hellomysql");
            return  connection;
        }catch (SQLException exception){
            System.out.println(exception);
            return null;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return null;
        }
    }

}
