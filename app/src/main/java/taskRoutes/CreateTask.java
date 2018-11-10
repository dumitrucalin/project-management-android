package taskRoutes;

import com.example.calin.task_manager.GeneralInfo;
import com.example.calin.task_manager.HttpUrlConnection;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTask {
    public static Boolean CreateTask(String taskName, String taskString, String taskPriority, Date taskDeadline,
                                     List<String> userNamesReceivers, String userNameCreator) {
        List<String> viewers = new ArrayList<String> ();
        for (String userNameReceiver : userNamesReceivers) {
            viewers.add(userNameReceiver);
        }
        if (!userNameCreator.equals(userNamesReceivers.get(0))) {
            viewers.add(userNameCreator);
        }

        Map<String, String> parameter = new HashMap<String, String>();

        parameter.clear();
        parameter.put("taskName", taskName);
        parameter.put("taskString", taskString);
        parameter.put("taskPriority", taskPriority);
        parameter.put("taskDeadline", taskDeadline.toString());
        parameter.put("usernamesReceiver", userNamesReceivers.toString());
        parameter.put("usernameCreator", userNameCreator);

        HttpUrlConnection createTask = new HttpUrlConnection(parameter, "tasks/create");

        createTask.postThread().start();

        try {
            createTask.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) createTask.response.get("err") == 0) {
            if (userNameCreator.equals(userNamesReceivers.get(0))) {
                ((List<Map>) GeneralInfo.tasks.get("tasksReceived")).add(parameter);
            } else {
                ((List<Map>) GeneralInfo.tasks.get("tasksGiven")).add(parameter);
            }
            return true;
        } else {
            return false;
        }
    }
}
