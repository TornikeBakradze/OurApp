package ge.ourApp.controller;

import ge.ourApp.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    @ResponseBody
    public ResponseEntity<String> handleUserNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity
                .status(404)
                .body(ex.getMessage());
    }


    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<String> handleException(AppException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.getMessage());
    }
}
