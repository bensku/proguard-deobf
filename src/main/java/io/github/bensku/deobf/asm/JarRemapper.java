package io.github.bensku.deobf.asm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;

public class JarRemapper {

    public static void remap(DeobfRemapper mapper, JarFile from, JarFile to) throws IOException {
        ClassWriter writer = new ClassWriter(0);
        ClassRemapper visitor = new ClassRemapper(writer, mapper);
        for (Enumeration<JarEntry> entries = from.entries(); entries.hasMoreElements();) {
           JarEntry next = entries.nextElement();
           if (!next.isDirectory()) {
               try (InputStream is = from.getInputStream(next)) {
                   // TODO read next.size amount
               }
           }
        }
        writer.toByteArray();
    }
}
