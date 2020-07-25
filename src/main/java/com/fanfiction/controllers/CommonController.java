package com.fanfiction.controllers;

import com.fanfiction.DTO.GenreNewCompositionDTO;
import com.fanfiction.models.Composition;
import com.fanfiction.DTO.UserDTO;
import com.fanfiction.service.ExportPdfService;
import com.fanfiction.service.UserService;
import com.fanfiction.service.CompositionService;
import com.fanfiction.service.SearchService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/fanfic")
public class CommonController {

	@Autowired
	private CompositionService compositionService;

	@Autowired
	private UserService userService;

	@Autowired
	private SearchService searchService;

	@Autowired
	private ExportPdfService exportPdfService;

	@GetMapping("/allGenres")
	public List<GenreNewCompositionDTO> getGenre() {
		return compositionService.allGenre();
	}

	@PostMapping("/edituser")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> updateUser(@RequestBody UserDTO USERDTO){
		return userService.editUserName(USERDTO);
	}

	@GetMapping("search/{searchRequest}")
	public Set<Composition> search(@PathVariable String searchRequest) throws InterruptedException {
		return searchService.initializeHibernateSearch(searchRequest);
	}

	@GetMapping("exportToPdf/{compositionId}")
	public void exportPdf(@PathVariable Long compositionId, HttpServletResponse response) throws IOException, DocumentException{

/*		response.setContentType("application/pdf");

		response.setHeader("Content-disposition","inline; filename='composition.pdf'");*/
		exportPdfService.exportPdf(compositionId, response);

	}
}
