package demo.authentication;

import br.com.six2six.fixturefactory.Fixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.authentication.dto.UserDto;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends AuthenticationApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @WithMockUser(username = "test", password = "test")
    public void testPost() throws Exception {

        final String user = objectMapper.writeValueAsString(Fixture.from(UserDto.class).gimme("VALID"));

        mvc.perform(post("/users").with(csrf())
                .content(user)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username", Matchers.is("test")))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.roles.[*].description", Matchers.containsInAnyOrder("USER")))
                .andExpect(jsonPath("$.roles.[*].name", Matchers.containsInAnyOrder("USER_ROLE")));

        mvc.perform(get("/users/{userId}", 2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username", Matchers.is("test")))
                .andExpect(jsonPath("$.password").doesNotExist());
    }
}
