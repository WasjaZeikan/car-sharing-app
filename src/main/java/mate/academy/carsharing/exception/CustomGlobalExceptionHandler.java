package mate.academy.carsharing.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatusCode statusCode,
                                                             WebRequest request) {
        Map<String,Object> errorBody = getDefaultErrorBody(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorBody, headers, statusCode);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleDefaultException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, null,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Map<String,Object> errorBody = getDefaultErrorBody(ex, request, HttpStatus.BAD_REQUEST);
        List<Object> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .toList();
        errorBody.put("errors", errors);
        return new ResponseEntity<>(errorBody, headers, status);
    }

    private Object getErrorMessage(ObjectError error) {
        if (error instanceof FieldError) {
            String fieldName = ((FieldError) error).getField();
            return fieldName + " " + error.getDefaultMessage();
        }
        return error.getDefaultMessage();
    }

    private Map<String,Object> getDefaultErrorBody(Exception ex,
                                                   WebRequest request,
                                                   HttpStatus httpStatus) {
        Map<String,Object> errorBody = new LinkedHashMap<>();
        errorBody.put("message", ex.getLocalizedMessage());
        errorBody.put("dateTime", LocalDateTime.now());
        errorBody.put("code", httpStatus);
        errorBody.put("path", request.getDescription(false));
        return errorBody;
    }
}
