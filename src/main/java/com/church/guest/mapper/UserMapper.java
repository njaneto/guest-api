package com.church.guest.mapper;

import com.church.guest.web.dto.UserRequest;
import com.church.guest.web.dto.UserResponse;
import com.church.guest.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return user != null ? UserResponse
                .builder()
                .id(user.getId())
                .build() : null;
    }

    public static User toUser(UserRequest request) {
        return request != null ? User
                .builder()
                .login(request.getLogin())
                .roles(request.getRoles())
                .build(): null;
    }
}
