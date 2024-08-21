package com.kgkilas.filtering.specification;

import com.kgkilas.filtering.filters.FieldType;
import com.kgkilas.filtering.filters.RangeFilter;
import com.kgkilas.filtering.filters.StringFilter;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.function.BiFunction;


public class CriteriaSpecification<T> implements Specification<T> {

    public <F extends Comparable<? super F>> Specification<T> buildSpecification(String column, RangeFilter<F> rangeFilter) {
        if (rangeFilter == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> buildRangePredicate(root.get(column), rangeFilter, criteriaBuilder);
    }

    public static <F extends Comparable<? super F>> Predicate buildRangePredicate(Path<F> fieldPath, RangeFilter<F> rangeFilter, CriteriaBuilder criteriaBuilder) {
        // Initialize with conjunction predicate (i.e., true)
        Predicate combinedPredicate = criteriaBuilder.conjunction();
        if (rangeFilter != null) {
            // Construct the map of conditions and corresponding predicate functions
            Map<Boolean, BiFunction<Path<F>, CriteriaBuilder, Predicate>> predicateMap = constructPredicateMap(rangeFilter);
            // Iterate over the map and apply the predicates if the condition is true
            for (Map.Entry<Boolean, BiFunction<Path<F>, CriteriaBuilder, Predicate>> entry : predicateMap.entrySet()) {
                if (entry.getKey()) {
                    combinedPredicate = criteriaBuilder.and(combinedPredicate, entry.getValue().apply(fieldPath, criteriaBuilder));
                }
            }
        }

        return combinedPredicate;
    }

    private static <F extends Comparable<? super F>> Map<Boolean, BiFunction<Path<F>, CriteriaBuilder, Predicate>> constructPredicateMap(RangeFilter<F> rangeFilter) {
        // Map to store the condition checks and corresponding predicate functions
        Map<Boolean, BiFunction<Path<F>, CriteriaBuilder, Predicate>> predicateMap = new LinkedHashMap<>();
        predicateMap.put(rangeFilter.getEquals() != null, (path, cb) -> cb.equal(path, rangeFilter.getEquals().getValue()));
        predicateMap.put(rangeFilter.getIn() != null && !rangeFilter.getIn().isEmpty(), (path, cb) -> path.in(rangeFilter.getIn().stream().map(FieldType::getValue).toList()));
        predicateMap.put(rangeFilter.getNotIn() != null && !rangeFilter.getNotIn().isEmpty(), (path, cb) -> path.in(rangeFilter.getNotIn().stream().map(FieldType::getValue).toList()).not());
        predicateMap.put(rangeFilter.getGreaterThan() != null, (path, cb) -> cb.greaterThan(path, rangeFilter.getGreaterThan().getValue()));
        predicateMap.put(rangeFilter.getGreaterThanOrEqual() != null, (path, cb) -> cb.greaterThanOrEqualTo(path, rangeFilter.getGreaterThanOrEqual().getValue()));
        predicateMap.put(rangeFilter.getLessThan() != null, (path, cb) -> cb.lessThan(path, rangeFilter.getLessThan().getValue()));
        predicateMap.put(rangeFilter.getLessThanOrEqual() != null, (path, cb) -> cb.lessThanOrEqualTo(path, rangeFilter.getLessThanOrEqual().getValue()));
        predicateMap.put(rangeFilter.getLike() != null, (path, cb) -> cb.like(path.as(String.class), "%" + rangeFilter.getLike().getValue().toString() + "%"));
        if(rangeFilter.getClass() == StringFilter.class){
            predicateMap.put(((StringFilter) rangeFilter).getContains() != null, (path, cb) -> cb.like(cb.lower(path.as(String.class)), "%" + ((StringFilter) rangeFilter).getContains().toLowerCase() + "%"));
            predicateMap.put(((StringFilter) rangeFilter).getDoesNotContain() != null, (path, cb) -> cb.not(cb.like(cb.lower(path.as(String.class)), "%" + ((StringFilter) rangeFilter).getDoesNotContain().toLowerCase() + "%")));
        }
        return predicateMap;
    }

    public static <T, F extends Comparable<? super F>> Specification<T> buildJoinSpecification(
            String column, RangeFilter<F> rangeFilter, String attribute) {
        return (root, query, criteriaBuilder) -> {
            Path<F> fieldPath = root.get(column).get(attribute);
            return buildRangePredicate(fieldPath, rangeFilter, criteriaBuilder);
        };
    }

    public static <T> Specification<T> combine(Collection<Specification<T>> specifications) {
        Specification<T> combinedSpecification = null;
        for (Specification<T> specification : specifications) {
            if (combinedSpecification == null) {
                combinedSpecification = Specification.where(specification);
            } else {
                combinedSpecification = combinedSpecification.and(specification);
            }
        }
        return combinedSpecification;
    }

    public static <T> void addIfNotNull(List<Specification<T>> specifications, Specification<T> specification) {
        if (specification != null) {
            specifications.add(specification);
        }
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}