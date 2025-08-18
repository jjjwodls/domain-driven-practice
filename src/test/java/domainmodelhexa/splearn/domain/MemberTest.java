package domainmodelhexa.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class MemberTest {

    Member member;

    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };

        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("email@splearn.com", "jaess", "secret");

        member = Member.create(memberCreateRequest, passwordEncoder);
    }

    @Test
    void createMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

//    spot bug 에서 예외나므로 주석
//    @Test
//    void constructorNullCheck(){
//        assertThatThrownBy(() -> Member.create(null,"jaess","secret",passwordEncoder))
//                .isInstanceOf(NullPointerException.class);
//    }

    @Test
    void activate(){
        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail(){
        member.activate();

        assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate(){
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVE);
    }

    @Test
    void deactivateFail(){
        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void verifyPassword(){
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }

    @Test
    void changeNickname(){
        assertThat(member.getNickname()).isEqualTo("jaess");

        member.changeNickname("jaein");

        assertThat(member.getNickname()).isEqualTo("jaein");
    }

    @Test
    void changePassword(){
        member.changePassword("verySecret",passwordEncoder);

        assertThat(member.verifyPassword("verySecret",passwordEncoder)).isTrue();
    }

    @Test
    void shouldBeActive(){
        assertThat(member.isActive()).isFalse();

        member.activate();

        assertThat(member.isActive()).isTrue();

        member.deactivate();

        assertThat(member.isActive()).isFalse();
    }

    @Test
    void invalidEmail(){
        assertThatThrownBy(() -> Member.create(new MemberCreateRequest("invalid email", "jaess", "secret"), passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);

        Member.create(new MemberCreateRequest("jaess@gmail.com", "jaess", "secret"), passwordEncoder);

    }
}