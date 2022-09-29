package kr.megaptera.makaobank.controllers;

import kr.megaptera.makaobank.exceptions.*;
import kr.megaptera.makaobank.models.*;
import kr.megaptera.makaobank.services.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessionController.class)
class SessionControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  private LoginService loginService;

  @BeforeEach
  void setUp() {
    Account account = Account.fake("1234");
    given(loginService.login("1234","password"))
        .willReturn(account);
    given(loginService.login("1234","xxx"))
        .willThrow(new LoginFailed());
  }

  @Test
  void loginSuccess() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/session")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"accountNumber\":\"1234\"," +
                "\"password\":\"password\"" +
                "}"))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("\"amount\":")
        ));
  }

  @Test
  void loginFailed() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/session")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"accountNumber\":\"1234\"," +
                "\"password\":\"xxx\"" +
                "}"))
        .andExpect(status().isBadRequest());
  }
}