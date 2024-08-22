package com.kgkilas.filtering.filters;

import lombok.Getter;

import java.io.Serial;
import java.util.Objects;

@Getter
public class RangeFilter<T extends Comparable<? super T>> extends BaseFilter<T> {
    @Serial
    private static final long serialVersionUID = 2L;

    private FieldType<T> greaterThan;
    private FieldType<T> lessThan;
    private FieldType<T> greaterThanOrEqual;
    private FieldType<T> lessThanOrEqual;
    private FieldType<T> like;

    public RangeFilter() {}

    public RangeFilter(RangeFilter<T> filter) {
        super(filter);
        this.greaterThan = filter.greaterThan;
        this.lessThan = filter.lessThan;
        this.greaterThanOrEqual = filter.greaterThanOrEqual;
        this.lessThanOrEqual = filter.lessThanOrEqual;
        this.like = filter.like;
    }

    @Override
    public RangeFilter<T> copy() {
        return new RangeFilter<>(this);
    }

    public RangeFilter<T> setGreaterThan(T greaterThan) {
        this.greaterThan = new FieldType<>(greaterThan);
        return this;
    }

    public RangeFilter<T> setLessThan(T lessThan) {
        this.lessThan = new FieldType<>(lessThan);
        return this;
    }

    public RangeFilter<T> setGreaterThanOrEqual(T greaterThanOrEqual) {
        this.greaterThanOrEqual = new FieldType<>(greaterThanOrEqual);
        return this;
    }

    public RangeFilter<T> setLessThanOrEqual(T lessThanOrEqual) {
        this.lessThanOrEqual = new FieldType<>(lessThanOrEqual);
        return this;
    }

    public RangeFilter<T> setLike(T like) {
        this.like = new FieldType<>(like);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RangeFilter<?> that = (RangeFilter<?>) o;
        return Objects.equals(greaterThan, that.greaterThan) &&
                Objects.equals(lessThan, that.lessThan) &&
                Objects.equals(greaterThanOrEqual, that.greaterThanOrEqual) &&
                Objects.equals(lessThanOrEqual, that.lessThanOrEqual) &&
                Objects.equals(like, that.like);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), greaterThan, lessThan, greaterThanOrEqual, lessThanOrEqual, like);
    }

    @Override
    public String toString() {
        return super.toString() + " [" +
                (greaterThan != null ? "greaterThan=" + greaterThan + ", " : "") +
                (lessThan != null ? "lessThan=" + lessThan + ", " : "") +
                (greaterThanOrEqual != null ? "greaterThanOrEqual=" + greaterThanOrEqual + ", " : "") +
                (lessThanOrEqual != null ? "lessThanOrEqual=" + lessThanOrEqual + ", " : "") +
                (like != null ? "like=" + like : "") +
                "]";
    }
}