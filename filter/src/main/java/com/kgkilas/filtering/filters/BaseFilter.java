package com.kgkilas.filtering.filters;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class BaseFilter<T extends Comparable<? super T>> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private FieldType<T> equals;
    private FieldType<T> notEquals;
    private Boolean specified;
    private List<FieldType<T>> in;
    private List<FieldType<T>> notIn;

    public BaseFilter() {}

    public BaseFilter(BaseFilter<T> filter) {
        this.equals = filter.equals;
        this.notEquals = filter.notEquals;
        this.specified = filter.specified;
        this.in = (filter.in == null) ? null : new ArrayList<>(filter.in);
        this.notIn = (filter.notIn == null) ? null : new ArrayList<>(filter.notIn);
    }

    public BaseFilter<T> copy() {
        return new BaseFilter<>(this);
    }

    public BaseFilter<T> setEquals(T equals) {
        this.equals = new FieldType<>(equals);
        return this;
    }

    public BaseFilter<T> setNotEquals(T notEquals) {
        this.notEquals = new FieldType<>(notEquals);
        return this;
    }

    public BaseFilter<T> setSpecified(Boolean specified) {
        this.specified = specified;
        return this;
    }

    public BaseFilter<T> setIn(List<T> inValues) {
        if (inValues != null) {
            this.in = new ArrayList<>();
            for (T value : inValues) {
                this.in.add(new FieldType<>(value));
            }
        }
        return this;
    }

    public BaseFilter<T> setNotIn(List<T> notInValues) {
        if (notInValues != null) {
            this.notIn = new ArrayList<>();
            for (T value : notInValues) {
                this.notIn.add(new FieldType<>(value));
            }
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseFilter<?> filter = (BaseFilter<?>) o;
        return Objects.equals(equals, filter.equals) &&
                Objects.equals(notEquals, filter.notEquals) &&
                Objects.equals(specified, filter.specified) &&
                Objects.equals(in, filter.in) &&
                Objects.equals(notIn, filter.notIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equals, notEquals, specified, in, notIn);
    }

    @Override
    public String toString() {
        return getFilterName() + " [" +
                (equals != null ? "equals=" + equals + ", " : "") +
                (notEquals != null ? "notEquals=" + notEquals + ", " : "") +
                (specified != null ? "specified=" + specified + ", " : "") +
                (in != null ? "in=" + in + ", " : "") +
                (notIn != null ? "notIn=" + notIn : "") + "]";
    }

    protected String getFilterName() {
        return getClass().getSimpleName();
    }
}