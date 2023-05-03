package ru.clevertec.ecl.knyazev.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import ru.clevertec.ecl.knyazev.entity.GiftCertificate;

public interface GiftCertificateService extends SimpleService<GiftCertificate> {

	/**
	 * 
	 * Show gift certificates by tag name.
	 * 
	 * @param String tagName is the name of given tag.
	 * @return List<GiftCertificate> gift certificates with tag by tag name or empty
	 *         list.
	 */
	public List<GiftCertificate> showByTagName(String tagName);

	/**
	 * 
	 * Show gift certificates by part of given field value on given field name.
	 * 
	 * @param String fieldName
	 * @param String partFieldValue
	 * @param String sort order for sorting result. If not present or invalid -
	 *               without sorting. Sorting order should contain sorting field
	 *               name and order. For example: name:asc, date:desc.
	 * @return List<GiftCertificate> gift certificates that contains a part of given
	 *         field value on given field name.
	 */
	public List<GiftCertificate> showByPartFieldValue(String fieldName, String partFieldValue, String... sortOrder);

	enum SortField {
		date, name, description
	}

	enum SortOrder {
		asc, desc
	}

	default Comparator<GiftCertificate> getSortingComporator(String... sortOrder) {
		Map<SortField, SortOrder> sortFieldOrderMap = parseSortParams(sortOrder);
		Comparator<GiftCertificate> sortingComparator = null;
		
		if (!sortFieldOrderMap.isEmpty()) {
			
			for (Map.Entry<SortField, SortOrder> sortEntry : sortFieldOrderMap.entrySet()) {
				
				//sort by date or complex
				if (sortEntry.getKey().equals(SortField.date)) {
					Comparator<GiftCertificate> dateComparator = Comparator.comparing(GiftCertificate::getCreateDate);
					
					sortingComparator = buildSortingComparator(sortingComparator, dateComparator, sortEntry.getValue());
				}
				
				//sort by name or complex
				if (sortEntry.getKey().equals(SortField.name)) {
					Comparator<GiftCertificate> nameComparator = Comparator.comparing(GiftCertificate::getName);
					
					sortingComparator = buildSortingComparator(sortingComparator, nameComparator, sortEntry.getValue());
				}
				
				//sort by description or complex
				if (sortEntry.getKey().equals(SortField.description)) {
					Comparator<GiftCertificate> descriptionComparator = Comparator.comparing(GiftCertificate::getName);
					
					sortingComparator = buildSortingComparator(sortingComparator, descriptionComparator, sortEntry.getValue());
				}
				
			}			
		}

		return sortingComparator;
	}
	
	/**
	 * 
	 * Get complex sorting comparator depends on quantity fields for sorting
	 * 
	 * @param Comparator<GiftCertificate> sortingComparator complex sorting comparator
	 * @param Comparator<GiftCertificate> currentComparator current comparator for adding to complex comparator
	 * @param SortOrder sortOrder sort order for sorting in current comparator.
	 * @return
	 */
	private Comparator<GiftCertificate> buildSortingComparator(Comparator<GiftCertificate> sortingComparator, Comparator<GiftCertificate> currentComparator, SortOrder sortOrder) {
		
		if (sortingComparator == null) {
			
			if (sortOrder.equals(SortOrder.asc)) {
				sortingComparator = currentComparator;
			} else if (sortOrder.equals(SortOrder.desc)) {
				sortingComparator = currentComparator.reversed();
			} 						
			
		} else {
			
			if (sortOrder.equals(SortOrder.asc)) {
				sortingComparator = sortingComparator.thenComparing(currentComparator);
			} else if (sortOrder.equals(SortOrder.desc)) {
				sortingComparator = sortingComparator.thenComparing(currentComparator.reversed());
			} 
			
		}
		
		return sortingComparator;
	}

	/**
	 * 
	 * Parse sorting params to key value map. Params should be like {"name:asc", "date:desc"}
	 * 
	 * @param String sortOrder vararg that contains field-order values for sorting like name:asc 
	 * @return Map<SortField, SortOrder> map that contains field enum for sorting as key and order
	 *         enum as value. 
	 */
	private Map<SortField, SortOrder> parseSortParams(String... sortOrder) {
		Map<SortField, SortOrder> sortFieldOrderMap = new HashMap<>();
		
		if (sortOrder == null || sortOrder.length == 0) {
			return sortFieldOrderMap;
		}

		for (String sortFieldOrder : sortOrder) {
			if (sortFieldOrderMap != null && sortFieldOrder.contains(":")) {

				String[] fieldsOrders = sortFieldOrder.split(":");

				if (fieldsOrders != null && fieldsOrders.length == 2) {
					String field = fieldsOrders[0].toLowerCase();
					String order = fieldsOrders[1].toLowerCase();
					
					if (Stream.of(SortField.values()).anyMatch(sortField -> sortField.name().equals(field)) && 
						(Stream.of(SortOrder.values()).anyMatch(sortOrd -> sortOrd.name().equals(order)))) {
						sortFieldOrderMap.put(SortField.valueOf(field), SortOrder.valueOf(order));
					}
				}

			}
		}

		return sortFieldOrderMap;
	}
}
