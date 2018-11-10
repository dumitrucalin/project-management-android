package groupRoutes;

import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.Map;

public class DeleteUserGroup {
    public static Boolean DeleteUserGroup(String groupName, String userName) {
        Map<String, String> parameter = new HashMap<String, String>();

        parameter.clear();
        parameter.put("groupName", groupName);
        parameter.put("username", userName);

        HttpUrlConnection deleteUserGroup = new HttpUrlConnection(parameter, "groups/user/delete");
        deleteUserGroup.postThread().start();

        try {
            deleteUserGroup.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) deleteUserGroup.response.get("err") == 0) {
            return true;
        } else {
            return false;
        }
    }
}
