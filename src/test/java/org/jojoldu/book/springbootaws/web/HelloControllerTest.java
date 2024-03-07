package org.jojoldu.book.springbootaws.web;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@ExtendWith(SpringExtension.class)  // juit5부터는 @RunWith(SpringRunner.class)를 사용불가
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles="USER")
    public void hello_return() throws Exception{
        String hello = "hello";
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

//    @WithMockUser(roles="USER")
//    @Test
//    public void helloDto_return() throws Exception{
//        String name = "hello";
//        int amount = 1000;
//        mvc.perform(
//                get("/hello/dto").param("name",name)
//                        .param("amount",String.valueOf(amount)))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) jsonPath("$.name",is(name)))
//                .andExpect((ResultMatcher) jsonPath("$.amount",is(amount)));
//
//    }

}
