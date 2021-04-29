package com.base.app.core.domain.base;

import com.base.app.core.utils.FilterUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CoreFilterService<T> {
    public CoreFilterService() {
    }

    public boolean recursionListFilter(List<T> lists, String searchParams) {
        return FilterUtils.recursionListFilter(lists, searchParams);
    }

    public List<T> filter(List<T> lists, String searchParams) {
        return FilterUtils.filter(lists, searchParams);
    }

    public Page<T> filter(List<T> lists, Pageable pageable, String searchParams) {
        return FilterUtils.filterWithPaging(lists, pageable, searchParams);
    }
}
