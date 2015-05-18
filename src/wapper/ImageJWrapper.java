package wapper;

import fiji.threshold.Auto_Local_Threshold;
import ij.ImagePlus;
import ij.plugin.ImageCalculator;
import ij.plugin.filter.MaximumFinder;
import ij.process.ImageProcessor;
import pivLayer.Imagen;

public class ImageJWrapper {

	public static Imagen substractFilter(Imagen imagen1, Imagen imagen2) {
		ImagePlus imp1 = new ImagePlus("", imagen1.getImage());
		ImagePlus imp2 = new ImagePlus("", imagen2.getImage());
		ImageCalculator ic = new ImageCalculator();
		ImagePlus imp3 = ic.run("Subtract create", imp1, imp2);
		return new Imagen(imp3.getBufferedImage());
	}

	public static Imagen autoLocalThreshold(Imagen imagen1, String metodo, int radio, double parametro1, double parametro2, boolean fondoBlanco) {
		ImagePlus imp1 = new ImagePlus("", imagen1.getImage());
		Auto_Local_Threshold a = new Auto_Local_Threshold();
		a.exec(imp1, metodo, radio, parametro1, parametro2, fondoBlanco);
		return new Imagen(imp1.getBufferedImage());
	}

	public static Imagen findMaxima(Imagen imagen1, int noiseTolerance, int outputType, boolean excludeEdges) {
		ImagePlus imp1 = new ImagePlus("", imagen1.getImage());
		ImageProcessor ip = imp1.getProcessor();
		MaximumFinder mf = new MaximumFinder();
		ImagePlus imp2 = new ImagePlus("", mf.findMaxima(ip, noiseTolerance, outputType, excludeEdges));
		return new Imagen(imp2.getBufferedImage());
	}

}
