import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.practice_user.controller.UserController;
import org.practice_user.dto.UserDto;
import org.practice_user.services.UserService;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        Model model = mock(Model.class);
        when(userService.getAllUsers()).thenReturn(List.of(new UserDto()));

        String viewName = userController.getAllUsers(model);

        assertEquals("index", viewName);
        verify(userService, times(1)).getAllUsers();
        verify(model, times(1)).addAttribute(eq("users"), any());
    }

    @Test
    void testShowAddUserForm() {
        String viewName = userController.showAddUserForm();
        assertEquals("addUser", viewName);
    }

    @Test
    void testAddUser() {
        String login = "newUser";
        Model model = mock(Model.class);

        String viewName = userController.addUser(login, model);

        assertEquals("redirect:/users", viewName);
        verify(userService, times(1)).addUser(login);
    }

    @Test
    void testShowUploadDocumentForm() {
        UUID userId = UUID.randomUUID();
        Model model = mock(Model.class);

        String viewName = userController.showUploadDocumentForm(userId, model);

        assertEquals("uploadDocument", viewName);
        verify(model, times(1)).addAttribute(eq("userId"), eq(userId));
    }

    @Test
    void testUploadDocument() throws IOException {
        UUID userId = UUID.randomUUID();
        MultipartFile file = mock(MultipartFile.class);
        Model model = mock(Model.class);

        String viewName = userController.uploadDocument(userId, file, model);

        assertEquals("redirect:/users", viewName);
        verify(userService, times(1)).uploadDocument(userId, file);
    }
}