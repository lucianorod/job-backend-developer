package demo.authentication;

import br.com.six2six.fixturefactory.Fixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.authentication.dto.UserDto;
import demo.authentication.model.User;
import demo.authentication.repository.UserRepository;
import demo.authentication.service.UserService;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends AuthenticationApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Before
    public void before() {
        userService.save(Fixture.from(UserDto.class).gimme("RODRIGO_USER"));
    }

    @After
    public void after() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void testPost() throws Exception {

        final String payload = objectMapper.writeValueAsString(Fixture.from(UserDto.class).gimme("TEST_USER"));

        mvc.perform(post("/users").with(csrf())
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username", Matchers.is("test")))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.roles.[*].description", Matchers.containsInAnyOrder("USER")))
                .andExpect(jsonPath("$.roles.[*].name", Matchers.containsInAnyOrder("ROLE_USER")));

        final User user = userRepository.findByUsername("test");

        mvc.perform(get("/users/{userId}", user.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username", Matchers.is("test")))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void testGet() throws Exception {

        final User user = userRepository.findByUsername("rodrigo");

        mvc.perform(get("/users/{userId}", user.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username", Matchers.is("rodrigo")))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void testUnauthorizedDelete() throws Exception {

        final User user = userRepository.findByUsername("rodrigo");

        mvc.perform(delete("/users/{userId}", user.getId()).with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    public void testAuthorizedDelete() throws Exception {

        final User user = userRepository.findByUsername("rodrigo");

        mvc.perform(delete("/users/{userId}", user.getId()).with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
