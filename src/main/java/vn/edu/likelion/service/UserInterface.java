package vn.edu.likelion.service;

import vn.edu.likelion.model.user.JWTAuthResponse;
import vn.edu.likelion.model.user.LoginDTO;
import vn.edu.likelion.model.user.UserRequest;
import vn.edu.likelion.model.user.UserResponse;

public interface UserInterface {
    UserResponse register(UserRequest userRequest);
    JWTAuthResponse login(LoginDTO loginDTO);
}
