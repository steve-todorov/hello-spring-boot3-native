package com.example.spring.boot;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(setterPrefix = "with")
@ToString
public class LombokExample
{

    private String message;

}
