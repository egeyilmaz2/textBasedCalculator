package com.ekinoks.textbasedcalculator.data_transfer.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "power")
@XmlAccessorType(XmlAccessType.FIELD)
public class PowerXML {
    @XmlValue
    private String powerValue;
}
