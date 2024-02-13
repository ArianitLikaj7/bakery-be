package com.example.bakerybe.dto;

import com.example.bakerybe.util.PageableUtil;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.beans.ConstructorProperties;

@Getter
@Setter
@NoArgsConstructor
public class PageRequest {

    @Min(0)
    private int page;

    @Min(1)
    @Max(100)
    private int pageSize;

    private String sort;
    private String sortDirection;

    @ConstructorProperties({"page", "pageSize", "sort", "sortDirection"})
    public PageRequest(int page, int pageSize, String sort, String sortDirection) {
        this.page = page;
        this.pageSize = pageSize;
        this.sort = sort;
        this.sortDirection = sortDirection;
    }

    @ConstructorProperties({"page", "pageSize"})
    public PageRequest(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public Pageable getPageable() {
        return PageableUtil.getPageable(this);
    }
}
