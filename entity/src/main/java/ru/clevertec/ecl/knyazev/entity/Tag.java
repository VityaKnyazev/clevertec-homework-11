package ru.clevertec.ecl.knyazev.entity;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tag {
	private Long id;
	
	private String name;
	
	private List<GiftCertificate> giftCertificates;
}
