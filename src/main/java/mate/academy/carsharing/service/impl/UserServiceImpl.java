package mate.academy.carsharing.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.carsharing.dto.user.UserRegistrationResponseDto;
import mate.academy.carsharing.dto.user.UserRequestDto;
import mate.academy.carsharing.dto.user.UserRolesRequestDto;
import mate.academy.carsharing.exception.EntityNotFoundException;
import mate.academy.carsharing.mapper.UserMapper;
import mate.academy.carsharing.model.User;
import mate.academy.carsharing.repository.UserRepository;
import mate.academy.carsharing.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateUserRoles(Long userId, UserRolesRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find user with id: " + userId));
        user.setRoles(Set.of(requestDto.roles()));
        userRepository.save(user);
    }

    @Override
    public UserRegistrationResponseDto getUserDto(User currentUser) {
        return userMapper.toDto(currentUser);
    }

    @Override
    public void updateUser(UserRequestDto requestDto, User currentUser) {
        UserRequestDto userRequestDto = new UserRequestDto(
                passwordEncoder.encode(requestDto.password()),
                requestDto.firstName(), requestDto.lastName());
        userMapper.updateUserFromDto(userRequestDto, currentUser);
        userRepository.save(currentUser);
    }
}
