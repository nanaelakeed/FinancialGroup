package com.example.FinancialGroup.service;


import com.example.FinancialGroup.dao.UserRepository;
import com.example.FinancialGroup.dto.ApiResponseDto;
import com.example.FinancialGroup.dto.GroupDto;
import com.example.FinancialGroup.dto.LoginRequestDto;
import com.example.FinancialGroup.dto.UserDto;
import com.example.FinancialGroup.entity.Group;
import com.example.FinancialGroup.entity.User;
import com.example.FinancialGroup.enums.StatusCode;
import com.example.FinancialGroup.enums.StatusMessage;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${auth.secret}")
    private String secret;

    private static Key key;




    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @PostConstruct
    private void securityInit(){
        key = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
    }

    private boolean validByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        return user != null;
    }



    public ApiResponseDto saveUser(UserDto userDto) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        User toBeSavedUser = this.modelMapper.map(userDto, User.class);
        toBeSavedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (!this.validByEmail(userDto.getEmail())) {
            responseDto = ApiResponseDto.builder()
                    .responseData(this.userRepository.save(toBeSavedUser))
                    .code(StatusCode.SUCCESS)
                    .message(StatusMessage.CREATED)
                    .build();
        } else {
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

    public ApiResponseDto getUserById(Long id) {
        User user = this.userRepository.getById(id);
        ApiResponseDto responseDto = new ApiResponseDto<>();
        if (user != null) {
            responseDto = ApiResponseDto.builder()
                    .responseData(this.userRepository.findById(id))
                    .message(StatusMessage.SUCCESS)
                    .code(StatusCode.SUCCESS)
                    .build();
        } else {
            responseDto = ApiResponseDto.builder()
                    .message(StatusMessage.NOT_FOUND)
                    .code(StatusCode.NOT_FOUND)
                    .responseData(null)
                    .build();
        }
        return responseDto;
    }


    public ApiResponseDto getUserGroups(Long id) {
        User user = this.userRepository.findById(id).orElse(null);
        ApiResponseDto responseDto = new ApiResponseDto<>();
        List<Group> g = this.userRepository.getGroups(id);
        if (user != null) {
            List<GroupDto> groups = this.userRepository.getGroups(id)
                    .stream()
                    .map(group -> this.modelMapper.map(group, GroupDto.class))
                    .collect(Collectors.toList());
            responseDto = ApiResponseDto.builder()
                    .responseData(groups)
                    .code(StatusCode.SUCCESS)
                    .message(StatusMessage.SUCCESS)
                    .build();
        } else {
            responseDto = ApiResponseDto.builder()
                    .message(StatusMessage.NOT_FOUND)
                    .code(StatusCode.NOT_FOUND)
                    .responseData(null)
                    .build();
        }
        return responseDto;
    }

    public ApiResponseDto deleteUser(Long id) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        if (this.userRepository.findById(id).isPresent()) {
            this.userRepository.deleteById(id);
            responseDto = ApiResponseDto.builder()
                    .responseData("Deleted")
                    .message(StatusMessage.SUCCESS)
                    .code(StatusCode.SUCCESS)
                    .build();
        } else {
            responseDto.setCode(StatusCode.NOT_FOUND);
            responseDto.setMessage(StatusMessage.NOT_FOUND);
        }
        return responseDto;
    }

    public ApiResponseDto login(LoginRequestDto loginRequestDto) {
        if (validByEmail(loginRequestDto.getEmail())) {
            User user = this.userRepository.findByEmail(loginRequestDto.getEmail());
            if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
                return ApiResponseDto.builder()
                        .responseData(user.getId())
                        .message(StatusMessage.SUCCESS)
                        .build();
            } else {
                return ApiResponseDto.builder()
                        .responseData(null)
                        .message(StatusMessage.Incorrect_password)
                        .code(StatusCode.ERROR)
                        .build();
            }
        } else {
            return ApiResponseDto.builder()
                    .message(StatusMessage.Invalid_email)
                    .build();
        }
    }

    public ApiResponseDto jwtLogin(LoginRequestDto loginRequestDto){
        User user=this.userRepository.findByEmail(loginRequestDto.getEmail());
        if(user==null){
            return ApiResponseDto.builder()
                    .message(StatusMessage.Invalid_email)
                    .build();
        }
        else{
            boolean correctPass=this.passwordEncoder.matches(loginRequestDto.getPassword(),user.getPassword());
            if(correctPass){
                String jwtToken = Jwts.builder()
                        .claim("email", loginRequestDto.getEmail())
                        .claim("password",loginRequestDto.getPassword())
                        .setSubject(loginRequestDto.getEmail())
                        .setId(UUID.randomUUID().toString())
                        .setIssuedAt(Date.from(Instant.now()))
                        .setExpiration(Date.from(Instant.now().plus(5L, ChronoUnit.MINUTES)))
                        .signWith(key)
                        .compact();
                user.setSession_id(jwtToken);
                this.userRepository.save(user);
                return ApiResponseDto.builder()
                        .responseData(jwtToken)
                        .message(StatusMessage.SUCCESS)
                        .build();
            }
            else{
                return ApiResponseDto.builder()
                        .responseData(null)
                        .message(StatusMessage.Incorrect_password)
                        .code(StatusCode.ERROR)
                        .build();
            }
        }
    }
}
