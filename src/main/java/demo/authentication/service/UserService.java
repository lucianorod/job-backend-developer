package demo.authentication.service;

import demo.authentication.dto.UserDetailsDto;
import demo.authentication.dto.UserDto;
import demo.authentication.exception.NotFoundException;
import demo.authentication.model.Role;
import demo.authentication.model.User;
import demo.authentication.repository.RoleRepository;
import demo.authentication.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User findById(Long userId) {
        logger.info("M=findById, finding User with userId {}", userId);
        return userRepository.findById(userId).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public User save(final UserDto userDto) {
        logger.info("M=save, saving User {}", userDto.toString());
        final User user = User.builder().username(userDto.getUsername()).password(bCryptPasswordEncoder.
                encode(userDto.getPassword())).build();

        Set<Role> roles = userDto.getRoles().stream().map(r -> roleRepository.getByName(r.getName()))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public void delete(Long userId) {
        logger.info("M=delete, deleting User with userId {}", userId);
        userRepository.deleteById(userId);
    }

    public UserDto me() {

        UserDetailsDto details = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return new UserDto(details.getUsername(), details.getPassword(), details.getCpf(), null);
    }
}
