package ru.clevertec.ecl.knyazev.service;

import java.util.List;

import ru.clevertec.ecl.knyazev.entity.GiftCertificate;

public interface GiftCertificateService extends SimpleService<GiftCertificate> {
	
	/**
	 * 
	 * Show gift certificates by tag name. 
	 * 
	 * @param String tagName is the name of given tag.
	 * @return List<GiftCertificate> gift certificates with tag by tag name or empty list.
	 */
	public List<GiftCertificate> showByTagName(String tagName);
	
	
	/**
	 * 
	 * Show gift certificates by part of given field value on given field name.
	 * 
	 * @param String fieldName
	 * @param String partFieldValue
	 * @param SortOrder sort order for sorting result. If not present - without sorting.
	 * @return List<GiftCertificate> gift certificates that contains a part of given field value on 
	 *         given field name.
	 */
	public List<GiftCertificate> showByPartFieldValue(String fieldName, String partFieldValue, SortOrder... sortOrder);
	
	public static enum SortOrder {
		date, name, unordered
	}
}
