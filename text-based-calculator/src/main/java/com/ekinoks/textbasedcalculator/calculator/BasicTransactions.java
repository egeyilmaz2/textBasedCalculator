package com.ekinoks.textbasedcalculator.calculator;

import com.ekinoks.textbasedcalculator.handler.ExceptionMassages;
import com.ekinoks.textbasedcalculator.handler.ApplicationException;

import java.util.Arrays;
import java.util.List;

public class BasicTransactions {
    public static Integer addition(Integer ... ints ){
        Integer result= 0;
        for(Integer intIterator: Arrays.stream(ints).toList()){
            result+=intIterator;
        }
        return result;
    }
    public static Integer differance(Integer ... ints ){
        //its left to right differance transaction
        List<Integer> integerList = Arrays.stream(ints).toList();
        Integer result= integerList.get(0);
        if(integerList.size()>1){
            for(Integer intIterator: integerList.subList(1,integerList.size())){
                result-=intIterator;
            }
        }
        return result;
    }

    public static Integer multiplier(Integer ... ints ){
        List<Integer> integerList = Arrays.stream(ints).toList();
        if(integerList.contains(0)){
            return 0;
        }
        Integer result = 1;

        for(Integer intIterator: integerList){
            result*=intIterator;
        }

        return result;
    }

    public static Integer divide(Integer ... ints ){
        List<Integer> integerList = Arrays.stream(ints).toList();
        if(integerList.get(0).equals(0)){
            return 0;
        }
        Integer result = integerList.get(0);

        if(integerList.size()>1){
            integerList = integerList.subList(1,integerList.size());

            if(integerList.contains(0)){
                throw new ApplicationException(ExceptionMassages.CONNOT_DIVIDE_ZERO.getExceptionCode(),ExceptionMassages.CONNOT_DIVIDE_ZERO.getExceptionMessage());
            }

            for(Integer intIterator: integerList){
                result/=intIterator;
            }
        }


        return result;
    }
}
