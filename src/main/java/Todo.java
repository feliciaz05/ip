public class Todo extends Task {

    //protected String tasky;

    public Todo(String description) {
        super(description);
    }

    @Override
    public String getType() {
        return "T"; // Todo type
    }

    @Override
    public String toString() {
        return super.toString();
    }
}