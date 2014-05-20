package at.porscheinformatik.tutorial.todo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

class Error
{
    public List<String> errors = new ArrayList<String>();

    void addError(String error)
    {
        errors.add(error);
    }

    Error addErrors(MessageSource messageSource, Locale locale, List<ObjectError> allErrors)
    {
        for (ObjectError error : allErrors)
        {
            if(error instanceof FieldError)
            {
                addError(((FieldError) error).getField() + ": " + messageSource.getMessage(error, LocaleContextHolder.getLocale()));
            }
            else
            {
                addError(messageSource.getMessage(error, LocaleContextHolder.getLocale()));
            }
            
        }
        return this;
    }
}
