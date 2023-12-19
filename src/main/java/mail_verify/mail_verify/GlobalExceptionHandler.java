package mail_verify.mail_verify;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final CommonErrorResult errorResult) {
        return ResponseEntity.status(errorResult.getHttpStatus())
                .body(new ErrorResponse(errorResult.name(), errorResult.getMessage()));
    }

    @ExceptionHandler({CommonException.class})
    public ResponseEntity<ErrorResponse> handleException(final CommonException exception) {
        log.warn("Exception occur: ", exception);
        return this.makeErrorResponseEntity(exception.getCommonErrorResult());
    }

    @Getter
    @RequiredArgsConstructor
    static class ErrorResponse {
        private final String code;
        private final String message;
    }
}
