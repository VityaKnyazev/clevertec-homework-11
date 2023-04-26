package ru.clevertec.ecl.knyazev.service;

import java.util.List;
import java.util.Optional;

import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

public interface SimpleService<T> {
	Optional<T> show(Long id);
	
	List<T> showAll();

	List<T> showAll(Integer page, Integer pageSize);

	List<T> showAll(Integer page);

	T add(T t) throws ServiceException;

	T change(T t) throws ServiceException;

	void remove(T t) throws ServiceException;
}
