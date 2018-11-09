package userRoutes;

import com.example.calin.task_manager.GeneralInfo;
import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.Map;

public class Logout {
    public static Boolean logout() {
        Map<String, String> parametre = new HashMap<String, String>();

        parametre.clear();
        parametre.put("token", GeneralInfo.token);

        HttpUrlConnection userLogout = new HttpUrlConnection(parametre, "users/logout");
        userLogout.postThread().start();

        try {
            userLogout.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) userLogout.response.get("err") == 0) {
            GeneralInfo.token = "";
            GeneralInfo.user.clear();
            GeneralInfo.tasks.clear();
            GeneralInfo.groupName = "";
            GeneralInfo.usersBasicInfo.clear();
            return true;
        } else {
            return false;
        }
    }
}
