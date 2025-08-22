package domainmodelhexa.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {

    //record 를 통해 값을 비교하면 동등성이 지켜지도록 되어 있음.
    @Test
    void equality(){
        var email1 = new Email("jaess@spearn.app");
        var email2 = new Email("jaess@spearn.app");

        assertThat(email1).isEqualTo(email2);
    }

}