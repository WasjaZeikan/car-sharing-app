package mate.academy.carsharing.service;

import java.util.List;
import mate.academy.carsharing.dto.car.CarRequestDto;
import mate.academy.carsharing.dto.car.CarResponseDto;
import org.springframework.data.domain.Pageable;

public interface CarService {
    CarResponseDto createCar(CarRequestDto requestDto);

    CarResponseDto getCarById(Long carId);

    List<CarResponseDto> getAllCars(Pageable pageable);

    void updateCar(Long carId, CarRequestDto requestDto);

    void deleteCar(Long carId);
}
