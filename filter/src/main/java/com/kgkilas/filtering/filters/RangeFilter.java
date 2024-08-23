package com.kgkilas.filtering.filters;

import lombok.Getter;

import java.io.Serial;
import java.util.Objects;

/**
 * RangeFilter is a generic filter class that allows filtering based on
 * range criteria like greater than, less than, greater than or equal to,
 * less than or equal to, and like conditions.
 *
 * @param <T> the type of the field to be filtered, which must be comparable.
 */
@Getter
public class RangeFilter<T extends Comparable<? super T>> extends BaseFilter<T> {
    @Serial
    private static final long serialVersionUID = 2L;

    private FieldType<T> greaterThan;
    private FieldType<T> lessThan;
    private FieldType<T> greaterThanOrEqual;
    private FieldType<T> lessThanOrEqual;
    private FieldType<T> like;

    /**
     * Default constructor for RangeFilter.
     */
    public RangeFilter() {
        // No initialization required for default constructor
    }

    /**
     * Copy constructor for creating a new RangeFilter from an existing one.
     *
     * @param filter the existing RangeFilter to copy from.
     */
    public RangeFilter(RangeFilter<T> filter) {
        super(filter);
        this.greaterThan = filter.greaterThan;
        this.lessThan = filter.lessThan;
        this.greaterThanOrEqual = filter.greaterThanOrEqual;
        this.lessThanOrEqual = filter.lessThanOrEqual;
        this.like = filter.like;
    }

    /**
     * Creates a copy of the current RangeFilter instance.
     *
     * @return a new RangeFilter instance with the same settings.
     */
    @Override
    public RangeFilter<T> copy() {
        return new RangeFilter<>(this);
    }

    /**
     * Sets the greaterThan field with the specified value.
     *
     * @param greaterThan the value to set as greaterThan.
     * @return the current RangeFilter instance for method chaining.
     */
    public RangeFilter<T> setGreaterThan(T greaterThan) {
        this.greaterThan = new FieldType<>(greaterThan);
        return this;
    }

    /**
     * Sets the lessThan field with the specified value.
     *
     * @param lessThan the value to set as lessThan.
     * @return the current RangeFilter instance for method chaining.
     */
    public RangeFilter<T> setLessThan(T lessThan) {
        this.lessThan = new FieldType<>(lessThan);
        return this;
    }

    /**
     * Sets the greaterThanOrEqual field with the specified value.
     *
     * @param greaterThanOrEqual the value to set as greaterThanOrEqual.
     * @return the current RangeFilter instance for method chaining.
     */
    public RangeFilter<T> setGreaterThanOrEqual(T greaterThanOrEqual) {
        this.greaterThanOrEqual = new FieldType<>(greaterThanOrEqual);
        return this;
    }

    /**
     * Sets the lessThanOrEqual field with the specified value.
     *
     * @param lessThanOrEqual the value to set as lessThanOrEqual.
     * @return the current RangeFilter instance for method chaining.
     */
    public RangeFilter<T> setLessThanOrEqual(T lessThanOrEqual) {
        this.lessThanOrEqual = new FieldType<>(lessThanOrEqual);
        return this;
    }

    /**
     * Sets the like field with the specified value.
     *
     * @param like the value to set as like.
     * @return the current RangeFilter instance for method chaining.
     */
    public RangeFilter<T> setLike(T like) {
        this.like = new FieldType<>(like);
        return this;
    }

    /**
     * Compares this RangeFilter with another object for equality.
     *
     * @param o the object to compare with this RangeFilter.
     * @return true if the objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RangeFilter<?> that) || !super.equals(o)) return false;
        return Objects.equals(greaterThan, that.greaterThan) &&
                Objects.equals(lessThan, that.lessThan) &&
                Objects.equals(greaterThanOrEqual, that.greaterThanOrEqual) &&
                Objects.equals(lessThanOrEqual, that.lessThanOrEqual) &&
                Objects.equals(like, that.like);
    }

    /**
     * Returns the hash code value for this RangeFilter.
     *
     * @return the hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), greaterThan, lessThan, greaterThanOrEqual, lessThanOrEqual, like);
    }

    /**
     * Provides a string representation of this RangeFilter.
     *
     * @return the string representation of this RangeFilter.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" [");
        if (greaterThan != null) sb.append("greaterThan=").append(greaterThan).append(", ");
        if (lessThan != null) sb.append("lessThan=").append(lessThan).append(", ");
        if (greaterThanOrEqual != null) sb.append("greaterThanOrEqual=").append(greaterThanOrEqual).append(", ");
        if (lessThanOrEqual != null) sb.append("lessThanOrEqual=").append(lessThanOrEqual).append(", ");
        if (like != null) sb.append("like=").append(like);
        sb.append("]");
        return sb.toString();
    }
}
