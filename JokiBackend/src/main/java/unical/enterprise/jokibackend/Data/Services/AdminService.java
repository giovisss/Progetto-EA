package unical.enterprise.jokibackend.Data.Services;

import unical.enterprise.jokibackend.Data.Entities.Admin;
import unical.enterprise.jokibackend.Dto.AdminDto;
import unical.enterprise.jokibackend.Dto.GameDto;

import java.util.Collection;
import java.util.UUID;

public interface AdminService {
    void save(Admin admin);

    AdminDto getById(UUID id);
    AdminDto getByUsername(String username);
    AdminDto getByEmail(String email);
    Collection<AdminDto> findAll();
    Collection<GameDto> getGamesInsertByAdminId(UUID id);
    AdminDto updateAdmin(UUID id, AdminDto adminDto);
    void delete(UUID id);

}