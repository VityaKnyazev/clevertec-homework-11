package ru.clevertec.ecl.knyazev.dto.mapper;

import java.util.List;

import org.hibernate.Hibernate;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.clevertec.ecl.knyazev.dto.GiftCertificateDTO;
import ru.clevertec.ecl.knyazev.entity.GiftCertificate;
import ru.clevertec.ecl.knyazev.entity.Tag;

@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {

	@Mapping(target = "price", numberFormat = "#########.00")
	@Mapping(target = "duration", dateFormat = "dd.MM.yyyy")
	@Mapping(target = "createDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	@Mapping(target = "lastUpdate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	@Mapping(expression = "java(mapTags(giftCertificate.getTags()))", target = "tags")
	GiftCertificateDTO toDTO(GiftCertificate giftCertificate);

	@Mapping(target = "price", numberFormat = "#########.00")
	@Mapping(target = "duration", dateFormat = "dd.MM.yyyy")
	@Mapping(target = "createDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	@Mapping(target = "lastUpdate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	GiftCertificate toGiftCertificate(GiftCertificateDTO giftCertificateDTO);

	List<GiftCertificateDTO> toDTOList(List<GiftCertificate> giftCertificates);

	@InheritInverseConfiguration
	List<GiftCertificate> toGiftCertificates(List<GiftCertificateDTO> GiftCertificatesDTO);

	default List<Tag> mapTags(List<Tag> tags) {
		if (Hibernate.isInitialized(tags)) {
			return tags;
		} else {
			return null;
		}
	}
}
