package ru.clevertec.ecl.knyazev.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TagDTO {
	
	@Min(value = 1L, message = "Id should be null or must be above 1")
	private Long id;

	@Size(min = 3, max = 25, message = "Name must have at least 3 symbols and must be less than or equals to 50 characters!")
	private String name;

}
