package com.controller;

import com.model.User;
import com.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class SpringController {

    private final AtomicInteger counter = new AtomicInteger();

    private final UserRepository userRepository;

    @Autowired
    public SpringController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    @Operation(summary = "Returns all users", tags = {"User",},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Returns all users",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)))
            })
    @ResponseBody
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    @Operation(summary = "Register a new user", tags = {"User",},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Returns the registered user",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)))
            })
    @ResponseBody
    public User register(@RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {
        User newUser = new User(counter.incrementAndGet(), name);
        return userRepository.addUser(newUser);
    }

    @PutMapping("/users/{id}")
    @Operation(summary = "Update a user's name", tags = {"User",},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Returns the updated user",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)))
            })
    @ResponseBody
    public User updateUser(@PathVariable(value = "id") int id, String newName) {
        return userRepository.updateUser(id, newName).orElseThrow(() -> new EntityNotFoundException("Error! User not found!"));
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete a user", tags = {"User",},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Returns a boolean",
                            content = @Content(mediaType = "application/json"))
            })
    @ResponseBody
    public Boolean deleteUser(@PathVariable(value = "id") int id) {
        return userRepository.deleteUser(id);
    }
}
