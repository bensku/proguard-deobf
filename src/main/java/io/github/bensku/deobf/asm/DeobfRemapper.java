package io.github.bensku.deobf.asm;

import java.util.List;
import java.util.Map;

import org.objectweb.asm.commons.Remapper;

import io.github.bensku.deobf.ClassMapping;
import io.github.bensku.deobf.Field;
import io.github.bensku.deobf.Mappings;
import io.github.bensku.deobf.Method;

/**
 * Deobfuscates with given mappings.
 */
public class DeobfRemapper extends Remapper {
    
    /**
     * Mappings we use.
     */
    private final Mappings maps;
    
    /**
     * Classes by their superclasses+superinterfaces.
     */
    private final Map<String, List<String>> supers;
    
    public DeobfRemapper(Mappings maps, Map<String, List<String>> supers) {
        this.maps = maps;
        this.supers = supers;
    }
    
    public String mapMethodName(String owner, String name, String descriptor) {
        ClassMapping map = maps.map(owner.replace('/', '.'));
        String newName = map.mapMethod(new Method(name, null, null, descriptor));
        if (newName == null) {
            List<String> parents = supers.get(owner);
            if (parents == null) {
                return name; // No supers, can't be reference to them
            }
            for (String parent : parents) {
                return mapMethodName(parent, name, descriptor);
            }
        }
        return newName;
    }
    
    public String mapFieldName(String owner, String name, String descriptor) {
        ClassMapping map = maps.map(owner.replace('/', '.'));
        String newName = map.mapField(new Field(name, null, descriptor));
        if (newName == null) {
            List<String> parents = supers.get(owner);
            if (parents == null) {
                return name; // No supers, can't be reference to them
            }
            for (String parent : parents) {
                return mapFieldName(parent, name, descriptor);
            }
            return name;
        }
        return newName;
    }

    public String map(String internalName) {
        // Internal name: fully qualified name, but with slashes instead of dots
        return maps.map(internalName.replace('/', '.')).getName()
                .replace('.', '/');
    }
}
