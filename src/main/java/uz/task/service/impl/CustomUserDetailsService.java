package uz.task.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.task.constants.UserStateEnum;
import uz.task.domain.CustomUserDetails;
import uz.task.domain.User;
import uz.task.repository.UserRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s){
        if(s == null || s.isBlank()) {
            throw new UsernameNotFoundException(s);
        }

        User user = userRepository.findByUsername(s);
        if(user == null) {
            throw new UsernameNotFoundException(s);
        }
        if(user.getState() != UserStateEnum.ACTIVE.getValue() || user.getState() == null) {
            throw new UsernameNotFoundException(s);
        }
        return new CustomUserDetails(s, user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(grantedRole(user))));
    }

    private String grantedRole(User user) {
        if(user.getId() == 1) {
            return "ROLE_ADMIN";
        } else {
            return "ROLE_USER";
        }
    }
}
