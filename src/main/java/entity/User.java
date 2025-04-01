package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "initials")
    @Size(min = 3, max = 200, message = "Your name is too short or too long. Rewrite it")
    private String username;

    @Column(name = "birth_date", columnDefinition = "DATE")
    private LocalDate birthdate;

    @Column(name = "tabel_number")
    private Integer tabelNumber;
}
