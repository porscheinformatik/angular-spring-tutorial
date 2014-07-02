package at.porscheinformatik.tutorial.todo.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.porscheinformatik.tutorial.todo.Todo;
import at.porscheinformatik.tutorial.todo.TodoService;

@RestController
@RequestMapping("/api/todo")
public class TodoController
{
    @Autowired
    private TodoService todoService;

    @RequestMapping(value = "/list", method = GET)
    public Iterable<Todo> listAll()
    {
        return todoService.listAll();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Todo get(@PathVariable int id)
    {
        return todoService.get(id);
    }

    @RequestMapping(value = "/new", method = POST)
    public Todo newTodo(@RequestBody @Valid Todo todo)
    {
        return todoService.addTodo(todo.title, todo.due);
    }

    @RequestMapping(value = "/{id}", method = POST)
    public Todo change(@PathVariable int id, @RequestBody @Valid Todo todo)
    {
        return todoService.change(id, todo);
    }
}
