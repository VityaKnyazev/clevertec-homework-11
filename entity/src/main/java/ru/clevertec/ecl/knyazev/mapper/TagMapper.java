package ru.clevertec.ecl.knyazev.mapper;

import org.mapstruct.Mapper;

import ru.clevertec.ecl.knyazev.entity.Tag;

@Mapper(componentModel = "spring")
public abstract class TagMapper {
	public Tag toTag() {
		return null;
	}
}
