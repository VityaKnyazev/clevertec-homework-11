package ru.clevertec.ecl.knyazev.entity.sort;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Represents abstract sorting class for sorting T type depends on sorting params
 * like {"name:asc", "date:desc"} that should produce complex Comparator<T> for sorting.
 * 
 * @author Vitya Knyazev
 *
 * @param <T> type that can be sorted
 */
public abstract class Sorting<T> {

	/**
	 * 
	 * Parse sorting params to key value map. Params should be like {"name:asc",
	 * "date:desc"}
	 * 
	 * @param String sortOrder varargs that contains field-order values for sorting
	 *               like name:asc
	 * @return Map<String, String> map that contains field for sorting as key and
	 *         order as value.
	 */
	Map<String, SortOrder> parseSortParams(String... sortOrder) {
		Map<String, SortOrder> sortFieldOrderMap = new HashMap<>();

		if (sortOrder == null || sortOrder.length == 0) {
			return sortFieldOrderMap;
		}

		for (String sortFieldOrder : sortOrder) {
			if (sortFieldOrderMap != null && sortFieldOrder.contains(":")) {

				String[] fieldsOrders = sortFieldOrder.split(":");

				if (fieldsOrders != null && fieldsOrders.length == 2) {
					String field = fieldsOrders[0].toLowerCase();
					String order = fieldsOrders[1].toLowerCase();

					if (validateParsedParams(field, order)) {
						sortFieldOrderMap.put(field, SortOrder.valueOf(order));
					}
				}

			}
		}

		return sortFieldOrderMap;
	}
	
	/**
	 * 
	 * Get complex sorting comparator depends on quantity of fields for sorting and sort order.
	 * 
	 * @param Comparator<T> sortingComparator complex sorting comparator
	 * @param Comparator<T> currentComparator current comparator for adding to complex comparator
	 * @param SortOrder sortOrder sort order for sorting in current comparator.
	 * @return Comparator<T> complex sorting comparator
	 */
	Comparator<T> buildSortingComparator(Comparator<T> sortingComparator, Comparator<T> currentComparator, SortOrder sortOrder) {
		
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
	 * Validate parsed field value and order value
	 * 
	 * @param String field for validating
	 * @param String order for validating
	 * @return Boolean true on success, otherwise - false
	 * 
	 */
	abstract boolean validateParsedParams(String field, String order);
	
	
	/**
	 * 
	 * SortOrder enum determines the sort order
	 *
	 */
	enum SortOrder {
		asc, desc
	}

}
