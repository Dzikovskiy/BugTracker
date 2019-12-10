package app.model;

public class TaskBuffer {
    private static String id = "";
    private static String task = "";
    private static String creator = "";

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        TaskBuffer.id = id;
    }

    public static String getCreator() {
        return creator;
    }

    public static void setCreator(String creator) {
        TaskBuffer.creator = creator;
    }

    public static String getTask() {
        return task;
    }

    public static void setTask(String task) {
        TaskBuffer.task = task;
    }
}
