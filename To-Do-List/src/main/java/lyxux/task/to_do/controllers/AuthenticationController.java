package lyxux.task.to_do.controllers;

import lombok.RequiredArgsConstructor;
import lyxux.task.to_do.dto.JwtAuthenticationResponseDTO;
import lyxux.task.to_do.dto.SignInRequestDTO;
import lyxux.task.to_do.dto.SignUpRequestDTO;
import lyxux.task.to_do.dto.UserDTO;
import lyxux.task.to_do.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        if (authenticationService.emailAlreadyExists(signUpRequestDTO.getEmail())) {
            System.out.println("Email already in use!");
            return new ResponseEntity("Email is already used to signup! Try another...", HttpStatus.BAD_REQUEST);
        } else {
            try {
                return ResponseEntity.ok(authenticationService.signUp(signUpRequestDTO));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (ResponseStatusException e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponseDTO> signIn(@RequestBody SignInRequestDTO signInRequestDTO) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequestDTO));
    }
}
