package com.programm.projects.plus.resource.api;

public interface DefaultValuesResource extends Resource{

    @Override
    default String asString() {
        return null;
    }

    @Override
    default Boolean asBoolean() {
        return null;
    }

    @Override
    default Integer asInt() {
        return null;
    }

    @Override
    default Long asLong() {
        return null;
    }

    @Override
    default Float asFloat() {
        return null;
    }

    @Override
    default Double asDouble() {
        return null;
    }
}
