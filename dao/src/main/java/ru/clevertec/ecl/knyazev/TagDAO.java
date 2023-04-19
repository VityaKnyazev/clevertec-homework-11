package ru.clevertec.ecl.knyazev;

import java.util.List;
import java.util.Optional;

import ru.clevertec.ecl.knyazev.entity.Tag;

/**
 * 
 * Interface for Tag data access object.
 *
 */
public interface TagDAO {
	/**
	 * 
	 * Get Optional<Tag> from database on giving id.
	 * 
	 * @param Long id for searching tag in database.
	 * @return Optional<Tag> tag. If tag not exists in database - Optional empty.
	 */
	Optional<Tag> getTagById(Long id);
	
	/**
	 * 
	 * Get all tags from database
	 * 
	 * @return List<Tag> all tags from database or empty list.
	 */
	List<Tag> getAllTags();
	
	/**
	 * 
	 * Get all tags from database on given Integer page of given quantity of elements.
	 * 
	 * @param Integer page number.
	 * @param Integer elementsOnPage quantity of elements on page.
	 * @return List<Tag> tags on given Integer page of given Integer quantity of elements
	 *         from database or empty list.
	 */
	List<Tag> getAllTags(Integer page, Integer elementsOnPage);
	
	/**
	 * 
	 * Get all tags from database on given Integer page.
	 * 
	 * @param Integer page number.
	 * @return List<Tag> tags on given Integer page from database or empty list.
	 */
	List<Tag> getAllTags(Integer page);
	
	
	/**
	 * 
	 * Save tag to database
	 * 
	 * @param Tag tag for saving
	 * @return Optional<Tag> if was success on saving - optional tag, otherwise - optional empty.
	 */
	Optional<Tag> saveTag(Tag tag);
	
	/**
	 * 
	 * Update tag in database
	 * 
	 * @param Tag tag for updating
	 * @return Optional<Tag> if was success on updating - optional tag, otherwise - optional empty
	 */
	Optional<Tag> updateTag(Tag tag);
	
	/**
	 * 
	 * Delete tag in database
	 * 
	 * @param Tag tag for deleting
	 * @return Boolean true on success deleting, otherwise - false.
	 */
	Boolean deleteTag(Tag tag);
}
