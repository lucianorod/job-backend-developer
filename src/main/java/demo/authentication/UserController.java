package demo.authentication;

import demo.authentication.model.User;
import demo.authentication.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController(value = "/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public User get(@PathVariable(value = "userId") Long userId) {
        return userService.findById(userId);
    }

    @PostMapping
    public User post(@RequestBody User user) {
        return userService.save(user);
    }
}
