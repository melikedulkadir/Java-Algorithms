import java.util.ArrayList;
import java.util.List;

public class Project {
    private final String name;
    private final List<Task> tasks;

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) System.out.print(symbol);
        System.out.println();
    }

    /**
     * Schedule all tasks within this project such that they will be completed as early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */
    public int[] getEarliestSchedule() {
        // TODO: YOUR CODE HERE
        int[] schedule = new int[tasks.size()];
        int startDay = 0;
        Graph graph = new Graph(tasks.size());

        // Create new edges with tasks
        for (Task task : tasks) {
            for (int dependent_task : task.getDependencies()) {
                graph.addEdge(dependent_task, task.getTaskID(), false);
            }
        }

        List<Integer> topologicalOrder = new ArrayList<>(graph.topologicalOrder());

        // Find all tasks' start day
        for (int i : topologicalOrder) {
            for (Task task : tasks) {
                if (i == task.getTaskID()) {
                    int endDay = 0;
                    for (int dependency : task.getDependencies()) {
                        if (endDay < schedule[dependency] + tasks.get(dependency).getDuration())
                            endDay = schedule[dependency] + tasks.get(dependency).getDuration();
                    }
                    startDay = endDay;
                }
            }
            // Add start days to schedule array
            schedule[i] = startDay;
        }
        return schedule;
    }

    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        int projectDuration = 0;
        // TODO: YOUR CODE HERE
        projectDuration = getEarliestSchedule()[getEarliestSchedule().length - 1] + (tasks.get(getEarliestSchedule().length - 1).getDuration());
        return projectDuration;
    }

    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s", "Task ID", "Description", "Start", "End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i], schedule[i] + t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length - 1).getDuration() + schedule[schedule.length - 1]));
        printlnDash(limit, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }

        return name.equals(project.name) && equal == tasks.size();
    }

}
