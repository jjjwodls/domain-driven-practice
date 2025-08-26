package domainmodelhexa.splearn.domain;

import domainmodelhexa.splearn.domain.member.Member;
import domainmodelhexa.splearn.domain.member.MemberInfoUpdateRequest;
import domainmodelhexa.splearn.domain.member.MemberStatus;
import domainmodelhexa.splearn.domain.member.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static domainmodelhexa.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static domainmodelhexa.splearn.domain.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class MemberTest {

    Member member;

    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        this.passwordEncoder = createPasswordEncoder();

        member = Member.register(createMemberRegisterRequest(), passwordEncoder);
    }



    @Test
    void registerMember() {

        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(member.getDetail().getRegisteredAt()).isNotNull();
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
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
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
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
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
        assertThat(member.verifyPassword("secret_long", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello_long", passwordEncoder)).isFalse();
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
        assertThatThrownBy(() -> Member.register(createMemberRegisterRequest("invalid email"), passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRegisterRequest(), passwordEncoder);

    }
    
    @Test
    void updateInfo(){
        member.activate();

        var request = new MemberInfoUpdateRequest("Jang", "jaein100", "자기소개");
        
        member.updateInfo(request);
        
        assertThat(member.getNickname()).isEqualTo(request.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(request.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(request.introduction());
    }

}