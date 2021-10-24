package uz.task.service;

import uz.task.domain.User;
import uz.task.dto.UserSaveDto;
import uz.task.exception.UserAlreadyExistException;

import java.util.List;

public interface UserService {
    User save(UserSaveDto model);
    boolean checkIfUserExist(String username);
    User findByUsername();
}
