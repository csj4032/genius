package com.genius.backend.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.activation.FileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Controller
public class GeniusController {

	@Autowired
	private ServletContext servletContext;

	@GetMapping("/greeting")
	public String greeting() {
		return "greeting";
	}

	@GetMapping("/picture/{width}/{height}/{extension:jpg|png|gif}")
	public ResponseEntity<byte[]> picture(HttpServletResponse response, @PathVariable(name = "width") Integer width, @PathVariable(name = "width") Integer height, @PathVariable(name = "extension", required = false) String extension) throws IOException {
		File images = new File("/resources/static/images/20181231_121003_029.jpg");
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=" + images.getName())
				.contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(images)))
				.body(Files.readAllBytes(images.toPath()));
	}
}