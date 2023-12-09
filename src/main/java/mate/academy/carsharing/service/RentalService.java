package mate.academy.carsharing.service;

import java.time.LocalDate;
import java.util.List;
import mate.academy.carsharing.dto.rental.RentalRequestDto;
import mate.academy.carsharing.dto.rental.RentalResponseDto;
import org.springframework.data.domain.Pageable;

public interface RentalService {
    RentalResponseDto createRental(RentalRequestDto requestDto, Long userId);

    RentalResponseDto getById(Long rentalId);

    void setActualReturnDate(Long rentalId, LocalDate returnDate);

    List<RentalResponseDto> getRentals(Pageable pageable, Long userId, Boolean isActiveRental);
}
