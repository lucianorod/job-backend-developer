package demo.authentication.controller;

import demo.authentication.dto.UserDto;
import demo.authentication.model.User;
import demo.authentication.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User post(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User get(@PathVariable(name = "userId") Long userId) {
        return userService.findById(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "userId") Long userId) {
        userService.delete(userId);
    }

    @GetMapping("/me")
    public Principal user(Principal principal) {
        return principal;
    }
}
