package com.sogeti.sun.demo.business.pv;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class PVQueryParameters {

    Map<String, String> parameters = new HashMap<>();

    public PVQueryParameters() {
        parameters.put("angle", "35");
        parameters.put("aspect", "0");
        parameters.put("browser", "1");
        parameters.put("js", "1");
        parameters.put("lat", "45.83");
        parameters.put("lon", "4.88");
        parameters.put("loss", "14");
        parameters.put("mountingplace", "free");
        parameters.put("outputformat", "json");
        parameters.put("peakpower", "1");
        parameters.put("pvtechchoice", "crystSi");
        parameters.put("raddatabase", "PVGIS-SARAH");
        parameters.put("select_database_grid", "PVGIS-SARAH");
        parameters.put("usehorizon", "1");
        parameters.put("angle", "50");
        parameters.put("aspect", "25");
    }
    
    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public String getQueryString() throws UnsupportedEncodingException {
        return QueryStringBuilder.getParamsString(parameters);
    }

}
