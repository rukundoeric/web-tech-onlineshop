package com.webtech.onlineshop.application.service;

import com.webtech.onlineshop.application.dto.UserDto;
import com.webtech.onlineshop.application.model.User;
import com.webtech.onlineshop.application.repository.IUserRepository;
import com.webtech.onlineshop.common.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private  IUserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    // Create a new user
    public User createUser(User user) {
        try {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setRole("USER");
            user.setPassword(encodedPassword);
            User newUser = userRepository.save(user);
            newUser.setPassword(null);
            return newUser;
        } catch (DataIntegrityViolationException ex) {
            throw new RequestException("User already exist!");
        } catch (Exception e) {
            throw new RequestException(e);
        }
    }

    public User createAdminUser(User user) {
        try {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setRole("ADMIN");
            user.setPassword(encodedPassword);

            User newUser = userRepository.save(user);
            newUser.setPassword(null);
            return newUser;
        } catch (Exception e) {
            throw new RequestException(e);
        }
    }

    // Retrieve a user by ID
    public Optional<UserDto> getUserById(UUID id) {
        try  {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return Optional.of(new UserDto(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getRole()));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RequestException(e);
        }
    }

    public Optional<User> findUserById(UUID id) {
        try  {
            return userRepository.findById(id);
        } catch (Exception e) {
            throw new RequestException(e);
        }
    }

    // Retrieve a user by ID
    public Optional<UserDto> getUserByEmail(String email) {
        try  {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                return Optional.of(new UserDto(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getRole()));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RequestException(e);
        }
    }

    // Retrieve a user by ID
    public Optional<User> findUserByEmail(String email) {
        try  {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new RequestException(e);
        }
    }

    // Retrieve all users
    public List<UserDto> getAllUsers() {
        try {
            return userRepository.findAll().stream()
                    .map(user -> new UserDto(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getRole()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RequestException(e);
        }
    }

    // Update a user
    public UserDto updateUser(UUID id, User userDetails) {
       try {
           User updatedUser = userRepository.findById(id)
                   .map(user -> {
                       user.setEmail(userDetails.getEmail());
                       user.setPassword(userDetails.getPassword());
                       user.setFirstName(userDetails.getFirstName());
                       user.setLastName(userDetails.getLastName());
                       user.setRole(userDetails.getRole());
                       return userRepository.save(user);
                   })
                   .orElseThrow(() -> new RuntimeException("User not found with id " + id));
           return new UserDto(updatedUser.getId(), updatedUser.getEmail(), updatedUser.getFirstName(), updatedUser.getLastName(), updatedUser.getRole());

       } catch (Exception e) {
           throw new RequestException(e);
       }
    }

    // Delete a user
    public void deleteUser(UUID id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(e);
        }
    }
}

