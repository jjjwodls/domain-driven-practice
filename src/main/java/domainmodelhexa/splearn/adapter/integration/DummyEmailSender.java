package domainmodelhexa.splearn.adapter.integration;

import domainmodelhexa.splearn.application.required.EmailSender;
import domainmodelhexa.splearn.domain.Email;
import org.springframework.stereotype.Component;

@Component
public class DummyEmailSender implements EmailSender {

    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender = " + email);
    }
}
