package io.github.bensku.deobf.loader;

import java.util.HashMap;
import java.util.Map;

import io.github.bensku.deobf.ClassMapping;
import io.github.bensku.deobf.DescriptorGenerator;
import io.github.bensku.deobf.Field;
import io.github.bensku.deobf.Mappings;
import io.github.bensku.deobf.Method;

/**
 * Loads mappings.txt generated by ProGuard.
 *
 */
public class ProguardLoader implements MappingsLoader {

    @Override
    public Mappings load(String data) {
        Map<String, ClassMapping> maps = new HashMap<>();
        
        String currentSource = null;
        ClassMapping.Builder current = null;
        for (String line : data.split("\n")) {
            if (line.startsWith("#")) {
                continue; // Comment line, ignore it
            }
            String[] split = splitLine(line);
            if (!line.startsWith("    ")) { // Begin class mapping
                if (currentSource != null) { // If not first, add build class mapping
                    maps.put(currentSource, current.build());
                }
                
                // Read from and to from line
                currentSource = split[0];
                current = new ClassMapping.Builder(split[1].substring(0, split[1].length() - 1));
            } else { // Field/method mapping info
                int firstColon = split[0].indexOf(':');
                if (firstColon != -1) { // Method mapping
                    int secondColon = split[0].indexOf(':', firstColon + 1);
                    String[] from = split[0].substring(secondColon + 1).split(" ");
                    String to = split[1];
                    
                    String returnType = from[0];
                    int bracket = from[1].indexOf('(');
                    String fromName = from[1].substring(0, bracket);
                    if (fromName.equals("<init>") || fromName.equals("<clinit>")) {
                        // Constructors and static initializers are never changed; ignore them
                        continue;
                    }
                    
                    String[] params = from[1].substring(bracket + 1, from[1].indexOf(')', bracket)).split(",");
                    current.method(new Method(fromName, params, returnType), to);
                } else { // Field mapping
                    String[] from = split[0].split(" "); // Ignore type
                    String to = split[1];
                    current.field(new Field(from[1], from[0]), to);
                }
            }
        }
        return new Mappings(maps);
    }

    private String[] splitLine(String line) {
        String[] split = line.split("->");
        split[0] = split[0].trim();
        split[1] = split[1].trim();
        return split;
    }
}
