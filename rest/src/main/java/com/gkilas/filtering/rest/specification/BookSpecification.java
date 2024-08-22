package com.gkilas.filtering.rest.specification;

import com.gkilas.filtering.rest.criteria.BookCriteria;
import com.gkilas.filtering.rest.domain.Author_;
import com.gkilas.filtering.rest.domain.Book;
import com.gkilas.filtering.rest.domain.Book_;
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
         * @param bookCriteriaSpecification The generic specification builder.
         * @param bookCriteria      The criteria for filtering events.
         * @return A list of specifications.
         */
        private static List<Specification<Book>> buildSpecifications(CriteriaSpecification<Book> bookCriteriaSpecification, BookCriteria bookCriteria) {
                List<Specification<Book>> specifications = new ArrayList<>();
                addIfNotNull(specifications, bookCriteriaSpecification.buildSpecification(Book_.ID, bookCriteria.getBookId()));
                addIfNotNull(specifications, bookCriteriaSpecification.buildSpecification(Book_.TITLE, bookCriteria.getTitle()));
                addIfNotNull(specifications, buildJoinSpecification(Book_.AUTHOR, bookCriteria.getAuthorName(), Author_.NAME));
                return specifications;
        }
}