package com.programm.projects.plus.resource.api;

import com.programm.projects.plus.core.resource.Resource;

import java.util.function.Supplier;

/**
 * Representing an empty resource which is returned when no resource was found.
 * All methods should throw a {@link NullPointerException} except for the methods with a default value specified!
 */
public final class NullResource implements Resource{

    private NullPointerException throwException() {
        return new NullPointerException(errorMessage);
    }

    private final String errorMessage;


    public NullResource(String errorMessage) {
        this.errorMessage = errorMessage;
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

    @Override
    public <T extends Exception> String asString(Supplier<T> ex) throws T {
        throw throwException();
    }

    @Override
    public <T extends Exception> boolean asBoolean(Supplier<T> ex) throws T {
        throw throwException();
    }

    @Override
    public <T extends Exception> int asInt(Supplier<T> ex) throws T {
        throw throwException();
    }

    @Override
    public <T extends Exception> long asLong(Supplier<T> ex) throws T {
        throw throwException();
    }

    @Override
    public <T extends Exception> float asFloat(Supplier<T> ex) throws T {
        throw throwException();
    }

    @Override
    public <T extends Exception> double asDouble(Supplier<T> ex) throws T {
        throw throwException();
    }

    @Override
    public String toString() {
        return "Null Resource [err:" + errorMessage + "]";
    }
}
