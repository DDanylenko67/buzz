package ntukhpi.ddy.buzz.service;


import ntukhpi.ddy.buzz.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}