package com.aeribmm.filmcritic.Controller;

import com.aeribmm.filmcritic.Model.UserModel.User;
import com.aeribmm.filmcritic.Model.UserModel.UserDTO;
import com.aeribmm.filmcritic.Model.UserModel.UserProfileDTO;
import com.aeribmm.filmcritic.Service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")//status and exception done
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userService.convertToDTO(user));//status 200
    }

    @GetMapping()//status and exception done
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")//status and exception done
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user != null) {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PutMapping("/update/{id}")//status and exception done
    public ResponseEntity<User> updateUser(@PathVariable Integer id,@RequestBody User updatedUser){
        User existingUser = userService.getUserById(id);
        updatedUser.setId(id);
        userService.createUser(updatedUser);
        return ResponseEntity.ok(updatedUser);
    }
    //todo patch

    @GetMapping("/{username}/profile")
    public ResponseEntity<UserProfileDTO> getProfileByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.getUserProfile(username));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getProfile(Authentication authentication){
        String email = authentication.getName();
        System.out.println(email  == null);
        System.out.println("user email: " + authentication.getName());
        return ResponseEntity.ok(userService.getUserProfile(authentication.getName()));
    }
}
