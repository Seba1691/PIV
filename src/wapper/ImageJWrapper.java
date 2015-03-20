package wapper;

import ij.ImagePlus;
import ij.plugin.ImageCalculator;
import pivLayer.Imagen;

public class ImageJWrapper {

	public static Imagen substractFilter(Imagen imagen1, Imagen imagen2) {
		ImagePlus imp1 = new ImagePlus("", imagen1.getImage());
		ImagePlus imp2 = new ImagePlus("", imagen2.getImage());
		ImageCalculator ic = new ImageCalculator();
		ImagePlus imp3 = ic.run("Subtract create", imp1, imp2);
		return new Imagen(imp3.getBufferedImage());
	}
}
