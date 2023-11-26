package mate.academy.carsharing.dto.car;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import mate.academy.carsharing.model.Car;

public record CarRequestDto(@NotBlank String model,
                            @NotBlank String brand,
                            @NotNull @Min(1) Integer inventory,
                            @NotNull @Min(1) BigDecimal dailyFee,
                            Car.Type type) {
}
