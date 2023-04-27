package ru.clevertec.ecl.knyazev.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.clevertec.ecl.knyazev.dao.DAO;
import ru.clevertec.ecl.knyazev.entity.Tag;

@Service
public class TagService implements SimpleService<Tag> {
	private DAO<Tag> tagDAOJPA;

	public TagService() {
	}

	@Autowired
	public TagService(DAO<Tag> tagDAOJPA) {
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
	public void remove(Tag tag) {
		Boolean result = tagDAOJPA.delete(tag);
		
		if (!result) {
			throw new ServiceException("Error on deleting tag");
		}
	}

}
