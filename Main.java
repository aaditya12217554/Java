import java.util.*;

class Process {
    String name;
    int processID;
    List<Process> dependencies;

    public Process(String name, int processID) {
        this.name = name;
        this.processID = processID;
        this.dependencies = new ArrayList<>();
    }
}

class WaitForGraph {
    List<Process> processes;

    public WaitForGraph() {
        this.processes = new ArrayList<>();
    }

    public boolean hasDeadlock() {
        Set<Process> visited = new HashSet<>();
        Set<Process> inStack = new HashSet<>();

        for (Process process : processes) {
            if (!visited.contains(process) && hasCycle(process, visited, inStack)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasCycle(Process process, Set<Process> visited, Set<Process> inStack) {
        visited.add(process);
        inStack.add(process);

        for (Process dependency : process.dependencies) {
            if (!visited.contains(dependency)) {
                if (hasCycle(dependency, visited, inStack)) {
                    return true;
                }
            } else if (inStack.contains(dependency)) {
                return true;
            }
        }

        inStack.remove(process);
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        WaitForGraph graph = new WaitForGraph();

        Process p1 = new Process("Process 1", 1);
        Process p2 = new Process("Process 2", 2);
        Process p3 = new Process("Process 3", 3);

        // Add dependencies
        p1.dependencies.add(p2);
        p2.dependencies.add(p3);
        // Create a cycle
        p3.dependencies.add(p1);

        graph.processes.add(p1);
        graph.processes.add(p2);
        graph.processes.add(p3);

        if (graph.hasDeadlock()) {
            System.out.println("Deadlock detected!");
        } else {
            System.out.println("No deadlock detected.");
        }
    }
}
