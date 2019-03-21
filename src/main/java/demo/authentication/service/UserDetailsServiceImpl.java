package demo.authentication.service;

import demo.authentication.dto.UserDetailsDto;
import demo.authentication.model.User;
import demo.authentication.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User user = userRepository.findByUsername(username);

        final Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toSet());

        return UserDetailsDto.builder().username(user.getUsername()).password(user.getPassword())
                .authorities(authorities).accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true)
                .enabled(true).cpf(user.getCpf()).build();
    }
}
