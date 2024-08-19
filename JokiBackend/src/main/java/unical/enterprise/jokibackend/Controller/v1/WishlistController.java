package unical.enterprise.jokibackend.Controller.v1;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unical.enterprise.jokibackend.Data.Dto.GameDto;
import unical.enterprise.jokibackend.Data.Services.Interfaces.GameService;
import unical.enterprise.jokibackend.Data.Services.Interfaces.WishlistService;
import unical.enterprise.jokibackend.Utility.CustomContextManager.UserContextHolder;

import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/wishlist")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    private final GameService gameService;

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<String> getWishlist(){
        var out = wishlistService.getByUserUsername(UserContextHolder.getContext().getPreferredUsername());
        return ResponseEntity.ok(new Gson().toJson(out));
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<String> addWishlist(@RequestBody String wishlistName){
        wishlistService.addWishlist(wishlistName);
        return ResponseEntity.ok("Wishlist added");
    }

    @GetMapping(value = "/{wishlistName}", produces = "application/json")
    public ResponseEntity<String> getWishlist(@PathVariable String wishlistName){
        var out = wishlistService.getByWishlistName(wishlistName);
        return ResponseEntity.ok(new Gson().toJson(out));
    }

    @DeleteMapping(value = "/{wishlistName}", produces = "application/json")
    public ResponseEntity<String> deleteWishlist(@PathVariable String wishlistName){
        wishlistService.deleteById(wishlistService.getByWishlistName(wishlistName).getId());
        return ResponseEntity.ok("Wishlist deleted");
    }

    @PostMapping(value = "/{wishlistName}/{gameId}", produces = "application/json")
    public ResponseEntity<String> addGameToWishlist(@PathVariable UUID gameId, @PathVariable String wishlistName){
        boolean added = wishlistService.addGameToWishlist(gameService.getGameById(gameId), wishlistName);

        if (added) return ResponseEntity.ok("Game added to wishlist");
        else return ResponseEntity.badRequest().body("Game already in wishlist");
    }

    @DeleteMapping(value = "/{wishlistName}/{gameId}", produces = "application/json")
    public ResponseEntity<String> removeGameFromWishlist(@PathVariable UUID gameId, @PathVariable String wishlistName){
        wishlistService.removeGameFromWishlist(gameService.getGameById(gameId), wishlistName);
        return ResponseEntity.ok("Game removed from wishlist");
    }

    // TODO: IMPLEMENTARE LOGICA AMICIZIA

    @GetMapping(value = "/other/{username}", produces = "application/json")
//    @PreAuthorize("@userPermissionEvaluator.canVisulize(#username)")
    public ResponseEntity<String> getWishlistByUsername(@PathVariable String username){
        throw new NotImplementedException();
    }
}
