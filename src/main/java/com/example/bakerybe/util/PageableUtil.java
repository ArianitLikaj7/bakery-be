package com.example.bakerybe.util;

import com.example.bakerybe.dto.PageRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtil {

    public static Pageable getPageable(PageRequest pageRequest) {

        int page = pageRequest.getPage();
        int pageSize = pageRequest.getPageSize();
        String sort = pageRequest.getSort();
        String sortDirection = pageRequest.getSortDirection();

        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(sortDirection)) {
            if (sortDirection.equals("asc")) {
                return org.springframework.data.domain.PageRequest.of(page, pageSize, Sort.by(sort).ascending());
            }

            return org.springframework.data.domain.PageRequest.of(page, pageSize, Sort.by(sort).descending());
        }

        if (StringUtils.isNotBlank(sort)) {
            return org.springframework.data.domain.PageRequest.of(page, pageSize, Sort.by(sort));
        }

        return org.springframework.data.domain.PageRequest.of(page, pageSize);
    }
}
