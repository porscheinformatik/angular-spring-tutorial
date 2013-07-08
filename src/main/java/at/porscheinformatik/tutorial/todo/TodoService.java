package at.porscheinformatik.tutorial.todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TodoService
{
    private int idSeq = 1;
    private List<Todo> todos = new ArrayList<Todo>();

    public TodoService()
    {
        todos.add(new Todo(idSeq++, "Abwaschen"));
        todos.add(new Todo(idSeq++, "Einkaufen gehen"));
    }

    public List<Todo> listAll()
    {
        return Collections.unmodifiableList(todos);
    }

    public Todo addTodo(String title)
    {
        Todo todo = new Todo(idSeq++, title);
        todos.add(todo);
        return todo;
    }
}
