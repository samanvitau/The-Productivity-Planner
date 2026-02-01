import java.util.ArrayList;
import java.util.Scanner;

public class ProductivityPlanner {
    // Our Instance variablse
    private ArrayList<Task> tasks;
    private String selectedPersonType;
    private int streak;
    private Scanner scanner;
    
    // Constructor
    public ProductivityPlanner() {
        tasks = new ArrayList<Task>();
        selectedPersonType = "morning";
        streak = 0;
        scanner = new Scanner(System.in);
    }
    
        public void run() {
        System.out.println("=================================");
        System.out.println("  THE PRODUCTIVITY PLANNER");
        System.out.println("=================================");
        System.out.println();
        
        selectPersonType();
        
        boolean running = true;
        while (running) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            if (choice == 1) {
                addTask();
            } else if (choice == 2) {
                viewTasks();
            } else if (choice == 3) {
                toggleTaskCompletion();
            } else if (choice == 4) {
                deleteTask();
            } else if (choice == 5) {
                displayStats();
            } else if (choice == 6) {
                selectPersonType();
            } else if (choice == 7) {
                displayQuote();
            } else if (choice == 8) {
                running = false;
                System.out.println();
                System.out.println("Goodbye! Keep being productive!");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // this is to let the user select their person type(morning, evening or afternoon)
    private void selectPersonType() {
        System.out.println("What type of person are you?");
        System.out.println("1. Morning person (Peak: 6AM to 12PM)");
        System.out.println("2. Afternoon person (Peak: 12PM to 6PM)");
        System.out.println("3. Evening person (Peak: 6PM to 12AM)");
        System.out.print("Select (1-3): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 1) {
            selectedPersonType = "morning";
        } else if (choice == 2) {
            selectedPersonType = "afternoon";
        } else if (choice == 3) {
            selectedPersonType = "evening";
        } else {
            selectedPersonType = "morning";
        }
        
        System.out.println();
        System.out.println("You selected: " + selectedPersonType + " person");
        System.out.println();
    }
    
    // Display the menu/landing page
    private void displayMenu() {
        System.out.println();
        System.out.println("--- MENU ---");
        System.out.println("1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Complete/Uncomplete Task");
        System.out.println("4. Delete Task");
        System.out.println("5. View Statistics");
        System.out.println("6. Change Person Type");
        System.out.println("7. View Motivational Quote");
        System.out.println("8. Exit");
    }
    
    // Add a new task
    private void addTask() {
        System.out.println();
        System.out.println("--- ADD NEW TASK ---");
        
        System.out.print("Enter task description: ");
        String text = scanner.nextLine();
        
        if (text.length() == 0) {
            System.out.println("Task cannot be empty!");
            return;
        }
        
        System.out.println();
        System.out.println("Select priority:");
        System.out.println("1. Low");
        System.out.println("2. Medium");
        System.out.println("3. High");
        System.out.print("Priority (1-3): ");
        int priorityChoice = scanner.nextInt();
        scanner.nextLine();
        
        String priority = "low";
        if (priorityChoice == 1) {
            priority = "low";
        } else if (priorityChoice == 2) {
            priority = "medium";
        } else if (priorityChoice == 3) {
            priority = "high";
        }
        
        System.out.println();
        System.out.println("Select category:");
        System.out.println("1. Tests/Homework");
        System.out.println("2. Meetings");
        System.out.println("3. Chores");
        System.out.println("4. Other");
        System.out.println("5. None");
        System.out.print("Category (1-5): ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();
        
        String category = "";
        if (categoryChoice == 1) {
            category = "Tests/Homework";
        } else if (categoryChoice == 2) {
            category = "Meetings";
        } else if (categoryChoice == 3) {
            category = "Chores";
        } else if (categoryChoice == 4) {
            category = "Other";
        }
        
        long id = System.currentTimeMillis();
        Task task = new Task(id, text, priority, category, selectedPersonType);
        tasks.add(task);
        
        System.out.println();
        System.out.println("Task added successfully to " + selectedPersonType + " tasks!");
    }
    
    // View all tasks left
    private void viewTasks() {
        System.out.println();
        System.out.println("=== YOUR TASKS ===");
        System.out.println();
        
        if (tasks.size() == 0) {
            System.out.println("No tasks yet. Add some to get started!");
            return;
        }
        
        System.out.println("MORNING TASKS:");
        displayTasksByTimeOfDay("morning");
        
        System.out.println();
        System.out.println("AFTERNOON TASKS:");
        displayTasksByTimeOfDay("afternoon");
        
        System.out.println();
        System.out.println("EVENING TASKS:");
        displayTasksByTimeOfDay("evening");
    }
    
    // method to display tasks for a specific time of the day
    private void displayTasksByTimeOfDay(String timeOfDay) {
        boolean found = false;
        
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getTimeOfDay().equals(timeOfDay)) {
                System.out.println((i + 1) + ". " + task);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("  No tasks for this time period.");
        }
    }
    
    // mark a task as completed
    private void toggleTaskCompletion() {
        if (tasks.size() == 0) {
            System.out.println();
            System.out.println("No tasks available!");
            return;
        }
        
        viewTasks();
        System.out.println();
        System.out.print("Enter task number to toggle completion: ");
        int taskNumber = scanner.nextInt();
        scanner.nextLine();
        
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Invalid task number!");
            return;
        }
        
        Task task = tasks.get(taskNumber - 1);
        task.setCompleted(!task.getCompleted());
        
        if (task.getCompleted()) {
            System.out.println("Task marked as completed!");
            
            // Update streak every after 5 completed tasks
            int completedCount = countCompletedTasks();
            if (completedCount % 5 == 0) {
                streak++;
                System.out.println("Streak increased to " + streak + "!");
            }
        } else {
            System.out.println("Task marked as incomplete.");
        }
    }
    
    // method to count the total tasks completed by the user
    private int countCompletedTasks() {
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getCompleted()) {
                count++;
            }
        }
        return count;
    }
    
    // Delete a task after it's done
    private void deleteTask() {
        if (tasks.size() == 0) {
            System.out.println();
            System.out.println("No tasks to delete!");
            return;
        }
        
        viewTasks();
        System.out.println();
        System.out.print("Enter task number to delete: ");
        int taskNumber = scanner.nextInt();
        scanner.nextLine();
        
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Invalid task number!");
            return;
        }
        
        tasks.remove(taskNumber - 1);
        System.out.println("Task deleted successfully!");
    }
    
    // show the stats to the user
    private void displayStats() {
        System.out.println();
        System.out.println("=== STATISTICS ===");
        System.out.println("Total tasks: " + tasks.size());
        System.out.println("Completed: " + countCompletedTasks());
        System.out.println("Daily Streak: " + streak);
        System.out.println();
        System.out.println("Breakdown by time:");
        System.out.println("  Morning: " + countTasksByTime("morning"));
        System.out.println("  Afternoon: " + countTasksByTime("afternoon"));
        System.out.println("  Evening: " + countTasksByTime("evening"));
    }
    
    // method to count tasks by the time of the day
    private int countTasksByTime(String timeOfDay) {
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTimeOfDay().equals(timeOfDay)) {
                count++;
            }
        }
        return count;
    }
    
    // Display a motivational quote by Ada Lovelace
    private void displayQuote() {
        System.out.println();
        System.out.println("Woman in Tech Spotlight");
        System.out.println("\"The question isn't whose going to let me;");
        System.out.println("it's who's going to stop me\"");
        System.out.println("- Ada Lovelace, First Computer Programmer");
        System.out.println();
    }
    
    public static void main(String[] args) {
        ProductivityPlanner planner = new ProductivityPlanner();
        planner.run();
    }
}
