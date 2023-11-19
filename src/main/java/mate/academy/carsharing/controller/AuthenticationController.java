package mate.academy.carsharing.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.carsharing.dto.user.UserLoginRequestDto;
import mate.academy.carsharing.dto.user.UserLoginResponseDto;
import mate.academy.carsharing.dto.user.UserRegistrationRequestDto;
import mate.academy.carsharing.dto.user.UserRegistrationResponseDto;
import mate.academy.carsharing.exception.RegistrationException;
import mate.academy.carsharing.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.login(requestDto);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegistrationResponseDto register(@RequestBody @Valid
                                                    UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return authenticationService.register(requestDto);
    }
}
