package domainmodelhexa.splearn.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfileTest {

    @Test
    void profile(){
        new Profile("jaess");
        new Profile("jaess12312");
        new Profile("");
    }

    @Test
    void profileFail(){
        assertThatThrownBy(() -> new Profile("longcharactertest123123")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("프로필")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url(){
        var profile = new Profile("jaess");

        assertThat(profile.url()).isEqualTo("@jaess");
    }
}