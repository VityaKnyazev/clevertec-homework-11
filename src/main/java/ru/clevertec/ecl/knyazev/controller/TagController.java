package ru.clevertec.ecl.knyazev.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.knyazev.dto.TagDTO;
import ru.clevertec.ecl.knyazev.dto.mapper.TagMapper;
import ru.clevertec.ecl.knyazev.entity.Tag;
import ru.clevertec.ecl.knyazev.service.SimpleService;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

@RestController
@Validated
@NoArgsConstructor
@AllArgsConstructor(onConstructor_={@Autowired})
public class TagController {
	private SimpleService<Tag> tagServiceImpl;

	private TagMapper tagMapperImpl;

	@GetMapping("/tags")
	public ResponseEntity<?> getAll(
			@RequestParam(required = false, name = "page") @Positive(message = "Page must be above or equals to 1") Integer page,
			@RequestParam(required = false, name = "pagesize") @Positive(message = "Page size must be above or equals to 1") Integer pageSize) {
		List<Tag> tags;

		if (page != null) {
			if (pageSize != null) {
				tags = tagServiceImpl.showAll(page, pageSize);
			} else {
				tags = tagServiceImpl.showAll(page);
			}
		} else {
			tags = tagServiceImpl.showAll();
		}

		if (tags.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nothing found");
		} else {
			return ResponseEntity.ok().body(tagMapperImpl.toDTOList(tags));
		}
	}

	@GetMapping("/tags/{id}")
	public ResponseEntity<?> getTag(
			@PathVariable @Positive(message = "Tag id must be greater than or equals to 1") Long id) {
		Optional<Tag> tagWrap = tagServiceImpl.show(id);

		if (tagWrap.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nothing found");
		} else {
			return ResponseEntity.ok().body(tagMapperImpl.toDTO(tagWrap.get()));
		}
	}

	@PostMapping("/tags")
	public ResponseEntity<?> addTag(@Valid @RequestBody TagDTO tagDTO) {
		try {
			Tag savedTag = tagServiceImpl
					.add(tagMapperImpl.toTag(tagDTO));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(tagMapperImpl.toDTO(savedTag));
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/tags")
	public ResponseEntity<?> changeTag(@Valid @RequestBody TagDTO tagDTO) {
		try {
			Tag updatedTag = tagServiceImpl
					.change(tagMapperImpl.toTag(tagDTO));
			return ResponseEntity.ok().body(tagMapperImpl.toDTO(updatedTag));
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/tags")
	public ResponseEntity<?> removeTag(@Valid @RequestBody TagDTO tagDTO) {

		try {
			tagServiceImpl.remove(tagMapperImpl.toTag(tagDTO));
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Removed successfully");
		} catch (ServiceException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
