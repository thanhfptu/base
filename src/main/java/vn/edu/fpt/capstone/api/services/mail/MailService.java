package vn.edu.fpt.capstone.api.services.mail;

import java.util.List;

public interface MailService {

    String SERVICE_NAME = "MailService";

    void send(List<String> receivers, String subject, String content);

    void send(String receiver, String subject, String content);

}
