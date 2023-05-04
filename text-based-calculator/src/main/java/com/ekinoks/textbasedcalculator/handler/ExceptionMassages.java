package com.ekinoks.textbasedcalculator.handler;

import lombok.Getter;

@Getter
public enum ExceptionMassages {
    CONNOT_DIVIDE_ZERO("CONNOT_DIVIDE_ZERO.ERROR"," A number cannot divide to zero!")
    ;
    private  String exceptionCode;
    private String exceptionMessage;

    ExceptionMassages(String exceptionCode,String exceptionMessage) {
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
    }
}
