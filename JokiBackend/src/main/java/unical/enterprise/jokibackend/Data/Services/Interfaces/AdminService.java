package unical.enterprise.jokibackend.Data.Services.Interfaces;

import unical.enterprise.jokibackend.Data.Dto.AdminDto;
import unical.enterprise.jokibackend.Data.Dto.GameDto;

import java.lang.StackWalker.Option;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface AdminService {
    AdminDto getById(UUID id);
    AdminDto getByUsername(String username);
    AdminDto getByEmail(String email);
    Collection<AdminDto> findAll();
    AdminDto updateAdmin(UUID id, AdminDto adminDto);
    AdminDto save(AdminDto adminDto);
    void delete(UUID id);
    Optional<Collection<GameDto>> findGamesByAdminUsername(String username);
}
