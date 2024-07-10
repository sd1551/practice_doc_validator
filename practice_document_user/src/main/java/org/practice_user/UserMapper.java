package org.practice_user;

import java.util.List;

import org.mapstruct.*;
import org.practice_user.dto.UserDto;
import org.practice_user.entity.UserEntity;

@Mapper(componentModel = "spring", uses = {DocumentMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto toDto(UserEntity userEntity);

    List<UserDto> toDtoList(List<UserEntity> userEntities);

    @Mapping(target = "documents", ignore = true)
    UserEntity toEntity(UserDto userDto);
}
