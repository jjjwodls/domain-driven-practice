package domainmodelhexa.splearn.application.member.provided;

import domainmodelhexa.splearn.domain.member.Member;
import domainmodelhexa.splearn.domain.member.MemberInfoUpdateRequest;
import domainmodelhexa.splearn.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;

/**
 * 회원의 등록과 관련된 기능을 제공한다.
 */
public interface MemberRegister {

    Member register(@Valid MemberRegisterRequest registerRequest);

    Member activate(Long memberId);

    Member deactivate(Long memberId);

    Member updateInfo(Long memberId, @Valid MemberInfoUpdateRequest memberInfoUpdateRequest);
}