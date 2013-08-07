package integrationtest.support;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Dummy class for support integration testing.
 * <p>
 * @author Balazs Berkes
 */
public class ComponentWithGenericFields {

    private Set<String> set;
    private List<String> list;
    private Map<String, Object> map;
    private Set<Object> objectSet;

    public Set<String> getSet() {
        return set;
    }

    public List<String> getList() {
        return list;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public Set<Object> getObjectSet() {
        return objectSet;
    }
}
