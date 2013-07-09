package at.porscheinformatik.tutorial.todo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.ObjectError;

class Error
{
    public List<String> errors = new ArrayList<String>();

    void addError(String error)
    {
        errors.add(error);
    }

    Error addErrors(List<ObjectError> objectErrors)
    {
        for (ObjectError error : objectErrors)
        {
            errors.add(error.getDefaultMessage());
        }
        return this;
    }
}
