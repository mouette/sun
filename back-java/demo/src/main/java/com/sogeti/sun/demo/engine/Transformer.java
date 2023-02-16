package com.sogeti.sun.demo.engine;

import java.util.ArrayList;
import java.util.List;

import org.drools.core.event.DebugAgendaEventListener;
import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sogeti.sun.demo.business.Parameters;
import com.sogeti.sun.demo.business.Scenario;

@Service
public class Transformer {

	@Autowired
	private KieContainer kieContainer;

	public List<Object> transform(Scenario s) {
		var results = new ArrayList<Object>();
		try {
			KieSession session = KnowledgeSessionHelper.getStatefulKnowledgeSessionWithCallback(kieContainer);
			// session.addEventListener(new DebugAgendaEventListener());
			// session.addEventListener( new DebugRuleRuntimeEventListener());
			session.setGlobal("parametres", new Parameters());
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
