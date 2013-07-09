package at.porscheinformatik.tutorial.todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TodoService
{
    private int idSeq = 1;
    private List<Todo> todos = new ArrayList<Todo>();

    public TodoService()
    {
        todos.add(new Todo(idSeq++, "Abwaschen", null));
        todos.add(new Todo(idSeq++, "Einkaufen gehen", new Date()));
    }

    public List<Todo> listAll()
    {
        return Collections.unmodifiableList(todos);
    }

    public Todo get(int id)
    {
        for (Todo todo : todos)
        {
            if (todo.id == id)
            {
                return todo;
            }
        }
        return null;
    }

    public Todo addTodo(String title, Date due)
    {
        Todo todo = new Todo(idSeq++, title, due);
        todos.add(todo);
        return todo;
    }

    public Todo change(int id, Todo changedTodo)
    {
        for (Todo todo : todos)
        {
            if(todo.id == id)
            {
                todo.title = changedTodo.title;
                todo.completed = changedTodo.completed;
                todo.due = changedTodo.due;
                return todo;
            }
        }
        return null;
    }
}
