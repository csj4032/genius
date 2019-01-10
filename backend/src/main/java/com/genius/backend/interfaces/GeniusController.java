package com.genius.backend.interfaces;

import lombok.extern.slf4j.Slf4j;
import net.imglib2.FinalInterval;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import net.imglib2.util.Intervals;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@Controller
public class GeniusController {

	@GetMapping("/greeting")
	public String greeting() {
		return "greeting";
	}

	@GetMapping("/picture/{width}/{height}/{extension:jpg|png|gif}")
	public void picture(HttpServletResponse response,
						@PathVariable(name = "width") Integer width,
						@PathVariable(name = "height") Integer height,
						@PathVariable(name = "extension", required = false) String extension) throws IOException {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
		graphics.setColor(Color.GRAY);
		graphics.fillRect(0, 0, width, height);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		ImageIO.write(bufferedImage, extension, response.getOutputStream());
		response.flushBuffer();
	}
}