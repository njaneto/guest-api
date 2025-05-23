package com.church.guest.web;

import com.church.guest.web.dto.UserRequest;
import com.church.guest.web.dto.UserResponse;
import com.church.guest.mapper.UserMapper;
import com.church.guest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin(value = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController( UserService userService ) {
        this.userService = userService;
    }

    @PostMapping( value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @Secured("ROLE_USER_WRITER")
    public UserResponse userCreate(@Valid @RequestBody UserRequest request ){
        return UserMapper.toUserResponse(userService.userCreate(request));
    }

}
