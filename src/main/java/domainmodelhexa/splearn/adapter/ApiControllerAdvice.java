package domainmodelhexa.splearn.adapter;

import domainmodelhexa.splearn.domain.member.DuplicateEmailException;
import domainmodelhexa.splearn.domain.member.DuplicateProfileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler({DuplicateEmailException.class, DuplicateProfileException.class})
    public ProblemDetail emailExceptionHandler(DuplicateEmailException exception) {
        return getProblemDetail(HttpStatus.CONFLICT, exception);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exception) {
        return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }


    /**
     * RFC9457 에서 정의된 ProblemDetail 로 Spring 6 이후에 추가됨, http 응답코드로 모든 서버의 예외 상황을 다 나타내기 어려움
     * 그래서 {status : , error, data : .. } 로 사용했는데, 6 버전 이후에는 실패 했을 때는 ProblemDetail 의 정의된 스펙에 따라 정의해서 리턴하자.
     * Body = {"type":"about:blank","title":"Conflict","status":409,"detail":"이미 사용중인 이메일 입니다 : email@splearn.com","instance":"/api/members"} 으로 리턴되며
     * type : 링크 형식으로 제공되며 어떤 예외에 대한 정보가 담긴 링크 정보임.
     * title : 응답 코드에 대한 설명이 들어가고 바꿀 수 있음.
     * detail : 예외 메시지
     * instance : api 실행에 사용된 url 이 들어감.
     * @param exception
     * @return ProblemDetail
     */
    private static ProblemDetail getProblemDetail(HttpStatus status, Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        //custom exception properties
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("exception", exception.getClass().getSimpleName());
        return problemDetail;
    }
}
