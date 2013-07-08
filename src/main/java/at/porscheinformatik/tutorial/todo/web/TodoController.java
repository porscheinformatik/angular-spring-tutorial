package at.porscheinformatik.tutorial.todo.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.porscheinformatik.tutorial.todo.Todo;
import at.porscheinformatik.tutorial.todo.TodoService;

@Controller
public class TodoController
{
    @Inject
    private TodoService todoService;

    @RequestMapping("/todo/list")
    @ResponseBody
    public List<Todo> listAll()
    {
        return todoService.listAll();
    }

    @RequestMapping(value = "/todo/new", method = RequestMethod.POST)
    @ResponseBody
    public Todo newTodo(@RequestBody Todo todo)
    {
        return todoService.addTodo(todo.title);
    }
}
