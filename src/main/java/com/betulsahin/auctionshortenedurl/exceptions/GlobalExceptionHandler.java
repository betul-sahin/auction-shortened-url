package com.betulsahin.auctionshortenedurl.exceptions;

import com.betulsahin.auctionshortenedurl.dtos.AppErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public AppErrorResponse handleValidationException(MethodArgumentNotValidException ex){
        StringBuilder stringBuilderErrorMessages = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            stringBuilderErrorMessages.append(errorMessage);
        });

        AppErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST,
                stringBuilderErrorMessages.toString());

        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserIsAlreadyExistException.class})
    @ResponseBody
    public AppErrorResponse handleException(UserIsAlreadyExistException ex){
        AppErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());

        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserUrlNotFoundException.class})
    @ResponseBody
    public AppErrorResponse handleException(UserUrlNotFoundException ex){
        AppErrorResponse response = prepareErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());

        return response;
    }

    private AppErrorResponse prepareErrorResponse(HttpStatus badRequest, String exceptionMessage) {
        AppErrorResponse response = new AppErrorResponse();
        response.setStatus(badRequest.value());
        response.setMessage(exceptionMessage);
        response.setTimestamp(System.currentTimeMillis());

        return response;
    }
}
