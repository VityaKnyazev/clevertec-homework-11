package ru.clevertec.ecl.knyazev.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
	/**
	 * 
	 * Get Optional<T> entity from database on giving id.
	 * 
	 * @param id for searching T entity in database.
	 * @return Optional of type T. If T entity not exists in database - Optional empty.
	 */
	Optional<T> getById(Long id);
	
	/**
	 * 
	 * Get all T entities from database
	 * 
	 * @return all T entities from database of type T or empty list.
	 */
	List<T> getAll();
	
	/**
	 * 
	 * Get all entities from database on given Integer page of given quantity of elements.
	 * 
	 * @param page number.
	 * @param elementsOnPage quantity of elements on page.
	 * @return list of T entities on given Integer page of given Integer quantity of elements
	 *         from database or empty list.
	 */
	List<T> getAll(Integer page, Integer elementsOnPage);
	
	/**
	 * 
	 * Get all entities from database on given Integer page.
	 * 
	 * @param page number.
	 * @return list of T entities on given Integer page from database or empty list.
	 */
	List<T> getAll(Integer page);
		
	
	/**
	 * 
	 * Save entity to database
	 * 
	 * @param T entity for saving
	 * @return Optional of T. If was success on saving - optional with entity, otherwise - optional empty.
	 */
	Optional<T> save(T entity);
	
	/**
	 * 
	 * Update entity in database
	 * 
	 * @param T entity for updating
	 * @return Optional T. If was success on updating - optional with entity, otherwise - optional empty
	 */
	Optional<T> update(T entity);
	
	/**
	 * 
	 * Delete T entity in database
	 * 
	 * @param Entity for deleting
	 * @return Boolean true on success deleting, otherwise - false.
	 */
	Boolean delete(T entity);
}
