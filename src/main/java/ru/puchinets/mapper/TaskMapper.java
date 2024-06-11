package ru.puchinets.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import ru.puchinets.dto.request.TaskRequestDto;
import ru.puchinets.dto.response.TaskResponseDto;
import ru.puchinets.entity.Task;
import ru.puchinets.mapper.helper.DateMapperHelper;
import ru.puchinets.mapper.helper.TagMapperHelper;
import ru.puchinets.mapper.helper.TaskMapperHelper;


@Mapper(componentModel = "spring",
        uses = {TaskMapperHelper.class, TagMapperHelper.class, DateMapperHelper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper extends BaseMapper<TaskRequestDto, TaskResponseDto, Task> {

    @Override
    TaskResponseDto toDto(Task task);

    @Override
    @Mappings({
            @Mapping(source = "userId", target = "user"),
            @Mapping(source = "tagId", target = "tag"),
            @Mapping(source = "taskStatus", target = "taskStatus", defaultValue = "NOT_BEGINNED")
    })
    Task fromDto(TaskRequestDto dto);

    @Override
    default Task fromDto(TaskRequestDto request, Task entity) {
        Task requestTask = fromDto(request);
        if (!requestTask.getId().equals(entity.getId())) return entity;
        if (requestTask.getContent() != null && !requestTask.getContent().equals(entity.getContent()))
            entity.setContent(requestTask.getContent());
        if (requestTask.getDescription() != null && !requestTask.getDescription().equals(entity.getDescription()))
            entity.setDescription(requestTask.getDescription());
        if (requestTask.getUser() != null && !requestTask.getUser().equals(entity.getUser()))
            entity.setUser(requestTask.getUser());
        if (requestTask.getTag() != null && !requestTask.getTag().equals(entity.getTag()))
            entity.setTag(requestTask.getTag());
        if (requestTask.getTaskStatus() != null && !requestTask.getTaskStatus().equals(entity.getTaskStatus()))
            entity.setTaskStatus(requestTask.getTaskStatus());
        return entity;
    }
}
