package unical.enterprise.jokibackend.Data.Dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unical.enterprise.jokibackend.Data.Entities.Cart;
import unical.enterprise.jokibackend.Data.Entities.Embeddable.CartId;

@Repository
public interface CartDao extends JpaRepository<Cart, CartId>{
    Optional<Cart> findByUserId(UUID userId);
}