package demo.authentication.service;

import demo.authentication.dto.UserDto;
import demo.authentication.exception.NotFoundException;
import demo.authentication.model.Role;
import demo.authentication.model.User;
import demo.authentication.repository.RoleRepository;
import demo.authentication.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public User save(final UserDto userDto) {
        final User user = User.builder().username(userDto.getUsername()).password(bCryptPasswordEncoder.
                encode(userDto.getPassword())).build();
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("USER_ROLE").get());
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
