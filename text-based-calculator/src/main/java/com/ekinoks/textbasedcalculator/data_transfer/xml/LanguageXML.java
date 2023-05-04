package com.ekinoks.textbasedcalculator.data_transfer.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "language")
@XmlAccessorType(XmlAccessType.FIELD)
public class LanguageXML {
    @XmlAttribute(name = "language_code")
    private String languageCode;

    @XmlAttribute(name = "language_name")
    private String languageName;

    @XmlAttribute(name = "optional_flag_photo")
    private String optionalFlagPhoto;

    @XmlAttribute(name = "one_allowed")
    private String oneAllowed;

    @XmlElement(name = "numbers")
    private NumbersXML numbersXML;

    @XmlElement(name = "tens")
    private TensXML tensXML;

    @XmlElement(name = "ten_power")
    private TenPowerXML tenPowerXML;

    @XmlElement(name = "minus")
    private MinusXML minusXML;
}
