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
    
    public Field(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
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
        return Objects.equals(name, other.name) && Objects.equals(type, other.type);
    }
    
}
