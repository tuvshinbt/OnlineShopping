package edu.mum.cs490.project.mock.transaction.api.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import javax.ws.rs.core.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
public class TransactionControllerTest {
    private static final String AUTH = "Authorization";
    private static final String BASIC = "Basic dXNlcjpwYXNz";
    private static final String URL = "/mock/transaction/api";

    @Autowired
    private MockMvc mvc;

    @Autowired
    @Qualifier("apiAES")
    private AES aes;

    @Test
    public void testUnauthorizated() throws Exception {
        System.out.println("Testing testUnauthorizated() header authentication");
        this.mvc.perform(MockMvcRequestBuilders.post(URL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401))
                .andExpect(content().string(""));
    }

    @Test
    public void testAuthorizated() throws Exception {
        System.out.println("Testing testAuthorizated() header authentication");
        String jsonStr = "{\"txnId\":\"1525902473171\",\"srcCardNo\":\"4929127657563699\",\"expirationDate\":\"05/2018\",\"nameOnCard\":\"YEE RICK\",\"zipCode\":\"52557\",\"cardType\":\"VISA\",\"amount\":2000.0,\"dstCardNo\":\"4000300020001000\",\"cvv\":\"123\"}";
        String encrypedData = aes.encrypt(jsonStr);
        System.out.println(encrypedData);
        this.mvc.perform(MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTH, BASIC)
                .content(encrypedData))
                .andExpect(status().isOk());
    }

    @Test
    public void testWithoutSrcAccount() throws Exception {
        System.out.println("Testing testWithoutSrcAccount()");
        String jsonStr = "{\"txnId\":\"1525902473171\",\"expirationDate\":\"05/2018\",\"nameOnCard\":\"YEE RICK\",\"zipCode\":\"52557\",\"cardType\":\"VISA\",\"amount\":200.0,\"dstCardNo\":\"4000300020001000\",\"cvv\":\"123\"}";
        String encrypedData = aes.encrypt(jsonStr);
        System.out.println(encrypedData);
        this.mvc.perform(MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTH, BASIC)
                .content(encrypedData))
                .andExpect(status().isOk())
                // 400
                .andExpect(content().string("sxIouSXU3uVAyleMuMzgxA=="));
    }

    @Test
    public void testEmptySrcAccount() throws Exception {
        System.out.println("Testing testEmptySrcAccount()");
        String jsonStr = "{\"txnId\":\"1525902473171\",\"srcCardNo\":\"\",\"expirationDate\":\"05/2018\",\"nameOnCard\":\"YEE RICK\",\"zipCode\":\"52557\",\"cardType\":\"VISA\",\"amount\":20000.0,\"dstCardNo\":\"4000300020001000\",\"cvv\":\"123\"}";
        String encrypedData = aes.encrypt(jsonStr);
        System.out.println(encrypedData);
        this.mvc.perform(MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTH, BASIC)
                .content(encrypedData))
                .andExpect(status().isOk())
                // 400
                .andExpect(content().string("sxIouSXU3uVAyleMuMzgxA=="));
    }

    @Test
    public void testNotFoundSrcAccount() throws Exception {
        System.out.println("Testing testNotFoundSrcAccount()");
        String jsonStr = "{\"txnId\":\"1525902473171\",\"srcCardNo\":\"4929000000000000\",\"expirationDate\":\"05/2018\",\"nameOnCard\":\"YEE RICK\",\"zipCode\":\"52557\",\"cardType\":\"VISA\",\"amount\":20000.0,\"dstCardNo\":\"4000300020001000\",\"cvv\":\"123\"}";
        String encrypedData = aes.encrypt(jsonStr);
        System.out.println(encrypedData);
        this.mvc.perform(MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTH, BASIC)
                .content(encrypedData))
                .andExpect(status().isOk())
                // 2
                .andExpect(content().string("wkRFjk7nC0mNnaXaXViMzA=="));
    }

    @Test
    public void testSuccess() throws Exception {
        System.out.println("Testing testSuccess()");
        String jsonStr = "{\"txnId\":\"1525902473171\",\"srcCardNo\":\"4929127657563699\",\"expirationDate\":\"05/2018\",\"nameOnCard\":\"YEE RICK\",\"zipCode\":\"52557\",\"cardType\":\"VISA\",\"amount\":2000.0,\"dstCardNo\":\"4000300020001000\",\"cvv\":\"123\"}";
        String encrypedData = aes.encrypt(jsonStr);
        System.out.println(encrypedData);
        this.mvc.perform(MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTH, BASIC)
                .content(encrypedData))
                .andExpect(status().isOk())
                // 1
                .andExpect(content().string("xtIwuiMndZeTaUgq6QC7Og=="));
    }

    @Test
    public void testNotEnoughAmountSrcAccount() throws Exception {
        System.out.println("Testing testNotEnoughSrcAccount()");
        String jsonStr = "{\"txnId\":\"1525902473171\",\"srcCardNo\":\"4929127657563699\",\"expirationDate\":\"05/2018\",\"nameOnCard\":\"YEE RICK\",\"zipCode\":\"52557\",\"cardType\":\"VISA\",\"amount\":200000.0,\"dstCardNo\":\"4000300020001000\",\"cvv\":\"123\"}";
        String encrypedData = aes.encrypt(jsonStr);
        System.out.println(encrypedData);
        this.mvc.perform(MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTH, BASIC)
                .content(encrypedData))
                .andExpect(status().isOk())
                // 3
                .andExpect(content().string("T0+JzonrVFAaAGE3OqmKCw=="));
    }
}
