package com.ekinoks.textbasedcalculator.text_num_complier;

import com.ekinoks.textbasedcalculator.data_transfer.xml.LanguageXML;
import com.ekinoks.textbasedcalculator.handler.ApplicationException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TextNumberCompiler {
    private  String language = "turkish";

    private Boolean minus;

    private Boolean oneAllowed;
    private  HashMap<String,String> tensNumberHolder = new HashMap<>();
    private  HashMap<String,String> specialNumberHolder = new HashMap<>();

    private  HashMap<String,String> tenPowerHolder = new HashMap<>();


    public TextNumberCompiler(){
        holderFiller();
    }
    public  Integer textToNUmber(String numberString){

        Integer result= 0;
        List<String> textListLeftToRight = Arrays.stream(numberString.split(" ")).toList();

        Integer currentDigit=0;
        boolean tenPower=false;
        for(int i = 0; i<textListLeftToRight.size();i+=1){
            String numText = textListLeftToRight.get(i);

            if(tensNumberHolder.containsKey(numText)) {
                if(tenPower){
                    result+=currentDigit;
                    currentDigit=0;
                    tenPower=false;
                }
                Integer number = Integer.parseInt(tensNumberHolder.get(numText));
                currentDigit += number;
            }
            else if(specialNumberHolder.containsKey(numText)) {
                if(tenPower){
                    result+=currentDigit;
                    currentDigit=0;
                    tenPower=false;
                }
                Integer number = Integer.parseInt(specialNumberHolder.get(numText));
                currentDigit+= number;
            }

            else if(tenPowerHolder.containsKey(numText)){
                Integer number = Integer.parseInt(tenPowerHolder.get(numText));
                currentDigit =  (int)(currentDigit * Math.pow(10,number));
                if(number>=3){
                    tenPower=true;
                }
            }

            if(i==textListLeftToRight.size()-1 && currentDigit>0){
                result+=currentDigit;
            }

        }
        System.out.println(result);
        return result;
    }

    public String numberToText(String numberString){

        Map<String, String> tensNumberHolderInversed =
                tensNumberHolder.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        Map<String, String> specialNumberHolderInversed =
                specialNumberHolder.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        Map<String, String> tenPowerHolderInversed =
                tenPowerHolder.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        boolean minus = false;
        List<String> threePartialList = new ArrayList<>();

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        df.setDecimalFormatSymbols(symbols);
        df.setGroupingSize(3);

        Integer integerValue;
        if(!numberString.contains("-")){
            integerValue = Integer.parseInt(numberString);
        }
        else{
            minus = true;
            integerValue = Integer.parseInt(numberString.substring(1));
        }

        String formated = df.format(integerValue);

        threePartialList = Arrays.stream(formated.split(" ")).toList();

        List<String> result = new ArrayList<>();

        for(int i=0;i<threePartialList.size();i+=1){
            String current=threePartialList.get(i);
            int currentLength = current.length();

            boolean specCheck = false;
            if(currentLength>=3){
                if(current.charAt(0) == '1' && !oneAllowed){
                    result.add(tenPowerHolderInversed.get("2"));
                }else{
                    result.add(specialNumberHolderInversed.get(String.valueOf(current.charAt(0))));
                    result.add(tenPowerHolderInversed.get("2"));
                }
            }
            if(currentLength>=2){
                String specialTenControl = String.valueOf(current.charAt(currentLength-2)+current.charAt(currentLength-1));
                if(specialNumberHolderInversed.containsKey(specialTenControl)){
                    result.add(specialNumberHolderInversed.get(specialTenControl));
                    specCheck = true;
                }

                int tens = Integer.parseInt(String.valueOf(current.charAt(currentLength-2))) * 10;
                if(tens !=0){
                    result.add(tensNumberHolderInversed.get(String.valueOf(tens)));
                }
            }

            if(current.length()>=1 && !specCheck){
                String specNumber = String.valueOf(current.charAt(currentLength-1));

                if(!specNumber.equals("0")){
                      result.add(specialNumberHolderInversed.get(specNumber)) ;
                }

            }

            int tenPower = (threePartialList.size() - i - 1) * 3;
            String tenPowerStr = String.valueOf(tenPower);

            if(tenPower>2 && tenPowerHolderInversed.containsKey(tenPowerStr)){
                if(tenPower == 3 &&current.equals("1") && !oneAllowed){
                    result.remove(result.size()-1);
                }
                result.add(tenPowerHolderInversed.get(tenPowerStr));
            }
        }
        System.out.println(String.join(" ",result));
        return null;
    }

    private String changeLanguage(String changedLanguage){
       language=changedLanguage;

       holderFiller();

       return language;
    }

    private void holderFiller(){


        JAXBContext jaxbContext;
        LanguageXML languageXML;
        try
        {
            InputStream xmlStream = new ClassPathResource("locale/"+language+".xml").getInputStream();

            jaxbContext = JAXBContext.newInstance(LanguageXML.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            languageXML = (LanguageXML) unmarshaller.unmarshal(xmlStream);

        }
        catch (JAXBException e)
        {
            throw new ApplicationException("","");
        }
        catch (Exception e)
        {
           throw new ApplicationException("","");
        }
        if(Objects.nonNull(languageXML)){
            languageXML.getNumbersXML().getNumberXML().forEach(iterator->specialNumberHolder.put(iterator.getTextXML().getTextValue(),iterator.getValueXML().getValue()));

            languageXML.getTensXML().getNumberXML().forEach(iterator->tensNumberHolder.put(iterator.getTextXML().getTextValue(),iterator.getValueXML().getValue()));

            languageXML.getTenPowerXML().getDigitXML().forEach(iterator->tenPowerHolder.put(iterator.getTextXML().getTextValue(),iterator.getPowerXML().getPowerValue()));

            minus = languageXML.getMinusXML().getTextValue().equals("true");
            oneAllowed = languageXML.getOneAllowed().equals("true");

        }
    }


}
