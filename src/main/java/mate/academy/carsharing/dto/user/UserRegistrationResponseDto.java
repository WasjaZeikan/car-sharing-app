package mate.academy.carsharing.dto.user;

public record UserRegistrationResponseDto(Long id,
                                          String email,
                                          String firstName,
                                          String lastName) {
}
