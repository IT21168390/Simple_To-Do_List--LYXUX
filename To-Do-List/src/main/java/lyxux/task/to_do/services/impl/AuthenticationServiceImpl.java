package lyxux.task.to_do.services.impl;

import lombok.RequiredArgsConstructor;
import lyxux.task.to_do.dto.JwtAuthenticationResponseDTO;
import lyxux.task.to_do.dto.SignInRequestDTO;
import lyxux.task.to_do.dto.SignUpRequestDTO;
import lyxux.task.to_do.dto.UserDTO;
import lyxux.task.to_do.models.User;
import lyxux.task.to_do.repositories.UserRepository;
import lyxux.task.to_do.services.AuthenticationService;
import lyxux.task.to_do.services.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserDTO signUp(SignUpRequestDTO signUpRequestDTO) {
        User user = new User();

        if (signUpRequestDTO.getEmail() == null) {
            throw new IllegalArgumentException("Email is required!");
        } else {
            user.setEmail(signUpRequestDTO.getEmail());
        }
        user.setName(signUpRequestDTO.getName());
        user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));

        //return userRepository.save(user);
        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setName(createdUser.getName());

        return userDTO;
    }

    public boolean emailAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public JwtAuthenticationResponseDTO signIn(SignInRequestDTO signInRequest) throws IllegalArgumentException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        var user = userRepository.findByEmail(signInRequest.getEmail());
        //UserDetails userDetails = userRepository.findByEmail(signInRequest.getEmail());
        //UserDTO userDTO = new UserDTO();
        var jwt = jwtService.generateToken(user);

        JwtAuthenticationResponseDTO jwtAuthenticationResponseDTO = new JwtAuthenticationResponseDTO();
        jwtAuthenticationResponseDTO.setToken(jwt);
        jwtAuthenticationResponseDTO.setEmail(user.getUsername());

        return jwtAuthenticationResponseDTO;
    }

}
