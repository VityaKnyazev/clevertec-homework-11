package ru.clevertec.ecl.knyazev.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceException;
import ru.clevertec.ecl.knyazev.entity.GiftCertificate;
import ru.clevertec.ecl.knyazev.entity.Tag;

@Repository
public class GiftCertificateDAOJPA implements DAO<GiftCertificate>, GiftCertificateDAO {
	private static final Integer PAGE_EL_LIMIT = 20;

	private static final Logger logger = LoggerFactory.getLogger(GiftCertificateDAOJPA.class);

	private SessionFactory sessionFactory;

	public GiftCertificateDAOJPA() {
	}

	@Autowired
	public GiftCertificateDAOJPA(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Optional<GiftCertificate> getById(Long id) {
		Optional<GiftCertificate> giftCertificateWrap = Optional.empty();

		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();

			if (id != null && id > 0L) {
				GiftCertificate giftCertificateDB = session.find(GiftCertificate.class, id);

				if (giftCertificateDB != null) {
					giftCertificateDB.getTags().size();

					session.getTransaction().commit();

					giftCertificateWrap = Optional.of(giftCertificateDB);
				} else {
					session.getTransaction().rollback();
					logger.error("Error. Gift certificate not exists on given id={}", id);
				}
			} else {
				session.getTransaction().rollback();
				logger.error("Error getting gift certificate by invalid id={}", id);
			}
		}

		return giftCertificateWrap;
	}

	@Override
	public List<GiftCertificate> getAll() {
		List<GiftCertificate> giftCertificates = new ArrayList<>();

		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();

			giftCertificates = session.createQuery("SELECT g FROM GiftCertificate g", GiftCertificate.class)
					.getResultList();

			if (!giftCertificates.isEmpty()) {
				session.getTransaction().commit();
			} else {
				session.getTransaction().rollback();
				logger.error("No gift certificates were found");
			}
		}

		return giftCertificates;
	}

	@Override
	public List<GiftCertificate> getAll(Integer page, Integer elementsOnPage) {
		List<GiftCertificate> giftCertificates = new ArrayList<>();

		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();

			if ((page == null || page < 1) || (elementsOnPage == null || elementsOnPage < 1)) {
				session.getTransaction().rollback();
				logger.error("Invalid given param: page={} or elements on page={}", page, elementsOnPage);
				return giftCertificates;
			}

			Integer offsset = (page - 1) * elementsOnPage;

			giftCertificates = session.createQuery("SELECT g FROM GiftCertificate g", GiftCertificate.class)
					.setFirstResult(offsset).setMaxResults(elementsOnPage).getResultList();

			if (!giftCertificates.isEmpty()) {
				session.getTransaction().commit();
			} else {
				session.getTransaction().rollback();
				logger.error("No gift certificates were found with given page={}, and elements on page={}", page,
						elementsOnPage);
			}
		}

		return giftCertificates;
	}

	@Override
	public List<GiftCertificate> getAll(Integer page) {
		return getAll(page, PAGE_EL_LIMIT);
	}

	@Override
	public List<GiftCertificate> getByTagName(String tagName) {
		List<GiftCertificate> giftCertificates = new ArrayList<>();

		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();

			giftCertificates = session
					.createQuery("SELECT g FROM GiftCertificate g JOIN FETCH g.tags t WHERE t.name = ?1",
							GiftCertificate.class)
					.setParameter(1, tagName).getResultList();

			if (!giftCertificates.isEmpty()) {
				session.getTransaction().commit();
			} else {
				session.getTransaction().rollback();
				logger.error("No gift certificates were found with given tag name={}", tagName);
			}
		}

		return giftCertificates;
	}

	@Override
	public List<GiftCertificate> getByTagPartFieldValue(String fieldName, String partFieldValue) {
		List<GiftCertificate> giftCertificates = new ArrayList<>();

		if (partFieldValue != null && !partFieldValue.isBlank()) {
			partFieldValue = "%" + partFieldValue + "%";
		} else {
			return giftCertificates;
		}

		Session session = sessionFactory.openSession();
		try {
			session.getTransaction().begin();

			giftCertificates = session.createQuery(
					"SELECT g FROM GiftCertificate g JOIN FETCH g.tags t WHERE t." + fieldName + " LIKE ?1",
					GiftCertificate.class).setParameter(1, partFieldValue).getResultList();

			if (!giftCertificates.isEmpty()) {
				session.getTransaction().commit();
			} else {
				session.getTransaction().rollback();
				logger.error("No gift certificates were found with given part of tag field value={} on field={}",
						partFieldValue, fieldName);
			}
		} catch (Exception e) {
			session.getTransaction().rollback();
			logger.error("Error in field name={}. {}", fieldName, e.getMessage(), e);
		} finally {
			session.close();
		}

		return giftCertificates;
	}

	@Override
	public Optional<GiftCertificate> save(GiftCertificate giftCertificate) {
		Optional<GiftCertificate> giftCertificateWrap = Optional.empty();

		Session session = sessionFactory.openSession();
		try {
			session.getTransaction().begin();

			if (giftCertificate != null && giftCertificate.getId() == null) {

				List<Tag> tags = giftCertificate.getTags();
				if (tags != null && !tags.isEmpty()) {
					tags.stream().forEach(tag -> {
						if (tag.getId() != null) {
							session.refresh(tag);
						}
					});
				}

				giftCertificate.setCreateDate(LocalDateTime.now());

				session.persist(giftCertificate);

				session.getTransaction().commit();

				giftCertificateWrap = Optional.of(giftCertificate);
			} else {
				session.getTransaction().rollback();
				logger.error("Error saving on invalid gift certificate: {}", giftCertificate);
			}
		} catch (PersistenceException e) {
			session.getTransaction().rollback();
			logger.error("Error on saving gift certificate: {}", e.getMessage(), e);
		} finally {
			session.close();
		}

		return giftCertificateWrap;
	}

	@Override
	public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {

		Optional<GiftCertificate> giftCertificateWrap = Optional.empty();

		Session session = sessionFactory.openSession();
		try {
			session.getTransaction().begin();

			if (giftCertificate != null && giftCertificate.getId() != null && giftCertificate.getId() > 0L) {

				GiftCertificate giftCertificateDB = session.find(GiftCertificate.class, giftCertificate.getId());

				if (giftCertificateDB != null) {
					// Preparing tags for saving or updating
					List<Tag> tags = giftCertificate.getTags();
					tags = (tags == null) ? new ArrayList<>() : tags;
					tags.stream().forEach(tag -> {
						if (tag.getId() != null) {
							session.refresh(tag);
						}
					});

					List<Tag> dbTags = giftCertificateDB.getTags();

					if (dbTags == null) {
						giftCertificateDB.setTags(tags);
					} else {
						tags.stream().forEach(tag -> {
							if (!dbTags.contains(tag)) {
								dbTags.add(tag);
							}
						});
						giftCertificateDB.setTags(dbTags);
					}

					if (giftCertificate.getName() != null) {
						giftCertificateDB.setName(giftCertificate.getName());
					}

					if (giftCertificate.getDescription() != null) {
						giftCertificateDB.setDescription(giftCertificate.getDescription());
					}

					if (giftCertificate.getPrice() != null) {
						giftCertificateDB.setPrice(giftCertificate.getPrice());
					}

					if (giftCertificate.getDuration() != null) {
						giftCertificateDB.setDuration(giftCertificate.getDuration());
					}

					giftCertificateDB.setLastUpdate(LocalDateTime.now());

					session.merge(giftCertificateDB);

					session.getTransaction().commit();

					giftCertificateWrap = Optional.of(giftCertificateDB);
				} else {
					session.getTransaction().rollback();
					logger.error("Error on updating. Gift certificate not exists on given id={}",
							giftCertificate.getId());
				}
			} else {
				session.getTransaction().rollback();
				logger.error("Error updating on invalid gift certificate: {}", giftCertificate);
			}
		} catch (PersistenceException e) {
			session.getTransaction().rollback();
			logger.error("Error on updating gift certificate: {}", e.getMessage(), e);
		} finally {
			session.close();
		}

		return giftCertificateWrap;
	}

	@Override
	public Boolean delete(GiftCertificate giftCertificate) {
		Boolean result = false;

		try (Session session = sessionFactory.openSession()) {
			session.getTransaction().begin();

			if (giftCertificate != null && giftCertificate.getId() != null && giftCertificate.getId() > 0L) {

				GiftCertificate dbGiftCertificate = session.find(GiftCertificate.class, giftCertificate.getId());

				if (dbGiftCertificate != null) {
					session.remove(dbGiftCertificate);
					session.getTransaction().commit();

					result = true;
				} else {
					session.getTransaction().rollback();
					logger.error("Gift certificate not exists with given id={}", giftCertificate.getId());
				}
			} else {
				session.getTransaction().rollback();
				logger.error("Given invalid gift certificate={} for deleting", giftCertificate);
			}
		}

		return result;
	}

}
