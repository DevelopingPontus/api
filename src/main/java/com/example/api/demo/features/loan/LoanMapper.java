package com.example.api.demo.features.loan;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.api.demo.common.interfaces.MapperInterface;

@Component
public class LoanMapper implements MapperInterface<Loan, LoanReq1, LoanRes1> {
    @Override
    public LoanRes1 entityToDto(Loan entity) {
        if (entity == null) {
            return null;
        }
        return new LoanRes1(entity.getId(), entity.getBook().getId(), entity.getLoanDate(), entity.getRetunDate());
    }

    @Override
    public Loan dtoToEntity(LoanReq1 dto) {
        if (dto == null) {
            return null;
        }
        return new Loan();
    }

    @Override
    public List<Loan> dtoListToEntityList(List<LoanReq1> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::dtoToEntity).toList();
    }

    @Override
    public List<LoanRes1> entityListToDtoList(List<Loan> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::entityToDto).toList();
    }

}
