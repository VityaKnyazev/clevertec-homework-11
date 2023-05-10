package ru.clevertec.ecl.knyazev.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.ecl.knyazev.entity.Tag;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(value = Include.NON_NULL)
public class GiftCertificateDTO {
	
	@Min(value = 1L, message = "Id should be null or must be above 1")
	private Long id;

	@Size(min = 3, max = 25, message = "Name must have at least 3 symbols and must be less than or equals to 25 characters!")
	private String name;

	@Size(min = 3, max = 50, message = "Description must have at least 3 symbols and must be less than or equals to 50 characters!")
	private String description;

	@Pattern(regexp = "^[0-9]{1,9},[0-9]{2}$", message = "Price must have value like #########,##")
	private String price;

	@Pattern(regexp = "^[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}$", message = "duration must be like dd.MM.yyyy HH:mm")
	private String duration;

	private String createDate;

	private String lastUpdate;
	
	private List<Tag> tags;

}
