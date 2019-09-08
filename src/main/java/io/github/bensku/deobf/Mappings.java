package io.github.bensku.deobf;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps class names
 *
 */
public class Mappings {

    private final Map<String, ClassMapping> classes;
    
    public Mappings(Map<String, ClassMapping> classes) {
        this.classes = classes;
    }
    
    public ClassMapping map(String name) {
        return classes.get(name);
    }
    
    public Map<String, ClassMapping> getClasses() {
        return classes;
    }
    
    /**
     * Reverses these mappings.
     * @return Reversed mappings.
     */
    public Mappings reversed() {
        Map<String, ClassMapping> reversed = new HashMap<>();
        for (Map.Entry<String, ClassMapping> entry : classes.entrySet()) {
            reversed.put(entry.getValue().getName(), entry.getValue().reversed(entry.getKey()));
        }
        return new Mappings(reversed);
    }
}
