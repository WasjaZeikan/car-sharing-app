package mate.academy.carsharing.dto.rental;

import jakarta.validation.constraints.NotNull;

public record RentalRequestDto(@NotNull Long carId,
                               @NotNull Long daysToReturn) {
}
