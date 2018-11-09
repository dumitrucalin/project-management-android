package userRoutes;

import com.example.calin.task_manager.GeneralInfo;
import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.Map;

public class Login {
    public static Boolean Login(String userName, String userPassword) {
        Map<String, String> parametre = new HashMap<String, String>();

        parametre.clear();
        parametre.put("username", userName);
        parametre.put("password", userPassword);

        HttpUrlConnection userLogin = new HttpUrlConnection(parametre, "users/login");
        userLogin.getThread().start();

        try {
            userLogin.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) userLogin.response.get("err") == 0) {
            GeneralInfo.token = (String) userLogin.response.get("token");
            return true;
        } else {
            return false;
        }
    }
}
