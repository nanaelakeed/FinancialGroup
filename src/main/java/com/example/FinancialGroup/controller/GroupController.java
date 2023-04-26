package com.example.FinancialGroup.controller;

import com.example.FinancialGroup.dto.ApiResponseDto;
import com.example.FinancialGroup.entity.Group;
import com.example.FinancialGroup.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public ApiResponseDto getAllGroups(){return this.groupService.getAllGroups();}

    @PostMapping
    public ApiResponseDto saveGroup(@RequestBody Group group){
        return this.groupService.saveGroup(group);
    }

    @DeleteMapping(path = "/{id}")
    public ApiResponseDto deleteGroup(@PathVariable Long id){return this.groupService.deleteGroup(id);}
}
