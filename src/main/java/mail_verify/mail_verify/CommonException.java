package mail_verify.mail_verify;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommonException extends RuntimeException{

    private final CommonErrorResult commonErrorResult;
}