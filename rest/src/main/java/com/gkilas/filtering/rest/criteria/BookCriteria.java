package com.gkilas.filtering.rest.criteria;

import com.kgkilas.filtering.filters.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookCriteria implements Serializable {

    private LongFilter bookId;
    private LongFilter authorId;
    private StringFilter authorName;
    private StringFilter title;
}
