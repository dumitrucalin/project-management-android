package userRoutes;

import com.example.calin.task_manager.GeneralInfo;
import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.Map;

public class UpdateUser {
    public static Boolean UpdateUser(String userFullName, String userEmail) {
        Map<String, String> parametre = new HashMap<String, String>();

        parametre.clear();
        parametre.put("username", (String) GeneralInfo.user.get("username"));
        parametre.put("fullName", userFullName);
        parametre.put("email", userEmail);

        HttpUrlConnection updateUser = new HttpUrlConnection(parametre, "users/update");
        updateUser.postThread().start();

        try {
            updateUser.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) updateUser.response.get("err") == 0) {
            GeneralInfo.user.put("fullName", userFullName);
            GeneralInfo.user.put("email", userEmail);
            return true;
        } else {
            return false;
        }
    }
}
