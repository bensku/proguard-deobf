package io.github.bensku.deobf.loader;

import io.github.bensku.deobf.Mappings;

public interface MappingsLoader {

    Mappings load(String data);
}
