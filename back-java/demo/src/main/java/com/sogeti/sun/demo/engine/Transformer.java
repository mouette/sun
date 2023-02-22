package com.sogeti.sun.demo.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sogeti.sun.demo.business.Parameters;
import com.sogeti.sun.demo.business.Scenario;

@Service
public class Transformer {

	Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private KieContainer kieContainer;

	public List<Object> transform(Scenario s) {
		String correlationId = "toto";
		logger.info(correlationId);
		var results = new ArrayList<Object>();
		try {
			KieSession session = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer);
			session.setGlobal("parametres", new Parameters());
			session.getEnvironment().set("correlationId", correlationId);
			session.insert(s);
			session.fireAllRules();
			var facts = session.getFactHandles();
			for (FactHandle fact : facts) {
				results.add(session.getObject(fact));
			}
			session.dispose();

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return results;
	}

}
