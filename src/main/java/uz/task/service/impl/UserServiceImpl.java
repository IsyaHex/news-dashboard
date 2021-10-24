package uz.task.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.task.constants.UserStateEnum;
import uz.task.domain.User;
import uz.task.dto.UserSaveDto;
import uz.task.repository.UserRepository;
import uz.task.service.UserService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User save(UserSaveDto model) {
//        if(checkIfUserExist(model.getUsername())) {
//            throw new UserAlreadyExistException("User already exist for this username!");
//        }
        User user = new User();
        addNewUser(model, user);
        return userRepository.save(user);
    }

    @Override
    public boolean checkIfUserExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public User findByUsername() {
        return userRepository.findByUsername(getCurrentUsername());
    }

    private void addNewUser(UserSaveDto model, User user) {
        user.setUsername(model.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
        user.setEmail(model.getEmail());
        user.setCreateDate(dateTimeFormatter(ZonedDateTime.now()));
        user.setAge(model.getAge());
        user.setState(UserStateEnum.ACTIVE.getValue());
    }

    private String dateTimeFormatter(ZonedDateTime time) {
        time = ZonedDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return time.format(format);
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if(principal instanceof Principal) {
            return ((Principal) principal).getName();
        }
        return String.valueOf(principal);
    }

}
