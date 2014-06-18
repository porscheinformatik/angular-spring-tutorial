package at.porscheinformatik.tutorial.todo.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import at.porscheinformatik.tutorial.todo.Todo;
import at.porscheinformatik.tutorial.todo.TodoService;

@Controller
@RequestMapping("/api/todo")
public class TodoController
{
    @Autowired
    private TodoService todoService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/list", method = GET)  
    @ResponseBody
    public Iterable<Todo> listAll()
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
            return new Error().addErrors(messageSource, LocaleContextHolder.getLocale(), result.getAllErrors());
        }

        return todoService.addTodo(todo.title, todo.due);
    }

    @RequestMapping(value = "/{id}", method = POST)
    @ResponseBody
    public Object change(@PathVariable int id, @RequestBody @Valid Todo todo, BindingResult result)
    {
        if (result.hasErrors())
        {
            return new Error().addErrors(messageSource, LocaleContextHolder.getLocale(), result.getAllErrors());
        }

        return todoService.change(id, todo);
    }

}
