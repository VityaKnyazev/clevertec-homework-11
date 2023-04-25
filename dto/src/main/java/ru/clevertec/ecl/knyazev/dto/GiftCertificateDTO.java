package ru.clevertec.ecl.knyazev.dto;

import java.util.List;

import lombok.Data;
import ru.clevertec.ecl.knyazev.entity.Tag;

@Data
public class GiftCertificateDTO {
	private Long id;

	private String name;

	private String description;

	private String price;

	private String duration;

	private String createDate;

	private String lastUpdate;
	
	private List<Tag> tags;

}
