package uz.task.service;

import uz.task.domain.User;
import uz.task.dto.UserSaveDto;

public interface UserService {
    User save(UserSaveDto model);
    User findByUsername();
//    boolean checkIfUserExist(String username);
}
