package com.kgkilas.filtering.specification;

import com.kgkilas.filtering.filters.FieldType;
import com.kgkilas.filtering.filters.RangeFilter;
import com.kgkilas.filtering.filters.StringFilter;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class implements the Specification interface to provide a flexible way
 * to create dynamic queries based on filtering criteria.
 *
 * @param <T> the type of the entity
 */
public class CriteriaSpecification<T> implements Specification<T> {

    /**
     * Builds a specification based on a column and a range filter.
     *
     * @param column the name of the column to filter on
     * @param rangeFilter the range filter containing the filtering conditions
     * @param <F> the type of the field being filtered
     * @return a specification that can be used for querying
     */
    public <F extends Comparable<? super F>> Specification<T> buildSpecification(String column, RangeFilter<F> rangeFilter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); // Initialize the predicate to a conjunction
            if (column != null && rangeFilter != null) {
                Path<F> fieldPath = root.get(column);
                if (fieldPath != null) {
                    predicate = buildRangePredicate(fieldPath, rangeFilter, criteriaBuilder);
                }
            }
            return predicate;
        };
    }

    /**
     * Builds a predicate based on a range filter.
     *
     * @param fieldPath the path to the field being filtered
     * @param rangeFilter the range filter containing the filtering conditions
     * @param criteriaBuilder the CriteriaBuilder used to construct the predicate
     * @param <F> the type of the field being filtered
     * @return a predicate based on the range filter conditions, or a conjunction if no conditions apply
     */
    public static <F extends Comparable<? super F>> Predicate buildRangePredicate(Path<F> fieldPath, RangeFilter<F> rangeFilter, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction(); // Initialize the predicate to a conjunction

        if (rangeFilter != null && fieldPath != null) {
            List<Predicate> predicates = new ArrayList<>();

            addIfNotNull(predicates, rangeFilter.getEquals() != null,
                    criteriaBuilder.equal(fieldPath, Optional.ofNullable(rangeFilter.getEquals()).map(FieldType::getValue).orElse(null)));
            addIfNotNull(predicates, rangeFilter.getIn() != null && !rangeFilter.getIn().isEmpty(),
                    fieldPath.in(mapToValues(rangeFilter.getIn())));
            addIfNotNull(predicates, rangeFilter.getNotIn() != null && !rangeFilter.getNotIn().isEmpty(),
                    criteriaBuilder.not(fieldPath.in(mapToValues(rangeFilter.getNotIn()))));
            addIfNotNull(predicates, rangeFilter.getGreaterThan() != null,
                    criteriaBuilder.greaterThan(fieldPath, Optional.ofNullable(rangeFilter.getGreaterThan()).map(FieldType::getValue).orElse(null)));
            addIfNotNull(predicates, rangeFilter.getGreaterThanOrEqual() != null,
                    criteriaBuilder.greaterThanOrEqualTo(fieldPath, Optional.ofNullable(rangeFilter.getGreaterThanOrEqual()).map(FieldType::getValue).orElse(null)));
            addIfNotNull(predicates, rangeFilter.getLessThan() != null,
                    criteriaBuilder.lessThan(fieldPath, Optional.ofNullable(rangeFilter.getLessThan()).map(FieldType::getValue).orElse(null)));
            addIfNotNull(predicates, rangeFilter.getLessThanOrEqual() != null,
                    criteriaBuilder.lessThanOrEqualTo(fieldPath, Optional.ofNullable(rangeFilter.getLessThanOrEqual()).map(FieldType::getValue).orElse(null)));
            if (rangeFilter.getLike() != null) {
                addIfNotNull(predicates,
                        true,
                        criteriaBuilder.like(
                                fieldPath.as(String.class),
                                "%" + Optional.ofNullable(rangeFilter.getLike()).map(FieldType::getValue).map(Object::toString).orElse("") + "%"
                        )
                );
            }
            if (rangeFilter instanceof StringFilter stringFilter) {
                addIfNotNull(predicates, stringFilter.getContains() != null,
                        criteriaBuilder.like(criteriaBuilder.lower(fieldPath.as(String.class)), "%" + Optional.ofNullable(stringFilter.getContains()).orElse("").toLowerCase() + "%"));
                addIfNotNull(predicates, stringFilter.getDoesNotContain() != null,
                        criteriaBuilder.not(criteriaBuilder.like(criteriaBuilder.lower(fieldPath.as(String.class)), "%" + Optional.ofNullable(stringFilter.getDoesNotContain()).orElse("").toLowerCase() + "%")));
            }

            if (!predicates.isEmpty()) {
                predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        }

        return predicate;
    }

    /**
     * Adds a predicate to the list if the condition is true.
     *
     * @param predicates the list of predicates
     * @param condition the condition to check
     * @param predicate the predicate to add
     */
    private static <F extends Comparable<? super F>> void addIfNotNull(List<Predicate> predicates, boolean condition, Predicate predicate) {
        if (condition && predicate != null) {
            predicates.add(predicate);
        }
    }

    /**
     * Maps a list of FieldType objects to their values.
     *
     * @param fieldTypes the list of FieldType objects
     * @param <F> the type of the field being filtered
     * @return a list of field values
     */
    public static <F extends Comparable<? super F>> List<F> mapToValues(List<FieldType<F>> fieldTypes) {
        return fieldTypes == null ? Collections.emptyList() : fieldTypes.stream().map(FieldType::getValue).collect(Collectors.toList());
    }

    /**
     * Builds a join specification for filtering based on an attribute in a related entity.
     *
     * @param column the name of the column representing the relationship
     * @param rangeFilter the range filter containing the filtering conditions
     * @param attribute the attribute in the related entity to filter on
     * @param <T> the type of the entity
     * @param <F> the type of the field being filtered
     * @return a specification that can be used for querying
     */
    public static <T, F extends Comparable<? super F>> Specification<T> buildJoinSpecification(String column, RangeFilter<F> rangeFilter, String attribute) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); // Initialize the predicate to a conjunction
            if (column != null && rangeFilter != null && attribute != null) {
                Path<F> attributePath = root.get(column).get(attribute);
                if (attributePath != null) {
                    predicate = buildRangePredicate(attributePath, rangeFilter, criteriaBuilder);
                }
            }
            return predicate;
        };
    }

    /**
     * Combines a collection of specifications into a single specification using logical AND.
     *
     * @param specifications the collection of specifications to combine
     * @param <T> the type of the entity
     * @return a combined specification, or a conjunction if the collection is empty
     */
    public static <T> Specification<T> combine(Collection<Specification<T>> specifications) {
        return (root, query, criteriaBuilder) -> {
            Predicate combinedPredicate = criteriaBuilder.conjunction(); // Initialize the predicate to a conjunction
            if (specifications != null && !specifications.isEmpty()) {
                List<Predicate> predicates = new ArrayList<>();
                for (Specification<T> spec : specifications) {
                    if (spec != null) {
                        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
                        if (predicate != null) {
                            predicates.add(predicate);
                        }
                    }
                }
                if (!predicates.isEmpty()) {
                    combinedPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                }
            }
            return combinedPredicate;
        };
    }

    /**
     * Adds a specification to the list if it's not null.
     *
     * @param specifications The list of specifications.
     * @param specification The specification to add.
     */
    public static <T> void addIfNotNull(List<Specification<T>> specifications, Specification<T> specification) {
        if (specification != null) {
            specifications.add(specification);
        }
    }

    /**
     * This method must be implemented as part of the Specification interface.
     * Currently, it returns a conjunction, as the actual predicate logic is handled by other methods.
     */
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.conjunction();
    }
}