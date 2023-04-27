package ru.clevertec.ecl.knyazev.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.PersistenceException;
import ru.clevertec.ecl.knyazev.entity.Tag;

public class TagDAOJPA implements DAO<Tag>{
	private static final Integer PAGE_EL_LIMIT = 20;

	private static final Logger logger = LoggerFactory.getLogger(TagDAOJPA.class);
	
	private SessionFactory sessionFactory;
	
	TagDAOJPA() {
	}
	
	@Autowired
	TagDAOJPA(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Optional<Tag> getById(Long id) {
		Optional<Tag> tagWrap = Optional.empty();

		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();

			if (id != null && id > 0L) {
				Tag tagDB = session.find(Tag.class, id);

				if (tagDB != null) {					
					session.getTransaction().commit();

					tagWrap = Optional.of(tagDB);
				} else {
					session.getTransaction().rollback();
					logger.error("Error. Tag not exists on given id={}", id);
				}
			} else {
				session.getTransaction().rollback();
				logger.error("Error getting tag by invalid id={}", id);
			}
		}

		return tagWrap;
	}

	@Override
	public List<Tag> getAll() {
		List<Tag> tags = new ArrayList<>();

		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();

			tags = session.createQuery("SELECT t FROM Tag t", Tag.class)
					.getResultList();

			if (!tags.isEmpty()) {
				session.getTransaction().commit();
			} else {
				session.getTransaction().rollback();
				logger.error("No tags were found");
			}
		}

		return tags;
	}

	@Override
	public List<Tag> getAll(Integer page, Integer elementsOnPage) {
		List<Tag> tags = new ArrayList<>();

		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();

			if ((page == null || page < 1) || (elementsOnPage == null || elementsOnPage < 1)) {
				session.getTransaction().rollback();
				logger.error("Invalid given param: page={} or elements on page={}", page, elementsOnPage);
				return tags;
			}

			Integer offsset = (page - 1) * elementsOnPage;

			tags = session.createQuery("SELECT t FROM Tag t", Tag.class)
					.setFirstResult(offsset).setMaxResults(elementsOnPage).getResultList();

			if (!tags.isEmpty()) {
				session.getTransaction().commit();
			} else {
				session.getTransaction().rollback();
				logger.error("No tags were found with given page={}, and elements on page={}", page,
						elementsOnPage);
			}
		}

		return tags;
	}

	@Override
	public List<Tag> getAll(Integer page) {
		return getAll(page, PAGE_EL_LIMIT);
	}

	@Override
	public Optional<Tag> save(Tag tag) {
		Optional<Tag> tagWrap = Optional.empty();

		Session session = sessionFactory.openSession();
		try {
			session.getTransaction().begin();

			if (tag != null && tag.getId() == null) {
				session.persist(tag);

				session.getTransaction().commit();

				tagWrap = Optional.of(tag);
			} else {
				session.getTransaction().rollback();
				logger.error("Error saving on invalid tag: {}", tag);
			}
		} catch (PersistenceException e) {
			session.getTransaction().rollback();
			logger.error("Error on saving tag: {}", e.getMessage(), e);
		} finally {
			session.close();
		}

		return tagWrap;
	}

	@Override
	public Optional<Tag> update(Tag tag) {

		Optional<Tag> tagWrap = Optional.empty();

		Session session = sessionFactory.openSession();
		try {
			session.getTransaction().begin();

			if (tag != null && tag.getId() != null && tag.getId() > 0L) {

				Tag tagDB = session.find(Tag.class, tag.getId());

				if (tagDB != null) {
					
					if (tag.getName() != null) {
						tagDB.setName(tag.getName());
					}

					session.merge(tagDB);
					
					session.getTransaction().commit();

					tagWrap = Optional.of(tagDB);
				} else {
					session.getTransaction().rollback();
					logger.error("Error on updating. Tag not exists on given id={}",
							tag.getId());
				}
			} else {
				session.getTransaction().rollback();
				logger.error("Error updating on invalid tag: {}", tag);
			}
		} catch (PersistenceException e) {
			session.getTransaction().rollback();
			logger.error("Error on updating tag: {}", e.getMessage(), e);
		} finally {
			session.close();
		}

		return tagWrap;
	}

	@Override
	public Boolean delete(Tag tag) {
		Boolean result = false;

		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();

			if (tag != null && tag.getId() != null && tag.getId() > 0L) {

				Tag dbTag = session.find(Tag.class, tag.getId());

				if (dbTag != null) {
					session.remove(dbTag);
					session.getTransaction().commit();

					result = true;
				} else {
					session.getTransaction().rollback();
					logger.error("Tag not exists with given id={}", tag.getId());
				}
			} else {
				session.getTransaction().rollback();
				logger.error("Given invalid tag={} for deleting", tag);
			}
		}

		return result;
	}
}
