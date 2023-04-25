package ru.clevertec.ecl.knyazev.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.clevertec.ecl.knyazev.dao.util.TestAppConfig;
import ru.clevertec.ecl.knyazev.dao.util.TestContainersConfig;
import ru.clevertec.ecl.knyazev.entity.GiftCertificate;
import ru.clevertec.ecl.knyazev.entity.Tag;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({ @ContextConfiguration(classes = { TestContainersConfig.class }),
		@ContextConfiguration(classes = { TestAppConfig.class }) })

public class GiftCertificateTest {
	@Autowired
	DAO<GiftCertificate> giftCertificateDAOJPA;

	@Test
	public void checkGetByIdShouldReturnGiftCertificate() {
		Long inputId = 1L;

		Optional<GiftCertificate> giftCertificateActual = giftCertificateDAOJPA.getById(inputId);

		Long expectedId = 1L;
		assertAll( () -> assertThat(giftCertificateActual).isNotEmpty(),
				   () -> assertThat(giftCertificateActual.get().getId()).isEqualTo(expectedId)
			     );
	}
	
	@ParameterizedTest
	@NullSource
	@ValueSource(longs = {0, -1, 1258})
	public void checkGetByIdShouldShouldReturnOptionalEmpty(Long id) {
		Optional<GiftCertificate> giftCertificateActual = giftCertificateDAOJPA.getById(id);
		
		assertThat(giftCertificateActual).isEmpty();
	}
	
	@Test
	public void checkGetAllShouldReturnGiftCertificates() {
		List<GiftCertificate> giftCertificates = giftCertificateDAOJPA.getAll();
		
		assertThat(giftCertificates).isNotEmpty();
	}
	
	@Test 
	public void checkGetAllOnPageAndElementSizeShouldReturnGiftCertificates() {
		
		GiftCertificate expectedGiftCertificate = GiftCertificate.builder()
												  .id(2L)
												  .name("Ноутбуки")
												  .description("На ноутбуки")
												  .price(new BigDecimal(300.000))
												  .duration(Date.valueOf("2023-08-20"))
												  .build();
												  	
		List<GiftCertificate> actualGiftCertificates = giftCertificateDAOJPA.getAll(1, 3);
		
		assertAll(
					() -> assertThat(actualGiftCertificates).isNotEmpty(),
					() -> assertThat(actualGiftCertificates.get(1).getId()).isEqualTo(expectedGiftCertificate.getId()),
					() -> assertThat(actualGiftCertificates.get(1).getName()).isEqualTo(expectedGiftCertificate.getName()),
					() -> assertThat(actualGiftCertificates.get(1).getDescription()).isEqualTo(expectedGiftCertificate.getDescription()),
					() -> assertThat(actualGiftCertificates.get(1).getPrice()).isEqualByComparingTo(expectedGiftCertificate.getPrice()),
					() -> assertThat(actualGiftCertificates.get(1).getDuration()).isEqualToIgnoringMillis(expectedGiftCertificate.getDuration())		
				);		
	}
	
	@ParameterizedTest
	@MethodSource("getInvalidPageAndElSize")
	public void checkGetAllOnInvalidPageAndElementSizeShouldReturnEmptyList(Integer invalidPage, Integer invalidPageSize) {
		
		List<GiftCertificate> actualGiftCertificates = giftCertificateDAOJPA.getAll(invalidPage, invalidPageSize);
		
		assertThat(actualGiftCertificates).isEmpty();
	}
	
	@Test
	public void checkGetAllOnPageShouldReturnGiftCertificates() {
		List<GiftCertificate> actualGiftCertificates = giftCertificateDAOJPA.getAll(1);
		
		assertThat(actualGiftCertificates).isNotEmpty();
	}
	
	@Test
	public void checkSaveShouldReturnSavedGiftCertificate() {
		GiftCertificate savingGiftCertificate = GiftCertificate.builder()
														 .name("Ванны")
														 .description("На ванны")
														 .price(new BigDecimal(100.000))
														 .duration(Date.valueOf("2023-11-01"))
														 .build();
		
		Optional<GiftCertificate> savedGiftCertificate = giftCertificateDAOJPA.save(savingGiftCertificate);
		
		assertThat(savedGiftCertificate).isNotEmpty();
	}
	
	@Test
	public void checkSaveShouldReturnSavedGiftCertificateWithSavedTags() {
		GiftCertificate savingGiftCertificate = GiftCertificate.builder()
														 .name("Бассейны")
														 .description("На бассейны")
														 .price(new BigDecimal(100.000))
														 .duration(Date.valueOf("2023-09-12"))
														 .tags(new ArrayList<>() {
															private static final long serialVersionUID = -6127791584368731125L;

														 {
															 add(Tag.builder()
																	 .name("Деду")
																	 .build());
															 add(Tag.builder()
																	 .name("Любовнице")
																	 .build());
															 		
														 }})
														 .build();
		
		Optional<GiftCertificate> savedGiftCertificate = giftCertificateDAOJPA.save(savingGiftCertificate);
		
		assertAll(
					() -> assertThat(savedGiftCertificate).isNotEmpty(),
					() -> assertThat(savedGiftCertificate.get().getTags()).hasSize(2)
				);
	}
	
	@Test
	public void checkSaveShouldReturnSavedGiftCertificateWithSavedAndExistingTags() {
		GiftCertificate savingGiftCertificate = GiftCertificate.builder()
														 .name("Автомобили")
														 .description("На автомобили")
														 .price(new BigDecimal(150.000))
														 .duration(Date.valueOf("2023-08-12"))
														 .tags(new ArrayList<>() {
															private static final long serialVersionUID = 11515515157L;

														 {
															 add(Tag.builder()
																	 .id(1L)
																	 .build());
															 add(Tag.builder()
																	 .id(2L)
																	 .build());
															 add(Tag.builder()
																	 .name("Спутнице")
																	 .build());
															 		
														 }})
														 .build();
		
		Optional<GiftCertificate> savedGiftCertificate = giftCertificateDAOJPA.save(savingGiftCertificate);
		
		assertAll(
					() -> assertThat(savedGiftCertificate).isNotEmpty(),
					() -> assertThat(savedGiftCertificate.get().getTags()).hasSize(3)
				);
	}
	
	@ParameterizedTest
	@MethodSource("getInvalidGiftCertificatesForSaving")
	public void checkSaveShouldReturnOptionalEmptyOnInvalidGiftCertificate(GiftCertificate invalidGiftCertificate) {
		Optional<GiftCertificate> savedGiftCertificate = giftCertificateDAOJPA.save(invalidGiftCertificate);
		
		assertThat(savedGiftCertificate).isEmpty();
	}
	
	@Test
	public void checkUpdateShouldReturnUpdatedGiftCertificate() {
		GiftCertificate updatingGiftCertificate = GiftCertificate.builder()
				 .id(4L)
				 .name("Пиротехника")
				 .description("На пиротехнику, феерверки")
				 .price(new BigDecimal(200.000))
				 .duration(Date.valueOf("2023-09-25"))
				 .tags(new ArrayList<>() {
					private static final long serialVersionUID = 125124551215L;

				 {
					 add(Tag.builder()
							 .id(1L)
							 .build());
					 add(Tag.builder()
							 .id(2L)
							 .build());
					 add(Tag.builder()
							 .name("Попутчице")
							 .build());
					 		
				 }})
				 .build();
		
		Optional<GiftCertificate> updatedGiftCertificate = giftCertificateDAOJPA.update(updatingGiftCertificate);
		
		assertAll(
				() -> assertThat(updatedGiftCertificate).isNotEmpty(),
				() -> assertThat(updatedGiftCertificate.get().getTags()).hasSize(6)
			);
	}
	
	@ParameterizedTest
	@MethodSource("getInvalidGiftCertificatesForUpdating")
	public void checkUpdateShouldReturnOptionalEmptyOnInvalidGiftCertificate(GiftCertificate invalidGiftCertificate) {
		Optional<GiftCertificate> savedGiftCertificate = giftCertificateDAOJPA.update(invalidGiftCertificate);
		
		assertThat(savedGiftCertificate).isEmpty();
	}
	
	@Test
	public void checkDeleteShouldReturnTrue() {
		GiftCertificate deletingGiftCertificate = GiftCertificate.builder()
				 .id(6L)
				 .build();
		
		Boolean result = giftCertificateDAOJPA.delete(deletingGiftCertificate);
		
		assertThat(result).isTrue();
	}
	
	@ParameterizedTest
	@MethodSource("getInvalidGiftCertificateForDeleting")
	public void checkDeleteShouldReturnFalse(GiftCertificate invalidGiftCertificate) {
Boolean result = giftCertificateDAOJPA.delete(invalidGiftCertificate);
		
		assertThat(result).isFalse();
	}
	
	private static Stream<Arguments> getInvalidPageAndElSize() {
		return Stream.of(
					Arguments.of(null, 1),
					Arguments.of(0, 1),
					Arguments.of(-1, 1),
					Arguments.of(1, null),
					Arguments.of(1, 0),
					Arguments.of(1, -1),
					Arguments.of(0, 0),
					Arguments.of(20, 10)
				);
	}
	
	private static Stream<Arguments> getInvalidGiftCertificatesForSaving() {
		return Stream.of(
				null,
				Arguments.of(GiftCertificate.builder()
						 .id(1L)
						 .name("Табак")
						 .description("На такбак")
						 .price(new BigDecimal(250.000))
						 .duration(Date.valueOf("2023-10-06"))
					     .build()),
				Arguments.of(GiftCertificate.builder()
						 .name("Ноутбуки")
						 .description("На ноуты")
						 .price(new BigDecimal(350.000))
						 .duration(Date.valueOf("2023-04-08"))
					     .build())
			);		
	}
	
	private static Stream<Arguments> getInvalidGiftCertificatesForUpdating() {
		return Stream.of(
				null,
				Arguments.of(GiftCertificate.builder()
						 .id(null)
						 .name("Табак")
						 .description("На такбак")
						 .price(new BigDecimal(250.000))
						 .duration(Date.valueOf("2023-10-06"))
					     .build()),
				Arguments.of(GiftCertificate.builder()
						 .id(0L)
						 .name("Табак")
						 .description("На такбак")
						 .price(new BigDecimal(250.000))
						 .duration(Date.valueOf("2023-10-06"))
					     .build()),
				Arguments.of(GiftCertificate.builder()
						 .id(2458L)
						 .name("Табак")
						 .description("На такбак")
						 .price(new BigDecimal(350.000))
						 .duration(Date.valueOf("2023-04-08"))
					     .build()),
				Arguments.of(GiftCertificate.builder()
						 .id(1L)
						 .name("Ноутбуки")
						 .description("На ноуты")
						 .price(new BigDecimal(100.000))
						 .duration(Date.valueOf("2023-05-12"))
					     .build())
			);		
	}
	
	private static Stream<Arguments> getInvalidGiftCertificateForDeleting() {
		return Stream.of(
				null,
				Arguments.of(GiftCertificate.builder()
						 .id(null)
						 .name("Табак")
						 .description("На такбак")
						 .price(new BigDecimal(250.000))
						 .duration(Date.valueOf("2023-10-06"))
					     .build()),
				Arguments.of(GiftCertificate.builder()
						 .id(0L)
						 .name("Табак")
						 .description("На такбак")
						 .price(new BigDecimal(250.000))
						 .duration(Date.valueOf("2023-10-06"))
					     .build()),
				Arguments.of(GiftCertificate.builder()
						 .id(2468L)
						 .name("Табак")
						 .description("На такбак")
						 .price(new BigDecimal(350.000))
						 .duration(Date.valueOf("2023-04-08"))
					     .build())
			);		
	}
}
