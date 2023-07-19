package dev.gracie.elearn.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.gracie.elearn.config.Constants;
//import dev.gracie.elearn.models.ai.ResponseDatabase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class AppUser {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
//    @ManyToOne
//    private ResponseDatabase responseDatabase;
    private List<String> responses;

    //    @NotNull
//    @Pattern(regexp = Constants.LOGIN_REGEX)
//    @Size(min = 1, max = 50)
//    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;


    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;


    //    @Size(max = 20)
//    @Column(name = "activation_key", length = 20)
//    @JsonIgnore
    private String activationKey;

    //    @Size(max = 20)
//    @Column(name = "reset_key", length = 20)
//    @JsonIgnore
    private String resetKey;

    //    @Column(name = "reset_date")
    private Instant resetDate = null;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "appUser_authority",
            joinColumns = {@JoinColumn(name = "appUser_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
    )
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();
}
