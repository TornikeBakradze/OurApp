package ge.ourApp.controller;

import ge.ourApp.exceptions.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    @ResponseBody
    public ResponseEntity<String> handleUserNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }


    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<String> handleAppException(AppException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.getMessage());
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseBody
    public ResponseEntity<String> accessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("Data integrity exception: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<List<List<String>>> handleBindErrors(MethodArgumentNotValidException exception) {

        List<List<String>> errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    List<String> errorMap = new ArrayList<>();
                    errorMap.add(fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());

        log.error(errorList.toString());

        return ResponseEntity.badRequest().body(errorList);
    }

}
