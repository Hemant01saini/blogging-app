package com.blogapp.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
            UserNotFoundException.class,
            PostNotFoundException.class,
            CategoryNotFoundException.class,
            TagNotFoundException.class,
            CommentNotFoundException.class,
            MediaNotFoundException.class,
            SavedPostNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(
            RuntimeException ex,
            HttpServletRequest request ) {

        return buildError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            UsernameAlreadyExistsException.class,
            AlreadyFollowingException.class,
            AlreadyLikedException.class
    })
    public ResponseEntity<ErrorResponse> handleConflict(
            RuntimeException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException ex,
            HttpServletRequest request){

        return buildError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request
        );
    }

//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
//            UserNotFoundException ex,
//            HttpServletRequest request ) {
//
//        return buildError(
//                HttpStatus.NOT_FOUND,
//                ex.getMessage(),
//                request
//        );
//    }

//    @ExceptionHandler(EmailAlreadyExistsException.class)
//    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(
//            EmailAlreadyExistsException ex,
//            HttpServletRequest request) {
//
//        return buildError(
//                HttpStatus.CONFLICT,
//                ex.getMessage(),
//                request
//        );
//    }

//    @ExceptionHandler(UsernameAlreadyExistsException.class)
//    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(
//            UsernameAlreadyExistsException ex,
//            HttpServletRequest request) {
//
//        return buildError(
//                HttpStatus.CONFLICT,
//                ex.getMessage(),
//                request
//        );
//    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFile(
            InvalidFileException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String errorMessage = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return buildError(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                request
        );
       }

    @ExceptionHandler(AccessDeniedException.class)

    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex,
            HttpServletRequest request) {

        return buildError(
                HttpStatus.FORBIDDEN,
                "Access Denied",
                request
        );
    }


    private ResponseEntity<ErrorResponse> buildError(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ) {

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("Unexpected exception", ex);

        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong. Please try again later.",
                request
        );
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public Map<String,Object> handleResourceNotFound(ResourceNotFoundException ex){
//        Map<String,Object> map = new HashMap<>();
//        map.put("message", ex.getMessage());
//        map.put("Timestamp", LocalDateTime.now());
//        map.put("Trace", ex.getCause());
//        map.put("Status code", HttpStatus.NOT_FOUND);
//        return map;
//    }

}
