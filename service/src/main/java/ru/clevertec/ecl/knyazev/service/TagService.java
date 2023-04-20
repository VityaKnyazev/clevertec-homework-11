package ru.clevertec.ecl.knyazev.service;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.HTML.Tag;

public interface TagService {
	Optional<Tag> showTag(Long id);
	
	List<Tag> showAllTags();

	List<Tag> showAllTags(Integer page, Integer pageSize);

	List<Tag> showAllTags(Integer page);

	Tag addTag(Tag tag);

	Tag changeTag(Tag tag);

	void removeTag(Tag tag);
}
