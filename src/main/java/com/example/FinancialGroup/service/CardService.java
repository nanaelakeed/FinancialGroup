package com.example.FinancialGroup.service;


import com.example.FinancialGroup.dao.CreditCardRepository;
import com.example.FinancialGroup.dto.ApiResponseDto;
import com.example.FinancialGroup.dto.CardDto;
import com.example.FinancialGroup.entity.CreditCard;
import com.example.FinancialGroup.enums.StatusCode;
import com.example.FinancialGroup.enums.StatusMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final CreditCardRepository creditCardRepository;
    private final ModelMapper modelMapper;

    public ApiResponseDto saveCard(CardDto card){
        if(this.creditCardRepository.findCreditCardByCreditNumber(card.getCreditNumber())!=null){
            return ApiResponseDto.builder().message(StatusMessage.ALREADY_EXISTS).build();
        }else{
            if(card.getCreditNumber().length()<15){
                return ApiResponseDto.builder()
                        .message(StatusMessage.Credit_Card_number_Invalid)
                        .code(StatusCode.ERROR)
                        .build();
            }
            else {
                CreditCard toBeSavedCard=this.modelMapper.map(card,CreditCard.class);
                toBeSavedCard.setCreditNumber(passwordEncoder.encode(card.getCreditNumber()));
                return ApiResponseDto.builder().responseData(this.creditCardRepository.save(toBeSavedCard))
                        .message(StatusMessage.CREATED)
                        .code(StatusCode.SUCCESS)
                        .build();
            }
        }
    }


    public ApiResponseDto getAllCards(){
        return ApiResponseDto.builder()
                .responseData(this.creditCardRepository.findAll()
                        .stream()
                        .map(card -> this.modelMapper.map(card,CardDto.class))
                        .collect(Collectors.toList())
                )
                .message(StatusMessage.SUCCESS)
                .build();
    }

}
