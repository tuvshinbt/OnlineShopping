package edu.mum.cs490.project.mock.transaction.api.util;

import edu.mum.cs490.project.mock.transaction.api.bean.InitBean;
import edu.mum.cs490.project.mock.transaction.api.util.AES;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AESTest {

    @Autowired
    @Qualifier("apiAES")
    private AES aes;

    @Test
    public void encryptValue1() {
        System.out.println("Testing encryptValue1() " + aes.getSecretKeyWord());
        assertEquals("xtIwuiMndZeTaUgq6QC7Og==", aes.encrypt("1"));
    }

    @Test
    public void encryptValue2() {
        System.out.println("Testing encryptValue2() " + aes.getSecretKeyWord());
        assertEquals("wkRFjk7nC0mNnaXaXViMzA==", aes.encrypt("2"));
    }

    @Test
    public void encryptValue3() {
        System.out.println("Testing encryptValue3() " + aes.getSecretKeyWord());
        assertEquals("T0+JzonrVFAaAGE3OqmKCw==", aes.encrypt("3"));
    }

    @Test
    public void encryptValue400() {
        System.out.println("Testing encryptValue400() " + aes.getSecretKeyWord());
        assertEquals("sxIouSXU3uVAyleMuMzgxA==", aes.encrypt("400"));
    }

    @Test
    public void encryptJsonData() {
        System.out.println("Testing encryptJsonData() " + aes.getSecretKeyWord());
        assertEquals("cjDqlhaq3X/L6IvCGzHwFB8OBblSbotFmKZJQWE+fB0hm8KyahCd9s8DQtlL8GVBz7pQCzjKT+PtVbD7xRI3NziwYC/615DJ36cG4A3ODRZvgJr3ehQ55p30mkeEdSup8iCth9t8Cq16cjZZJrDDVkpwzlxjJbeLV1xv9Uy2ICxjcBbB+p109reOQlVX/LZ1a5VUfMMdg5CxKa/pxxUO22DgKATff5quAX2FHPWiIWk=",
                aes.encrypt("{\"txnId\":\"000\",\"srcCardNo\":\"4929127657563699\",\"expirationDate\":\"05/2020\",\"nameOnCard\":\"TEST\",\"zipCode\":\"52557\",\"amount\":100,\"dstCardNo\":\"4000300020001000\",\"cvv\":\"123\"}"));
    }

    @Test
    public void decryptValue1() {
        System.out.println("Testing decryptValue1() " + aes.getSecretKeyWord());
        assertEquals("1", aes.decrypt("xtIwuiMndZeTaUgq6QC7Og=="));
    }

    @Test
    public void decryptJsonData() {
        System.out.println("Testing decryptJsonData() " + aes.getSecretKeyWord());
        assertEquals("{\"txnId\":\"000\",\"srcCardNo\":\"4929127657563699\",\"expirationDate\":\"05/2020\",\"nameOnCard\":\"TEST\",\"zipCode\":\"52557\",\"amount\":100,\"dstCardNo\":\"4000300020001000\",\"cvv\":\"123\"}",
                aes.decrypt("cjDqlhaq3X/L6IvCGzHwFB8OBblSbotFmKZJQWE+fB0hm8KyahCd9s8DQtlL8GVBz7pQCzjKT+PtVbD7xRI3NziwYC/615DJ36cG4A3ODRZvgJr3ehQ55p30mkeEdSup8iCth9t8Cq16cjZZJrDDVkpwzlxjJbeLV1xv9Uy2ICxjcBbB+p109reOQlVX/LZ1a5VUfMMdg5CxKa/pxxUO22DgKATff5quAX2FHPWiIWk="));
    }
}