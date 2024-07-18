package com.kgkilas.filtering.filters;

import java.io.Serial;

public class LongFilter extends RangeFilter<Long> {
    @Serial
    private static final long serialVersionUID = 3L;

    public LongFilter() {
    }

    public LongFilter(LongFilter filter) {
        super(filter);
    }

    public LongFilter copy() {
        return new LongFilter(this);
    }
}
