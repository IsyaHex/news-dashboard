package uz.task.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.task.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uz.task.domain.UserFactory.firstUser;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void findByUsername() {
        when(userRepository.findByUsername(firstUser().getUsername())).thenReturn(firstUser());
        assertThat(userRepository.findByUsername(firstUser().getUsername())).isNotNull().isEqualTo(firstUser());
    }
}