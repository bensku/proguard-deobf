package io.github.bensku.deobf.asm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Collects superclasses/interfaces from visited classes.
 *
 */
public class SuperCollector extends ClassVisitor {

    private final Map<String, List<String>> supers;
    
    public SuperCollector() {
        super(Opcodes.ASM7);
        this.supers = new HashMap<>();
    }
    
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        supers.compute(name, (k, v) -> {
            if (v == null) {
                v = new ArrayList<>();
            }
            if (!superName.equals("java/lang/Object")) {
                v.add(superName);
            }
            if (interfaces != null) {
                v.addAll(Arrays.asList(interfaces));
            }
            return v.isEmpty() ? null : v;
        });
    }
    
    public Map<String, List<String>> getSupers() {
        return supers;
    }
}
