package com.kgkilas.filtering.criteria;

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

    LongFilter bookId;
    LongFilter authorId;
    StringFilter authorName;
    StringFilter title;
}
