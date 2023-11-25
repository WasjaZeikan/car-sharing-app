package mate.academy.carsharing.dto.user;

import jakarta.validation.constraints.NotNull;
import mate.academy.carsharing.model.User;

public record UserRolesRequestDto(@NotNull User.Role[] roles) {
}
