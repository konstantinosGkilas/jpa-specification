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
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FieldType<?> other = (FieldType<?>) obj;
        return value.equals(other.value);
    }
}
