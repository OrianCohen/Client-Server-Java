package clientserver.PartOne;

import java.util.Collection;

public interface Node {
    public Collection<Node> getCollection(Class<? extends HasUUID> test);
}
