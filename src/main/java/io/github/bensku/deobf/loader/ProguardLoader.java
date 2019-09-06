package io.github.bensku.deobf.loader;

import java.util.HashMap;
import java.util.Map;

import io.github.bensku.deobf.ClassMapping;
import io.github.bensku.deobf.Mappings;

public class ProguardLoader implements MappingsLoader {

    @Override
    public Mappings load(String data) {
        Map<String, ClassMapping> maps = new HashMap<>();
        
        String currentSource = null;
        ClassMapping.Builder current = null;
        for (String line : data.split("\n")) {
            String[] split = splitLine(line);
            if (!line.startsWith("    ")) { // Begin class mapping
                if (currentSource != null) { // If not first, add build class mapping
                    maps.put(currentSource, current.build());
                }
                
                // Read from and to from line
                currentSource = split[0];
                current = new ClassMapping.Builder(split[1].substring(0, split[1].length() - 1));
            } else { // Field/method mapping info
                
            }
        }
        return null;
    }

    private String[] splitLine(String line) {
        String[] split = line.split("->");
        split[0] = split[0].trim();
        split[1] = split[1].trim();
        return split;
    }
}
