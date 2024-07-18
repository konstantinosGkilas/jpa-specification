package com.kgkilas.specification.filters;

import java.io.Serial;
import java.time.LocalDate;

public class LocalDateFilter extends RangeFilter<LocalDate> {
    @Serial
    private static final long serialVersionUID = 5L;

    public LocalDateFilter() {
    }

    public LocalDateFilter(LocalDateFilter filter) {
        super(filter);
    }

    public LocalDateFilter copy() {
        return new LocalDateFilter(this);
    }
}
