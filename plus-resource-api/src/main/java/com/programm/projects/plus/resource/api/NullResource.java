package com.programm.projects.plus.resource.api;

import com.programm.projects.plus.core.resource.Resource;

/**
 * Representing an empty resource which is returned when no resource was found.
 * All methods should throw a {@link NullPointerException} except for the methods with a default value specified!
 */
public final class NullResource implements Resource{

    private static NullPointerException throwException() {
        return new NullPointerException("This resource is an EmptyResource and should only be used with defaults. A resource can be checked if it represents an empty resource by the isEmptyResource() method.");
    }

    @Override
    public String asString() {
        throw throwException();
    }

    @Override
    public Boolean asBoolean() {
        throw throwException();
    }

    @Override
    public Integer asInt() {
        throw throwException();
    }

    @Override
    public Long asLong() {
        throw throwException();
    }

    @Override
    public Float asFloat() {
        throw throwException();
    }

    @Override
    public Double asDouble() {
        throw throwException();
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public String asString(String defaultValue) {
        return defaultValue;
    }

    @Override
    public boolean asBoolean(boolean defaultValue) {
        return defaultValue;
    }

    @Override
    public int asInt(int defaultValue) {
        return defaultValue;
    }

    @Override
    public long asLong(long defaultValue) {
        return defaultValue;
    }

    @Override
    public float asFloat(float defaultValue) {
        return defaultValue;
    }

    @Override
    public double asDouble(double defaultValue) {
        return defaultValue;
    }

    @Override
    public Resource get(String name) {
        throw throwException();
    }
}
