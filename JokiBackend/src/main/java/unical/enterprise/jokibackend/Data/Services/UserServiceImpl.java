package unical.enterprise.jokibackend.Data.Services;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import unical.enterprise.jokibackend.Data.Dao.GameDao;
import unical.enterprise.jokibackend.Data.Dao.UserDao;
import unical.enterprise.jokibackend.Data.Dto.GameDto;
import unical.enterprise.jokibackend.Data.Dto.UpdateUserDto;
import unical.enterprise.jokibackend.Data.Entities.Game;
import unical.enterprise.jokibackend.Data.Entities.User;
import unical.enterprise.jokibackend.Data.Services.Interfaces.UserService;
import unical.enterprise.jokibackend.Data.Dto.UserDto;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final GameDao gameDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public UserDto getUserById(UUID id) {
        User user = userDao.findById(id).orElse(null);
        return checkBeforeReturn(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userDao.findUserByUsername(username).orElse(null);
        return checkBeforeReturn(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userDao.findUserByEmail(email).orElse(null);
        return checkBeforeReturn(user);
    }

    @Override
    public Collection<UserDto> getAllUsers() {
        return userDao.findAll()
            .stream().map(user -> modelMapper
            .map(user, UserDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public Boolean updateUser(String username, UpdateUserDto userDto) {
        User user = userDao.findUserByUsername(username).orElse(null);

        if (user == null)   return false;

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthdate(userDto.getBirthdate());

        userDao.save(user);

        return true;
    }

    @Override
    public void delete(UUID id) {
        userDao.deleteById(id);
    }


    @Override
    public void deleteByUsername(String username) {
        userDao.deleteByUsername(username);
    }

    private UserDto checkBeforeReturn(User user) {
        if(user == null) return null;

        return modelMapper.map(user, UserDto.class);
    }
    public Collection<GameDto> getUsernameGames(String username) {
        return userDao.findGamesByUsername(username)
                       .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                       .stream()
                       .map(game -> modelMapper.map(game, GameDto.class))
                       .collect(Collectors.toList());
    }

    @Override
    public User getFriendByUsername(String first, String second) {
        Collection<User> friends = userDao.findFriendsByUsername(first).orElse(null);

        if(friends == null) return null;

        return friends.stream().filter(friend -> friend.getUsername().equals(second)).findFirst().orElse(null);
    }

    @Override
    @Transactional
    public boolean addGameToUserLibrary(String username, UUID gameId) {
        if (!gameDao.existsById(gameId)) {
            throw new EntityNotFoundException("Game not found");
        }
        int updatedRows = userDao.addGameToLibrary(username, gameId);
        return updatedRows > 0;
    }
    
    @Override
    @Transactional
    public boolean removeGameFromUserLibrary(String username, UUID gameId) {
        // Verifica se il gioco esiste
        if (!gameDao.existsById(gameId)) {
            throw new EntityNotFoundException("Game not found");
        }

        // Rimuovi il gioco dalla libreria dell'utente
        int updatedRows = userDao.removeGameFromLibrary(username, gameId);
        return updatedRows > 0;
    }
}
