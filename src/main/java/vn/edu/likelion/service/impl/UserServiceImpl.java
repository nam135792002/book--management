package vn.edu.likelion.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.likelion.entity.Role;
import vn.edu.likelion.entity.User;
import vn.edu.likelion.exception.ApiException;
import vn.edu.likelion.exception.ResourceNotFoundException;
import vn.edu.likelion.model.user.JWTAuthResponse;
import vn.edu.likelion.model.user.LoginDTO;
import vn.edu.likelion.model.user.UserRequest;
import vn.edu.likelion.model.user.UserResponse;
import vn.edu.likelion.repository.UserRepository;
import vn.edu.likelion.security.JwtTokenProvider;
import vn.edu.likelion.service.UserInterface;

@Service
public class UserServiceImpl implements UserInterface {

    @Autowired private UserRepository userRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenProvider jwtTokenProvider;

    @Override
    public UserResponse register(UserRequest userRequest) {
        if(userRepository.existsUserByEmail(userRequest.getEmail())){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Email '" + userRequest.getEmail() + "' is existed in system");
        }

        User user = modelMapper.map(userRequest, User.class);
        user.setRole(new Role(userRequest.getRoleId()));
        String passwordEncode = passwordEncoder.encode(userRequest.getPassword());
        user.setPassword(passwordEncode);
        return convertToResponse(userRepository.save(user));
    }

    @Override
    public JWTAuthResponse login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);

            JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
            jwtAuthResponse.setAccessToken(token);

            return jwtAuthResponse;
        }catch (BadCredentialsException e) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Email or password is incorrect.");
        }
    }

    private UserResponse convertToResponse(User user){
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setRoleName(user.getRole().getName());
        return userResponse;
    }
}
