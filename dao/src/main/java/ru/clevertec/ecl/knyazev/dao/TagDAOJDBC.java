package ru.clevertec.ecl.knyazev.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.clevertec.ecl.knyazev.entity.Tag;

@Repository
public class TagDAOJDBC implements TagDAO {
	private static final Integer PAGE_EL_LIMIT = 20;

	private static final Logger logger = LoggerFactory.getLogger(TagDAOJDBC.class);

	private static final String SELECT_QUERY = "SELECT id, name FROM tag";

	private JdbcTemplate jdbcTemplate;

	public TagDAOJDBC() {
	}

	@Autowired
	public TagDAOJDBC(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Optional<Tag> getTagByName(String name) {
		Optional<Tag> tagWrap = Optional.empty();

		if (name != null && name.length() > 0L) {
			tagWrap = jdbcTemplate.query(SELECT_QUERY + " WHERE name=?", new BeanPropertyRowMapper<>(Tag.class), name)
					.stream().findAny();
		}

		return tagWrap;
	}
	
	@Override
	public Optional<Tag> getTagByPartOfName(String name) {
		Optional<Tag> tagWrap = Optional.empty();

		if (name != null && name.length() > 0L) {
			name = "%" + name + "%";
			
			tagWrap = jdbcTemplate.query(SELECT_QUERY + " WHERE name LIKE ?", new BeanPropertyRowMapper<>(Tag.class), name)
					.stream().findAny();
		}

		return tagWrap;
	}
	
	@Override
	public Optional<Tag> getTagByPartOfDescription(String description) {
		Optional<Tag> tagWrap = Optional.empty();

		if (description != null && description.length() > 0L) {
			description = "%" + description + "%";
			
			tagWrap = jdbcTemplate.query(SELECT_QUERY + " WHERE description LIKE ?", new BeanPropertyRowMapper<>(Tag.class), description)
					.stream().findAny();
		}

		return tagWrap;
	}

	@Override
	public Optional<Tag> getTagById(Long id) {
		Optional<Tag> tagWrap = Optional.empty();

		if (id != null && id > 0L) {
			tagWrap = jdbcTemplate.query(SELECT_QUERY + " WHERE id=?", new BeanPropertyRowMapper<>(Tag.class), id)
					.stream().findAny();
		} else {
			logger.error("Error getting tag by invalid id={}", id);
		}

		return tagWrap;
	}

	@Override
	public List<Tag> getAllTags() {
		return jdbcTemplate.query(SELECT_QUERY, new BeanPropertyRowMapper<>(Tag.class));
	}

	@Override
	public List<Tag> getAllTags(Integer page, Integer elementsOnPage) {
		List<Tag> tags = new ArrayList<>();

		if ((page == null || page < 1) || (elementsOnPage == null || elementsOnPage < 1)) {
			return tags;
		}

		Integer offsset = (page - 1) * elementsOnPage;

		return jdbcTemplate.query(SELECT_QUERY + " LIMIT ? OFFSET ?", new BeanPropertyRowMapper<>(Tag.class),
				elementsOnPage, offsset);
	}

	@Override
	public List<Tag> getAllTags(Integer page) {
		return getAllTags(page, PAGE_EL_LIMIT);
	}
	
	@Override
	public List<Tag> getAllTagsByGiftCertificateId(Long giftCertificateId) {
		List<Tag> tags = new ArrayList<>();

		if (giftCertificateId != null && giftCertificateId > 0L) {
			tags = jdbcTemplate.query(SELECT_QUERY + "WHERE id=ANY(SELECT tag_id FROM gift_certificate_tag WHERE gift_certificate_id = ?)", new BeanPropertyRowMapper<>(Tag.class), giftCertificateId);
		} else {
			logger.error("Error getting tags by invalid gift certificate id={}", giftCertificateId);
		}

		return tags;
	}

	@Override
	public Optional<Tag> saveTag(Tag tag) {
		Optional<Tag> tagWrap = Optional.empty();

		if (tag != null && tag.getId() == null) {
			jdbcTemplate.update("INSERT INTO tag(name) VALUES(?)", tag.getName());
		} else {
			logger.error("Error saving on invalid tag: {}", tag);
		}

		return tagWrap;
	}

	@Override
	public Optional<Tag> updateTag(Tag tag) {
		Optional<Tag> tagWrap = Optional.empty();

		if (tag != null && tag.getId() != null && tag.getId() > 0L) {
			Tag tagDB = getTagById(tag.getId()).orElse(null);

			if (tagDB != null) {
				if (!tag.equals(tagDB)) {
					tagDB.setName(tag.getName());
					
					jdbcTemplate.update("UPDATE tag SET name = ?", tagDB.getName());

					tagWrap = Optional.of(tagDB);
				} else {
					logger.error("Error on updating. tag name is null or already exists");
				}
			} else {
				logger.error("Error on updating. Tag not exists on given id={}", tag.getId());
			}
		} else {
			logger.error("Error updating on invalid tag: {}", tag);
		}

		return tagWrap;
	}

	@Override
	public Boolean deleteTag(Tag tag) {
		Boolean result = false;

		if (tag != null && tag.getId() != null && tag.getId() > 0L) {

			Tag dbTag = getTagById(tag.getId()).orElse(null);

			if (dbTag != null) {
				jdbcTemplate.update("DELETE FROM tag WHERE id = ?", tag.getId());

				result = true;
			} else {
				logger.error("Tag not exists with given id={}", tag.getId());
			}
		} else {
			logger.error("Given invalid tag={} for deleting", tag);
		}

		return result;
	}

}
