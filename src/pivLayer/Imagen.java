package pivLayer;

import java.awt.image.BufferedImage;

public class Imagen extends ElementoProcesable {

	private BufferedImage image;

	public Imagen(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
