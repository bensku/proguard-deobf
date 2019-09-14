package io.github.bensku.deobf;

import java.util.Objects;

public class Method {
    
    /**
     * Method name.
     */
    private final String name;

    /**
     * Method parameters.
     */
    private final String[] parameters;
    
    /**
     * Return type of method.
     */
    private final String returnType;
    
    /**
     * Parameters and return type parsed to descriptor.
     */
    private final String descriptor;

    public Method(String name, String[] parameters, String returnType) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.descriptor = DescriptorGenerator.getMethodDescriptor(parameters, returnType);
    }
    
    public Method(String name, String[] parameters, String returnType, String descriptor) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.descriptor = descriptor;
    }
    
    public String getName() {
        return name;
    }

    public String[] getParameters() {
        return parameters;
    }

    public String getReturnType() {
        return returnType;
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
    
    @Override
    public String toString() {
        return name + "." + descriptor;
    }
    
}
