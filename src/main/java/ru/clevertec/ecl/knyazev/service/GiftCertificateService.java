package ru.clevertec.ecl.knyazev.service;

import java.util.List;

import ru.clevertec.ecl.knyazev.entity.GiftCertificate;

public interface GiftCertificateService extends SimpleService<GiftCertificate> {

	/**
	 * 
	 * Show gift certificates by tag name.
	 * 
	 * @param tagName is the name of given tag.
	 * @return gift certificates with tag by tag name or empty list.
	 */
	public List<GiftCertificate> showByTagName(String tagName);

	/**
	 * 
	 * Show gift certificates by part of given field value on given field name.
	 * 
	 * @param fieldName      field name
	 * @param partFieldValue field value
	 * @param sort           order for sorting result. If not present or invalid -
	 *                       without sorting. Sorting order should contain sorting
	 *                       field name and order. For example: name:asc, date:desc.
	 * @return gift certificates that contains a part of given field value on given
	 *         field name.
	 */
	public List<GiftCertificate> showByPartFieldValue(String fieldName, String partFieldValue, String... sortOrder);

	/**
	 * 
	 * Show gift certificates depending on given request params:
	 * - Show all tags also should by page number and elements on page.
	 * - Show by tag name also should by page number, elements on page and sorting.
	 * - Show by part of gift certificate name also should by page number, elements on page and sorting.
	 * - Show by part of gift certificate description also should by page number, elements on page and sorting.
	 * 
	 * @param tagName tag name that gift certificate can contains. Tag name is a searching field
	 * @param partFieldNameValue part of name field value of gift certificates. It's a searching field 
	 * @param partFieldDescriptionValue part of description field value of gift certificates. It's a searching field 
	 * @param page page number
	 * @param pageSize quantity of elements on page
	 * @param sortOrder sorting order
	 * @return gift certificates depending on request params or empty list.
	 */
	public List<GiftCertificate> showAll(String tagName, String partFieldNameValue, String partFieldDescriptionValue, Integer page,
			Integer pageSize, String... sortOrder);

}
