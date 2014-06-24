package at.porscheinformatik.tutorial.todo;

import java.util.Date;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RolesAllowed({"USER", "ADMIN"})
public class TodoService
{
    @Autowired
    private TodoRepository repository;

    public Iterable<Todo> listAll()
    {
        return repository.findAll();
    }

    public Todo get(int id)
    {
        return repository.findOne(id);
    }

    @Transactional
    public Todo addTodo(String title, Date due)
    {
        Todo todo = new Todo(title, due);
        return repository.save(todo);
    }

    @Transactional
    public Todo change(int id, Todo changedTodo)
    {
        Todo todo = repository.findOne(id);
        todo.title = changedTodo.title;
        todo.due = changedTodo.due;
        todo.completed = changedTodo.completed;
        return todo;
    }
}
