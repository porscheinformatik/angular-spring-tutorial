package at.porscheinformatik.tutorial.todo;

public class Todo
{
    public int id;
    public String title;
    public boolean completed;

    Todo()
    {
    }

    public Todo(int id, String title)
    {
        this.id = id;
        this.title = title;
    }
}
