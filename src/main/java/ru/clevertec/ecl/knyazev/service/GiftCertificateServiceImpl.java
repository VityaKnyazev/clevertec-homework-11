package ru.clevertec.ecl.knyazev.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.knyazev.dao.GiftCertificateDAO;
import ru.clevertec.ecl.knyazev.entity.GiftCertificate;
import ru.clevertec.ecl.knyazev.entity.sort.GiftCertificateSorting;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class GiftCertificateServiceImpl implements GiftCertificateService {
	private GiftCertificateDAO giftCertificateDAOJPAImpl;

	@Override
	public Optional<GiftCertificate> show(Long id) {
		return giftCertificateDAOJPAImpl.getById(id);
	}

	@Override
	public List<GiftCertificate> showAll() {
		return giftCertificateDAOJPAImpl.getAll();
	}

	@Override
	public List<GiftCertificate> showAll(Integer page, Integer pageSize) {
		return giftCertificateDAOJPAImpl.getAll(page, pageSize);
	}

	@Override
	public List<GiftCertificate> showAll(Integer page) {
		return giftCertificateDAOJPAImpl.getAll(page);
	}

	@Override
	public List<GiftCertificate> showByTagName(String tagName) {
		return giftCertificateDAOJPAImpl.getByTagName(tagName);
	}

	@Override
	public List<GiftCertificate> showByPartFieldValue(String fieldName, String partFieldValue, String... sortOrder) {
		return giftCertificateDAOJPAImpl.getByPartFieldValue(fieldName, partFieldValue);
	}

	@Override
	public List<GiftCertificate> showAll(String tagName, String partFieldNameValue, String partFieldDescriptionValue,
			Integer page, Integer pageSize, String... sortOrder) {
		List<GiftCertificate> giftCertificates = new ArrayList<>();

		GiftCertificateSorting giftCertificateSorting = new GiftCertificateSorting();
		Comparator<GiftCertificate> certificateComporator = giftCertificateSorting.getSortingComporator(sortOrder);

		// When showing all gift certificates
		if (tagName == null && partFieldNameValue == null && partFieldDescriptionValue == null) {

			if (page != null) {
				if (pageSize != null) {
					giftCertificates = giftCertificateDAOJPAImpl.getAll(page, pageSize);
				} else {
					giftCertificates = giftCertificateDAOJPAImpl.getAll(page);
				}
			} else {
				giftCertificates = giftCertificateDAOJPAImpl.getAll();
			}
			// When showing all gift certificates by tag name
		} else if (tagName != null && partFieldNameValue == null && partFieldDescriptionValue == null) {
			if (page != null) {
				if (pageSize != null) {
					giftCertificates = giftCertificateDAOJPAImpl.getByTagName(tagName, page, pageSize);
				} else {
					giftCertificates = giftCertificateDAOJPAImpl.getByTagName(tagName, page);
				}
			} else {
				giftCertificates = giftCertificateDAOJPAImpl.getByTagName(tagName);
			}
			// When showing all gift certificates by part of gift certificate name
		} else if (tagName == null && partFieldNameValue != null && partFieldDescriptionValue == null) {
			final String fieldName = "name";
			
			if (page != null) {
				if (pageSize != null) {
					giftCertificates = giftCertificateDAOJPAImpl.getByPartFieldValue(fieldName, partFieldNameValue, page,
							pageSize);
				} else {
					giftCertificates = giftCertificateDAOJPAImpl.getByPartFieldValue(fieldName, partFieldNameValue, page);
				}
			} else {
				giftCertificates = giftCertificateDAOJPAImpl.getByPartFieldValue(fieldName, partFieldNameValue);
			}
			// When showing all gift certificates by part of gift certificate description
		} else if (tagName == null && partFieldNameValue == null && partFieldDescriptionValue != null) {
			final String fieldName = "description";
			
			if (page != null) {
				if (pageSize != null) {
					giftCertificates = giftCertificateDAOJPAImpl.getByPartFieldValue(fieldName, partFieldDescriptionValue,
							page, pageSize);
				} else {
					giftCertificates = giftCertificateDAOJPAImpl.getByPartFieldValue(fieldName, partFieldDescriptionValue,
							page);
				}
			} else {
				giftCertificates = giftCertificateDAOJPAImpl.getByPartFieldValue(fieldName, partFieldDescriptionValue);
			}
		}

		if (certificateComporator != null) {
			giftCertificates = giftCertificates.stream().sorted(certificateComporator).toList();
		}

		return giftCertificates;
	}

	@Override
	public GiftCertificate add(GiftCertificate giftCertificate) throws ServiceException {
		Optional<GiftCertificate> savedGiftCertificateWrap = giftCertificateDAOJPAImpl.save(giftCertificate);

		if (savedGiftCertificateWrap.isEmpty()) {
			throw new ServiceException("Error on adding gift certificate");
		}

		return savedGiftCertificateWrap.get();
	}

	@Override
	public GiftCertificate change(GiftCertificate giftCertificate) throws ServiceException {
		Optional<GiftCertificate> updatedGiftCertificateWrap = giftCertificateDAOJPAImpl.update(giftCertificate);

		if (updatedGiftCertificateWrap.isEmpty()) {
			throw new ServiceException("Error on updating gift certificate");
		}

		return updatedGiftCertificateWrap.get();
	}

	@Override
	public void remove(GiftCertificate giftCertificate) throws ServiceException {
		Boolean result = giftCertificateDAOJPAImpl.delete(giftCertificate);

		if (!result) {
			throw new ServiceException("Error on deleting gift certificate");
		}
	}

}
