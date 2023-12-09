package mate.academy.carsharing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import mate.academy.carsharing.dto.rental.RentalRequestDto;
import mate.academy.carsharing.dto.rental.RentalResponseDto;
import mate.academy.carsharing.exception.RentalException;
import mate.academy.carsharing.mapper.RentalMapper;
import mate.academy.carsharing.model.Car;
import mate.academy.carsharing.model.Rental;
import mate.academy.carsharing.repository.CarRepository;
import mate.academy.carsharing.repository.RentalRepository;
import mate.academy.carsharing.service.impl.RentalServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {
    @InjectMocks
    private RentalServiceImpl rentalService;
    @Mock
    private RentalMapper rentalMapper;
    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private CarRepository carRepository;

    @Test
    void createRental_ok_validRentalRequestDto() {
        RentalRequestDto requestDto = new RentalRequestDto(1L, 20L);

        RentalResponseDto expected = new RentalResponseDto(1L,
                LocalDate.now(),
                LocalDate.now().plusDays(20L),
                LocalDate.now(),
                1L,
                1L);

        Rental rental = new Rental();
        rental.setId(1L);
        rental.setActualReturnDate(LocalDate.now());
        rental.setRentalDate(LocalDate.now());
        rental.setUserId(1L);
        rental.setCarId(1L);
        rental.setReturnDate(LocalDate.now().plusDays(20L));

        Car car = new Car();
        car.setInventory(3);
        car.setId(1L);

        when(rentalMapper.toDto(any(Rental.class))).thenReturn(expected);
        when(rentalRepository.save(any(Rental.class))).thenReturn(rental);
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(carRepository.save(any(Car.class))).thenReturn(car);

        RentalResponseDto actual = rentalService.createRental(requestDto, 1L);

        assertEquals(expected, actual);
        verify(carRepository, times(1)).save(any(Car.class));
        verify(carRepository, times(1)).findById(anyLong());
        verify(rentalMapper, times(1)).toDto(any(Rental.class));
        verify(rentalRepository, times(1)).save(any(Rental.class));
        verifyNoMoreInteractions(carRepository, rentalRepository, rentalRepository);
    }

    @Test
    void createRental_notOk_throwRentalException() {
        Car car = new Car();
        car.setId(1L);
        car.setInventory(0);
        RentalRequestDto requestDto = new RentalRequestDto(1L, 10L);
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));

        assertThrows(RentalException.class, () -> rentalService.createRental(requestDto, 1L),
                "Car with id: 1 is not available");
        verify(carRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(carRepository);
    }

    @Test
    void getById_ok_returnValidRentalResponseDto() {
        Rental rental = new Rental();
        rental.setId(1L);
        rental.setCarId(1L);
        rental.setUserId(1L);
        rental.setRentalDate(LocalDate.now());
        rental.setReturnDate(LocalDate.now().plusDays(10L));

        RentalResponseDto expected = new RentalResponseDto(1L, LocalDate.now(),
                LocalDate.now().plusDays(10L),
                null,
                1L,
                1L);

        when(rentalRepository.findById(anyLong())).thenReturn(Optional.of(rental));
        when(rentalMapper.toDto(any(Rental.class))).thenReturn(expected);

        RentalResponseDto actual = rentalService.getById(1L);

        assertEquals(expected, actual);
        verify(rentalMapper, times(1)).toDto(any(Rental.class));
        verify(rentalRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(rentalMapper, rentalRepository);
    }

    @Test
    void setActualReturnDate_ok_validRentalId() {
        Rental rental = new Rental();
        rental.setId(1L);
        rental.setRentalDate(LocalDate.now());
        rental.setReturnDate(LocalDate.now().plusDays(20L));
        rental.setCarId(1L);
        rental.setUserId(1L);

        Car car = new Car();
        car.setId(1L);
        car.setInventory(3);

        when(rentalRepository.findById(anyLong())).thenReturn(Optional.of(rental));
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(rentalRepository.save(any(Rental.class))).thenReturn(rental);
        when(carRepository.save(any(Car.class))).thenReturn(car);

        rentalService.setActualReturnDate(1L, LocalDate.now().plusDays(10L));

        assertEquals(LocalDate.now().plusDays(10L), rental.getActualReturnDate());
        assertEquals(4, car.getInventory());
        verify(rentalRepository, times(1)).findById(anyLong());
        verify(rentalRepository, times(1)).save(any(Rental.class));
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any(Car.class));
        verifyNoMoreInteractions(carRepository, rentalRepository);
    }

    @Test
    void getRentals_ok_AllThreeParameter() {
        Rental rental = new Rental();
        rental.setId(1L);
        rental.setActualReturnDate(LocalDate.now());
        rental.setRentalDate(LocalDate.now());
        rental.setUserId(1L);
        rental.setCarId(1L);
        rental.setReturnDate(LocalDate.now().plusDays(20L));

        RentalResponseDto expected = new RentalResponseDto(1L,
                LocalDate.now(),
                LocalDate.now().plusDays(20L),
                LocalDate.now(),
                1L,
                1L);

        Pageable pageable = PageRequest.of(0, 20);

        Page<Rental> rentalPage = new PageImpl<>(List.of(rental), pageable, 1L);

        when(rentalRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(rentalPage);
        when(rentalMapper.toDto(any())).thenReturn(expected);

        List<RentalResponseDto> actual = rentalService.getRentals(pageable, 1L, true);

        assertEquals(expected, actual.get(0));
        assertEquals(1, actual.size());
        verify(rentalRepository, times(1))
                .findAll(any(Specification.class), any(Pageable.class));
        verify(rentalMapper, times(1)).toDto(any());
        verifyNoMoreInteractions(rentalRepository, rentalMapper);
    }
}