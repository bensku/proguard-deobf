package io.github.bensku.deobf.asm;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;

public class JarRemapper {

    public static void remap(DeobfRemapper mapper, JarFile from, JarOutputStream to) throws IOException {
        for (Enumeration<JarEntry> entries = from.entries(); entries.hasMoreElements();) {
           JarEntry next = entries.nextElement();
           if (!next.isDirectory()) {
               try (InputStream is = from.getInputStream(next)) {
                   if (!next.getName().endsWith(".class")) { // Not a class, copy as-is
                       ByteArrayOutputStream originFile = new ByteArrayOutputStream();
                       int readed;
                       byte[] data = new byte[16384];
                       while ((readed = is.read(data, 0, data.length)) != -1) {
                         originFile.write(data, 0, readed);
                       }
                       
                       to.putNextEntry(new JarEntry(next.getName()));
                       to.write(originFile.toByteArray());
                       continue;
                   }
                   if (next.getName().contains("/")) { // Not obfuscated class, ignore
                       continue;
                   }
                   ByteArrayOutputStream originClass = new ByteArrayOutputStream();
                   int readed;
                   byte[] data = new byte[16384];
                   while ((readed = is.read(data, 0, data.length)) != -1) {
                     originClass.write(data, 0, readed);
                   }
                   
                   ClassWriter writer = new ClassWriter(0);
                   ClassRemapper visitor = new ClassRemapper(writer, mapper);
                   ClassReader reader = new ClassReader(originClass.toByteArray());
                   reader.accept(visitor, 0);
                   String plainName = next.getName().replace(".class", "");
                   to.putNextEntry(new JarEntry(mapper.map(plainName) + ".class"));
                   to.write(writer.toByteArray());
               }
           }
        }
    }
    
    public static void remap(DeobfRemapper mapper, Path from, Path to) throws IOException {
        try (JarFile jar = new JarFile(from.toFile());
                JarOutputStream out = new JarOutputStream(new FileOutputStream(to.toFile()))) {
            remap(mapper, jar, out);
        }
    }
}
