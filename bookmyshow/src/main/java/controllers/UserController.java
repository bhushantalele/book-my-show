package controllers;


import dto.SignUpRequestDto;
import dto.SignUpResponseDto;
import lombok.Getter;
import lombok.Setter;
import models.ResponseStatus;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import repositories.UserRepository;
import services.UserService;

import java.util.Optional;

@Controller
@Getter
@Setter
public class UserController {
    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        Optional<User> optionalUser = userRepository.findByEmail(signUpRequestDto.getEmail());

        SignUpResponseDto responseDto = new SignUpResponseDto();
        if (optionalUser.isEmpty()) {
            //SignUp
            try {
                User user = userService.signUp(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
                responseDto.setResponseStatus(ResponseStatus.SUCCESS);
                responseDto.setUserId(user.getId());
            } catch (Exception exception) {
                responseDto.setResponseStatus(ResponseStatus.FAILURE);
            }
        } else {
            //Login
            try {
                User user = userService.login(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
                responseDto.setUserId(user.getId());
                responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            } catch (Exception exception) {
                responseDto.setResponseStatus(ResponseStatus.FAILURE);
            }
        }
        return responseDto;
    }
}