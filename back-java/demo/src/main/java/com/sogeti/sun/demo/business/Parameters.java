package com.sogeti.sun.demo.business;

import java.util.ArrayList;
import java.util.List;

public class Parameters {
    public static final int COUT_RACCORDEMENT_PAR_METRE = 1000;
    public static final int COUT_OMBRIERE_PAR_METRE_2 = 500;

    public static List<Double> getProduction(Float latitude, Float longitude, Float puissance) {
        var result = new ArrayList<Double>();
        for (int i = 0; i < 5; i++) {
            result.add(Double.valueOf(i * 10.0));
        }
        return result;
    }
}
