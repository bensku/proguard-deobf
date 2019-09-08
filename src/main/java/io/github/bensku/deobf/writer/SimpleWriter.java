package io.github.bensku.deobf.writer;

import java.util.Map;

import io.github.bensku.deobf.ClassMapping;
import io.github.bensku.deobf.Field;
import io.github.bensku.deobf.Mappings;
import io.github.bensku.deobf.Method;

/**
 * Writes mappings in a very simple format.
 *
 */
public class SimpleWriter implements MappingsWriter {

    @Override
    public String write(Mappings maps) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ClassMapping> entry : maps.getClasses().entrySet()) {
            sb.append("CLASS ").append(entry.getKey()).append(' ')
                    .append(entry.getValue().getName()).append('\n');
            for (Map.Entry<Field, String> field : entry.getValue().getFields().entrySet()) {
                sb.append("FIELD ").append(field.getKey().getName()).append(' ')
                        .append(field.getKey().getType()).append(' ')
                        .append(field.getValue()).append('\n');
            }
            for (Map.Entry<Method, String> field : entry.getValue().getMethods().entrySet()) {
                sb.append("FIELD ").append(field.getKey().getName()).append(' ')
                        .append(field.getKey().getDescriptor()).append(' ')
                        .append(field.getValue()).append('\n');
            }
        }
        return sb.toString();
    }

}
