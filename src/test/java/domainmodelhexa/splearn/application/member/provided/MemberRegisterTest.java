package domainmodelhexa.splearn.application.member.provided;

import domainmodelhexa.splearn.SplearnTestConfiguration;
import domainmodelhexa.splearn.domain.MemberFixture;
import domainmodelhexa.splearn.domain.member.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {


    @Test
    void register(){
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail(){
        memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest())).isInstanceOf(DuplicateEmailException.class);

    }

    @Test
    void activate(){
        Member member = registerMember();

        member = memberRegister.activate(member.getId());

        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();

    }

    @Test
    void deactivate(){
        Member member = registerMember();

        memberRegister.activate(member.getId());

        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVE);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();

    }

    private Member registerMember() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        entityManager.flush();
        entityManager.clear();
        return member;
    }

    @Test
    void updateInfo(){
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        var request = new MemberInfoUpdateRequest("Jang22", "jaein100", "자기소개");

        member = memberRegister.updateInfo(member.getId(), request);

        assertThat(member.getNickname()).isEqualTo(request.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(request.profileAddress());
    }

    @Test
    void memberRegisterRequestFail(){
        checkValidation(new MemberRegisterRequest("jaess@Email.com", "jaes", "longsecret"));
        checkValidation(new MemberRegisterRequest("jaess@Email.com", "jaesasdasdasdasdasdasdasdasdasasdasd", "longsecret"));
        checkValidation(new MemberRegisterRequest("jaess", "jaesasdasdasdasdasdasdasdasdasasdasd", "longsecret"));
    }

    private void checkValidation(MemberRegisterRequest memberRegisterRequest) {
        assertThatThrownBy(() -> memberRegister.register(memberRegisterRequest))
        .isInstanceOf(ConstraintViolationException.class);
    }
}
