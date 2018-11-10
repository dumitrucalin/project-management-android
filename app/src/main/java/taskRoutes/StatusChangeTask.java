package taskRoutes;

import com.example.calin.task_manager.GeneralInfo;
import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusChangeTask {
    public static Boolean StatusChangeTask(String userNameCreator, String groupName, String taskId, String taskStatus) {
        Map<String, String> parameter = new HashMap<String, String>();

        parameter.clear();
        parameter.put("usernameCreator", userNameCreator);
        parameter.put("groupName", groupName);
        parameter.put("taskId", taskId);
        parameter.put("taskStatus", taskStatus);

        HttpUrlConnection statusChangeTask = new HttpUrlConnection(parameter, "tasks/status/change");
        statusChangeTask.postThread().start();

        try {
            statusChangeTask.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) statusChangeTask.response.get("err") == 0) {
            for (Map task : (List<Map>) GeneralInfo.tasks.get("tasksReceived")) {
                if (task.get("taskId").equals(taskId)) {
                    task.put("taskStatus", taskStatus);
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
