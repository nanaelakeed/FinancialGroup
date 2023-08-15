package com.example.FinancialGroup.service;


import com.example.FinancialGroup.dao.UserRepository;
import com.example.FinancialGroup.dto.ApiResponseDto;
import com.example.FinancialGroup.dto.GroupDto;
import com.example.FinancialGroup.dto.UserDto;
import com.example.FinancialGroup.entity.Group;
import com.example.FinancialGroup.entity.User;
import com.example.FinancialGroup.enums.StatusCode;
import com.example.FinancialGroup.enums.StatusMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;




    private boolean validByEmail(String email){
        User user=this.userRepository.findByEmail(email);
        return user != null;
    }


    public ApiResponseDto saveUser(UserDto userDto){
        ApiResponseDto responseDto=new ApiResponseDto<>();
        User toBeSavedUser=this.modelMapper.map(userDto,User.class);
        toBeSavedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if(!this.validByEmail(userDto.getEmail())){
            responseDto=ApiResponseDto.builder()
                    .responseData(this.userRepository.save(toBeSavedUser))
                    .code(StatusCode.SUCCESS)
                    .message(StatusMessage.CREATED)
                    .build();
        }else{
            responseDto.setCode(StatusCode.ALREADY_EXISTS);
            responseDto.setMessage(StatusMessage.ALREADY_EXISTS);
        }
        return responseDto;
    }


    public ApiResponseDto getAllUsers() {
        return ApiResponseDto
                .builder()
                .responseData(this.userRepository.findAll())
                .code(StatusCode.SUCCESS)
                .message(StatusMessage.SUCCESS)
                .build();
    }

    public ApiResponseDto getUserById(Long id){
        User user=this.userRepository.getById(id);
        ApiResponseDto responseDto=new ApiResponseDto<>();
        if(user!=null) {
            responseDto = ApiResponseDto.builder()
                    .responseData(this.userRepository.findById(id))
                    .message(StatusMessage.SUCCESS)
                    .code(StatusCode.SUCCESS)
                    .build();
        }
        else{
           responseDto=ApiResponseDto.builder()
                   .message(StatusMessage.NOT_FOUND)
                   .code(StatusCode.NOT_FOUND)
                   .responseData(null)
                   .build();
        }
        return responseDto;
    }




    public ApiResponseDto getUserGroups(Long id){
        User user=this.userRepository.findById(id).orElse(null);
        ApiResponseDto responseDto=new ApiResponseDto<>();
        List<Group> g=this.userRepository.getGroups(id);
        if(user!=null){
            List<GroupDto> groups= this.userRepository.getGroups(id)
                    .stream()
                    .map(group -> this.modelMapper.map(group,GroupDto.class))
                    .collect(Collectors.toList());
            responseDto=ApiResponseDto.builder()
                    .responseData(groups)
                    .code(StatusCode.SUCCESS)
                    .message(StatusMessage.SUCCESS)
                    .build();
        }else{
            responseDto=ApiResponseDto.builder()
                    .message(StatusMessage.NOT_FOUND)
                    .code(StatusCode.NOT_FOUND)
                    .responseData(null)
                    .build();
        }
        return responseDto;
    }

    public ApiResponseDto deleteUser(Long id){
        ApiResponseDto responseDto=new ApiResponseDto<>();
        if(this.userRepository.findById(id)!=null){
            this.userRepository.deleteById(id);
            responseDto=ApiResponseDto.builder()
                    .responseData("Deleted")
                    .message(StatusMessage.SUCCESS)
                    .code(StatusCode.SUCCESS)
                    .build();
        }
        else{
            responseDto.setCode(StatusCode.NOT_FOUND);
            responseDto.setMessage(StatusMessage.NOT_FOUND);
        }
        return responseDto;
    }
}
