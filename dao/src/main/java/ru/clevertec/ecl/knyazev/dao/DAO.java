package ru.clevertec.ecl.knyazev.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
	/**
	 * 
	 * Get Optional<T> entity from database on giving id.
	 * 
	 * @param Long id for searching T entity in database.
	 * @return Optional<T>. If T entity not exists in database - Optional empty.
	 */
	Optional<T> getById(Long id);
	
	/**
	 * 
	 * Get all T entities from database
	 * 
	 * @return List<T> all entities from database or empty list.
	 */
	List<T> getAll();
	
	/**
	 * 
	 * Get all entities from database on given Integer page of given quantity of elements.
	 * 
	 * @param Integer page number.
	 * @param Integer elementsOnPage quantity of elements on page.
	 * @return List<T> entities on given Integer page of given Integer quantity of elements
	 *         from database or empty list.
	 */
	List<T> getAll(Integer page, Integer elementsOnPage);
	
	/**
	 * 
	 * Get all entities from database on given Integer page.
	 * 
	 * @param Integer page number.
	 * @return List<T> entities on given Integer page from database or empty list.
	 */
	List<T> getAll(Integer page);
		
	
	/**
	 * 
	 * Save entity to database
	 * 
	 * @param T entity for saving
	 * @return Optional<T> if was success on saving - optional entity, otherwise - optional empty.
	 */
	Optional<T> save(T entity);
	
	/**
	 * 
	 * Update entity in database
	 * 
	 * @param T entity for updating
	 * @return Optional<T> if was success on updating - optional entity, otherwise - optional empty
	 */
	Optional<T> update(T entity);
	
	/**
	 * 
	 * Delete entity in database
	 * 
	 * @param Entity for deleting
	 * @return Boolean true on success deleting, otherwise - false.
	 */
	Boolean delete(T entity);
}
