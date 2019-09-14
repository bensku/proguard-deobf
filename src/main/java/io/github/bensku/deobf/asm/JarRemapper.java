package io.github.bensku.deobf.asm;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.function.Predicate;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;

import io.github.bensku.deobf.Mappings;

public class JarRemapper {
    
    public static void remap(Mappings maps, Path from, Path to, Predicate<Path> classFilter) throws IOException {
        try (FileSystem fromFs = FileSystems.newFileSystem(new URI("jar", from.toUri().toString(), null), Collections.emptyMap());
                FileSystem toFs = FileSystems.newFileSystem(new URI("jar", to.toUri().toString(), null), Collections.singletonMap("create", "true"))) {
            Path fromRoot = fromFs.getPath("/");
            Path toRoot = toFs.getPath("/");
            
            // Go through jar contents once to find superclasses/interfaces
            SuperCollector superCollector = new SuperCollector();
            Files.walk(fromRoot).forEach(path -> {
                if (path.toString().endsWith(".class")) {
                    try {
                        byte[] data = Files.readAllBytes(path);
                        ClassReader reader = new ClassReader(data);
                        reader.accept(superCollector, ClassReader.SKIP_CODE);
                    } catch (IOException e) {
                        e.printStackTrace(); // TODO handle
                    }
                }
            });
            DeobfRemapper mapper = new DeobfRemapper(maps, superCollector.getSupers());
            
            // And once more, this time actually remapping classes
            Files.walk(fromRoot).forEach(path -> {
                if (Files.isDirectory(path)) {
                    return; // Ignore directories while remapping
                }
                
                // Copy resources as-is
                if (!path.toString().endsWith(".class")) {
                    Path toPath = toRoot.resolve(path);
                    try {
                        Files.createDirectories(toPath.getParent());
                        Files.copy(path, toPath);
                    } catch (IOException e) {
                        e.printStackTrace(); // TODO handle
                    }
                    return;
                }
                
                // Test with class filter to avoid copying not obfuscated stuff
                if (!classFilter.test(path)) {
                    return;
                }
                
                // Remap this class
                try {                    
                    // Read and remap
                    byte[] data = Files.readAllBytes(path);
                    ClassWriter writer = new ClassWriter(0);
                    ClassRemapper visitor = new ClassRemapper(writer, mapper);
                    ClassReader reader = new ClassReader(data);
                    reader.accept(visitor, 0);
                    
                    // Write to target jar
                    String plainName = path.getFileName().toString().replace(".class", "");
                    Path toPath = toRoot.resolve(mapper.map(plainName) + ".class");
                    Files.createDirectories(toPath.getParent());
                    Files.write(toPath, writer.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace(); // TODO handle
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
