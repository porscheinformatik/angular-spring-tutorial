package at.porscheinformatik.tutorial.todo.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

/**
 * Controller serving all static files.
 */
@Controller
public class StaticController
{
    /**
     * "/" is mapped to "index"
     * 
     * @param model .
     * @param request .
     * @return .
     */
    @RequestMapping(value = "/", method = GET)
    public String index(Map<String, Object> model, WebRequest request)
    {
        return anyPage("index", model, request);
    }

    /**
     * Any {page}.html is mapped to its corresponding template
     * 
     * @param page .
     * @param model .
     * @param request .
     * @return .
     */
    @RequestMapping(value = "/{page}.html", method = GET)
    public String anyPage(@PathVariable String page, Map<String, Object> model, WebRequest request)
    {
        model.put("error", request.getParameter("error") != null);
        return page;
    }
}
