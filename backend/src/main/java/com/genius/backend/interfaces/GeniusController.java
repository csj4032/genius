package com.genius.backend.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/picture/{width}/{height}/{color}/{extension:jpg|png|gif}")
	public void picture(HttpServletResponse response,
						@PathVariable(name = "width") Integer width,
						@PathVariable(name = "height") Integer height,
						@PathVariable(name = "color") String color,
						@PathVariable(name = "extension", required = false) String extension,
						@RequestParam(name = "text", required = false, defaultValue = "") String text) throws IOException {
		response.setContentType("image/" + extension);
		var bufferedImage = getBufferedImage(width, height, color, text);
		ImageIO.write(bufferedImage, extension, response.getOutputStream());
		response.flushBuffer();
	}

	@NotNull
	private BufferedImage getBufferedImage(Integer width, Integer height, String color, String text) {
		var intValue = Integer.parseInt(color, 16);
		var bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		var graphics2 = (Graphics2D) bufferedImage.getGraphics();
		graphics2.setColor(new Color(255, 255, 255, 0));
		graphics2.fillRect(0, 0, width, height);
		if (!text.isEmpty()) {
			graphics2.setPaint(new Color((intValue >> 16) & 0xFF, (intValue >> 8) & 0xFF, intValue & 0xFF));
			graphics2.setFont(new Font("DejaVu Sans", Font.BOLD, 100));
			graphics2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			var fm = graphics2.getFontMetrics();
			graphics2.drawString(text, bufferedImage.getWidth() - fm.stringWidth(text), fm.getHeight());
			graphics2.dispose();
		}
		return bufferedImage;
	}
}