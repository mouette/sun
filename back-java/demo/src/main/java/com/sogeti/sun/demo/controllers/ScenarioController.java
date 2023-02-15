package com.sogeti.sun.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sogeti.sun.demo.business.Scenario;
import com.sogeti.sun.demo.business.ConsoMode;
import com.sogeti.sun.demo.business.InstallationMode;
import com.sogeti.sun.demo.engine.Transformer;

@RestController
@RequestMapping(value = "/scenarios")
public class ScenarioController {

    @Autowired
    private Transformer transformer;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Scenario getResult() {
        Scenario s = new Scenario(ConsoMode.VT, InstallationMode.OMBRIERE);
        transformer.transform(s);
        return s;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public List<Object> getResult(@RequestBody Scenario s) {   
        return transformer.transform(s);
    }
}
