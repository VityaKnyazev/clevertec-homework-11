package ru.clevertec.ecl.knyazev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.clevertec.ecl.knyazev.dao.DAO;
import ru.clevertec.ecl.knyazev.entity.Tag;
import ru.clevertec.ecl.knyazev.service.exception.ServiceException;

@Service
public class TagServiceImpl implements SimpleService<Tag> {
	private DAO<Tag> tagDAOJPA;

	public TagServiceImpl() {
	}

	@Autowired
	public TagServiceImpl(DAO<Tag> tagDAOJPA) {
		this.tagDAOJPA = tagDAOJPA;
	}

	@Override
	public Optional<Tag> show(Long id) {
		return tagDAOJPA.getById(id);
	}

	@Override
	public List<Tag> showAll() {
		return tagDAOJPA.getAll();
	}

	@Override
	public List<Tag> showAll(Integer page, Integer pageSize) {
		return tagDAOJPA.getAll(page, pageSize);
	}

	@Override
	public List<Tag> showAll(Integer page) {
		return tagDAOJPA.getAll(page);
	}

	@Override
	public Tag add(Tag tag) throws ServiceException {
		Optional<Tag> savedTagWrap = tagDAOJPA.save(tag);

		if (savedTagWrap.isEmpty()) {
			throw new ServiceException("Error on adding tag");
		}

		return savedTagWrap.get();
	}

	@Override
	public Tag change(Tag tag) throws ServiceException {
		Optional<Tag> updatedTagWrap = tagDAOJPA.update(tag);

		if (updatedTagWrap.isEmpty()) {
			throw new ServiceException("Error on updating tag");
		}

		return updatedTagWrap.get();
	}

	@Override
	public void remove(Tag tag) throws ServiceException {
		Boolean result = tagDAOJPA.delete(tag);
		
		if (!result) {
			throw new ServiceException("Error on deleting tag");
		}
	}

}
