package ru.clevertec.ecl.knyazev.mapper;

import java.sql.ResultSet;

import org.mapstruct.Mapper;

import ru.clevertec.ecl.knyazev.entity.GiftCertificate;

@Mapper(componentModel = "spring")
public abstract class GiftCertificateMapper {
	
	public GiftCertificate toGiftCertificate(ResultSet resultSet) {
		return null;	
	}
	
}
