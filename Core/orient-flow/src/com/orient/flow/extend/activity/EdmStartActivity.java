package com.orient.flow.extend.activity;

import java.util.List;

import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListenerMng;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.jpdl.internal.activity.JpdlActivity;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.task.FormBehaviour;




/**
 * the self extended start activity
 *
 * @author [创建人]  spf <br/> 
 * 		   [创建时间] 2014-8-5 下午2:14:55 <br/> 
 * 		   [修改人] spf <br/>
 * 		   [修改时间] 2014-8-5 下午2:14:55
 * @see
 */
public class EdmStartActivity extends JpdlActivity implements FormBehaviour {

  private static final long serialVersionUID = 1L;
  
  String formResourceName;
  
  static public String START_TRANSITION = "start_transition";

  public void execute(ActivityExecution execution) {
	  ExecutionImpl theExe = (ExecutionImpl)execution;
	  Activity theActivity = theExe.getActivity();
	  Object theStartTran = theExe.getVariable(EdmStartActivity.START_TRANSITION);
	  if(theStartTran != null){
		  String theStartTranName = theStartTran.toString();
		  List<? extends Transition> outgoingTransitions = theActivity.getOutgoingTransitions();
		  if ( (outgoingTransitions!=null) && (!outgoingTransitions.isEmpty()) ) {
			  for(Transition trans : outgoingTransitions){
				  Activity nextAct = trans.getDestination();
				  if(theStartTranName.equals(nextAct.getName())){
//					  theExe.setTransition((TransitionImpl)trans);
					  theExe.take(trans);
				  }
			  }
		  }
	  }
	  
	  List<FlowTaskExecutionEventListener> flowTaskExecutionEventListeners = FlowTaskExecutionEventListenerMng.getInstance()
			  .getListenersForTask(theExe.getProcessInstance().getId(), theExe.getProcessDefinition().getName(), FlowTaskExecutionEventListener.FLOW_START);
	  for(FlowTaskExecutionEventListener listener : flowTaskExecutionEventListeners){
		  listener.triggered(theExe, null, null);
	  }

  }

  public String getFormResourceName() {
    return formResourceName;
  }
  public void setFormResourceName(String formResourceName) {
    this.formResourceName = formResourceName;
  }
}
