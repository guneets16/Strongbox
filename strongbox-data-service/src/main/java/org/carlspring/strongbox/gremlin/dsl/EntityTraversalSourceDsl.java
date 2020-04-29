package org.carlspring.strongbox.gremlin.dsl;

import org.apache.tinkerpop.gremlin.process.remote.RemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.TraversalStrategies;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.DefaultGraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.step.map.GraphStep;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.carlspring.strongbox.data.domain.DomainEntity;

/**
 * @author sbespalov
 */
public class EntityTraversalSourceDsl extends GraphTraversalSource
{

    public EntityTraversalSourceDsl(Graph graph,
                                    TraversalStrategies traversalStrategies)
    {
        super(graph, traversalStrategies);
    }

    public EntityTraversalSourceDsl(Graph graph)
    {
        super(graph);
    }

    public EntityTraversalSourceDsl(RemoteConnection connection)
    {
        super(connection);
    }

    public GraphTraversal<Vertex, Vertex> V(DomainEntity entity)
    {
        Long vertexId = entity.getNativeId();

        GraphTraversalSource clone = this.clone();
        clone.getBytecode().addStep(GraphTraversal.Symbols.V);
        GraphTraversal<Vertex, Vertex> traversal = new DefaultGraphTraversal<>(clone);
        if (vertexId != null)
        {
            traversal.asAdmin().addStep(new GraphStep<>(traversal.asAdmin(), Vertex.class, true, vertexId));
        }
        else
        {
            traversal.asAdmin().addStep(new GraphStep<>(traversal.asAdmin(), Vertex.class, true));
        }

        return traversal;
    }
}