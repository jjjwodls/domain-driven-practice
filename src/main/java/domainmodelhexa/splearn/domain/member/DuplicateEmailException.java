package domainmodelhexa.splearn.domain.member;

//@ResponseStatus(HttpStatus.CONFLICT) //domain layer 에서의 코드가 adapter 에 의존하는 코드가 된다.
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
