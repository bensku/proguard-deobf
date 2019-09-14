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
        int arrayStart = name.indexOf('[');
        if (arrayStart != -1) {
            name = name.substring(0, arrayStart);
        }
        return classes.getOrDefault(name, ClassMapping.notMapped(name));
    }
    
    public String mapType(String name) {
        int arrayStart = name.indexOf('[');
        String baseName;
        String arrayPart;
        if (arrayStart != -1) {
            baseName = name.substring(0, arrayStart);
            arrayPart = name.substring(arrayStart);
        } else {
            baseName = name;
            arrayPart = "";
        }
        
        return classes.getOrDefault(baseName, ClassMapping.notMapped(baseName)).getName() + arrayPart;
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
            reversed.put(entry.getValue().getName(), entry.getValue().reversed(this, entry.getKey()));
        }
        return new Mappings(reversed);
    }
}
