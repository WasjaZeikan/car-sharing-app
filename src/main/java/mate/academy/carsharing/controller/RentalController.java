package mate.academy.carsharing.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.carsharing.dto.rental.RentalRequestDto;
import mate.academy.carsharing.dto.rental.RentalResponseDto;
import mate.academy.carsharing.model.User;
import mate.academy.carsharing.service.RentalService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    RentalResponseDto createRental(@RequestBody @Valid RentalRequestDto requestDto,
                                   Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return rentalService.createRental(requestDto, currentUser.getId());
    }

    @GetMapping("/{id}")
    RentalResponseDto getById(@PathVariable Long id) {
        return rentalService.getById(id);
    }

    @PostMapping("/{id}/return")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void setRentalActualReturnDate(@PathVariable Long id) {
        rentalService.setActualReturnDate(id, LocalDate.now());
    }

    @GetMapping
    List<RentalResponseDto> getRentals(Pageable pageable,
                                       @RequestParam(value = "user_id",
                                               required = false) Long userId,
                                       @RequestParam(value = "is_active",
                                       required = false) Boolean isActive) {
        return rentalService.getRentals(pageable, userId, isActive);
    }
}
