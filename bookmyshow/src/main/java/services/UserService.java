package services;

import lombok.Getter;
import lombok.Setter;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repositories.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Getter
@Setter
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }
    public User login(String email, String password){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new RuntimeException("Email is not present in DB.");
        }
        User user = optionalUser.get();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(bCryptPasswordEncoder.matches(password, user.getPassword())){
           //Login Successful
            return user;
        }
        throw new RuntimeException("Password Mismatch");
    }
    public User signUp(String email, String password){
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()){
            return login(email,password);
        }
        User user = new User();
        user.setEmail(email);

        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setUserName("");
        user.setBookings(new ArrayList<>());

        User savedUser = userRepository.save(user);

        return savedUser;
    }
}
