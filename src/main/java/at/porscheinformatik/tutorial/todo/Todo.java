package at.porscheinformatik.tutorial.todo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Future;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiObject(name = "Todo entry", description = "Represents a single todo entry in a todo list.")
@Entity
@Table(name = "todos")
public class Todo
{
    @Id
    @GeneratedValue
    public Integer id;

    @NotEmpty
    @Length(min = 5, max = 50)
    @ApiObjectField(name = "Title", required = true)
    public String title;

    @ApiObjectField(name = "Due date")
    @Future
    public Date due;

    @ApiObjectField(name = "Is the todo completed?", required = true)
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
