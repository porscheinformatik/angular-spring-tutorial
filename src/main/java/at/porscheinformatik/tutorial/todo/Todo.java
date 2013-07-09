package at.porscheinformatik.tutorial.todo;

import java.util.Date;

import javax.validation.constraints.Future;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Todo
{
    public int id;

    @NotEmpty
    @Length(min = 5, max = 50)
    public String title;

    @Future
    public Date due;

    public boolean completed;

    Todo()
    {
    }

    public Todo(int id, String title, Date due)
    {
        this.id = id;
        this.title = title;
        this.due = due;
    }
}
