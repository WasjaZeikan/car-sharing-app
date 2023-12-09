package mate.academy.carsharing.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.carsharing.dto.rental.RentalRequestDto;
import mate.academy.carsharing.dto.rental.RentalResponseDto;
import mate.academy.carsharing.exception.EntityNotFoundException;
import mate.academy.carsharing.exception.RentalException;
import mate.academy.carsharing.mapper.RentalMapper;
import mate.academy.carsharing.model.Car;
import mate.academy.carsharing.model.Rental;
import mate.academy.carsharing.repository.CarRepository;
import mate.academy.carsharing.repository.RentalRepository;
import mate.academy.carsharing.service.RentalService;
import mate.academy.carsharing.specification.RentalSpecifications;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private final RentalMapper rentalMapper;

    @Override
    public RentalResponseDto createRental(RentalRequestDto requestDto, Long userId) {
        Long carId = requestDto.carId();
        Car car = carRepository.findById(requestDto.carId()).orElseThrow(
                () -> new EntityNotFoundException("Can't find a car with id: " + carId));
        if (car.getInventory() <= 0) {
            throw new RentalException("Car with id: " + carId + " is not available");
        }
        Rental rental = new Rental();
        rental.setRentalDate(LocalDate.now());
        rental.setReturnDate(LocalDate.now().plusDays(requestDto.daysToReturn()));
        rental.setCarId(carId);
        rental.setUserId(userId);
        rentalRepository.save(rental);
        car.setInventory(car.getInventory() - 1);
        carRepository.save(car);
        return rentalMapper.toDto(rental);
    }

    @Override
    public RentalResponseDto getById(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(
                () -> new EntityNotFoundException("Can't find a rental with id: " + rentalId));
        return rentalMapper.toDto(rental);
    }

    @Override
    public void setActualReturnDate(Long rentalId, LocalDate returnDate) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(
                () -> new EntityNotFoundException("Can't find a rental with id: " + rentalId));
        if (rental.getActualReturnDate() != null) {
            throw new RentalException("Rental with id: " + rentalId + " is not active");
        }
        rental.setActualReturnDate(returnDate);
        Car car = carRepository.findById(rental.getCarId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find a car with id: " + rental.getCarId()));
        car.setInventory(car.getInventory() + 1);
        carRepository.save(car);
        rentalRepository.save(rental);
    }

    @Override
    public List<RentalResponseDto> getRentals(Pageable pageable,
                                              Long userId,
                                              Boolean isActiveRental) {
        List<Specification<Rental>> specifications = new ArrayList<>();
        if (isActiveRental != null) {
            specifications.add(RentalSpecifications.getSpecificationByIsActive(isActiveRental));
        }
        if (userId != null) {
            specifications.add(RentalSpecifications.getSpecificationByUserId(userId));
        }
        return rentalRepository.findAll(Specification.allOf(specifications), pageable).stream()
                .map(rentalMapper::toDto)
                .toList();
    }
}
