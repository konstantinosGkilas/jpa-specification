package com.kgkilas.filtering.filters;

import lombok.Getter;

import java.io.Serial;
import java.util.Objects;

@Getter
public class StringFilter extends RangeFilter<String> {
    @Serial
    private static final long serialVersionUID = 8L;
    private String contains;
    private String doesNotContain;

    public StringFilter() {
    }

    public StringFilter(StringFilter filter) {
        super(filter);
        this.contains = filter.contains;
        this.doesNotContain = filter.doesNotContain;
    }

    public StringFilter copy() {
        return new StringFilter(this);
    }

    public StringFilter setContains(String contains) {
        this.contains = contains;
        return this;
    }

    public StringFilter setDoesNotContain(String doesNotContain) {
        this.doesNotContain = doesNotContain;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            if (!super.equals(o)) {
                return false;
            } else {
                StringFilter that = (StringFilter)o;
                return Objects.equals(this.contains, that.contains) && Objects.equals(this.doesNotContain, that.doesNotContain);
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{super.hashCode(), this.contains, this.doesNotContain});
    }

    public String toString() {
        return this.getFilterName() + " ["
                + (this.getEquals() != null ? "equals=" + this.getEquals() + ", " : "")
                + (this.getNotEquals() != null ? "notEquals=" + this.getNotEquals() + ", " : "")
                + (this.getSpecified() != null ? "specified=" + this.getSpecified() + ", " : "")
                + (this.getIn() != null ? "in=" + this.getIn() + ", " : "")
                + (this.getNotIn() != null ? "notIn=" + this.getNotIn() + ", " : "")
                + (this.getGreaterThan() != null ? "greaterThan=" + this.getGreaterThan() + ", " : "")
                + (this.getLessThan() != null ? "lessThan=" + this.getLessThan() + ", " : "")
                + (this.getGreaterThanOrEqual() != null ? "greaterThanOrEqual=" + this.getGreaterThanOrEqual() + ", " : "")
                + (this.getLessThanOrEqual() != null ? "lessThanOrEqual=" + this.getLessThanOrEqual() + ", " : "")
                + (this.getLike() != null ? "like=" + this.getLike() : "")
                + (this.getContains() != null ? "contains=" + this.getContains() + ", " : "")
                + (this.getDoesNotContain() != null ? "doesNotContain=" + this.getDoesNotContain() : "")
                + "]";
    }
}
