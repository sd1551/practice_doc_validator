import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.practice_user.*;
import org.practice_user.dto.UserDto;
import org.practice_user.entity.UserEntity;
import org.practice_user.repo.DocumentRepository;
import org.practice_user.repo.UserRepository;
import org.practice_user.services.UserService;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<UserEntity> userEntities = List.of(new UserEntity());
        when(userRepository.findAll()).thenReturn(userEntities);
        when(userMapper.toDtoList(userEntities)).thenReturn(List.of(new UserDto()));

        List<UserDto> users = userService.getAllUsers();

        assertNotNull(users);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testAddUser() {
        String login = "testUser";
        userService.addUser(login);

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}