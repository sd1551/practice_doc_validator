package org.practice_user;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static UserDto toDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUserId(userEntity.getUserId().toString());
        userDto.setLogin(userEntity.getLogin());
        userDto.setDocuments(DocumentMapper.toDtoList(userEntity.getDocuments()));
        return userDto;
    }

    public static List<UserDto> toDtoList(List<UserEntity> userEntities) {
        return userEntities.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public static UserEntity toEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.fromString(userDto.getUserId()));
        userEntity.setLogin(userDto.getLogin());
        userEntity.setDocuments(DocumentMapper.toEntityList(userDto.getDocuments()));
        return userEntity;
    }
}