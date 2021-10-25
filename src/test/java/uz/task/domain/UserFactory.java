package uz.task.domain;

import uz.task.constants.UserStateEnum;
import java.util.ArrayList;
import java.util.List;

public class UserFactory {

    public static User firstUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("John2255");
        user.setEmail("John@uk.co");
        user.setPassword("MyPassword123456");
        user.setAge(25);
        user.setState(UserStateEnum.ACTIVE.getValue());
        return user;
    }

    public static User secondUser() {
        User user = new User();
        user.setId(2L);
        user.setUsername("Alex11");
        user.setEmail("Alex@gmail.com");
        user.setPassword("MyPassword123456");
        user.setAge(32);
        user.setState(UserStateEnum.ACTIVE.getValue());
        return user;
    }

    public static List<User> getUserList() {
        List<User> list = new ArrayList<>();
        list.add(firstUser());
        list.add(secondUser());
        return list;
    }
}
