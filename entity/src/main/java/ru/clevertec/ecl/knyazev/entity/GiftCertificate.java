package ru.clevertec.ecl.knyazev.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GiftCertificate {
	private Long id;

	private String name;
	
	private String description;
	
	private BigDecimal price;
	
	private Date duration;
	
	private LocalDateTime createDate;
	
	private LocalDateTime lastUpdate;
	
	private List<Tag> tags;
}
