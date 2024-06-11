package ru.puchinets.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.puchinets.dto.request.CommentRequestDto;
import ru.puchinets.dto.response.CommentResponseDto;
import ru.puchinets.entity.Comment;
import ru.puchinets.mapper.helper.CommentMapperHelper;
import ru.puchinets.mapper.helper.DateMapperHelper;

@Mapper(componentModel = "spring", uses = {CommentMapperHelper.class, DateMapperHelper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper extends BaseMapper<CommentRequestDto, CommentResponseDto, Comment> {

    @Override
    CommentResponseDto toDto(Comment entity);

    @Override
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "taskId", target = "task")
    @Mapping(target = "id", ignore = true)
    Comment fromDto(CommentRequestDto request);

    @Override
    default Comment fromDto(CommentRequestDto request, Comment entity) {
        if (request.id() == null || !request.id().equals(entity.getId())) return entity;
        Comment upd = fromDto(request);
        if (upd.getSubject() != null) entity.setSubject(upd.getSubject());
        if (upd.getContent() != null) entity.setContent(upd.getContent());
        return entity;
    }
}
