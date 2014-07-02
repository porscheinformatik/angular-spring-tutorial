package at.porscheinformatik.tutorial.todo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TodoControllerAdvice
{
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException pe)
    {
        List<String> errors = tranformObjectErrors(messageSource, pe.getBindingResult().getAllErrors());
        return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
    }

    private static List<String> tranformObjectErrors(MessageSource messageSource, List<ObjectError> objectErrors)
    {
        Locale locale = LocaleContextHolder.getLocale();
        List<String> errors = new ArrayList<String>();
        for (ObjectError objectError : objectErrors)
        {
            if (objectError instanceof FieldError)
            {
                String fieldLabelKey = objectError.getObjectName() + "." + ((FieldError) objectError).getField();
                errors.add(messageSource.getMessage(fieldLabelKey, null, locale) + ": "
                    + messageSource.getMessage(objectError, locale));
            }
            else
            {
                errors.add(messageSource.getMessage(objectError, locale));
            }
        }
        return errors;
    }
}
