package taskRoutes;

import com.example.calin.task_manager.GeneralInfo;
import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteIdTask {
    public static Boolean DeleteIdTask(String groupName, String userName, String taskId) {
        Map<String, String> parameter = new HashMap<String, String>();

        parameter.clear();
        parameter.put("username", userName);
        parameter.put("groupName", groupName);
        parameter.put("taskId", taskId);

        HttpUrlConnection deleteTaskId = new HttpUrlConnection(parameter, "groups/task/delete");
        deleteTaskId.postThread().start();

        try {
            deleteTaskId.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) deleteTaskId.response.get("err") == 0) {
            ((List<String>) GeneralInfo.tasks.get("tasksGiven")).remove(taskId);
            ((List<String>) GeneralInfo.tasks.get("tasksReceived")).remove(taskId);
            return true;
        } else {
            return false;
        }
    }
}
