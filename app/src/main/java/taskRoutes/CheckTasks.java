package taskRoutes;

import com.example.calin.task_manager.GeneralInfo;
import com.example.calin.task_manager.HttpUrlConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class CheckTasks extends TimerTask {
    public void run() {
        Map<String, String> parametre = new HashMap<String, String>();
        parametre.put("username", (String) GeneralInfo.user.get("username"));
        parametre.put("groupName", GeneralInfo.groupName);

        HttpUrlConnection checkTasks = new HttpUrlConnection(parametre, "tasks/status/get");
        checkTasks.getThread().start();

        try {
            checkTasks.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) checkTasks.response.get("err") == 0) {
            if ((Boolean) checkTasks.response.get("tasksModified")) {
                GetTasks();
            }
        } else {
//          TODO: Error for checking if tasks were modified
        }
    }

    public static void GetTasks() {
        Map<String, String> parametre = new HashMap<String, String>();
        parametre.put("username", (String) GeneralInfo.user.get("username"));
        parametre.put("groupName", GeneralInfo.groupName);

        HttpUrlConnection getTasks = new HttpUrlConnection(parametre, "tasks/get");
        getTasks.getThread().start();

        try {
            getTasks.thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if ((Integer) getTasks.response.get("err") == 0) {
            GeneralInfo.tasks = (Map) getTasks.response.get("tasks");
        } else {
//          TODO: Error for geting the tasks
        }
    }
}
