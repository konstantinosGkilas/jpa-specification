package com.kgkilas.filtering.filters;

import java.io.Serial;

public class BooleanFilter extends RangeFilter<Boolean> {
    @Serial
    private static final long serialVersionUID = 4L;

    public BooleanFilter() {
    }

    public BooleanFilter(BooleanFilter filter) {
        super(filter);
    }

    public BooleanFilter copy() {
        return new BooleanFilter(this);
    }
}
