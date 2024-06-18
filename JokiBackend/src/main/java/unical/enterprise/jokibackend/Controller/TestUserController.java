package unical.enterprise.jokibackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import unical.enterprise.jokibackend.Dto.KeycloakUserDTO;
import unical.enterprise.jokibackend.Service.KeycloakService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class TestUserController {
    @Autowired
    KeycloakService service;

    @GetMapping("/diocane")
    @PreAuthorize("hasRole('client_user')")
    public String diocane() {
        return "diocane";
    }

    @GetMapping("/diocane-2")
    @PreAuthorize("hasRole('client_admin')")
    public String diocane2() {
        return "diocane 2";
    }

    @GetMapping("/privato/{username}")
    @PreAuthorize("#username == authentication.name")
    public String privato(@PathVariable String username) {
        return "HA ACCESSO";
    }

    @GetMapping("/amici/{username}")
    @PreAuthorize("@userPermissionEvaluator.isFriend(authentication, #username)")
    public String amici(@PathVariable String username) {
        return "Sono amici";
    }

    @PostMapping("/user-data")
    public String userData() {
        return "username/id: " + SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody KeycloakUserDTO userDTO){
        try {
            service.addUser(userDTO);
            return ResponseEntity.ok("User added successfully");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding user");
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<KeycloakUserDTO> getUser(@PathVariable String username){
        try {
            var tmp = service.getUser(username).get(0);

            KeycloakUserDTO userDTO = new KeycloakUserDTO();
            userDTO.setUserName(tmp.getUsername());
            userDTO.setEmailId(tmp.getEmail());
            userDTO.setFirstname(tmp.getFirstName());
            userDTO.setLastName(tmp.getLastName());

            return ResponseEntity.ok(userDTO);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
}
