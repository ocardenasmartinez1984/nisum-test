package cl.cardenas.repositories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phone")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String number;
    @Column(nullable = false)
    private String cityCode;
    @Column(nullable = false)
    private String countryCode;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
