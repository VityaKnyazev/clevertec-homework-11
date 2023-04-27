package ru.clevertec.ecl.knyazev.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ru.clevertec.ecl.knyazev.dto.TagDTO;
import ru.clevertec.ecl.knyazev.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

	TagDTO toDTO(Tag tag);

	Tag toTag(TagDTO tagDTO);

	List<TagDTO> toDTOList(List<Tag> tags);
	
	List<Tag> toTags(List<TagDTO> tagsDTO);
}
