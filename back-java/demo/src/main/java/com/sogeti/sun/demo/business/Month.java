package com.sogeti.sun.demo.business;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Month {
    Integer name;
    Double production;
    Double consommation;
    Double net;

    public Month(Integer name, Double production) {
        this.name = name;
        this.production = production;
        this.consommation = null;
    }
}
