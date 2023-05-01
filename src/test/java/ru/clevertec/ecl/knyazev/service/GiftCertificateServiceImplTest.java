package ru.clevertec.ecl.knyazev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.clevertec.ecl.knyazev.dao.GiftCertificateDAO;
import ru.clevertec.ecl.knyazev.entity.GiftCertificate;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {
	@Mock
	private GiftCertificateDAO giftCertificateDAOJPAMock;
	
	@InjectMocks
	private GiftCertificateServiceImpl giftCertificateServiceImpl;
	
	@Test
	public void checkShowShouldReturnOptionalGiftCertificate() {
		
		Mockito.when(giftCertificateDAOJPAMock.getById(Mockito.longThat(id -> id != null && id > 0L))).thenAnswer(invocation -> {
			Long inputId = invocation.getArgument(0);
			
			return Optional.of(GiftCertificate.builder()
						   .id(inputId)
						   .name("Таблетки")
						   .description("На таблетки")
						   .price(new BigDecimal(200.00))
						   .duration(Date.valueOf("2023-12-06"))
						   .build());
		});
		
		Long inputId = 3L;
		
		Optional<GiftCertificate> giftCertificateWrap = giftCertificateServiceImpl.show(inputId);
		
		assertAll(
				() -> assertThat(giftCertificateWrap).isNotEmpty(),
				() -> assertThat(giftCertificateWrap.get().getId()).isEqualTo(inputId)
				);
		
	}
	
	@Test
	public void checkShowAllShouldReturnGiftCertificates() {
		
		Mockito.when(giftCertificateDAOJPAMock.getAll()).thenAnswer(invocation -> {
				
			return List.of(GiftCertificate.builder()
						   .id(1L)
						   .name("Таблетки")
						   .description("На таблетки")
						   .price(new BigDecimal(200.00))
						   .duration(Date.valueOf("2023-12-06"))
						   .build(),
					GiftCertificate.builder()
					   .id(2L)
					   .name("Бады")
					   .description("На бады")
					   .price(new BigDecimal(100.00))
					   .duration(Date.valueOf("2023-08-18"))
					   .build());
		});
				
		List<GiftCertificate> giftCertificates = giftCertificateServiceImpl.showAll();
		
		assertAll(
				() -> assertThat(giftCertificates).isNotEmpty(),
				() -> assertThat(giftCertificates.get(0).getName()).isEqualTo("Таблетки")
				);
		
	}
	
	@Test
	public void checkShowAllShouldReturnGiftCertificatesOnPageAndSize() {
		
		List<GiftCertificate> giftCertificates = List.of(GiftCertificate.builder()
				   .id(1L)
				   .name("Таблетки")
				   .description("На таблетки")
				   .price(new BigDecimal(200.00))
				   .duration(Date.valueOf("2023-12-06"))
				   .build(),
			GiftCertificate.builder()
			   .id(2L)
			   .name("Бады")
			   .description("На бады")
			   .price(new BigDecimal(100.00))
			   .duration(Date.valueOf("2023-08-18"))
			   .build());
		
		Mockito.when(giftCertificateDAOJPAMock.getAll(Mockito.intThat(page -> page != null && page > 0), 
				Mockito.intThat(pageS -> pageS != null && pageS > 0))).thenReturn(giftCertificates);
				
		Integer inputPage = 1;
		Integer inputPageSize = 2;
		
		List<GiftCertificate> actualGiftCertificates = giftCertificateServiceImpl.showAll(inputPage, inputPageSize);
		
		assertAll(
				() -> assertThat(actualGiftCertificates).isNotEmpty(),
				() -> assertThat(actualGiftCertificates.get(0).getName()).isEqualTo("Таблетки")
				);
		
	}
	
	@Test
	public void checkShowAllShouldReturnGiftCertificatesOnPage() {
		
		List<GiftCertificate> giftCertificates = List.of(GiftCertificate.builder()
				   .id(1L)
				   .name("Таблетки")
				   .description("На таблетки")
				   .price(new BigDecimal(200.00))
				   .duration(Date.valueOf("2023-12-06"))
				   .build(),
			GiftCertificate.builder()
			   .id(2L)
			   .name("Бады")
			   .description("На бады")
			   .price(new BigDecimal(100.00))
			   .duration(Date.valueOf("2023-08-18"))
			   .build());
		
		Mockito.when(giftCertificateDAOJPAMock.getAll(Mockito.intThat(page -> page != null && page > 0)))
											  .thenReturn(giftCertificates);
				
		Integer inputPage = 1;
		
		List<GiftCertificate> actualGiftCertificates = giftCertificateServiceImpl.showAll(inputPage);
		
		assertAll(
				() -> assertThat(actualGiftCertificates).isNotEmpty(),
				() -> assertThat(actualGiftCertificates.get(0).getName()).isEqualTo("Таблетки")
				);
		
	}
	
	@Test
	public void checkAddShoulReturnAddedCertificate() throws ServiceException {
		Optional<GiftCertificate> giftCertificateWrap = Optional.of(GiftCertificate.builder()
										  .id(1L)
										  .name("Таблетки")
										  .description("На таблетки")
										  .price(new BigDecimal(200.00))
										  .duration(Date.valueOf("2023-12-06"))
										  .build());
		
		Mockito.when(giftCertificateDAOJPAMock.save(Mockito.any(GiftCertificate.class))).thenReturn(giftCertificateWrap);
		
		GiftCertificate inputGiftCertificate = GiftCertificate.builder().name("Таблетки").build();
		GiftCertificate actualGiftCertificate = giftCertificateServiceImpl.add(inputGiftCertificate);
		
		assertAll(
				() -> assertThat(actualGiftCertificate.getId()).isEqualTo(1L),
				() -> assertThat(actualGiftCertificate.getName()).isEqualTo("Таблетки")
		);
	}
	
	@Test
	public void checkAddShouldThrowServiceExceptionOnFailedSaving() {
		Mockito.when(giftCertificateDAOJPAMock.save(Mockito.any(GiftCertificate.class))).thenReturn(Optional.empty());
		
		GiftCertificate invalidGiftCertificate = GiftCertificate.builder().id(1L).build();
		
		assertThatExceptionOfType(ServiceException.class).isThrownBy(() -> giftCertificateServiceImpl.add(invalidGiftCertificate));
	}
	
	@Test
	public void checkChangeShoulReturnChangedCertificate() throws ServiceException {		
		Mockito.when(giftCertificateDAOJPAMock.update(Mockito.any(GiftCertificate.class)))
		       .thenAnswer(invocation -> Optional.of(invocation.getArgument(0)));
		
		GiftCertificate inputGiftCertificate = GiftCertificate.builder()
															.id(2L)
															.name("Таблетки")
															.build();
		
		GiftCertificate actualGiftCertificate = giftCertificateServiceImpl.change(inputGiftCertificate);
		
		assertAll(
				() -> assertThat(actualGiftCertificate.getId()).isEqualTo(2L),
				() -> assertThat(actualGiftCertificate.getName()).isEqualTo("Таблетки")
		);
	}
	
	@Test
	public void checkChangeShouldThrowServiceExceptionOnFailedChanging() {
		Mockito.when(giftCertificateDAOJPAMock.update(Mockito.any(GiftCertificate.class))).thenReturn(Optional.empty());
		
		GiftCertificate invalidGiftCertificate = GiftCertificate.builder()
																.id(null)
																.build();
		
		assertThatExceptionOfType(ServiceException.class).isThrownBy(() -> giftCertificateServiceImpl.change(invalidGiftCertificate));
	}
	
	@Test
	public void checkRemoveShoulDoesntThrowExceptionOnSuccessRemoving() throws ServiceException {		
		Mockito.when(giftCertificateDAOJPAMock.delete(Mockito.any(GiftCertificate.class))).thenReturn(true);
		
		GiftCertificate inputGiftCertificate = GiftCertificate.builder()
															.id(2L)
															.build();
		
		assertDoesNotThrow(() -> giftCertificateServiceImpl.remove(inputGiftCertificate));
	}
	
	@Test
	public void checkRemoveShouldThrowServiceExceptionOnFailedRemoving() {
		Mockito.when(giftCertificateDAOJPAMock.delete(Mockito.any(GiftCertificate.class))).thenReturn(false);
		
		GiftCertificate invalidGiftCertificate = GiftCertificate.builder()
																.id(null)
																.build();
		
		assertThatExceptionOfType(ServiceException.class).isThrownBy(() -> giftCertificateServiceImpl.remove(invalidGiftCertificate));
	}
	
}
