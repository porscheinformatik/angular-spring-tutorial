package at.porscheinformatik.tutorial.todo.web;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.validation.Valid;

import org.jsondoc.core.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.porscheinformatik.tutorial.todo.Todo;
import at.porscheinformatik.tutorial.todo.TodoService;

@Api(name = "Todo Resource", description = "API for mananging Todo items.")
@ApiVersion(since = "1.0.0")
@ApiAuthBasic(roles = "USER", testusers = @ApiAuthBasicUser(username = "user", password = "user"))
@RestController
@RequestMapping(value = "/api/todos", produces = APPLICATION_JSON_VALUE)
public class TodoController
{
    @Autowired
    private TodoService todoService;

    @ApiMethod(description = "A list of all todos")
    @ApiResponseObject
    @RequestMapping(method = GET)
    public Iterable<Todo> listAll()
    {
        return todoService.listAll();
    }

    @ApiMethod(description = "Get a single todo")
    @ApiResponseObject
    @RequestMapping(value = "/{id}", method = GET)
    public Todo get(@ApiPathParam(name = "id", description = "the id of the todo object") @PathVariable("id") int id)
    {
        return todoService.get(id);
    }

    @ApiMethod(description = "Create a new todo entry")
    @ApiResponseObject
    @RequestMapping(method = POST)
    public Todo newTodo(@ApiBodyObject @RequestBody @Valid Todo todo)
    {
        return todoService.addTodo(todo.title, todo.due);
    }

    @ApiMethod(description = "Change an existing todo entry")
    @ApiResponseObject
    @RequestMapping(value = "/{id}", method = {POST, PUT})
    public Todo change(@PathVariable("id") int id, @ApiBodyObject @RequestBody @Valid Todo todo)
    {
        return todoService.change(id, todo);
    }
}
