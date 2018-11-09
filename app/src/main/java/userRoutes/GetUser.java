package userRoutes;

import com.example.calin.task_manager.GeneralInfo;
import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.Map;

public class GetUser {
    public static Boolean GetUser() {
        Map<String, String> parametre = new HashMap<String, String>();

        parametre.clear();
        parametre.put("token", GeneralInfo.token);

        HttpUrlConnection userGet = new HttpUrlConnection(parametre, "users/login");
        userGet.getThread().start();

        try {
            userGet.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) userGet.response.get("err") == 0) {
            GeneralInfo.user = (Map) userGet.response.get("user");
            return true;
        } else {
            return false;
        }
    }
}
