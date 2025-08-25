package domainmodelhexa.splearn;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class SplearnApplicationTest {

    @Test
    void run(){
        //static close 해줘야된다..
        try(MockedStatic<SpringApplication> mock = Mockito.mockStatic(SpringApplication.class)){
            SplearnApplication.main(new String[0]);
            mock.verify(() -> SpringApplication.run(SplearnApplication.class, new String[0]));
        }
    }
}