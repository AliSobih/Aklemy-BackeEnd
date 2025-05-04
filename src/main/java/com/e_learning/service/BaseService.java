package com.e_learning.service;

import com.e_learning.entities.BaseEntity;
import com.e_learning.util.PageQueryUtil;
import com.e_learning.util.PageResult;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BaseService<E extends BaseEntity> {
	
	public List<E> findAll();
	
	public E findById(Long id);

	public E save(E entity);



	public List<E> saveAll(List<E> entities);

	public void delete(E entity);
	
	public void deleteById(Long id);
	
	public PageResult<E> getDataPage(PageQueryUtil pageUtil);
	
	public PageResult<E> getDataPage(PageQueryUtil pageUtil, String sortField , Direction sortDirection);
	
	public JpaRepository<E, Long> Repository();


}
