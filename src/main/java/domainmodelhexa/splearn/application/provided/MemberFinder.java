package domainmodelhexa.splearn.application.provided;

import domainmodelhexa.splearn.domain.Member;

/**
 * 회원을 조회 한다.
 */
public interface MemberFinder {

    Member find(Long memberId);
}
