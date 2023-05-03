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
import jakarta.validation.constraints.Min;
import ru.clevertec.ecl.knyazev.dto.GiftCertificateDTO;
import ru.clevertec.ecl.knyazev.dto.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.knyazev.entity.GiftCertificate;
import ru.clevertec.ecl.knyazev.service.GiftCertificateService;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

@RestController
@Validated
public class GiftCertificateController {
	private GiftCertificateService giftCertificateServiceImpl;

	private GiftCertificateMapper giftCertificateMapperImpl;

	GiftCertificateController() {
	}

	@Autowired
	GiftCertificateController(GiftCertificateService giftCertificateServiceImpl,
			GiftCertificateMapper giftCertificateMapperImpl) {
		this.giftCertificateServiceImpl = giftCertificateServiceImpl;
		this.giftCertificateMapperImpl = giftCertificateMapperImpl;
	}

	@GetMapping("/certificates")
	public ResponseEntity<?> getAll(
			@RequestParam(required = false, name = "page") @Min(value = 1, message = "Page must be above or equals to 1") Integer page,
			@RequestParam(required = false, name = "pagesize") @Min(value = 1, message = "Page size must be above or equals to 1") Integer pageSize) {
		List<GiftCertificate> giftCertificates;

		if (page != null) {
			if (pageSize != null) {
				giftCertificates = giftCertificateServiceImpl.showAll(page, pageSize);
			} else {
				giftCertificates = giftCertificateServiceImpl.showAll(page);
			}
		} else {
			giftCertificates = giftCertificateServiceImpl.showAll();
		}

		if (giftCertificates.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nothing found");
		} else {
			return ResponseEntity.ok().body(giftCertificateMapperImpl.toDTOList(giftCertificates));
		}
	}

	@GetMapping("/certificates/{id}")
	public ResponseEntity<?> getGiftCertificate(
			@PathVariable @Min(value = 1, message = "Gift certificate id must be greater than or equals to 1") Long id) {
		Optional<GiftCertificate> giftCertificateWrap = giftCertificateServiceImpl.show(id);

		if (giftCertificateWrap.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nothing found");
		} else {
			return ResponseEntity.ok().body(giftCertificateMapperImpl.toDTO(giftCertificateWrap.get()));
		}
	}

	@PostMapping("/certificates")
	public ResponseEntity<?> addGiftCertificate(@Valid @RequestBody GiftCertificateDTO giftCertificateDTO) {
		try {
			GiftCertificate savedGiftCertificate = giftCertificateServiceImpl
					.add(giftCertificateMapperImpl.toGiftCertificate(giftCertificateDTO));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(giftCertificateMapperImpl.toDTO(savedGiftCertificate));
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/certificates")
	public ResponseEntity<?> changeGiftCertificate(@Valid @RequestBody GiftCertificateDTO giftCertificateDTO) {
		try {
			GiftCertificate updatedGiftCertificate = giftCertificateServiceImpl
					.change(giftCertificateMapperImpl.toGiftCertificate(giftCertificateDTO));
			return ResponseEntity.ok().body(giftCertificateMapperImpl.toDTO(updatedGiftCertificate));
		} catch (ServiceException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/certificates")
	public ResponseEntity<?> removeGiftCertificate(@Valid @RequestBody GiftCertificateDTO giftCertificateDTO) {

		try {
			giftCertificateServiceImpl.remove(giftCertificateMapperImpl.toGiftCertificate(giftCertificateDTO));
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Removed successfully");
		} catch (ServiceException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
