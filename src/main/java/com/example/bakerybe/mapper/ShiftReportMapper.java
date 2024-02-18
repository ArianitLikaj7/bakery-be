package com.example.bakerybe.mapper;

import com.example.bakerybe.dto.ShiftReportDto;
import com.example.bakerybe.entity.ShiftReport;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShiftReportMapper {

    private final ModelMapper mapper;

    public ShiftReportDto toDto(ShiftReport shiftReport){
        return mapper.map(shiftReport, ShiftReportDto.class);
    }
}
