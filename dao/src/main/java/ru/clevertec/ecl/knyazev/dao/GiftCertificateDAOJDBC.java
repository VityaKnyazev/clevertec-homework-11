package ru.clevertec.ecl.knyazev.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.clevertec.ecl.knyazev.dao.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.knyazev.entity.GiftCertificate;

@Repository
public class GiftCertificateDAOJDBC implements GiftCertificateDAO {
	private static final Integer PAGE_EL_LIMIT = 20;

	private static final Logger logger = LoggerFactory.getLogger(GiftCertificateDAOJDBC.class);

	private static final String SELECT_QUERY = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate";

	private JdbcTemplate jdbcTemplate;

	public GiftCertificateDAOJDBC() {
	}

	@Autowired
	public GiftCertificateDAOJDBC(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<GiftCertificate> getGiftCertificateById(Long id) {
		Optional<GiftCertificate> giftCertificateWrap = Optional.empty();

		if (id != null && id > 0L) {
			giftCertificateWrap = jdbcTemplate.query(SELECT_QUERY + " WHERE id=?", new GiftCertificateMapper(), id)
					.stream().findAny();
		} else {
			logger.error("Error getting gift certificate by invalid id={}", id);
		}

		return giftCertificateWrap;
	}

	@Override
	public List<GiftCertificate> getAllGiftCertificates() {
		return jdbcTemplate.query(SELECT_QUERY, new GiftCertificateMapper());
	}

	@Override
	public List<GiftCertificate> getAllGiftCertificates(Integer page, Integer elementsOnPage) {
		List<GiftCertificate> giftCertificates = new ArrayList<>();

		if ((page == null || page < 1) || (elementsOnPage == null || elementsOnPage < 1)) {
			return giftCertificates;
		}

		Integer offsset = (page - 1) * elementsOnPage;

		return jdbcTemplate.query(SELECT_QUERY + " LIMIT ? OFFSET ?", new GiftCertificateMapper(), elementsOnPage,
				offsset);
	}

	@Override
	public List<GiftCertificate> getAllGiftCertificates(Integer page) {
		return getAllGiftCertificates(page, PAGE_EL_LIMIT);
	}

	@Override
	public List<GiftCertificate> getAllGiftCertificatesByTagId(Long tagId) {
		List<GiftCertificate> giftCertificates = new ArrayList<>();

		if (tagId != null && tagId > 0L) {
			giftCertificates = jdbcTemplate.query(
					SELECT_QUERY
							+ "WHERE id=ANY(SELECT gift_certificate_id FROM gift_certificate_tag WHERE tag_id = ?)",
					new GiftCertificateMapper(), tagId);
		} else {
			logger.error("Error getting gift certificates by invalid tag id={}", tagId);
		}

		return giftCertificates;
	}

	@Override
	public Optional<GiftCertificate> saveGiftCertificate(GiftCertificate giftCertificate) {
		Optional<GiftCertificate> giftCertificateWrap = Optional.empty();

		if (giftCertificate != null && giftCertificate.getId() == null) {
			jdbcTemplate.update("INSERT INTO gift_certificate(name, description, price, duration, create_date) VALUES(?, ?, ?, ?, ?)",
					giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(),
					giftCertificate.getDuration(), LocalDateTime.now());
		} else {
			logger.error("Error saving on invalid gift certificate: {}", giftCertificate);
		}

		return giftCertificateWrap;
	}

	@Override
	public Optional<GiftCertificate> updateGiftCertificate(GiftCertificate giftCertificate) {
		Optional<GiftCertificate> giftCertificateWrap = Optional.empty();

		if (giftCertificate != null && giftCertificate.getId() != null && giftCertificate.getId() > 0L) {
			GiftCertificate giftCertificateDB = getGiftCertificateById(giftCertificate.getId()).orElse(null);

			if (giftCertificateDB != null) {
				if (!giftCertificate.equals(giftCertificateDB)) {
					giftCertificateDB.setName(giftCertificate.getName());
					giftCertificateDB.setDescription(giftCertificate.getDescription());
					giftCertificateDB.setPrice(giftCertificate.getPrice());
					giftCertificateDB.setDuration(giftCertificate.getDuration());
					giftCertificateDB.setLastUpdate(LocalDateTime.now());

					jdbcTemplate.update(
							"UPDATE tag SET name = ?, description = ?, price = ?, duration = ?, last_update_date = ?",
							giftCertificateDB.getName(), giftCertificateDB.getDescription(),
							giftCertificateDB.getPrice(), giftCertificateDB.getCreateDate(),
							giftCertificateDB.getLastUpdate());

					giftCertificateWrap = Optional.of(giftCertificateDB);
				} else {
					logger.error("Error on updating. Gift certificate fields already exists");
				}
			} else {
				logger.error("Error on updating. Gift certificate not exists on given id={}", giftCertificate.getId());
			}
		} else {
			logger.error("Error updating on invalid gift certificate: {}", giftCertificate);
		}

		return giftCertificateWrap;
	}

	@Override
	public Boolean deleteGiftCertificate(GiftCertificate giftCertificate) {
		Boolean result = false;

		if (giftCertificate != null && giftCertificate.getId() != null && giftCertificate.getId() > 0L) {

			GiftCertificate dbGiftCertificate = getGiftCertificateById(giftCertificate.getId()).orElse(null);

			if (dbGiftCertificate != null) {
				jdbcTemplate.update("DELETE FROM gift_certificate WHERE id = ?", giftCertificate.getId());

				result = true;
			} else {
				logger.error("Gift certificate not exists with given id={}", giftCertificate.getId());
			}
		} else {
			logger.error("Given invalid gift certificate={} for deleting", giftCertificate);
		}

		return result;
	}

}
