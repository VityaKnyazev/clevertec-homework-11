package ru.clevertec.ecl.knyazev.dao;

import java.util.List;

import ru.clevertec.ecl.knyazev.entity.GiftCertificate;

public interface GiftCertificateDAO extends DAO<GiftCertificate> {
	
	/**
	 * 
	 * Get Gift certificates with tag by tag name.
	 * 
	 * @param String tagName the name of the tag for searching gift certificates.
	 * @return List<GiftCertificate> gift certificates by tag name with found
	 *         tag.
	 */
	public List<GiftCertificate> getByTagName(String tagName);
	
	/**
	 * 
	 * Get Gift certificates by GiftCertificate field name and part of its value.
	 * 
	 * @param String fieldName on which will be searching a part of it value. 
	 * @param String partFieldValue the part of field value for searching
	 * @return List<GiftCertificate> gift certificates with tags where field name contains 
	 *         value as a part of field value.
	 */
	public List<GiftCertificate> getByPartFieldValue(String fieldName, String partFieldValue);

}
