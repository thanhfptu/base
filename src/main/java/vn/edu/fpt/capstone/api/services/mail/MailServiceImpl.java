package vn.edu.fpt.capstone.api.services.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import java.util.List;

@RequiredArgsConstructor
@Service(MailService.SERVICE_NAME)
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public void send(List<String> receivers, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receivers.toArray(new String[0]));
        message.setSubject(subject);
        message.setText(content);
        message.setSentDate(DateUtils.now());
        mailSender.send(message);
    }

    @Override
    public void send(String receiver, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(content);
        message.setSentDate(DateUtils.now());
        mailSender.send(message);
    }

}
