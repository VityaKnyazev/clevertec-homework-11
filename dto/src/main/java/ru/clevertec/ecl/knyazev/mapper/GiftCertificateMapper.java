package ru.clevertec.ecl.knyazev.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ru.clevertec.ecl.knyazev.dto.GiftCertificateDTO;
import ru.clevertec.ecl.knyazev.entity.GiftCertificate;

@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {
	
	@Mappings({
		@Mapping(target = "price", numberFormat = "#########.00"),
		@Mapping(target = "duration", dateFormat = "dd.MM.yyyy"),
		@Mapping(target = "createDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
		@Mapping(target = "lastUpdate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
		})
	GiftCertificateDTO toDTO(GiftCertificate giftCertificate);
	
	@InheritInverseConfiguration
	GiftCertificate toGiftCertificate(GiftCertificateDTO giftCertificateDTO);
}
