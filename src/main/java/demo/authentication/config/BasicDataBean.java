package demo.authentication.config;

import demo.authentication.dto.UserDto;
import demo.authentication.model.Role;
import demo.authentication.repository.RoleRepository;
import demo.authentication.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class BasicDataBean {

    private final RoleRepository roleRepository;

    private final UserService userService;

    @PostConstruct
    private void initialize() {
        roleRepository.findByName("USER_ROLE").
                orElse(roleRepository.save(Role.builder().name("USER_ROLE").description("USER").build()));
        userService.save(new UserDto("test", "test"));
    }
}
