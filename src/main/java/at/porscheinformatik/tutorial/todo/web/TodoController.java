package at.porscheinformatik.tutorial.todo.web;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import at.porscheinformatik.tutorial.todo.Todo;
import at.porscheinformatik.tutorial.todo.TodoService;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/todo")
public class TodoController
{
    @Inject
    private TodoService todoService;

    @RequestMapping(value = "/list", method = GET)
    @ResponseBody
    public List<Todo> listAll()
    {
        return todoService.listAll();
    }

    @RequestMapping(value = "/{id}", method = GET)
    @ResponseBody
    public Todo get(@PathVariable int id)
    {
        return todoService.get(id);
    }

    @RequestMapping(value = "/new", method = POST)
    @ResponseBody
    public Object newTodo(@RequestBody @Valid Todo todo, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new Error().addErrors(result.getAllErrors());
        }

        return todoService.addTodo(todo.title, todo.due);
    }

    @RequestMapping(value = "/{id}", method = POST)
    @ResponseBody
    public Object change(@PathVariable int id, @RequestBody @Valid Todo todo, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new Error().addErrors(result.getAllErrors());
        }

        return todoService.change(id, todo);
    }

}
