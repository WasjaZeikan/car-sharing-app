package mate.academy.carsharing.service;

import mate.academy.carsharing.dto.user.UserLoginRequestDto;
import mate.academy.carsharing.dto.user.UserLoginResponseDto;
import mate.academy.carsharing.dto.user.UserRegistrationRequestDto;
import mate.academy.carsharing.dto.user.UserRegistrationResponseDto;
import mate.academy.carsharing.exception.RegistrationException;

public interface AuthenticationService {
    UserLoginResponseDto login(UserLoginRequestDto requestDto);

    UserRegistrationResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;
}
