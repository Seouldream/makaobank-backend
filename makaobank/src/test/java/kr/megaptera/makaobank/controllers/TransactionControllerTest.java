package kr.megaptera.makaobank.controllers;

import kr.megaptera.makaobank.exceptions.*;
import kr.megaptera.makaobank.models.*;
import kr.megaptera.makaobank.services.*;
import kr.megaptera.makaobank.utils.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TransactionService transactionService;

  @MockBean
  private TransferService transferService;

  @SpyBean
  private JwtUtil jwtUtil;

  @Test
  void list() throws Exception {
    String token = jwtUtil.encode(new AccountNumber("1234"));

    AccountNumber accountNumber = new AccountNumber("1234");

    Transaction transaction = mock(Transaction.class);

    given(transactionService.list(accountNumber, 1))
        .willReturn(List.of(transaction));

    mockMvc.perform(MockMvcRequestBuilders.get("/transactions")
            .header("Authorization","Bearer " + token)
        )
        .andExpect(status().isOk())
        .andExpect(content().string(
            containsString("\"transactions\":[")
        ));

    verify(transactionService).list(accountNumber, 1);
  }

  @Test
  void transfer() throws Exception {
    String token = jwtUtil.encode(new AccountNumber("1234"));

    String name = "Test";
    AccountNumber sender = new AccountNumber("1234");
    AccountNumber receiver = new AccountNumber("5678");

    mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .header("Authorization","Bearer " + token)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"to\":\"" + receiver.value() + "\"," +
                "\"amount\":100000," +
                "\"name\":\"" + name + "\"" +
                "}"))
        .andExpect(status().isCreated());

    verify(transferService).transfer(sender, receiver, 100_000L, name);
  }

  @Test
  void transferWithIncorrectAccountNumber() throws Exception {
    String token = jwtUtil.encode(new AccountNumber("1234"));

    AccountNumber accountNumber = new AccountNumber("1234");
    given(transferService.transfer(any(), any(), any(), any()))
        .willThrow(new AccountNotFound(accountNumber));

    mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .header("Authorization","Bearer " + token)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"to\":\"" + accountNumber.value() + "\"," +
                "\"amount\":100000," +
                "\"name\":\"Test\"" +
                "}")
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().string(
            containsString("\"code\":1001")
        ));
  }

  @Test
  void transferWithIncorrectAmount() throws Exception {
    String token = jwtUtil.encode(new AccountNumber("1234"));

    Long amount = 100_000L;
    given(transferService.transfer(any(), any(), any(), any()))
        .willThrow(new IncorrectAmount(amount));

    mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .header("Authorization","Bearer " + token)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"to\":\"5678\"," +
                "\"amount\":" + amount + "," +
                "\"name\":\"Test\"" +
                "}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(
            containsString("\"code\":1002")
        ));
  }
}