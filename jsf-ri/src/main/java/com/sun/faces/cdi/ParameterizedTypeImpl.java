package com.sun.faces.cdi;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

public class ParameterizedTypeImpl implements ParameterizedType {

    private final Type ownerType;
    private final Class<?> rawType;
    private final Type[] actualTypeArguments;
    
    public ParameterizedTypeImpl(Class<?> rawType, Type[] actualTypeArguments) {
        this(null, rawType, actualTypeArguments);
    }

    public ParameterizedTypeImpl(Type ownerType, Class<?> rawType, Type[] actualTypeArguments) {
        this.ownerType = ownerType;
        this.rawType = rawType;
        this.actualTypeArguments = actualTypeArguments;
    }

    @Override
    public Type getOwnerType() {
        return ownerType;
    }

    @Override
    public Type getRawType() {
        return rawType;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return actualTypeArguments;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ParameterizedType ? equals((ParameterizedType) other) : false;
    }

    public boolean equals(ParameterizedType other) {
        return this == other ? true : Objects.equals(getOwnerType(), other.getOwnerType())
                && Objects.equals(getRawType(), other.getRawType())
                && Arrays.equals(getActualTypeArguments(), other.getActualTypeArguments());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOwnerType()) ^ Objects.hashCode(getRawType()) ^ Arrays.hashCode(getActualTypeArguments());
    }

}
