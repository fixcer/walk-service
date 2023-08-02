package dev.toannv.interview.walk.mapper;

import dev.toannv.interview.walk.domain.Step;
import dev.toannv.interview.walk.utils.DateUtils;
import dev.toannv.interview.walk.web.api.model.RecordStepRequest;
import dev.toannv.interview.walk.web.api.model.RecordStepResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface IStepMapper {
    IStepMapper INSTANCE = Mappers.getMapper(IStepMapper.class);

    @Mapping(target = "date", qualifiedByName = "toLocalDate")
    @Mapping(source = "id", target = "stepId")
    RecordStepResponse toResponse(Step step);

    @Mapping(target = "steps", ignore = true)
    Step toStep(RecordStepRequest recordStepRequest);

    @Named("toLocalDate")
    default LocalDate toLocalDate(Date date) {
        return DateUtils.toLocalDate(date);
    }
}
