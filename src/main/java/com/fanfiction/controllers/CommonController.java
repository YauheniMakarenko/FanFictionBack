package com.fanfiction.controllers;

import com.fanfiction.models.Composition;
import com.fanfiction.models.Genre;
import com.fanfiction.payload.request.EditNameRequest;
import com.fanfiction.service.UserService;
import com.fanfiction.service.CompositionService;
import com.fanfiction.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class CommonController {

	@Autowired
	private CompositionService compositionService;

	@Autowired
	private UserService userService;

	@Autowired
	private SearchService searchService;

	@GetMapping("/allGenres")
	public List<String> getGenre() {
		return compositionService.allGenre().stream().map(Genre::getGenrename).collect(Collectors.toList());
	}

	@PostMapping("/edituser")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> updateUser(@RequestBody EditNameRequest editNameRequest){
		return userService.editUserName(editNameRequest);
	}

	@GetMapping("search/{searchRequest}")
	public Set<Composition> search(@PathVariable String searchRequest) throws InterruptedException {
		return searchService.initializeHibernateSearch(searchRequest);
	}
}
