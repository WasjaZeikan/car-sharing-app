package mate.academy.carsharing.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.carsharing.dto.car.CarRequestDto;
import mate.academy.carsharing.dto.car.CarResponseDto;
import mate.academy.carsharing.exception.EntityNotFoundException;
import mate.academy.carsharing.mapper.CarMapper;
import mate.academy.carsharing.model.Car;
import mate.academy.carsharing.repository.CarRepository;
import mate.academy.carsharing.service.CarService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public CarResponseDto createCar(CarRequestDto requestDto) {
        Car car = carMapper.toModel(requestDto);
        carRepository.save(car);
        return carMapper.toDto(car);
    }

    @Override
    public CarResponseDto getCarById(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(
                () -> new EntityNotFoundException("Can't find a car with id: " + carId));
        return carMapper.toDto(car);
    }

    @Override
    public List<CarResponseDto> getAllCars(Pageable pageable) {
        return carRepository.findAll(pageable).stream()
                .map(carMapper::toDto)
                .toList();
    }

    @Override
    public void updateCar(Long carId, CarRequestDto requestDto) {
        Car car = carRepository.findById(carId).orElseThrow(
                () -> new EntityNotFoundException("Can't find a car with id: " + carId));
        carMapper.updateCarFromDto(requestDto, car);
        carRepository.save(car);
    }

    @Override
    public void deleteCar(Long carId) {
        carRepository.deleteById(carId);
    }
}
