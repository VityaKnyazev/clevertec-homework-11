package ru.clevertec.ecl.knyazev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.clevertec.ecl.knyazev.dao.DAO;
import ru.clevertec.ecl.knyazev.entity.Tag;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
	@Mock
	private DAO<Tag> tagDAOJPAMock;
	
	@InjectMocks
	private TagService tagService;
	
	@Test
	public void checkShowShouldReturnOptionalTag() {
		
		Mockito.when(tagDAOJPAMock.getById(Mockito.longThat(id -> id != null && id > 0L))).thenAnswer(invocation -> {
			Long inputId = invocation.getArgument(0);
			
			return Optional.of(Tag.builder()
						   .id(inputId)
						   .name("Другу")
						   .build());
		});
		
		Long inputId = 3L;
		
		Optional<Tag> tagWrap = tagService.show(inputId);
		
		assertAll(
				() -> assertThat(tagWrap).isNotEmpty(),
				() -> assertThat(tagWrap.get().getId()).isEqualTo(inputId)
				);
		
	}
	
	@Test
	public void checkShowAllShouldReturnTags() {
		
		Mockito.when(tagDAOJPAMock.getAll()).thenAnswer(invocation -> {
				
			return List.of(Tag.builder()
						   .id(1L)
						   .name("Другу")
						   .build(),
					Tag.builder()
					   .id(2L)
					   .name("Брату")
					   .build());
		});
				
		List<Tag> tags = tagService.showAll();
		
		assertAll(
				() -> assertThat(tags).isNotEmpty(),
				() -> assertThat(tags.get(0).getName()).isEqualTo("Другу")
				);
		
	}
	
	@Test
	public void checkShowAllShouldReturnTagsOnPageAndSize() {
		
		List<Tag> tags = List.of(Tag.builder()
					   .id(1L)
					   .name("Другу")
					   .build(),
				Tag.builder()
				   .id(2L)
				   .name("Брату")
				   .build());
		
		Mockito.when(tagDAOJPAMock.getAll(Mockito.intThat(page -> page != null && page > 0), 
				Mockito.intThat(pageS -> pageS != null && pageS > 0))).thenReturn(tags);
				
		Integer inputPage = 1;
		Integer inputPageSize = 2;
		
		List<Tag> actualTags = tagService.showAll(inputPage, inputPageSize);
		
		assertAll(
					() -> assertThat(actualTags).isNotEmpty(),
					() -> assertThat(actualTags.get(1).getName()).isEqualTo("Брату")
				);
		
	}
	
	@Test
	public void checkShowAllShouldReturnTagsOnPage() {
		
		List<Tag> tags = List.of(Tag.builder()
				   .id(1L)
				   .name("Другу")
				   .build(),
			Tag.builder()
			   .id(2L)
			   .name("Брату")
			   .build());
		
		Mockito.when(tagDAOJPAMock.getAll(Mockito.intThat(page -> page != null && page > 0)))
											  .thenReturn(tags);
				
		Integer inputPage = 1;
		
		List<Tag> actualTags = tagService.showAll(inputPage);
		
		assertAll(
					() -> assertThat(actualTags).isNotEmpty(),
					() -> assertThat(actualTags.get(0).getName()).isEqualTo("Другу")
				);
		
	}
	
	@Test
	public void checkAddShoulReturnAddedTag() throws ServiceException {
		Optional<Tag> tagWrap = Optional.of(Tag.builder()
										  .id(1L)
										  .name("Подруге")
										  .build());
		
		Mockito.when(tagDAOJPAMock.save(Mockito.any(Tag.class))).thenReturn(tagWrap);
		
		Tag inputTag = Tag.builder().name("Подруге").build();
		Tag actualTag = tagService.add(inputTag);
		
		assertAll(
				() -> assertThat(actualTag.getId()).isEqualTo(1L),
				() -> assertThat(actualTag.getName()).isEqualTo("Подруге")
		);
	}
	
	@Test
	public void checkAddShouldThrowServiceExceptionOnFailedSaving() {
		Mockito.when(tagDAOJPAMock.save(Mockito.any(Tag.class))).thenReturn(Optional.empty());
		
		Tag invalidTag = Tag.builder().id(1L).build();
		
		assertThatExceptionOfType(ServiceException.class).isThrownBy(() -> tagService.add(invalidTag));
	}
	
	@Test
	public void checkChangeShoulReturnChangedCertificate() throws ServiceException {		
		Mockito.when(tagDAOJPAMock.update(Mockito.any(Tag.class)))
		       .thenAnswer(invocation -> Optional.of(invocation.getArgument(0)));
		
		Tag inputTag = Tag.builder()
						  .id(2L)
						  .name("Подруге")
						  .build();
		
		Tag actualTag = tagService.change(inputTag);
		
		assertAll(
				() -> assertThat(actualTag.getId()).isEqualTo(2L),
				() -> assertThat(actualTag.getName()).isEqualTo("Подруге")
		);
	}
	
	@Test
	public void checkChangeShouldThrowServiceExceptionOnFailedChanging() {
		Mockito.when(tagDAOJPAMock.update(Mockito.any(Tag.class))).thenReturn(Optional.empty());
		
		Tag invalidTag = Tag.builder()
						    .id(null)
							.build();
		
		assertThatExceptionOfType(ServiceException.class).isThrownBy(() -> tagService.change(invalidTag));
	}
	
	@Test
	public void checkRemoveShoulDoesntThrowExceptionOnSuccessRemoving() throws ServiceException {		
		Mockito.when(tagDAOJPAMock.delete(Mockito.any(Tag.class))).thenReturn(true);
		
		Tag inputTag = Tag.builder()
						  .id(2L)
						  .build();
		
		assertDoesNotThrow(() -> tagService.remove(inputTag));
	}
	
	@Test
	public void checkRemoveShouldThrowServiceExceptionOnFailedRemoving() {
		Mockito.when(tagDAOJPAMock.delete(Mockito.any(Tag.class))).thenReturn(false);
		
		Tag invalidTag = Tag.builder()
							.id(null)
							.build();
		
		assertThatExceptionOfType(ServiceException.class).isThrownBy(() -> tagService.remove(invalidTag));
	}
	
}
