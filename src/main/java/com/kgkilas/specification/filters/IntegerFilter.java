package com.kgkilas.specification.filters;

import java.io.Serial;

public class IntegerFilter extends RangeFilter<Integer> {
    @Serial
    private static final long serialVersionUID = 6L;

    public IntegerFilter() {
    }

    public IntegerFilter(IntegerFilter filter) {
        super(filter);
    }

    public IntegerFilter copy() {
        return new IntegerFilter(this);
    }
}
