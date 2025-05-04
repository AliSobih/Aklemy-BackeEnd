package com.e_learning.controller;

import com.e_learning.dto.BaseDTO;
import com.e_learning.entities.BaseEntity;
import com.e_learning.entities.mapper.Mapper;
import com.e_learning.service.BaseServiceImp;
import com.e_learning.util.MessageResponse;
import com.e_learning.util.PageQueryUtil;
import com.e_learning.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;


public class 	BaseController<T extends BaseEntity, DTO extends BaseDTO>{
	@Autowired
	private BaseServiceImp<T> baseService;

	@Autowired
	private Mapper<T, DTO> mapper;

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public DTO get(@PathVariable(value = "id") Long id) {
		return mapper.toDTO(baseService.findById(id));
	}

	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<DTO> list() {
		return mapper.toDTOs(baseService.findAll());
	}


	@RequestMapping(value="/datapage", method = RequestMethod.POST)
	public PageResult<DTO> getDataPage(@RequestBody PageQueryUtil pageUtil) {
		return mapper.toDataPage(baseService.getDataPage(pageUtil));
	}

	@RequestMapping(value="/add", method = RequestMethod.POST)
	public MessageResponse create(@Valid @RequestBody DTO dto) {
		 baseService.save(mapper.toEntity(dto));
		 return new MessageResponse("Item has been saved successfully");
	}


	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public MessageResponse update(@Valid @PathVariable(value = "id") Long id, @Valid @RequestBody DTO dto) {
		 baseService.save(mapper.toEntity(dto));
		 return new MessageResponse("Item has been updated successfully");
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public MessageResponse delete(@PathVariable(value = "id") Long id) {
		baseService.deleteById(id);
		return new MessageResponse("Item has been deleted successfully");
	}

	@RequestMapping(value = "/softdelete/{id}", method = RequestMethod.PUT)
	public MessageResponse softDelete(@PathVariable(value = "id") Long id) {
		T entity = baseService.findById(id);
		if (entity != null) {
			entity.setDeleted(true);
			baseService.save(entity);
			return new MessageResponse("Item has been soft deleted successfully");
		} else {
			return new MessageResponse("Item not found");
		}
	}
}
