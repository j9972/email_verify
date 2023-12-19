package mail_verify.mail_verify.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mail_verify.mail_verify.CommonErrorResult;
import mail_verify.mail_verify.CommonException;
import mail_verify.mail_verify.EmailValidator;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;
    private final EmailValidator validator;

    public void sendEmail(String toEmail,
                          String title,
                          String text) throws MailException {

        if (!validator.test(toEmail)) {
            throw new CommonException(CommonErrorResult.WRONG_VALIDATE_EMAIL);
        }

        SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);

        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            throw new CommonException(CommonErrorResult.UNABLE_TO_SEND_EMAIL);
        }
    }

    // 발신할 이메일 데이터 세팅
    private SimpleMailMessage createEmailForm(String toEmail,
                                              String title,
                                              String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }
}