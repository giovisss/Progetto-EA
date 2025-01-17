package unical.enterprise.jokibackend.Data.Dao;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unical.enterprise.jokibackend.Data.Entities.Game;
import unical.enterprise.jokibackend.Data.Entities.User;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDao extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    void deleteByUsername(String username);

    @Query("SELECT u.friends FROM users u WHERE u.username = :username")
    Optional<Collection<User>> findFriendsByUsername(@Param("username") String username);

    // metodi per libreria
    @Query("SELECT u.games FROM users u WHERE u.username = :username")
    Optional<Collection<Game>> findGamesByUsername(@Param("username") String username);

    // aggiungere un gioco alla libreria
    @Modifying
    @Query(value = "INSERT INTO libraries (user_id, game_id) " +
                   "SELECT u.id, :gameId FROM users u WHERE u.username = :username",
           nativeQuery = true)
    int addGameToLibrary(@Param("username") String username, @Param("gameId") UUID gameId);

    // rimuovere un gioco dalla libreria
    @Modifying
    @Query(value = "DELETE FROM libraries " +
                   "WHERE user_id = (SELECT id FROM users WHERE username = :username) " +
                   "AND game_id = :gameId",
           nativeQuery = true)
    int removeGameFromLibrary(@Param("username") String username, @Param("gameId") UUID gameId);

    // metodi per carrello
    @Query("SELECT u.cartGames FROM users u WHERE u.username = :username")
    Optional<Collection<Game>> findCartGamesByUsername(@Param("username") String username);

    // Aggiungere un gioco al carrello
    @Modifying
    @Query(value = "INSERT INTO carts (user_id, game_id) " +
                "SELECT u.id, :gameId FROM users u WHERE u.username = :username " +
                "AND NOT EXISTS (SELECT 1 FROM carts c WHERE c.user_id = u.id AND c.game_id = :gameId)",
        nativeQuery = true)
    int addGameToCart(@Param("username") String username, @Param("gameId") UUID gameId);

    // Rimuovere un gioco dal carrello
    @Modifying
    @Query(value = "DELETE FROM carts " +
                   "WHERE user_id = (SELECT id FROM users WHERE username = :username) " +
                   "AND game_id = :gameId",
           nativeQuery = true)
    int removeGameFromCart(@Param("username") String username, @Param("gameId") UUID gameId);

    // Svuotare il carrello
    @Modifying
    @Query(value = "DELETE FROM carts " +
                   "WHERE user_id = (SELECT id FROM users WHERE username = :username)",
           nativeQuery = true)
    void clearUserCart(@Param("username") String username);


    @Query(value = """
            SELECT COUNT(*)
            FROM friends
            WHERE (user_id = :primo AND friend_id = :secondo)
               OR (user_id = :secondo AND friend_id = :primo)""",
        nativeQuery = true)
    int checkFriendship(@Param("primo") UUID primo, @Param("secondo") UUID secondo);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO friends (user_id, friend_id) VALUES (:primo, :secondo)", nativeQuery = true)
    void addFriendship(@Param("primo") UUID primo, @Param("secondo") UUID secondo);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM friends WHERE user_id = :primo AND friend_id = :secondo", nativeQuery = true)
    void removeFriendship(@Param("primo") UUID primo, @Param("secondo") UUID secondo);

    //delete game from libraries
    @Modifying
    @Query(value = "DELETE FROM libraries WHERE game_id = :id", nativeQuery = true)
    void deleteGameFromUsers(@Param("id") UUID id);
}
