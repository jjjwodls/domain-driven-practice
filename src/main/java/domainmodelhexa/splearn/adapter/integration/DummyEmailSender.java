package domainmodelhexa.splearn.adapter.integration;

import domainmodelhexa.splearn.application.member.required.EmailSender;
import domainmodelhexa.splearn.domain.shared.Email;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

@Component
@Fallback //다른 Bean 을 찾다가 없으면 해당 bean 을 사용해줘 -> bean 이 여러개인 경우 존재하므로.
//primary 어노테이션과 유사하다.
public class DummyEmailSender implements EmailSender {

    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender = " + email);
    }
}
