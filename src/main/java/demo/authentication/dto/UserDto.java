package demo.authentication.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

    private String username;

    private String password;

    private String cpf;

    private Set<RoleDto> roles;
}
