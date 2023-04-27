package ru.clevertec.ecl.knyazev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.clevertec.ecl.knyazev.dao.DAO;
import ru.clevertec.ecl.knyazev.entity.GiftCertificate;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

@Service
public class GiftCertificateService implements SimpleService<GiftCertificate> {
	private DAO<GiftCertificate> giftCertificateDAOJPA;

	public GiftCertificateService() {
	}

	@Autowired
	public GiftCertificateService(DAO<GiftCertificate> giftCertificateDAOJPA) {
		this.giftCertificateDAOJPA = giftCertificateDAOJPA;
	}

	@Override
	public Optional<GiftCertificate> show(Long id) {
		return giftCertificateDAOJPA.getById(id);
	}

	@Override
	public List<GiftCertificate> showAll() {
		return giftCertificateDAOJPA.getAll();
	}

	@Override
	public List<GiftCertificate> showAll(Integer page, Integer pageSize) {
		return giftCertificateDAOJPA.getAll(page, pageSize);
	}

	@Override
	public List<GiftCertificate> showAll(Integer page) {
		return giftCertificateDAOJPA.getAll(page);
	}

	@Override
	public GiftCertificate add(GiftCertificate giftCertificate) throws ServiceException {
		Optional<GiftCertificate> savedGiftCertificateWrap = giftCertificateDAOJPA.save(giftCertificate);

		if (savedGiftCertificateWrap.isEmpty()) {
			throw new ServiceException("Error on adding gift certificate");
		}

		return savedGiftCertificateWrap.get();
	}

	@Override
	public GiftCertificate change(GiftCertificate giftCertificate) throws ServiceException {
		Optional<GiftCertificate> updatedGiftCertificateWrap = giftCertificateDAOJPA.update(giftCertificate);

		if (updatedGiftCertificateWrap.isEmpty()) {
			throw new ServiceException("Error on updating gift certificate");
		}

		return updatedGiftCertificateWrap.get();
	}

	@Override
	public void remove(GiftCertificate giftCertificate) throws ServiceException {
		Boolean result = giftCertificateDAOJPA.delete(giftCertificate);
		
		if (!result) {
			throw new ServiceException("Error on deleting gift certificate");
		}
	}

}
