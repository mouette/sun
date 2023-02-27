package com.sogeti.sun.demo;

import java.io.IOException;
import java.nio.file.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.conf.MultithreadEvaluationOption;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {

    Logger logger = LoggerFactory.getLogger(DroolsConfig.class);

    private static final String RULE_DIRECTORY = "back-java/demo/src/main/resources/rules";
    private static final KieServices kieServices = KieServices.Factory.get();

    @Bean
    public KieContainer kieContainer() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        // Add the rules to the KieFileSystem
        Path ruleDirectory = Paths.get(RULE_DIRECTORY);
        if (!Files.exists(ruleDirectory)) {
            throw new RuntimeException("Rule directory does not exist: " + RULE_DIRECTORY);
        }

        try {
            Files.list(ruleDirectory)
                    .filter(path -> path.toString().endsWith(".drl") || path.toString().endsWith(".bpmn"))
                    .forEach(path -> kieFileSystem.write(ResourceFactory.newFileResource(path.toFile())));
        } catch (IOException e) {
            logger.error("Rule directory doesn't exist.", RULE_DIRECTORY);
        }

        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        logger.info("Building all rules...");
        kb.buildAll();
        if (kb.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
            logger.error("Build rules errors :");
            for (org.kie.api.builder.Message m : kb.getResults().getMessages()) {
                logger.error(m.getText());
            }
            return null;
        } else {
            KieModule kieModule = kb.getKieModule();
            KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());

            // Enable multithread evaluation
            KieBaseConfiguration kieBaseConf = kieServices.newKieBaseConfiguration();
            kieBaseConf.setOption(MultithreadEvaluationOption.YES);
            kieContainer.newKieBase(kieBaseConf);

            return kieContainer;
        }

    }
}