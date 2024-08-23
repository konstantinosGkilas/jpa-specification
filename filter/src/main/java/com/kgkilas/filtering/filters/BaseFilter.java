package com.kgkilas.filtering.filters;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BaseFilter class is a generic filter for various field types. It supports equality, non-equality,
 * specification checks, and inclusion/exclusion in lists.
 *
 * @param <T> the type of the field being filtered, which must be comparable.
 */
@Getter
public class BaseFilter<T extends Comparable<? super T>> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private FieldType<T> equals;       // Holds the value to be checked for equality.
    private FieldType<T> notEquals;    // Holds the value to be checked for inequality.
    private Boolean specified;         // Indicates whether the field should be specified (not null).
    private List<FieldType<T>> in;     // List of values for 'in' filtering.
    private List<FieldType<T>> notIn;  // List of values for 'not in' filtering.

    /**
     * Default constructor for BaseFilter.
     */
    public BaseFilter() {
        // Default constructor
    }

    /**
     * Copy constructor for BaseFilter. Creates a deep copy of the given filter.
     *
     * @param filter the BaseFilter instance to be copied.
     */
    public BaseFilter(BaseFilter<T> filter) {
        this.equals = filter.equals;
        this.notEquals = filter.notEquals;
        this.specified = filter.specified;
        this.in = copyList(filter.in);
        this.notIn = copyList(filter.notIn);
    }

    /**
     * Creates a copy of the current BaseFilter instance.
     *
     * @return a new BaseFilter instance that is a copy of the current instance.
     */
    public BaseFilter<T> copy() {
        return new BaseFilter<>(this);
    }

    /**
     * Sets the value to be checked for equality.
     *
     * @param equals the value to be set for equality check.
     * @return the current instance of BaseFilter with updated equality value.
     */
    public BaseFilter<T> setEquals(T equals) {
        this.equals = new FieldType<>(equals);
        return this;
    }

    /**
     * Sets the value to be checked for inequality.
     *
     * @param notEquals the value to be set for inequality check.
     * @return the current instance of BaseFilter with updated inequality value.
     */
    public BaseFilter<T> setNotEquals(T notEquals) {
        this.notEquals = new FieldType<>(notEquals);
        return this;
    }

    /**
     * Sets whether the field should be specified (not null).
     *
     * @param specified true if the field should be specified, false otherwise.
     * @return the current instance of BaseFilter with updated specified status.
     */
    public BaseFilter<T> setSpecified(Boolean specified) {
        this.specified = specified;
        return this;
    }

    /**
     * Sets a list of values for 'in' filtering.
     *
     * @param inValues the list of values to be set for 'in' filtering.
     * @return the current instance of BaseFilter with updated 'in' values.
     */
    public BaseFilter<T> setIn(List<T> inValues) {
        this.in = convertListToFieldType(inValues);
        return this;
    }

    /**
     * Sets a list of values for 'not in' filtering.
     *
     * @param notInValues the list of values to be set for 'not in' filtering.
     * @return the current instance of BaseFilter with updated 'not in' values.
     */
    public BaseFilter<T> setNotIn(List<T> notInValues) {
        this.notIn = convertListToFieldType(notInValues);
        return this;
    }

    /**
     * Compares this BaseFilter to another object for equality.
     *
     * @param o the object to compare to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        boolean isEqual = false;
        if (this == o) {
            isEqual = true;
        } else if (o != null && getClass() == o.getClass()) {
            BaseFilter<?> that = (BaseFilter<?>) o;
            isEqual = Objects.equals(this.equals, that.equals) &&
                    Objects.equals(this.notEquals, that.notEquals) &&
                    Objects.equals(this.specified, that.specified) &&
                    Objects.equals(this.in, that.in) &&
                    Objects.equals(this.notIn, that.notIn);
        }
        return isEqual;
    }

    /**
     * Generates a hash code for this BaseFilter.
     *
     * @return the hash code of this BaseFilter.
     */
    @Override
    public int hashCode() {
        return Objects.hash(equals, notEquals, specified, in, notIn);
    }

    /**
     * Returns a string representation of this BaseFilter, detailing the filter conditions.
     *
     * @return a string representation of the filter.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getFilterName() + " [");
        if (equals != null) sb.append("equals=").append(equals).append(", ");
        if (notEquals != null) sb.append("notEquals=").append(notEquals).append(", ");
        if (specified != null) sb.append("specified=").append(specified).append(", ");
        if (in != null) sb.append("in=").append(in).append(", ");
        if (notIn != null) sb.append("notIn=").append(notIn);

        if (sb.charAt(sb.length() - 2) == ',') {
            sb.deleteCharAt(sb.length() - 2); // Remove trailing comma
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns the simple class name of the filter for the toString method.
     *
     * @return the simple class name of this filter.
     */
    protected String getFilterName() {
        return getClass().getSimpleName();
    }

    /**
     * Converts a list of values into a list of FieldType objects.
     *
     * @param values the list of values to convert.
     * @return a list of FieldType objects.
     */
    private List<FieldType<T>> convertListToFieldType(List<T> values) {
        List<FieldType<T>> fieldTypeList = new ArrayList<>();
        if (values != null) {
            for (T value : values) {
                fieldTypeList.add(new FieldType<>(value));
            }
        }
        return fieldTypeList;
    }

    /**
     * Creates a shallow copy of the given list of FieldType objects.
     *
     * @param list the list to copy.
     * @return a shallow copy of the given list.
     */
    private List<FieldType<T>> copyList(List<FieldType<T>> list) {
        return (list != null) ? new ArrayList<>(list) : null;
    }
}