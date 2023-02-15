package com.sogeti.sun.demo.engine;

import org.kie.api.KieServices;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.AgendaEventListener;
import org.kie.api.event.rule.AgendaGroupPoppedEvent;
import org.kie.api.event.rule.AgendaGroupPushedEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.kie.api.event.rule.RuleFlowGroupDeactivatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

public class KnowledgeSessionHelper {
	public static KieContainer createRuleBase() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kieContainer = ks.getKieClasspathContainer();
		return kieContainer;
	}
	
	public static StatelessKieSession getStatelessKnowledgeSession(KieContainer kieContainer) {
		StatelessKieSession sessionStateless = kieContainer.newStatelessKieSession();
		return sessionStateless;
	}
		

	public static KieSession getStatefullKnowledgeSession(KieContainer kieContainer) {
		KieSession sessionStatefull = kieContainer.newKieSession();
		return sessionStatefull;
	}
	
	public static KieSession getStatefulKnowledgeSessionWithCallback(
        KieContainer kieContainer) {
        KieSession session = getStatefullKnowledgeSession(kieContainer);
        session.addEventListener(new RuleRuntimeEventListener() {
            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println("Object inserted \n"
                        + event.getObject().toString());
            }
            public void objectUpdated(ObjectUpdatedEvent event) {
                System.out.println("Object was updated \n"
                        + "new Content \n" + event.getObject().toString());
            }
            public void objectDeleted(ObjectDeletedEvent event) {
                System.out.println("Object retracted \n"
                        + event.getOldObject().toString());
            }
        });
        session.addEventListener(new AgendaEventListener() {
            public void matchCreated(MatchCreatedEvent event) {
                System.out.println("The rule "
                        + event.getMatch().getRule().getName()
                        + " can be fired in agenda");
            }
            public void matchCancelled(MatchCancelledEvent event) {
                System.out.println("The rule "
                        + event.getMatch().getRule().getName()
                        + " cannot b in agenda");
            }
            public void beforeMatchFired(BeforeMatchFiredEvent event) {
                System.out.println("The rule "
                        + event.getMatch().getRule().getName()
                        + " will be fired");
            }
            public void afterMatchFired(AfterMatchFiredEvent event) {
                System.out.println("The rule "
                        + event.getMatch().getRule().getName()
                        + " has be fired");
            }
            public void agendaGroupPopped(AgendaGroupPoppedEvent event) {
            }
            public void agendaGroupPushed(AgendaGroupPushedEvent event) {
            }
            public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
            }
            public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
            }
            public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
            }
            public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {
            }
        });
  	return session;
	}
	public static KieSession getStatefulKnowledgeSessionForJBPM(
            KieContainer kieContainer, String sessionName) {
          KieSession session = getStatefulKnowledgeSessionWithCallback(kieContainer);
/*        session.addEventListener(new ProcessEventListener() {

              @Override
              public void beforeVariableChanged(ProcessVariableChangedEvent arg0) {
                  // TODO Auto-generated method stub

              }

              @Override
              public void beforeProcessStarted(ProcessStartedEvent arg0) {
                  System.out.println("Process Name "+arg0.getProcessInstance().getProcessName()+" has been started");


              }

              @Override
              public void beforeProcessCompleted(ProcessCompletedEvent arg0) {
                  // TODO Auto-generated method stub

              }

              @Override
              public void beforeNodeTriggered(ProcessNodeTriggeredEvent arg0) {
                  // TODO Auto-generated method stub

              }

              @Override
              public void beforeNodeLeft(ProcessNodeLeftEvent arg0) {
                 if (arg0.getNodeInstance() instanceof RuleSetNodeInstance){
                      System.out.println("Node Name "+ arg0.getNodeInstance().getNodeName()+" has been left");        
                  }

              }

              @Override
              public void afterVariableChanged(ProcessVariableChangedEvent arg0) {
                  // TODO Auto-generated method stub

              }

              @Override
              public void afterProcessStarted(ProcessStartedEvent arg0) {

              }

              @Override
              public void afterProcessCompleted(ProcessCompletedEvent arg0) {
                  System.out.println("Process Name "+arg0.getProcessInstance().getProcessName()+" has stopped");


              }

              @Override
              public void afterNodeTriggered(ProcessNodeTriggeredEvent arg0) {
                  if (arg0.getNodeInstance() instanceof RuleSetNodeInstance){
                      System.out.println("Node Name "+ arg0.getNodeInstance().getNodeName()+" has been entered");        
                  }
              }

              @Override
              public void afterNodeLeft(ProcessNodeLeftEvent arg0) {
               }
          }) */;
        return session;
    }
}
