package ru.clevertec.ecl.knyazev.service;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
	Optional<T> show(Long id);
	
	List<T> showAll();

	List<T> showAll(Integer page, Integer pageSize);

	List<T> showAll(Integer page);

	void add(T t);

	void change(T t);

	void remove(T t);
}
