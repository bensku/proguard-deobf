package io.github.bensku.deobf.cli;

import java.util.HashMap;
import java.util.Map;

import io.github.bensku.deobf.loader.MappingsLoader;
import io.github.bensku.deobf.writer.MappingsWriter;

public class MappingTypes {

    /**
     * Registered loaders.
     */
    private static final Map<String, MappingsLoader> loaders = new HashMap<>();
    
    /**
     * Registered writers.
     */
    private static final Map<String, MappingsWriter> writers = new HashMap<>();
    
    public static void register(String key, MappingsLoader loader, MappingsWriter writer) {
        loaders.put(key, loader);
        writers.put(key, writer);
    }
    
    public static MappingsLoader getLoader(String key) {
        return loaders.get(key);
    }
    
    public static MappingsWriter getWriter(String key) {
        return writers.get(key);
    }
}
