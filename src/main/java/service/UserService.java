package service;

import constants.Constants;
import dto.UserDTO;
import entity.User;
import exceptions.SomethingWentWrong;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapper.UserMapper;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO getUser(Integer userId){
        return userRepository
                .findById(userId)
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new NoSuchElementException(Constants.ExceptionDescriptions.NO_SUCH_ELEMENT));
    }

    public UserDTO saveUser(User user){
        try {
            return userMapper.toUserDto(userRepository.save(user));
        } catch (Exception e){
            log.error("Error saving user " + user);
            throw new SomethingWentWrong(e.getMessage());
        }
    }

    public UserDTO updateUser(User user, Integer timeSheet){
        user.setTimeSheet(timeSheet);
        return userMapper.toUserDto(userRepository.save(user));
    }
}
