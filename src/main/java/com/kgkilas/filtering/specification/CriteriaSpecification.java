package com.kgkilas.filtering.specification;

import com.kgkilas.filtering.filters.RangeFilter;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.function.BiFunction;

public class CriteriaSpecification<T> implements Specification<T> {

    protected <F extends Comparable<? super F>> Specification<T> buildSpecification(String column, RangeFilter<F> filter) {
        if (filter == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> buildRangePredicate(root.get(column), filter, criteriaBuilder);
    }

    protected static <F extends Comparable<? super F>> Predicate buildRangePredicate(Path<F> fieldPath, RangeFilter<F> filter, CriteriaBuilder criteriaBuilder) {
        // Initialize with conjunction predicate (i.e., true)
        Predicate combinedPredicate = criteriaBuilder.conjunction();
        if (filter != null) {
            // Construct the map of conditions and corresponding predicate functions
            Map<Boolean, BiFunction<Path<F>, CriteriaBuilder, Predicate>> predicateMap = constructPredicateMap(filter);
            // Iterate over the map and apply the predicates if the condition is true
            for (Map.Entry<Boolean, BiFunction<Path<F>, CriteriaBuilder, Predicate>> entry : predicateMap.entrySet()) {
                if (entry.getKey()) {
                    combinedPredicate = criteriaBuilder.and(combinedPredicate, entry.getValue().apply(fieldPath, criteriaBuilder));
                }
            }
        }

        return combinedPredicate;
    }

    private static <F extends Comparable<? super F>> Map<Boolean, BiFunction<Path<F>, CriteriaBuilder, Predicate>> constructPredicateMap(RangeFilter<F> filter) {
        // Map to store the condition checks and corresponding predicate functions
        Map<Boolean, BiFunction<Path<F>, CriteriaBuilder, Predicate>> predicateMap = new LinkedHashMap<>();
        predicateMap.put(filter.getEquals() != null, (path, cb) -> cb.equal(path, filter.getEquals().getValue()));
       // predicateMap.put(filter.getIn() != null && !filter.getIn().isEmpty(), (path, cb) -> path.in(filter.getIn().));
        //predicateMap.put(filter.getNotIn() != null && !filter.getNotIn().isEmpty(), (path, cb) -> path.in(filter.getNotIn()).not());
        predicateMap.put(filter.getGreaterThan() != null, (path, cb) -> cb.greaterThan(path, filter.getGreaterThan().getValue()));
        predicateMap.put(filter.getGreaterThanOrEqual() != null, (path, cb) -> cb.greaterThanOrEqualTo(path, filter.getGreaterThanOrEqual().getValue()));
        predicateMap.put(filter.getLessThan() != null, (path, cb) -> cb.lessThan(path, filter.getLessThan().getValue()));
        predicateMap.put(filter.getLessThanOrEqual() != null, (path, cb) -> cb.lessThanOrEqualTo(path, filter.getLessThanOrEqual().getValue()));
        predicateMap.put(filter.getLike() != null, (path, cb) -> cb.like(path.as(String.class), "%" + filter.getLike().getValue().toString() + "%"));
        return predicateMap;
    }

    protected static <T, F extends Comparable<? super F>> Specification<T> buildJoinSpecification(
            String column, RangeFilter<F> filter, String attribute) {
        return (root, query, criteriaBuilder) -> {
            Path<F> fieldPath = root.get(column).get(attribute);
            return buildRangePredicate(fieldPath, filter, criteriaBuilder);
        };
    }

    protected static <T> Specification<T> combine(Collection<Specification<T>> specifications) {
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

    protected static <T> void addIfNotNull(List<Specification<T>> specifications, Specification<T> specification) {
        if (specification != null) {
            specifications.add(specification);
        }
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}