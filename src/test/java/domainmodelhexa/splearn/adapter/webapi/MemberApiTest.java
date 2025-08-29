package domainmodelhexa.splearn.adapter.webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domainmodelhexa.AssertThatUtils;
import domainmodelhexa.splearn.adapter.webapi.dto.MemberRegisterResponse;
import domainmodelhexa.splearn.application.member.provided.MemberRegister;
import domainmodelhexa.splearn.application.member.required.MemberRepository;
import domainmodelhexa.splearn.domain.MemberFixture;
import domainmodelhexa.splearn.domain.member.Member;
import domainmodelhexa.splearn.domain.member.MemberRegisterRequest;
import domainmodelhexa.splearn.domain.member.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

import static domainmodelhexa.AssertThatUtils.equalsTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@RequiredArgsConstructor
public class MemberApiTest {

    final MockMvcTester mockMvcTester;
    final ObjectMapper objectMapper;
    final MemberRepository memberRepository;
    final MemberRegister memberRegister;

    @Test
    void register() throws JsonProcessingException, UnsupportedEncodingException {
        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mockMvcTester.post().uri("/api/members") //mockMvcTester 를 통해 쉽게 assertThat 으로 가능
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson).exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()//json 으로 변경
                .hasPathSatisfying("$.memberId", AssertThatUtils.notNull())
                .hasPathSatisfying("$.email", equalsTo(request));


        MemberRegisterResponse response =
                objectMapper.readValue(result.getResponse().getContentAsString(), MemberRegisterResponse.class);


        Member member = memberRepository.findById(response.memberId()).orElseThrow();

        assertThat(member.getEmail().address()).isEqualTo(request.email());
        assertThat(member.getNickname()).isEqualTo(request.nickname());
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

    }

    @Test
    void duplicateEmail() throws JsonProcessingException {
        memberRegister.register(MemberFixture.createMemberRegisterRequest());

        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mockMvcTester.post().uri("/api/members") //mockMvcTester 를 통해 쉽게 assertThat 으로 가능
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson).exchange();

        assertThat(result)
                .apply(print())// 성공, 실패에 관계 없이 결과를 출력해줌.
                .hasStatus(HttpStatus.CONFLICT);
    }




}
