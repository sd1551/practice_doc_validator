package org.practice_user;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private String userId;
    private String login;
    private List<DocumentDto> documents;
}
