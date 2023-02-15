package com.sogeti.sun.demo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sogeti.sun.demo.business.pv.PVQueryParameters;

@RestController
@RequestMapping(value = "/api/measures")
public class MeasuresController {

    String base = "https://re.jrc.ec.europa.eu/api/v5_2/PVcalc";

    @RequestMapping(value = "", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getMeasuresJson(@RequestParam Map<String, String> allParams) throws IOException {
        PVQueryParameters pvQueryParameters = new PVQueryParameters();
        for(String key : allParams.keySet()) {
            if ("position".equals(key)) {
                String[] plist = allParams.get(key).split(",");
                pvQueryParameters.setParameter("lat", plist[0]);
                pvQueryParameters.setParameter("lon", plist[1]);
            }
            else {
                pvQueryParameters.setParameter(key, allParams.get(key));
            }
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = base + "?" + pvQueryParameters.getQueryString();
        Map<String, Object> resp = (Map<String, Object>) restTemplate.getForObject(url, Map.class);
        Map<String, Object> output = (Map<String, Object>) resp.get("outputs");
        Map<String, Object> monthly = (Map<String, Object>) output.get("monthly");
        List<Map<String, Object>> data = (List<Map<String, Object>>) monthly.get("fixed");
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> point : data) {
            Map<String, Object> map = new HashMap<>();
            map.put("month", point.get("month"));
            map.put("e", point.get("E_m"));
            result.add(map);
        }
        return result;
    }
}
