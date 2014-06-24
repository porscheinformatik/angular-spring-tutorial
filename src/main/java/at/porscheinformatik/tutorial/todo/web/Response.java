package at.porscheinformatik.tutorial.todo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.aspectj.bridge.MessageUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * Response sent to client via API.
 */
public final class Response
{
    /** Status */
    static enum Status
    {
        OK,
        ERROR
    };

    private final Status status;
    private final Object result;
    private Iterable<String> messages;

    private Response(Status status, Iterable<String> messages, Object result)
    {
        super();
        this.status = status;
        this.messages = messages;
        this.result = result;
    }

    /**
     * @param result the result
     * @return new OK status with result
     */
    public static Response ok(Object result)
    {
        return new Response(Status.OK, null, result);
    }

    /**
     * @param messages the error messages
     * @return new ERROR status with message
     */
    public static Response error(List<String> messages)
    {
        return new Response(Status.ERROR, messages, null);
    }

    /**
     * @param messageSource message source to resolve messages
     * @param objectErrors errors from validation
     * @return new ERROR status with message
     */
    public static Response error(MessageSource messageSource, List<ObjectError> objectErrors)
    {
        return error(tranformObjectErrors(messageSource, objectErrors));
    }

    public Status getStatus()
    {
        return status;
    }

    public Object getResult()
    {
        return result;
    }

    public Iterable<String> getMessages()
    {
        return messages;
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
                errors.add(messageSource.getMessage(fieldLabelKey, null, locale) + ": " + messageSource.getMessage(objectError, locale));
            }
            else
            {
                errors.add(messageSource.getMessage(objectError, locale));
            }
        }
        return errors;
    }
}
