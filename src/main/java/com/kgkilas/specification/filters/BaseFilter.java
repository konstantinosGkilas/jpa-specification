package com.kgkilas.specification.filters;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaseFilter<FIELD_TYPE> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private FIELD_TYPE equals;
    private FIELD_TYPE notEquals;
    private Boolean specified;
    private List<FIELD_TYPE> in;
    private List<FIELD_TYPE> notIn;

    public BaseFilter() {
    }

    public BaseFilter(BaseFilter<FIELD_TYPE> filter) {
        this.equals = filter.equals;
        this.notEquals = filter.notEquals;
        this.specified = filter.specified;
        this.in = filter.in == null ? null : new ArrayList<>(filter.in);
        this.notIn = filter.notIn == null ? null : new ArrayList<>(filter.notIn);
    }

    public BaseFilter<FIELD_TYPE> copy() {
        return new BaseFilter<>(this);
    }

    public FIELD_TYPE getEquals() {
        return this.equals;
    }

    public BaseFilter<FIELD_TYPE> setEquals(FIELD_TYPE equals) {
        this.equals = equals;
        return this;
    }

    public FIELD_TYPE getNotEquals() {
        return this.notEquals;
    }

    public BaseFilter<FIELD_TYPE> setNotEquals(FIELD_TYPE notEquals) {
        this.notEquals = notEquals;
        return this;
    }

    public Boolean getSpecified() {
        return this.specified;
    }

    public BaseFilter<FIELD_TYPE> setSpecified(Boolean specified) {
        this.specified = specified;
        return this;
    }

    public List<FIELD_TYPE> getIn() {
        return this.in;
    }

    public BaseFilter<FIELD_TYPE> setIn(List<FIELD_TYPE> in) {
        this.in = in;
        return this;
    }

    public List<FIELD_TYPE>  getNotIn() {
        return this.notIn;
    }

    public BaseFilter<FIELD_TYPE> setNotIn(List<FIELD_TYPE> notIn) {
        this.notIn = notIn;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            BaseFilter<?> filter = (BaseFilter)o;
            return Objects.equals(this.equals, filter.equals) && Objects.equals(this.notEquals, filter.notEquals) && Objects.equals(this.specified, filter.specified) && Objects.equals(this.in, filter.in) && Objects.equals(this.notIn, filter.notIn);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.equals, this.notEquals, this.specified, this.in, this.notIn);
    }

    public String toString() {
        return this.getFilterName() + " [" + (this.getEquals() != null ? "equals=" + this.getEquals() + ", " : "") + (this.getNotEquals() != null ? "notEquals=" + this.getNotEquals() + ", " : "") + (this.getSpecified() != null ? "specified=" + this.getSpecified() + ", " : "") + (this.getIn() != null ? "in=" + this.getIn() + ", " : "") + (this.getNotIn() != null ? "notIn=" + this.getNotIn() : "") + "]";
    }

    protected String getFilterName() {
        return this.getClass().getSimpleName();
    }
}