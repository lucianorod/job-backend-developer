package demo.authentication.config;

import demo.authentication.dto.RoleDto;
import demo.authentication.dto.UserDto;
import demo.authentication.model.Role;
import demo.authentication.repository.RoleRepository;
import demo.authentication.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class BasicDataBean {

    private final RoleRepository roleRepository;

    private final UserService userService;

    @PostConstruct
    @Transactional
    public void initialize() {
        roleRepository.save(Role.builder().name("ROLE_ADMIN").description("ADMIN").build());
        roleRepository.findByName("ROLE_USER").
                orElse(roleRepository.save(Role.builder().name("ROLE_USER").description("USER").build()));

        Set<RoleDto> roles = new HashSet<>(Collections.singleton(new RoleDto("USER")));
        userService.save(new UserDto("luciano", "test", "24208526050", roles));
    }
}
