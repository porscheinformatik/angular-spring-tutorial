package at.porscheinformatik.tutorial.todo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Todo
{
    public int id;

    @NotEmpty
    @Length(min = 5, max = 50)
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
