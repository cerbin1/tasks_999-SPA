package task.manager.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequest {

    private String email;
    private String username;
    private String password;
    private String name;
    private String surname;
    private Set<String> role;
}
