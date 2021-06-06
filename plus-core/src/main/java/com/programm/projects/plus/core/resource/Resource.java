package com.programm.projects.plus.core.resource;

import java.util.function.Supplier;

public interface Resource {

    //TODO: PATH AND ORIGIN

    String asString();
    Boolean asBoolean();
    Integer asInt();
    Long asLong();
    Float asFloat();
    Double asDouble();

    default boolean isNull(){ return false; }


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

    //THROWING
    default <T extends Exception> String asString(Supplier<T> ex) throws T {
        if(isNull()) throw ex.get();

        String value = asString();

        if(value == null) throw ex.get();
        return value;
    }

    default <T extends Exception> boolean asBoolean(Supplier<T> ex) throws T {
        if(isNull()) throw ex.get();

        Boolean value = asBoolean();

        if(value == null) throw ex.get();
        return value;
    }

    default <T extends Exception> int asInt(Supplier<T> ex) throws T {
        if(isNull()) throw ex.get();

        Integer value = asInt();

        if(value == null) throw ex.get();
        return value;
    }

    default <T extends Exception> long asLong(Supplier<T> ex) throws T {
        if(isNull()) throw ex.get();

        Long value = asLong();

        if(value == null) throw ex.get();
        return value;
    }

    default <T extends Exception> float asFloat(Supplier<T> ex) throws T {
        if(isNull()) throw ex.get();

        Float value = asFloat();

        if(value == null) throw ex.get();
        return value;
    }

    default <T extends Exception> double asDouble(Supplier<T> ex) throws T {
        if(isNull()) throw ex.get();

        Double value = asDouble();

        if(value == null) throw ex.get();
        return value;
    }

    /**
     * Method to get an underlying resource if this resource is not a value resource
     * @param name Name of underlying resource node
     * @return an resource node or <code>null</code> if there was no child node with the specified name
     */
    Resource get(String name);

}
