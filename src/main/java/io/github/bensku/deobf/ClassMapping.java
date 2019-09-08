package io.github.bensku.deobf;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains class name and member name mappings.
 *
 */
public class ClassMapping {
    
    public static ClassMapping notMapped(String name) {
        return new ClassMapping(name);
    }

    public static class Builder {

        private final ClassMapping built;

        public Builder(String name) {
            this.built = new ClassMapping(name);
        }
        
        public Builder field(Field from, String to) {
            built.fields.put(from, to);
            return this;
        }
        
        public Builder method(Method from, String to) {
            built.methods.put(from, to);
            return this;
        }
        
        public ClassMapping build() {
            return built;
        }
    }

    /**
     * Fully qualified target name of this class.
     */
    private final String name;

    /**
     * Field name mappings.
     */
    private final Map<Field, String> fields;

    /**
     * Method name mappings.
     */
    private final Map<Method, String> methods;

    private ClassMapping(String name) {
        this.name = name;
        this.fields = new HashMap<>();
        this.methods = new HashMap<>();
    }

    public String getName() {
        return name;
    }
    
    public String mapField(Field field) {
        return fields.get(field);
    }
    
    public Map<Field, String> getFields() {
        return fields;
    }
    
    public String mapMethod(Method method) {
        return methods.get(method);
    }
    
    public Map<Method, String> getMethods() {
        return methods;
    }

    /**
     * Reverses this class mapping.
     * @param original Original mappings.
     * @param fromName Name this mapping maps from.
     * @return Reversed mapping.
     */
    public ClassMapping reversed(Mappings original, String fromName) {
        // Create new mapping with name this was mapped from
        ClassMapping reversed = new ClassMapping(fromName);
        
        // Reverse field and method mappings
        for (Map.Entry<Field, String> entry : fields.entrySet()) {
            String newFrom = original.map(entry.getKey().getType()).getName();
            reversed.fields.put(new Field(entry.getValue(), newFrom),
                    entry.getKey().getName());
        }
        for (Map.Entry<Method, String> entry : methods.entrySet()) {
            String[] oldParams = entry.getKey().getParameters();
            String[] newParams = new String[oldParams.length];
            for (int i = 0; i < newParams.length; i++) {
                newParams[i] = original.map(oldParams[i]).getName();
            }
            String newReturn = original.map(entry.getKey().getReturnType()).getName();
            reversed.methods.put(new Method(entry.getValue(), newParams, newReturn), entry.getKey().getName());
        }
        return reversed;
    }

}
