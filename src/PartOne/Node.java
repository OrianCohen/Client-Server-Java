package src.PartOne;

import java.util.Collection;

public interface Node {
    Collection<Node> getCollection(Class<? extends HasUUID> test);
}
