package groupRoutes;

import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.Map;

public class GetUsersBasicInfo {
    public static Map<String, String> GetUsersBasicInfo(String groupName) {
        Map<String, String> parameter = new HashMap<String, String>();

        parameter.clear();
        parameter.put("groupName", groupName);

        HttpUrlConnection getGroupUsers = new HttpUrlConnection(parameter, "groups/users/get");
        getGroupUsers.getThread().start();

        try {
            getGroupUsers.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) getGroupUsers.response.get("err") == 0) {
            parameter.clear();
            parameter.put("usernames", getGroupUsers.response.get("usernames").toString());

            HttpUrlConnection getUsersBasicInfo = new HttpUrlConnection(parameter, "users/usersBasicInfo/get");
            getUsersBasicInfo.getThread().start();

            try {
                getUsersBasicInfo.thread.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            if ((Integer) getUsersBasicInfo.response.get("err") == 0) {
                return (Map) getUsersBasicInfo.response.get("usersBasicInfo");
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
