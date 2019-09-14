package io.github.bensku.deobf.cli;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import com.beust.jcommander.JCommander;

import io.github.bensku.deobf.Mappings;
import io.github.bensku.deobf.asm.DeobfRemapper;
import io.github.bensku.deobf.asm.JarRemapper;
import io.github.bensku.deobf.loader.MappingsLoader;
import io.github.bensku.deobf.loader.ProguardLoader;
import io.github.bensku.deobf.writer.MappingsWriter;
import io.github.bensku.deobf.writer.SimpleWriter;

/**
 * CLI for proguard-deobf.
 *
 */
public class DeobfCli {

    public static void main(String... argv) throws IOException {
        // Parse command-line arguments
        Args args = new Args();
        JCommander.newBuilder()
            .addObject(args)
            .build()
            .parse(argv);
        
        // Register built-in mapping types
        MappingTypes.register("proguard", new ProguardLoader(), null);
        MappingTypes.register("simple", null, new SimpleWriter());
        
        // Parse mappings
        Path mapsFile = Paths.get(args.mappings);
        MappingsLoader loader = MappingTypes.getLoader(args.mappingsFormat);
        Mappings maps = loader.load(Files.readString(mapsFile));
        
        if (args.reverse) {
            maps = maps.reversed();
        }
        
        // Convert them to some other type, if that was requested
        if (args.mappingsOut != null) {
            MappingsWriter writer = MappingTypes.getWriter(args.outFormat);
            Files.writeString(Paths.get(args.mappingsOut), writer.write(maps));
        }
        
        // Remap jar if it was provided
        if (args.jarIn != null) {
            JarRemapper.remap(maps, Paths.get(args.jarIn), Paths.get(args.jarOut), path -> path.getNameCount() == 1);
        }
    }
}
