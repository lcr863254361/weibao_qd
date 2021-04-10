package com.orient.flow.extend.activity;

import com.orient.flow.extend.extensions.FlowTaskExecutionEventListener;
import com.orient.flow.extend.extensions.FlowTaskExecutionEventListenerMng;
import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.jpdl.internal.activity.JpdlActivity;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;

import java.util.List;


/**
 * the self extended end activity
 *
 * @author [创建人]  spf <br/> 
 * 		   [创建时间] 2014-8-7 上午9:02:57 <br/> 
 * 		   [修改人] spf <br/>
 * 		   [修改时间] 2014-8-7 上午9:02:57
 * @see
 */
public class EdmEndErrorActivity extends JpdlActivity {

  private static final long serialVersionUID = 1L;
  
  protected boolean endProcessInstance = true;
  protected String state = null;

  public void execute(ActivityExecution execution) {
    execute((ExecutionImpl)execution);
  }
  
  public void execute(ExecutionImpl execution) {
    Activity activity = execution.getActivity();

    List<Transition> outgoingTransitions = (List<Transition>) activity.getOutgoingTransitions();
    ActivityImpl parentActivity = (ActivityImpl) activity.getParentActivity();

    if ( (parentActivity!=null)
         && ("group".equals(parentActivity.getType()))) {
      // if the end activity itself has an outgoing transition 
      // (such end activities should be drawn on the border of the group)
      if ( (outgoingTransitions!=null)
              && (outgoingTransitions.size()==1)
          ) {
         Transition outgoingTransition = outgoingTransitions.get(0);
         // taking the transition that goes over the group boundaries will 
         // destroy the scope automatically (see atomic operation TakeTransition)
         execution.take(outgoingTransition);

      } else {
        execution.setActivity(parentActivity);
        execution.signal();
      }
        
    } else {
      ExecutionImpl executionToEnd = null;
      if (endProcessInstance) {
        executionToEnd = execution.getProcessInstance();
      } else {
        executionToEnd = execution;
      }


      if (state==null) {
        executionToEnd.end();
      } else {
        executionToEnd.end(state);
      }

      List<FlowTaskExecutionEventListener> flowTaskExecutionEventListeners = FlowTaskExecutionEventListenerMng.getInstance()
              .getListenersForTask(executionToEnd.getProcessInstance().getId(), executionToEnd.getProcessDefinition().getName(), FlowTaskExecutionEventListener.FLOW_END_ERROR);
      for(FlowTaskExecutionEventListener listener : flowTaskExecutionEventListeners) {
        listener.triggered(executionToEnd, null, null);
      }
    }
  }
  
  public void setEndProcessInstance(boolean endProcessInstance) {
    this.endProcessInstance = endProcessInstance;
  }
  public void setState(String state) {
    this.state = state;
  }
}
