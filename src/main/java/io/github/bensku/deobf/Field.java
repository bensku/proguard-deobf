package io.github.bensku.deobf;

import java.util.Objects;

public class Field {

    /**
     * Field name.
     */
    private final String name;
    
    /**
     * Type of the field.
     */
    private final String type;
    
    private final String descriptor;
    
    public Field(String name, String type) {
        this.name = name;
        this.type = type;
        this.descriptor = DescriptorGenerator.getTypeDescriptor(type);
    }
    
    public Field(String name, String type, String descriptor) {
        this.name = name;
        this.type = type;
        this.descriptor = descriptor;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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
        Field other = (Field) obj;
        return Objects.equals(descriptor, other.descriptor) && Objects.equals(name, other.name);
    }
    
}
