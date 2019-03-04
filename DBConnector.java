// Could add methods to return number of exercises completed / failed in the last day/week/month
// Method to update the user's goal

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

    public void addUserExercise(int userID, String exerciseName, LocalDateTime dt, boolean completed) {
        String datetime = formatDateTime(dt);
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();
            String query = "insert into exercise values (" +
                              userID + "," + "\"" + exerciseName + "\"" +
                              "," + "\"" + datetime + "\"" + " " + completed + ");";
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

    public boolean madeWeeklyGoal(int userID) {
        // Looks to see if the user has completed more workouts than
        //   what they have as their goal
        int madeGoal = 0;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();
            String query = "select total_completed >= weekly_goal " + 
                           "from (select weekly_goal, sum(was_completed) as total_completed " +
                                 "from user, exercise " +
                                 "where time_completed between date_sub(now(), interval 1 week) and now() " +
                                 "and user.id=" + userID + " and exercise.id=" + userID + ") as goal_view;";
            ResultSet result = stmt.executeQuery(query);
            result.next();
            madeGoal = result.getInt(1);
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return madeGoal == 1;
    }

    public int getUserFailedExercisesWeekly(int userID) {
        // Returns number of times that the user has not completed an exercise
        // Returns -1 if the connection fails
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();
            String query = "select count(*) from exercise where id=" + userID + " and was_completed=0;";
            ResultSet result = stmt.executeQuery(query);
            result.next();
            int numFailed = result.getInt(1); 
            conn.close();
            return numFailed;
        } catch (SQLException se) {
            se.printStackTrace();
            return -1;
        }
    }

    public void updateUserGoal(int userID, int newGoal) {
        // Still need to do 
        return;
    }

}

