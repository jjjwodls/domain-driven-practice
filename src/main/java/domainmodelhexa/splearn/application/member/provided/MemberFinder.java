package domainmodelhexa.splearn.application.member.provided;

import domainmodelhexa.splearn.domain.member.Member;

/**
 * 회원을 조회 한다.
 */
public interface MemberFinder {

    Member find(Long memberId);
}
