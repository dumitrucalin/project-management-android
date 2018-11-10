package groupRoutes;

import com.example.calin.task_manager.GeneralInfo;
import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateGroup {
    public static Boolean CreateGroup(String groupName, List<String> userNames) {
        Map<String, String> parameter = new HashMap<String, String>();

        parameter.clear();
        parameter.put("groupName", groupName);
        parameter.put("usernames", userNames.toString());

        HttpUrlConnection createGroup = new HttpUrlConnection(parameter, "groups/create");
        createGroup.postThread().start();

        try {
            createGroup.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) createGroup.response.get("err") == 0) {
            ((List<String>) GeneralInfo.user.get("groupNames")).add(groupName);
            return true;
        } else {
            return false;
        }
    }
}
