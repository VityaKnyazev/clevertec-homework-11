package ru.clevertec.ecl.knyazev;

import java.util.List;
import java.util.Optional;

import ru.clevertec.ecl.knyazev.entity.GiftCertificate;

/**
 * 
 * Interface for GiftCertificate data access object.
 *
 */
public interface GiftCertificateDAO {
	/**
	 * 
	 * Get Optional<GiftCertificate> from database on giving id.
	 * 
	 * @param Long id for searching gift certificate in database.
	 * @return Optional<GiftCertificate> gift certificate. If gift certificate not exists in database - Optional empty.
	 */
	Optional<GiftCertificate> getGiftCertificateById(Long id);
	
	/**
	 * 
	 * Get all gift certificates from database
	 * 
	 * @return List<GiftCertificate> all gift certificates from database or empty list.
	 */
	List<GiftCertificate> getAllGiftCertificates();
	
	/**
	 * 
	 * Get all gift certificates from database on given Integer page of given quantity of elements.
	 * 
	 * @param Integer page number.
	 * @param Integer elementsOnPage quantity of elements on page.
	 * @return List<GiftCertificate> gift certificates on given Integer page of given Integer quantity of elements
	 *         from database or empty list.
	 */
	List<GiftCertificate> getAllGiftCertificates(Integer page, Integer elementsOnPage);
	
	/**
	 * 
	 * Get all gift certificates from database on given Integer page.
	 * 
	 * @param Integer page number.
	 * @return List<GiftCertificate> gift certificates on given Integer page from database or empty list.
	 */
	List<GiftCertificate> getAllGiftCertificates(Integer page);
	
	
	/**
	 * 
	 * Save gift certificate to database
	 * 
	 * @param GiftCertificate gift certificate for saving
	 * @return Optional<GiftCertificate> if was success on saving - optional gift certificate, otherwise - optional empty.
	 */
	Optional<GiftCertificate> saveGiftCertificate(GiftCertificate giftCertificate);
	
	/**
	 * 
	 * Update gift certificate in database
	 * 
	 * @param GiftCertificate gift certificate for updating
	 * @return Optional<GiftCertificate> if was success on updating - optional gift certificate, otherwise - optional empty
	 */
	Optional<GiftCertificate> updateGiftCertificate(GiftCertificate giftCertificate);
	
	/**
	 * 
	 * Delete gift certificate in database
	 * 
	 * @param GiftCertificate gift certificate for deleting
	 * @return Boolean true on success deleting, otherwise - false.
	 */
	Boolean deleteGiftCertificate(GiftCertificate giftCertificate);
}
