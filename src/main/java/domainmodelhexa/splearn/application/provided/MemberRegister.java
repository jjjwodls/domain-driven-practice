package domainmodelhexa.splearn.application.provided;

import domainmodelhexa.splearn.domain.Member;
import domainmodelhexa.splearn.domain.MemberRegisterRequest;
import jakarta.validation.Valid;

/**
 * 회원의 등록과 관련된 기능을 제공한다.
 */
public interface MemberRegister {

    Member register(@Valid MemberRegisterRequest registerRequest);

    Member activate(Long memberId);
}