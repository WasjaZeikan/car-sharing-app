package mate.academy.carsharing.mapper;

import mate.academy.carsharing.config.MapperConfig;
import mate.academy.carsharing.dto.car.CarRequestDto;
import mate.academy.carsharing.dto.car.CarResponseDto;
import mate.academy.carsharing.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CarMapper {
    CarResponseDto toDto(Car car);

    Car toModel(CarRequestDto requestDto);

    void updateCarFromDto(CarRequestDto requestDto, @MappingTarget Car car);
}
