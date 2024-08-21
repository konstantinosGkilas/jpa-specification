package com.gkilas.filtering.rest.specification;

import com.gkilas.filtering.rest.criteria.BookCriteria;
import com.gkilas.filtering.rest.domain.Book;
import org.springframework.data.jpa.domain.Specification;
import com.kgkilas.filtering.specification.*;
import java.util.ArrayList;
import java.util.List;

public class BookSpecification extends CriteriaSpecification<Book> {

public static Specification<Book> buildBookSpecification(BookCriteria eventCriteria) {

        return getSpecifications(eventCriteria);
        }

/**
 * Combines the specifications based on the provided BookCriteria.
 *
 * @param eventCriteria The criteria for filtering events.
 * @return A list of Specifications for the event entity.
 */
protected static Specification<Book> getSpecifications(BookCriteria eventCriteria) {
        CriteriaSpecification<Book> eventSpecification = new CriteriaSpecification<>();
        List<Specification<Book>> joinSpecifications = buildSpecifications(eventSpecification, eventCriteria);
        return combine(joinSpecifications);
        }

/**
 * Builds a list of specifications based on the provided criteria.
 *
 * @param eventSpecification The generic specification builder.
 * @param eventCriteria      The criteria for filtering events.
 * @return A list of specifications.
 */
private static List<Specification<Book>> buildSpecifications(CriteriaSpecification<Book> eventSpecification, BookCriteria eventCriteria) {
        List<Specification<Book>> specifications = new ArrayList<>();
        addIfNotNull(specifications, eventSpecification.buildSpecification("id", eventCriteria.getBookId()));
        addIfNotNull(specifications, eventSpecification.buildSpecification("title", eventCriteria.getTitle()));
        return specifications;
        }
}