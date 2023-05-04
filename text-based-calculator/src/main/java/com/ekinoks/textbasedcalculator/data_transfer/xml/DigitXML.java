package com.ekinoks.textbasedcalculator.data_transfer.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "digit")
@XmlAccessorType(XmlAccessType.FIELD)
public class DigitXML {
    @XmlElement(name = "prefix")
    private PrefixXML prefixXML;

    @XmlElement(name = "power")
    private PowerXML powerXML;

    @XmlElement(name = "text")
    private TextXML textXML;

}
