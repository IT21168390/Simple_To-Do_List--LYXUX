package lyxux.task.to_do.services;

import lyxux.task.to_do.dto.JwtAuthenticationResponseDTO;
import lyxux.task.to_do.dto.SignInRequestDTO;
import lyxux.task.to_do.dto.SignUpRequestDTO;
import lyxux.task.to_do.dto.UserDTO;

public interface AuthenticationService {
    //User signUp(SignUpRequestDTO signUpRequestDTO);
    UserDTO signUp(SignUpRequestDTO signUpRequestDTO);

    JwtAuthenticationResponseDTO signIn(SignInRequestDTO signInRequest);

    boolean emailAlreadyExists(String email);

}
