package com.example.api.demo.feature.loan.v1;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.api.demo.common.interfaces.MapperInterface;
import com.example.api.demo.feature.loan.Loan;

@Component
public class LoanMapperV1 implements MapperInterface<Loan, LoanReqestV1, LoanResponseV1> {
    @Override
    public LoanResponseV1 entityToDto(Loan entity) {
        if (entity == null) {
            return null;
        }
        return new LoanResponseV1(entity.getId(), entity.getBook().getId(), entity.getLoanDate(), entity.getRetunDate());
    }

    @Override
    public Loan dtoToEntity(LoanReqestV1 dto) {
        if (dto == null) {
            return null;
        }
        return new Loan();
    }

    @Override
    public List<Loan> dtoListToEntityList(List<LoanReqestV1> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::dtoToEntity).toList();
    }

    @Override
    public List<LoanResponseV1> entityListToDtoList(List<Loan> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::entityToDto).toList();
    }

}
