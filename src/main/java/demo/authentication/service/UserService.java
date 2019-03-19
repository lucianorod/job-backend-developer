package demo.authentication.service;

import demo.authentication.exception.NotFoundException;
import demo.authentication.model.User;
import demo.authentication.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(NotFoundException::new);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
