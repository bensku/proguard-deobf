package io.github.bensku.deobf.asm;

import org.objectweb.asm.commons.Remapper;

import io.github.bensku.deobf.ClassMapping;
import io.github.bensku.deobf.Field;
import io.github.bensku.deobf.Mappings;
import io.github.bensku.deobf.Method;

/**
 * Deobfuscates with given mappings.
 */
public class DeobfRemapper extends Remapper {
    
    private Mappings maps;
    
    public DeobfRemapper(Mappings maps) {
        this.maps = maps;
    }
    
    public String mapMethodName(String owner, String name, String descriptor) {
        ClassMapping map = maps.map(owner);
        return map.mapMethod(new Method(name, descriptor));
    }
    
    public String mapFieldName(String owner, String name, String descriptor) {
        ClassMapping map = maps.map(owner);
        return map.mapField(new Field(name, descriptor));
    }

    public String map(String internalName) {
        // Internal name: fully qualified name, but with slashes instead of dots
        return maps.map(internalName).getName();
    }
}
