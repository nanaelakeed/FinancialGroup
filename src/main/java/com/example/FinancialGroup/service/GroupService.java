package com.example.FinancialGroup.service;


import com.example.FinancialGroup.dao.GroupRepository;
import com.example.FinancialGroup.dto.ApiResponseDto;
import com.example.FinancialGroup.dto.GroupDto;
import com.example.FinancialGroup.dto.UserDto;
import com.example.FinancialGroup.entity.Group;
import com.example.FinancialGroup.enums.StatusCode;
import com.example.FinancialGroup.enums.StatusMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    @PersistenceContext
    private EntityManager entityManager;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;


    public ApiResponseDto getAllGroups(){
        ApiResponseDto responseDto=new ApiResponseDto<>();
        List<GroupDto> groupDtos=this.groupRepository.findAll()
                .stream()
                .map(group -> this.modelMapper.map(group,GroupDto.class))
                .collect(Collectors.toList());
        responseDto=ApiResponseDto.builder()
                .responseData(groupDtos)
                .message(StatusMessage.SUCCESS)
                .build();
        return responseDto;
    }

    public ApiResponseDto saveGroup(Group group) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        responseDto = ApiResponseDto.builder()
                .responseData(this.modelMapper.map(this.groupRepository.save(group), GroupDto.class))
                .message(StatusMessage.CREATED)
                .code(StatusCode.SUCCESS)
                .build();
        return responseDto;
    }

    @Transactional
    public ApiResponseDto saveUserGroup(Long group_id,Long user_id){
        this.entityManager.createNativeQuery("insert into group_users (group_id,user_id) values (?,?)")
                .setParameter(1,group_id)
                .setParameter(2,user_id)
                .executeUpdate();
        ApiResponseDto responseDto=ApiResponseDto.builder()
                .message(StatusMessage.SUCCESS)
                .build();
        return responseDto;
    }

    public ApiResponseDto deleteGroup(Long id) {
        ApiResponseDto responseDto = new ApiResponseDto<>();
        if (this.groupRepository.findById(id).isPresent()) {
            this.groupRepository.deleteById(id);
            responseDto.setMessage(StatusMessage.DELETED);
            responseDto.setCode(StatusCode.SUCCESS);
        } else {
            responseDto.setMessage(StatusMessage.NOT_FOUND);
            responseDto.setCode(StatusCode.NOT_FOUND);
        }
        return responseDto;
    }

    public ApiResponseDto groupUsers(Long groupId){
        ApiResponseDto responseDto=new ApiResponseDto<>();
        if(this.groupRepository.findById(groupId).isPresent()){
            responseDto.setResponseData(this.groupRepository.getAllByGroupId(groupId)
                    .stream()
                    .map(user -> this.modelMapper.map(user,UserDto.class))
                    .collect(Collectors.toList()));
            responseDto.setCode(StatusCode.SUCCESS);
            responseDto.setMessage(StatusMessage.SUCCESS);
        }else{
            responseDto.setResponseData(null);
            responseDto.setCode(StatusCode.NOT_FOUND);
        }
        return responseDto;
    }

}
