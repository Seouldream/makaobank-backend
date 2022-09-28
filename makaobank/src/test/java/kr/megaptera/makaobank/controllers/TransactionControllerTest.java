package kr.megaptera.makaobank.controllers;

import kr.megaptera.makaobank.exceptions.*;
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
import static org.mockito.Mockito.verify;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TransferService transferService;

  @Test
  void transfer() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"to\":\"5678\"," +
                "\"amount\":\"100000\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isCreated());

    verify(transferService).transfer("1234", "5678", 100_000L);
  }

  @Test
  void transferWithIncorrectAccountNumber() throws Exception {
    String accountNumber = "5678";
    given(transferService.transfer(any(), any(), any()))
        .willThrow(new AccountNotFound(accountNumber));

    mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"to\":\"" + accountNumber + "\"," +
                "\"amount\":100000" +
                "}")
        )
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("\"code\":1001")
        ));
  }

  @Test
  void transferWithIncorrectAmount() throws Exception {
    Long amount = 100_000L;
    given(transferService.transfer(any(), any(), any()))
        .willThrow(new IncorrectAmount(amount));

    mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"to\":\"5678\"," +
                "\"amount\":" + amount +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("\"code\":1002")
        ));
  }
}