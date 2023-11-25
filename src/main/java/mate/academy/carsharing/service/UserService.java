package mate.academy.carsharing.service;

import mate.academy.carsharing.dto.user.UserRegistrationResponseDto;
import mate.academy.carsharing.dto.user.UserRequestDto;
import mate.academy.carsharing.dto.user.UserRolesRequestDto;
import mate.academy.carsharing.model.User;

public interface UserService {
    void updateUserRoles(Long userId, UserRolesRequestDto requestDto);

    UserRegistrationResponseDto getUserDto(User currentUser);

    void updateUser(UserRequestDto requestDto, User currentUser);
}
