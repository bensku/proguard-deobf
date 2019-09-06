package io.github.bensku.deobf;

import java.util.List;
import java.util.Objects;

public class Method {
    
    /**
     * Method name.
     */
    private final String name;

    /**
     * Method descriptor.
     */
    private final String descriptor;

    public Method(String name, String descriptor) {
        this.name = name;
        this.descriptor = descriptor;
    }
    
    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriptor, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Method other = (Method) obj;
        return Objects.equals(descriptor, other.descriptor) && Objects.equals(name, other.name);
    }
    
}
