package com.kgkilas.filtering.filters;

import java.io.Serial;
import java.time.LocalDateTime;

public class LocalDateTimeFilter extends RangeFilter<LocalDateTime> {
    @Serial
    private static final long serialVersionUID = 7L;

    public LocalDateTimeFilter() {
    }

    public LocalDateTimeFilter(LocalDateTimeFilter filter) {
        super(filter);
    }

    public LocalDateTimeFilter copy() {
        return new LocalDateTimeFilter(this);
    }

    @Override
    public LocalDateTimeFilter setGreaterThanOrEqual(LocalDateTime equals) {

        super.setGreaterThanOrEqual(equals.withHour(0).withMinute(0));
        return this;
    }

    @Override
    public LocalDateTimeFilter setLessThanOrEqual(LocalDateTime equals) {
        super.setLessThanOrEqual(equals.withHour(23).withMinute(59));
        return this;
    }
}
