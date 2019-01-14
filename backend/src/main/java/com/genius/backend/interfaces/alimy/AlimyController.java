package com.genius.backend.interfaces.alimy;

import com.genius.backend.application.AlimyService;
import com.genius.backend.domain.model.alimy.Alimy;
import com.genius.backend.domain.model.alimy.AlimyDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Slf4j
@RestController
public class AlimyController {

	@Autowired
	private AlimyService alimyService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/alimy")
	public Page<AlimyDto.Response> alimy(AlimyDto.Search search, Pageable pageable) {
		return alimyService.searchWithPage(search, pageable);
	}

	@GetMapping("/alimy/{alimyId}")
	public ResponseEntity<AlimyDto.Response> alimyForId(@PathVariable("alimyId") Long alimyId) {
		return new ResponseEntity<>(alimyService.findById(alimyId), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value = "/alimy/save")
	public ResponseEntity<AlimyDto.Response> save(@Valid @RequestBody AlimyDto.RequestForSave request) {
		return new ResponseEntity<>(alimyService.save(request), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/alimy/update")
	public ResponseEntity<AlimyDto.Response> update(@Valid @RequestBody AlimyDto.RequestForUpdate request) {
		return new ResponseEntity<>(alimyService.update(request), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/alimy/status")
	public ResponseEntity<String> status(@Valid @RequestBody AlimyDto.RequestForStatus request) {
		var alimy = alimyService.update(request);
		return new ResponseEntity<>(alimy.getStatus().getText(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/alimy/delete")
	public ResponseEntity<Integer> delete(@RequestBody AlimyDto.RequestForDelete ids) {
		var count = alimyService.delete(ids.getIds());
		return new ResponseEntity<>(count, HttpStatus.OK);
	}
}