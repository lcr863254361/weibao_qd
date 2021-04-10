package com.orient.collab.business.projectCore.algorithm;

import com.google.common.collect.Lists;
import com.orient.collab.business.projectCore.strategy.GanttDependencyBasedStrategy;
import com.orient.collab.model.GanttPlanDependency;
import com.orient.collab.model.Plan;
import com.orient.utils.UtilFactory;
import org.jboss.util.graph.Edge;
import org.jboss.util.graph.Graph;
import org.jboss.util.graph.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * a gantt graph algorithm
 *
 * @author Seraph
 * 2016-07-24 下午4:31
 */
public class GanttGraph {

    public GanttGraph(List<Plan> plans, List<GanttPlanDependency> dependencies) {
        plans = (plans!=null ? plans : Lists.newArrayList());
        dependencies = (dependencies!=null ? dependencies : Lists.newArrayList());
        this.plans = plans;
        this.dependencies = dependencies;
        graphEx = new Graph<>();
        for (Plan plan : plans) {
            Vertex<Plan> vertex = new Vertex<>(plan.getName(), plan);
            graphEx.addVertex(vertex);
            idToVertexMap.put(plan.getId(), vertex);
        }

        for (GanttPlanDependency dependency : dependencies) {
            if (idToVertexMap.get(dependency.getStartPlanId()) != null && idToVertexMap.get(dependency.getFinishPlanId()) != null) {
                graphEx.addEdge(idToVertexMap.get(dependency.getStartPlanId()), idToVertexMap.get(dependency.getFinishPlanId()), Integer.valueOf(dependency.getType()));
            }
        }
    }

    public List<Plan> getToStartPlans() {
        return toStartPlans;
    }

    public boolean hasCycle() {
        Edge<Plan>[] cycle = this.graphEx.findCycles();
        return cycle.length > 0;
    }

    /**
     * note that a status changed plan may influence next one
     */
    public void travelGraphAndGetToStartPlans() {
        List<Vertex<Plan>> vertexes = this.graphEx.getVerticies();
        for (Vertex<Plan> vertex : vertexes) {
            if (vertex.visited()) {
                continue;
            }
            tranvelFromPlan(vertex);
        }
    }

    private void tranvelFromPlan(Vertex<Plan> v) {
        v.visit();

        Plan plan = v.getData();
        boolean canStart = true;
        if ("0".equals(plan.getStatus())) {
            List<Edge<Plan>> incomingEdges = v.getIncomingEdges();

            for (Edge<Plan> edge : incomingEdges) {
                canStart &= GanttDependencyBasedStrategy.fromType(edge.getCost()).canToPlanStart(edge.getFrom().getData(), plan);
            }

            if (canStart) {
                plan.setStatus("1");
                toStartPlans.add(plan);
            }
        } else {
            canStart = false;
        }

        for (int i = 0; i < v.getOutgoingEdgeCount(); ++i) {
            Edge e = v.getOutgoingEdge(i);
            //note that a status changed plan may influence next one
            //so if there exists status change, try to start next one
            if (!e.getTo().visited() || canStart) {
                this.tranvelFromPlan(e.getTo());
            }
        }
    }

    private Map<String, Vertex> idToVertexMap = UtilFactory.newHashMap();
    private List<Plan> plans;
    private List<GanttPlanDependency> dependencies;
    private Graph<Plan> graphEx;
    private List<Plan> toStartPlans = new ArrayList<>();

}
