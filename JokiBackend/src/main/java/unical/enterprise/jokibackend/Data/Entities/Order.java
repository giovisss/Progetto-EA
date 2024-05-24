package unical.enterprise.jokibackend.Data.Entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    @Column
    private UUID id;

    @Column(columnDefinition = "DATE")
    private Date date;

    @Column
    private Double price;

    @Column
    private Boolean status;

    @OneToMany
    @JoinColumn
    private Game game;

    @OneToOne
    @JoinColumn
    private User user;
}
