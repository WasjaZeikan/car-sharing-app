package mate.academy.carsharing.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.carsharing.dto.user.UserRegistrationResponseDto;
import mate.academy.carsharing.dto.user.UserRequestDto;
import mate.academy.carsharing.dto.user.UserRolesRequestDto;
import mate.academy.carsharing.model.User;
import mate.academy.carsharing.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/role")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void updateUserRoles(@RequestBody @Valid UserRolesRequestDto requestDto,
                         @PathVariable("id") Long userId) {
        userService.updateUserRoles(userId, requestDto);
    }

    @GetMapping("/me")
    UserRegistrationResponseDto getCurrentUser(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return userService.getUserDto(currentUser);
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void updateUser(@RequestBody UserRequestDto userRequestDto, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        userService.updateUser(userRequestDto, currentUser);
    }
}
