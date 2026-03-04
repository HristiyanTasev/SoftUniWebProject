package bg.softuni.eliteSportsEquipment.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ResourceNotFoundException.class,
            NoResourceFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException(
            Exception ex,
            Model model,
            HttpServletRequest request) {

        logger.warn("Resource not found: {}", ex.getMessage());

        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("status", 404);
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("path", request.getRequestURI());
        return "error/404";
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(
            BadRequestException ex,
            Model model,
            HttpServletRequest request) {

        logger.warn("Bad request: {}", ex.getMessage());

        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("status", 400);
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("path", request.getRequestURI());

        return "error/400";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex) {

        logger.error("Unexpected error occurred", ex);

        return "error/500";
    }
}
