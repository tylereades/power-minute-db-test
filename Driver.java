import java.util.*;
import java.time.*;
public class Driver {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/powerminute?useSSL=false";
        String username = "root";
        String password = "aj4AJ$aj4";
        DBConnector db = new DBConnector(url, username, password);
        //db.insertUser("ddd777", "jeff", "stevens", "bottle", 25);
        //db.addAccountabilityPartner(2,3);
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        //LocalDateTime dt = LocalDateTime.now();
        //System.out.println(dt.toString().substring(0,19));

        //db.addUserExercise(2, "hamstring stretch", dt);

        boolean thing = db.madeWeeklyGoal(2);
        int numFailed = db.getUserFailedExercisesWeekly(2);
        System.out.println(numFailed);


    }
}
