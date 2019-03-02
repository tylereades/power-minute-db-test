import java.sql.*;
import java.time.*;

public class DBConnector {
    private Statement stmt;
    private Connection conn;

    private String URL;
    private String USERNAME;
    private String PASSWORD;

    public DBConnector(String DBUrl, String DBUsername, String DBPassword) {
        URL = DBUrl;
        USERNAME = DBUsername;
        PASSWORD = DBPassword;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Done");
        } catch (Exception e) {
            System.out.print("Error " + e);
        }

    }

    /*private Connection getDBConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
        } catch (SQLException se) {
            throw new IllegalStateException("Cannot connect to the database");
        } catch (Exception e) {
            System.out.print("Error " + e);
        }

        return connection;
    }*/

    public void insertUser(String username, String firstName, String lastName,
                             String password, int weeklyGoal) {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();
            String query = "insert into user values (null," +
                              "\""+username+"\""+","+"\""+firstName+"\""+","+
                              "\""+lastName+"\""+","+"\""+password+"\""+","+ weeklyGoal+");";
            int result = stmt.executeUpdate(query);
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void addAccountabilityPartner(int userID, int partnerID) {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();
            String query = "insert into accountability_partner values (" +
                              userID + "," + partnerID + ");";
            int result = stmt.executeUpdate(query);
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void addUserExercise(int userID, String exerciseName, LocalDateTime dt) {
        String datetime = formatDateTime(dt);
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();
            String query = "insert into exercise values (" +
                              userID + "," + "\"" + exerciseName + "\"" +
                              "," + "\"" + datetime + "\"" + ");";
            int result = stmt.executeUpdate(query);
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private String formatDateTime(LocalDateTime dt) {
        // LocalDateTime.now() format is yyyy-mm-ddThh:mi:ss.xxxxxx
        //   Turns into yyyy-mm-dd hh:mi:ss
        String datetime = dt.toString();
        datetime = datetime.substring(0,10) + " "
                     + datetime.substring(11,19);
        return datetime;
    }

}

