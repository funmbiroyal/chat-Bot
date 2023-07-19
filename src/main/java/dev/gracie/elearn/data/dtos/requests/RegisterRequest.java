package dev.gracie.elearn.data.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;
    private Long id;


//    @NotBlank
//    @Pattern(regexp = Constants.LOGIN_REGEX)
//    @Size(min = 1, max = 50)
//    private String login;

    @Size(max = 25)
    private String firstName;

    @Size(max = 25)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private boolean activated = false;

    private Set<String> authorities;
    @Override
    public String toString() {
        return "UserDTO{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", activated=" + activated +
                ", authorities=" + authorities +
                "}";
    }

}
