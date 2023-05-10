package ru.clevertec.ecl.knyazev.entity.sort;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

import ru.clevertec.ecl.knyazev.entity.GiftCertificate;

public class GiftCertificateSorting extends Sorting<GiftCertificate> {

	public Comparator<GiftCertificate> getSortingComporator(String... sortOrder) {

		Map<String, SortOrder> sortFieldOrderMap = parseSortParams(sortOrder);
		Comparator<GiftCertificate> sortingComparator = null;

		if (!sortFieldOrderMap.isEmpty()) {

			for (Map.Entry<String, SortOrder> sortEntry : sortFieldOrderMap.entrySet()) {

				// sort by date or complex
				if (sortEntry.getKey().equals(SortField.date.name())) {
					Comparator<GiftCertificate> dateComparator = Comparator.comparing(GiftCertificate::getCreateDate);

					sortingComparator = buildSortingComparator(sortingComparator, dateComparator, sortEntry.getValue());
				}

				// sort by name or complex
				if (sortEntry.getKey().equals(SortField.name.name())) {
					Comparator<GiftCertificate> nameComparator = Comparator.comparing(GiftCertificate::getName);

					sortingComparator = buildSortingComparator(sortingComparator, nameComparator, sortEntry.getValue());
				}

				// sort by description or complex
				if (sortEntry.getKey().equals(SortField.description.name())) {
					Comparator<GiftCertificate> descriptionComparator = Comparator.comparing(GiftCertificate::getDescription);

					sortingComparator = buildSortingComparator(sortingComparator, descriptionComparator,
							sortEntry.getValue());
				}

			}
		}

		return sortingComparator;
	}

	@Override
	boolean validateParsedParams(String field, String order) {
		return (Stream.of(SortField.values()).anyMatch(sortField -> sortField.name().equals(field))
				&& Stream.of(SortOrder.values()).anyMatch(sortOrder -> sortOrder.name().equals(order)));
	}

	/**
	 * 
	 * SortField enum determines the sort fields
	 *
	 */
	private enum SortField {
		date, name, description
	}

}
