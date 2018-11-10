package groupRoutes;

import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateGroup {
    public static Boolean UpdateGroup(String groupName, List<String> userNames) {
        Map<String, String> parameter = new HashMap<String, String>();

        parameter.clear();
        parameter.put("groupName", groupName);
        parameter.put("usernames", userNames.toString());

        HttpUrlConnection updateGroup = new HttpUrlConnection(parameter, "groups/users/create");
        updateGroup.postThread().start();

        try {
            updateGroup.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) updateGroup.response.get("err") == 0) {
            GetUsersBasicInfo.GetUsersBasicInfo(groupName);
            return true;
        } else {
            return false;
        }
    }
}
