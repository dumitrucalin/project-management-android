package taskRoutes;

import com.example.calin.task_manager.GeneralInfo;
import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignTask {
    public static Boolean AssignTask(String taskId, String userNameCreator, String groupName,
                                     List<String> userNamesReceivers, List<String>userNamesToDelete) {
        Map<String, String> parameter = new HashMap<String, String>();

        parameter.clear();
        parameter.put("taskId", taskId);
        parameter.put("usernameCreator", userNameCreator);
        parameter.put("usernamesReceiver", userNamesReceivers.toString());

        HttpUrlConnection assignTask = new HttpUrlConnection(parameter, "tasks/receivers");
        assignTask.postThread().start();

        try {
            assignTask.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) assignTask.response.get("err") == 0) {
            parameter.clear();
            parameter.put("groupName", groupName);
            parameter.put("usernamesToDelete", userNamesToDelete.toString());

            HttpUrlConnection updateUsersTasks = new HttpUrlConnection(parameter, "tasks/reload");
            updateUsersTasks.postThread().start();

            try {
                updateUsersTasks.thread.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            if ((Integer) updateUsersTasks.response.get("err") == 0) {
                for (Map task : (List<Map>) GeneralInfo.tasks.get("tasksReceived")) {
                    if (task.get("taskId").equals(taskId)) {
                        task.put("usernamesReceiver", userNamesReceivers);
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
