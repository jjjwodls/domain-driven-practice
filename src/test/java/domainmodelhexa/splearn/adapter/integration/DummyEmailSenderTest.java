package domainmodelhexa.splearn.adapter.integration;

import domainmodelhexa.splearn.domain.shared.Email;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

import static org.assertj.core.api.Assertions.assertThat;

class DummyEmailSenderTest {

    @Test
    @StdIo
    void dummyEmailSender(StdOut out){
        DummyEmailSender dummyEmailSender = new DummyEmailSender();

        dummyEmailSender.send(new Email("jaess@splearn.app"),"subject","body");

        assertThat(out.capturedLines()[0]).isEqualTo("DummyEmailSender = Email[address=jaess@splearn.app]");
    }


}