package mate.academy.carsharing.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserLoginRequestDto(@NotNull
                                  @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/="
                                          + "?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
                                  String email,
                                  @NotNull String password) {
}
