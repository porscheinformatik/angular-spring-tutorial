package at.porscheinformatik.tutorial.todo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Future;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "todos")
public class Todo
{
    @Id
    @GeneratedValue
    public Integer id;

    @NotEmpty
    @Length(min = 5, max = 50)
    public String title;

    @Future
    public Date due;

    public boolean completed;

    Todo()
    {
    }

    public Todo(String title, Date due)
    {
        this.title = title;
        this.due = due;
    }
}
