package com.ekinoks.textbasedcalculator;

import com.ekinoks.textbasedcalculator.text_num_complier.TextNumberCompiler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TextBasedCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextBasedCalculatorApplication.class, args);
		new TextNumberCompiler().textToNUmber("altı milyon beş yüz seksen iki bin üç yüz altmış bir");
		new TextNumberCompiler().numberToText("1551225");

	}

}
