package com.programm.projects.plus.core.resource;

public interface Resource {

    String asString();
    Boolean asBoolean();
    Integer asInt();
    Long asLong();
    Float asFloat();
    Double asDouble();

    default boolean isEmptyResource(){ return false; }


    //DEFAULTS
    default String asString(String defaultValue){
        String value = asString();
        return value == null ? defaultValue : value;
    }

    default boolean asBoolean(boolean defaultValue){
        Boolean value = asBoolean();
        return value == null ? defaultValue : value;
    }

    default int asInt(int defaultValue){
        Integer value = asInt();
        return value == null ? defaultValue : value;
    }

    default long asLong(long defaultValue){
        Long value = asLong();
        return value == null ? defaultValue : value;
    }

    default float asFloat(float defaultValue){
        Float value = asFloat();
        return value == null ? defaultValue : value;
    }

    default double asDouble(double defaultValue){
        Double value = asDouble();
        return value == null ? defaultValue : value;
    }

    /**
     * Method to get an underlying resource if this resource is not a value resource
     * @param name Name of underlying resource node
     * @return an resource node or <code>null</code> if there was no child node with the specified name
     */
    Resource get(String name);

}
