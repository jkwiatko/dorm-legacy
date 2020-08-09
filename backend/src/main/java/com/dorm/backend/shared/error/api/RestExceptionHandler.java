package com.dorm.backend.shared.error.api;

import com.dorm.backend.shared.error.exc.CannotBookOwnRoomException;
import com.dorm.backend.shared.error.exc.FileNameAlreadyTaken;
import com.dorm.backend.shared.error.exc.NoSearchTypeSpecifiedException;
import com.dorm.backend.shared.error.exc.NoSuchCityException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException exc) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(exc.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(FileNameAlreadyTaken.class)
    protected ResponseEntity<ApiError> handleFileNameAlreadyTaken(FileNameAlreadyTaken exc) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(exc.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(NoSuchCityException.class)
    protected ResponseEntity<ApiError> handleNoSuchCity(NoSuchCityException exc) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(exc.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(NoSearchTypeSpecifiedException.class)
    protected ResponseEntity<ApiError> handleNoSearchType(NoSearchTypeSpecifiedException exc) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("No search type has been specified!");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(CannotBookOwnRoomException.class)
    protected ResponseEntity<ApiError> handleCannotBookOwnRoom(CannotBookOwnRoomException exc) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Nie możesz zarezerwować swojego własnego pokoju!");
        return buildResponseEntity(apiError);
    }


    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
