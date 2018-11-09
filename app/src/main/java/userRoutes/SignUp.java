package userRoutes;

import com.example.calin.task_manager.GeneralInfo;
import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.Map;

public class SignUp {
    public static Boolean SignUp(String userName, String userFullName, String userPassword, String userEmail) {
        Map<String, String> parametre = new HashMap<String, String>();

        parametre.clear();
        parametre.put("username", userName);
        parametre.put("fullName", userFullName);
        parametre.put("password", userPassword);
        parametre.put("email", userEmail);

        HttpUrlConnection userSignUp = new HttpUrlConnection(parametre, "users/signup");
        userSignUp.postThread().start();

        try {
            userSignUp.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) userSignUp.response.get("err") == 0) {
            GeneralInfo.token = (String) userSignUp.response.get("token");
            return true;
        } else {
            return false;
        }
    }
}
