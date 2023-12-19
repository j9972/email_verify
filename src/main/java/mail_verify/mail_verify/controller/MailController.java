package mail_verify.mail_verify.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mail_verify.mail_verify.Result;
import mail_verify.mail_verify.service.UserMailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor // 생성자 DI
@RequestMapping("/auth/mail")
public class MailController {

    private final UserMailService userMailService;

    @PostMapping("/send")
    public ResponseEntity<Result> authMail(@RequestBody String email) {

        userMailService.sendCode(email);

        return ResponseEntity.ok(Result.of("이메일이 성공적으로 보내졌습니다."));
    }

    //이메일 코드검증  -> accessToken 필요
    @GetMapping("/verify")
    public ResponseEntity<Result> validMailCode(@RequestParam("email") String email,
                                                @RequestParam("authCode") String authCode) throws IOException {

        boolean verify = userMailService.verify(email, authCode);

        return ResponseEntity.ok(Result.of(verify ? "검증이 성공했습니다" : "검증이 실패했습니다"));

    }
}

