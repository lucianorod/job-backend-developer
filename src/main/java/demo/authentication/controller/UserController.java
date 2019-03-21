package demo.authentication.controller;

import demo.authentication.dto.UserDto;
import demo.authentication.model.User;
import demo.authentication.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Secured("ROLE_USER")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User post(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @Secured("ROLE_USER")
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User get(@PathVariable(name = "userId") Long userId) {
        return userService.findById(userId);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "userId") Long userId) {
        userService.delete(userId);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserDto user() {
        return userService.me();
    }
}
