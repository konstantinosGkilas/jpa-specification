package com.kgkilas.filtering.filters;

import lombok.Getter;

@Getter
public class FieldType<T extends Comparable<? super T>> implements Comparable<FieldType<T>> {
    private final T value;

    public FieldType(T value) {
        this.value = value;
    }

    @Override
    public int compareTo(FieldType<T> other) {
        return this.value.compareTo(other.getValue());
    }

    @Override
    public String toString() {
        return "FieldType{" + "value=" + value + '}';
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;

        if (this == obj) {
            isEqual = true;
        } else if (obj != null && getClass() == obj.getClass()) {
            FieldType<?> other = (FieldType<?>) obj;
            isEqual = value.equals(other.value);
        }

        return isEqual;
    }
}