package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.UserDTO;
import com.project.shopapp.components.CookieUtil;
import com.project.shopapp.components.JwtTokenUtil;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.customexceptions.PermissionDenyException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IClaimService;
import com.project.shopapp.services.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final IClaimService claimService;
    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        //register user
        String username = userDTO.getUsername();
        String phoneNumber = userDTO.getPhoneNumber();

        if(userRepository.existsByUsername(username)) {
            throw new DataIntegrityViolationException("username already exists");
        }
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("phone number already exists");
        }
        Role role =roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        if(role.getName().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDenyException("You cannot register an admin account");
        }
        //convert from userDTO => userEntity
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .phoneNumber(userDTO.getPhoneNumber())
                .build();

        newUser.setRole(role);

        //Decoed kí tự password
        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);

        return userRepository.save(newUser);
    }


    @Override
    public String login(String username, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid username / password");
        }
        //return optionalUser.get();//muốn trả JWT token ?
        User existingUser = optionalUser.get();
        //check password
        if(!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new BadCredentialsException("Wrong username or password");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password,
                existingUser.getAuthorities()
        );

        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);

        //Streak Login
        claimService.claimStreakPoint(existingUser);
        //*****************************//

        return jwtTokenUtil.generateToken(existingUser);
    }

}
