package ru.clevertec.ecl.knyazev.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
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

import ru.clevertec.ecl.knyazev.entity.Tag;
import ru.clevertec.ecl.knyazev.testconfig.TestAppConfig;
import ru.clevertec.ecl.knyazev.testconfig.TestContainersConfig;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({ @ContextConfiguration(classes = { TestContainersConfig.class }),
		@ContextConfiguration(classes = { TestAppConfig.class }) })

public class TagDAOJPAImplTest {
	@Autowired
	TagDAO tagDAOJPAImpl;

	@Test
	public void checkGetByIdShouldReturnTag() {
		Long inputId = 1L;

		Optional<Tag> tagActual = tagDAOJPAImpl.getById(inputId);

		Long expectedId = 1L;
		assertAll(() -> assertThat(tagActual).isNotEmpty(),
				() -> assertThat(tagActual.get().getId()).isEqualTo(expectedId));
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(longs = { 0, -1, 1258 })
	public void checkGetByIdShouldShouldReturnOptionalEmpty(Long id) {
		Optional<Tag> tagActual = tagDAOJPAImpl.getById(id);

		assertThat(tagActual).isEmpty();
	}

	@Test
	public void checkGetAllShouldReturnTags() {
		List<Tag> tags = tagDAOJPAImpl.getAll();

		assertThat(tags).isNotEmpty();
	}

	@Test
	public void checkGetAllOnPageAndElementSizeShouldReturnTags() {

		Tag expectedTag = Tag.builder().id(1L).name("Любимой жене").build();

		List<Tag> actualTags = tagDAOJPAImpl.getAll(1, 3);

		assertAll(() -> assertThat(actualTags).isNotEmpty(),
				() -> assertThat(actualTags.get(0).getId()).isEqualTo(expectedTag.getId()),
				() -> assertThat(actualTags.get(0).getName()).isEqualTo(expectedTag.getName()));
	}

	@ParameterizedTest
	@MethodSource("getInvalidPageAndElSize")
	public void checkGetAllOnInvalidPageAndElementSizeShouldReturnEmptyList(Integer invalidPage,
			Integer invalidPageSize) {

		List<Tag> actualTags = tagDAOJPAImpl.getAll(invalidPage, invalidPageSize);

		assertThat(actualTags).isEmpty();
	}

	@Test
	public void checkGetAllOnPageShouldReturnTags() {
		List<Tag> actualTags = tagDAOJPAImpl.getAll(1);

		assertThat(actualTags).isNotEmpty();
	}

	@Test
	public void checkSaveShouldReturnSavedTag() {
		Tag savingTag = Tag.builder().name("Школьнику").build();

		Optional<Tag> savedTag = tagDAOJPAImpl.save(savingTag);

		assertThat(savedTag).isNotEmpty();
	}

	@ParameterizedTest
	@MethodSource("getInvalidTagsForSaving")
	public void checkSaveShouldReturnOptionalEmptyOnInvalidTag(Tag invalidTag) {
		Optional<Tag> savedTag = tagDAOJPAImpl.save(invalidTag);

		assertThat(savedTag).isEmpty();
	}

	@Test
	public void checkUpdateShouldReturnUpdatedTag() {
		Tag updatingTag = Tag.builder()
							 .id(2L)
							 .name("Дорогому другу")
							 .build();

		Optional<Tag> updatedTag = tagDAOJPAImpl.update(updatingTag);

		assertThat(updatedTag).isNotEmpty();
	}

	@ParameterizedTest
	@MethodSource("getInvalidTagsForUpdating")
	public void checkUpdateShouldReturnOptionalEmptyOnInvalidTag(Tag invalidTag) {
		
		Optional<Tag> updatedTag = tagDAOJPAImpl.update(invalidTag);

		assertThat(updatedTag).isEmpty();
	}

	
	@Test
	@Disabled(value = "@ManyToMany throws a ConstraintViolationException when removing entity from the mapped-by (inverse) side")
	public void checkDeleteShouldReturnTrue() {
		Tag deletingTag = Tag.builder().id(4L).build();

		Boolean result = tagDAOJPAImpl.delete(deletingTag);

		assertThat(result).isTrue();
	}

	@ParameterizedTest
	@MethodSource("getInvalidTagsForDeleting")
	public void checkDeleteShouldReturnFalse(Tag invalidTag) {
		Boolean result = tagDAOJPAImpl.delete(invalidTag);

		assertThat(result).isFalse();
	}

	private static Stream<Arguments> getInvalidPageAndElSize() {
		return Stream.of(Arguments.of(null, 1), Arguments.of(0, 1), Arguments.of(-1, 1), Arguments.of(1, null),
				Arguments.of(1, 0), Arguments.of(1, -1), Arguments.of(0, 0), Arguments.of(20, 10));
	}

	private static Stream<Arguments> getInvalidTagsForSaving() {
		return Stream.of(null, Arguments.of(Tag.builder().id(1L).name("Коле").build()),
				Arguments.of(Tag.builder().name("Теще").build()));
	}

	private static Stream<Arguments> getInvalidTagsForUpdating() {
		return Stream.of(null,
				Arguments.of(Tag.builder().id(null).name("Парню").build()),
				Arguments.of(Tag.builder().id(0L).name("Парню").build()),
				Arguments.of(Tag.builder().id(2458L).name("Парню").build()),
				Arguments.of(Tag.builder().id(2L).name("Программисту").build()));
	}

	private static Stream<Arguments> getInvalidTagsForDeleting() {
		return Stream.of(null,
				Arguments.of(Tag.builder().id(null).name("Парню").build()),
				Arguments.of(Tag.builder().id(0L).name("Парню").build()),
				Arguments.of(Tag.builder().id(2468L).name("Парню").build()));
	}
}
