package kr.megaptera.makaobank.controllers;

import kr.megaptera.makaobank.exceptions.*;
import kr.megaptera.makaobank.models.*;
import kr.megaptera.makaobank.services.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;


  @Test
  void account() throws Exception {
    given(accountService.detail(any())).willReturn(Account.fake("1234"));


    mockMvc.perform(MockMvcRequestBuilders.get("/accounts/me"))
        .andExpect(status().isOk())
        .andExpect(content().string(
            containsString("\"accountNumber\":\"1234\"")
        ));
  }

  @Test
  void accountNotFound() throws Exception {
    given(accountService.detail(any())).willThrow(new AccountNotFound("1234"));


    mockMvc.perform(MockMvcRequestBuilders.get("/accounts/me"))
        .andExpect(status().isNotFound());
  }
}