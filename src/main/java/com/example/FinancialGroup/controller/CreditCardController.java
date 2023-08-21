package com.example.FinancialGroup.controller;


import com.example.FinancialGroup.dto.ApiResponseDto;
import com.example.FinancialGroup.dto.CardDto;
import com.example.FinancialGroup.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CreditCardController {

    private final CardService cardService;


    @PostMapping
    public ApiResponseDto saveCard(@RequestBody CardDto card){
        return this.cardService.saveCard(card);
    }

    @GetMapping
    public ApiResponseDto getAllCards(){
        return this.cardService.getAllCards();
    }

    @GetMapping("/{userId}")
    private ApiResponseDto gerUserCards(@PathVariable Long userId){
        return this.cardService.getUserCards(userId);
    }

}
