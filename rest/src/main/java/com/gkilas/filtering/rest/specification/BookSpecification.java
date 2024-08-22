package com.gkilas.filtering.rest.specification;

import com.gkilas.filtering.rest.criteria.BookCriteria;
import com.gkilas.filtering.rest.domain.Author_;
import com.gkilas.filtering.rest.domain.Book;
import com.gkilas.filtering.rest.domain.Book_;
import org.springframework.data.jpa.domain.Specification;
import com.kgkilas.filtering.specification.CriteriaSpecification;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification extends CriteriaSpecification<Book> {

        public static Specification<Book> buildBookSpecification(BookCriteria bookCriteria) {
                return getSpecifications(bookCriteria);
        }

        /**
         * Combines the specifications based on the provided BookCriteria.
         *
         * @param bookCriteria The criteria for filtering books.
         * @return A combined Specification for the Book entity.
         */
        protected static Specification<Book> getSpecifications(BookCriteria bookCriteria) {
                CriteriaSpecification<Book> bookSpecification = new CriteriaSpecification<>();
                List<Specification<Book>> joinSpecifications = buildSpecifications(bookSpecification, bookCriteria);
                return combine(joinSpecifications);  // Ensure the combine method uses the new implementation
        }

        /**
         * Builds a list of specifications based on the provided criteria.
         *
         * @param bookCriteriaSpecification The generic specification builder.
         * @param bookCriteria      The criteria for filtering books.
         * @return A list of specifications.
         */
        private static List<Specification<Book>> buildSpecifications(CriteriaSpecification<Book> bookCriteriaSpecification, BookCriteria bookCriteria) {
                List<Specification<Book>> specifications = new ArrayList<>();

                // Build specifications for individual fields
                addIfNotNull(specifications, bookCriteriaSpecification.buildSpecification(Book_.ID, bookCriteria.getBookId()));
                addIfNotNull(specifications, bookCriteriaSpecification.buildSpecification(Book_.TITLE, bookCriteria.getTitle()));

                // Use buildJoinSpecification for related entity fields
                addIfNotNull(specifications, buildJoinSpecification(Book_.AUTHOR, bookCriteria.getAuthorName(), Author_.NAME));

                return specifications;
        }
}