package src.partOne.interfaces;

import java.util.Collection;

public interface Node {
    Collection<Node> getCollection(Class<? extends HasUUID> test);
}
