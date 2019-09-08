package io.github.bensku.deobf;

/**
 * Generates type descriptors from type names.
 *
 */
public class DescriptorGenerator {
    
    public static String getSingularDescriptor(String type) {
        switch (type) {
        case "byte":
            return "B";
        case "char":
            return "C";
        case "double":
            return "D";
        case "float":
            return "F";
        case "int":
            return "I";
        case "long":
            return "J";
        case "short":
            return "S";
        case "boolean":
            return "Z";
        case "void": // Only return type
            return "V";
        case "":
            return "";
        default:
            return "L" + type.replace('.', '/') + ";";
        }
    }
    
    public static String getTypeDescriptor(String type) {
        int arrayStart = type.indexOf('[');
        if (arrayStart == -1) {
            return getSingularDescriptor(type);
        } else {
            int dimensions = (int) type.chars().filter(c -> c == '[').count();
            return "[".repeat(dimensions) + getSingularDescriptor(type.substring(0, type.length() - (dimensions * 2)));
        }
    }
    
    public static String getMethodDescriptor(String[] paramTypes, String returnType) {
        StringBuilder sb = new StringBuilder("(");
        for (String param : paramTypes) {
            sb.append(getTypeDescriptor(param));
        }
        sb.append(")");
        sb.append(getTypeDescriptor(returnType));
        
        return sb.toString();
    }

}
