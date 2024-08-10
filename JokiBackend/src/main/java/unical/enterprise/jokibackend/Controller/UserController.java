package unical.enterprise.jokibackend.Controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import unical.enterprise.jokibackend.Data.Dto.GameDto;
import unical.enterprise.jokibackend.Data.Dto.UpdateUserDto;
//import unical.enterprise.jokibackend.Data.Services.KeycloakServiceImpl;
import unical.enterprise.jokibackend.Data.Services.UserServiceImpl;
import unical.enterprise.jokibackend.Utility.CustomContextManager.UserContextHolder;

import javax.ws.rs.Produces;

import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
   Logger logger = Logger.getLogger(UserController.class.getName());

//   private final KeycloakServiceImpl keycloakService;
   private final UserServiceImpl userService;

    // lista degli utenti
   @GetMapping("")
   @PreAuthorize("hasRole('client_admin')")
   @Produces("application/json")
   public ResponseEntity<String> getUsersList(){
       try {
           Gson gson = new Gson();
           var out = userService.getAllUsers();
           return ResponseEntity.ok(gson.toJson(out));
       }
       catch (Exception e) {
           logger.warning(e.getMessage());
           return ResponseEntity.badRequest().body("An error occurred");
       }
   }

   @GetMapping("/{username}")
   @PreAuthorize("hasRole('client_admin') or #username == authentication.name")
   @Produces("application/json")
   public ResponseEntity<String> getUser(@PathVariable String username) {
       try {
           Gson gson = new Gson();
           var out = userService.getUserByUsername(username);

           if (out == null) return ResponseEntity.notFound().build();
           else return ResponseEntity.ok(gson.toJson(out));
       }
       catch (Exception e) {
           logger.warning(e.getMessage());
           return ResponseEntity.badRequest().body("An error occurred");
       }
   }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('client_admin') or #username == authentication.name")
    @Produces("application/json")
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody UpdateUserDto userDto) {
        try {
            if(userService.updateUser(username, userDto)) return ResponseEntity.ok("User updated");
            else return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            logger.warning(e.getMessage());
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('client_admin') or #username == authentication.name")
    @Produces("plain/text")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        try {
            userService.deleteByUsername(username);
            return ResponseEntity.ok("User deleted");
        }
        catch (Exception e) {
            logger.warning(e.getMessage());
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    @GetMapping(value = "/games", produces = "application/json")
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<String> getUserGameList() {
        try {
            Gson gson = new Gson();
            // var out = userService.getUsernameGames(SecurityContextHolder.getContext().getAuthentication().getName());
            var out = userService.getUsernameGames(UserContextHolder.getContext().getPreferredUsername());
            return ResponseEntity.ok(gson.toJson(out));
        }
        catch (Exception e) {
            logger.warning(e.getMessage());
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    @PostMapping(value = "/{username}/library/{gameId}", produces = "application/json")
    public ResponseEntity<String> addGameToLibrary(@PathVariable String username, @PathVariable UUID gameId) {
        boolean added = userService.addGameToUserLibrary(username, gameId);
        if (added) {
            return ResponseEntity.ok("{\"message\": \"Game added to library successfully\", \"gameId\": \"" + gameId + "\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"Failed to add game to library\"}");
        }
    }

    @DeleteMapping(value = "/{username}/library/{gameId}", produces = "application/json")
    public ResponseEntity<String> removeGameFromLibrary(@PathVariable String username, @PathVariable UUID gameId) {
        boolean removed = userService.removeGameFromUserLibrary(username, gameId);
        if (removed) {
            return ResponseEntity.ok("{\"message\": \"Game removed from library successfully\", \"gameId\": \"" + gameId + "\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"Failed to remove game from library\"}");
        }
    }
}