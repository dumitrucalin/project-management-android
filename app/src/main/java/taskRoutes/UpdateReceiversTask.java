package taskRoutes;

import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateReceiversTask {
    public static Boolean UpdateReceiversTask(String taskId, String userNameCreator,
                                              String groupName, List<String> userNamesReceivers) {
        Map<String, String> parameter = new HashMap<String, String>();

        if (userNamesReceivers.size() == 0) {
            parameter.clear();
            parameter.put("taskId", taskId);
            parameter.put("groupName", groupName);
            parameter.put("usernameCreator", userNameCreator);
            parameter.put("taskStatus", "Reassign");

            HttpUrlConnection reassignTask = new HttpUrlConnection(parameter, "tasks/status/change");
            reassignTask.postThread().start();

            try {
                reassignTask.thread.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            if ((Integer) reassignTask.response.get("err") != 0) {
                return false;
            }
        }

        parameter.clear();
        parameter.put("taskId", taskId);
        parameter.put("usernameCreator", userNameCreator);
        parameter.put("groupName", groupName);
        parameter.put("usernamesReceiver", userNamesReceivers.toString());

        HttpUrlConnection updateReceiversTask = new HttpUrlConnection(parameter, "tasks/receivers");
        updateReceiversTask.postThread().start();

        try {
            updateReceiversTask.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) updateReceiversTask.response.get("err") == 0) {
            return true;
        } else {
            return false;
        }

    }
}
