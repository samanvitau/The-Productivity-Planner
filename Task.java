public class Task {
    private long id;
    private String text;
    private String priority;
    private String category;
    private String timeOfDay;
    private boolean completed;
    
    //our constructors:
    public Task(long id, String text, String priority, String category, String timeOfDay) {
        this.id = id;
        this.text = text;
        this.priority = priority;
        this.category = category;
        this.timeOfDay = timeOfDay;
        this.completed = false;
    }
    
    // our getter methods:
    public long getId() {
        return id;
    }
    
    public String getText() {
        return text;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public String getCategory() {
        return category;
    }
    
    public String getTimeOfDay() {
        return timeOfDay;
    }
    
    public boolean getCompleted() {
        return completed;
    }
    
    // Mutator method (setter)
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    // toString method
    public String toString() {
        String status = "[X]";
        if (!completed) {
            status = "[== ]";
        }
        
        String result = status + " " + text + " - Priority: " + priority;
        
        if (category != null && !category.equals("")) {
            result = result + ", Category: " + category;
        }
        
        return result;
    }
}
