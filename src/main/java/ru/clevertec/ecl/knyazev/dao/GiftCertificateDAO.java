package ru.clevertec.ecl.knyazev.dao;

import java.util.List;

import ru.clevertec.ecl.knyazev.entity.GiftCertificate;

public interface GiftCertificateDAO extends DAO<GiftCertificate> {
	
	/**
	 * 
	 * Get Gift certificates with tag by tag name.
	 * 
	 * @param tagName the name of the tag for searching gift certificates.
	 * @return gift certificates by tag name with found
	 *         tag or empty list.
	 */
	public List<GiftCertificate> getByTagName(String tagName);
	
	/**
	 * 
	 * Get Gift certificates with tag by tag name on given page and quantity elements on page.
	 * 
	 * @param tagName the name of the tag for searching gift certificates.
	 * @param page number.
	 * @param elementsOnPage quantity of elements on page.
	 * @return  gift certificates by tag name with found
	 *         tag on given page number of given Integer quantity of elements
	 *         or empty list.
	 */
	public List<GiftCertificate> getByTagName(String tagName, Integer page, Integer elementsOnPage);
	
	/**
	 * 
	 * Get Gift certificates with tag by tag name on given page.
	 * 
	 * @param tagName the name of the tag for searching gift certificates.
	 * @param page number.
	 * @return  gift certificates by tag name with found
	 *         tag on given page number or empty list.
	 */
	public List<GiftCertificate> getByTagName(String tagName, Integer page);
	
	/**
	 * 
	 * Get Gift certificates by GiftCertificate field name and part of its value.
	 * 
	 * @param fieldName on which will be searching a part of it value. 
	 * @param partFieldValue the part of field value for searching
	 * @return gift certificates with tags where field name contains 
	 *         value as a part of field value or empty list.
	 */
	public List<GiftCertificate> getByPartFieldValue(String fieldName, String partFieldValue);
	
	/**
	 * 
	 * Get Gift certificates by GiftCertificate field name and part of its value 
	 * on given page and quantity elements on page.
	 * 
	 * @param fieldName on which will be searching a part of it value. 
	 * @param partFieldValue the part of field value for searching
	 * @param page number.
	 * @param elementsOnPage quantity of elements on page.
	 * @return List<GiftCertificate> gift certificates with tags where field name contains 
	 *         value as a part of field value on given page number of given Integer quantity 
	 *         of elements or empty list.
	 */
	public List<GiftCertificate> getByPartFieldValue(String fieldName, String partFieldValue, Integer page, Integer elementsOnPage);
	
	/**
	 * 
	 * Get Gift certificates by GiftCertificate field name and part of its value 
	 * on given page.
	 * 
	 * @param fieldName on which will be searching a part of it value. 
	 * @param partFieldValue the part of field value for searching
	 * @param page number.
	 * @return gift certificates with tags where field name contains 
	 *         value as a part of field value on given page number or empty list.
	 */
	public List<GiftCertificate> getByPartFieldValue(String fieldName, String partFieldValue, Integer page);
}
