package org.practice_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    String UPLOAD_DIR = "static/uploads/";
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final DocumentRepository documentRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public UserService(UserRepository userRepository, DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
    }

    public List<UserDto> getAllUsers() {
        return UserMapper.toDtoList(userRepository.findAll());
    }

    public void uploadDocument(UUID userId, MultipartFile file) throws IOException {
        UUID documentId = UUID.randomUUID();

        Path filePath = Paths.get(UPLOAD_DIR + File.separator + documentId + ".jpg");

        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Ошибка сохранения файла", e);
        }

        String message = filePath + ";" + documentId + ";" + userId;
        kafkaTemplate.send("parser-document", message);
    }

    public void addUser(String login) {
        UUID userId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setLogin(login);
        userRepository.save(userEntity);
    }
}
