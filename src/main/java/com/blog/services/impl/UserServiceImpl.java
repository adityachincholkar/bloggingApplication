package com.blog.services.impl;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        User savedUser =  userRepo.save(user);
        return UserToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User" ,"Id" , userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = userRepo.save(user);
        UserDto userDto1 = UserToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById( Integer userId) {
       User user = userRepo.findById(userId)
               .orElseThrow(()-> new  ResourceNotFoundException("User" , " Id " ,userId));

       return UserToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtoList = users.stream().map(user-> UserToDto(user)).toList();
        return userDtoList;
    }

    @Override
    public void deleteUser(Integer userId) {
      User user =  userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User" , " Id " , userId));
      userRepo.delete(user);
    }

    private User dtoToUser(UserDto userDto){
    User user = modelMapper.map(userDto,User.class);
    return user;
    }

    public UserDto UserToDto(User user){
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
