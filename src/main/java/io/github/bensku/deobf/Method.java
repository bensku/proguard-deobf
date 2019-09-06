package io.github.bensku.deobf;

import java.util.List;
import java.util.Objects;

public class Method {
    
    /**
     * Method name.
     */
    private final String name;

    /**
     * Types of method arguments.
     */
    private final List<String> argumentTypes;
    
    /**
     * Return type of method.
     */
    private final String returnType;

    public Method(String name, List<String> argumentTypes, String returnType) {
        this.name = name;
        this.argumentTypes = argumentTypes;
        this.returnType = returnType;
    }
    
    public String getName() {
        return name;
    }

    public List<String> getArgumentTypes() {
        return argumentTypes;
    }

    public String getReturnType() {
        return returnType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(argumentTypes, name, returnType);
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
        return Objects.equals(argumentTypes, other.argumentTypes) && Objects.equals(name, other.name)
                && Objects.equals(returnType, other.returnType);
    }
}
