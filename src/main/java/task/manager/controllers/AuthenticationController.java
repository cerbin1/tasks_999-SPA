package task.manager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.manager.utils.StringUtils;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        if (email.equals("admin") && password.equals("admin")) {
            return new ResponseEntity<>(OK);
        }
        return new ResponseEntity<>(UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }
        return new ResponseEntity<>(CREATED);
    }
}
