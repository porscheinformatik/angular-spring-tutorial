package at.porscheinformatik.tutorial.todo.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
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

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/list", method = GET)
    public Response listAll()
    {
        return Response.ok(todoService.listAll());
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Response get(@PathVariable int id)
    {
        return Response.ok(todoService.get(id));
    }

    @RequestMapping(value = "/new", method = POST)
    public Response newTodo(@RequestBody @Valid Todo todo, BindingResult result)
    {
        if (result.hasErrors())
        {
            return Response.error(messageSource, result.getAllErrors());
        }

        return Response.ok(todoService.addTodo(todo.title, todo.due));
    }

    @RequestMapping(value = "/{id}", method = POST)
    public Response change(@PathVariable int id, @RequestBody @Valid Todo todo, BindingResult result)
    {
        if (result.hasErrors())
        {
            return Response.error(messageSource, result.getAllErrors());
        }

        return Response.ok(todoService.change(id, todo));
    }

}
