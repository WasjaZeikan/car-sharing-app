package mate.academy.carsharing.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import mate.academy.carsharing.validation.FieldMatch;

@FieldMatch(fieldName = "password", matchedFieldName = "repeatPassword")
public record UserRegistrationRequestDto(@NotNull
                                         @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/"
                                                 + "=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
                                         String email,
                                         @NotNull
                                         String password,
                                         @NotNull
                                         String repeatPassword,
                                         @NotNull
                                         String firstName,
                                         @NotNull
                                         String lastName) {
}
