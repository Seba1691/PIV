package wapper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Locale;

import javax.imageio.ImageIO;

import jpiv2.DisplayVecFrame;
import jpiv2.FileHandling;
import jpiv2.JPiv;
import jpiv2.PivData;
import main.MainPruebaAdapter;
import pivLayer.Imagen;
import pivLayer.MapaVectores;

public class JPIVWrapper {

	/**
	 * Procesador PIV
	 */
	public static MapaVectores doPiv(Imagen image1, Imagen image2) throws WrapperException {
		try {
			String path = "C:/Users/Seba/Desktop/PIV/Salidas/";
			String input1 = path + "imageSaved1.png";
			String input2 = path + "imageSaved2.png";

			ImageIO.write(image1.getImage(), "png", new File(input1));
			ImageIO.write(image2.getImage(), "png", new File(input2));

			JPiv jpiv = new JPiv();

			jpiv.getSettings().jpivLibPath = System.getProperty("user.dir") + "/resources/jpivlib";
			PrintStream defaultPrintStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(FileDescriptor.out), 128), true);
			System.setOut(defaultPrintStream);
			System.setErr(defaultPrintStream);

			int rmd = (int) (Math.random() * 100);
			System.out.println(rmd);
			String salida = path + "salida" + rmd;
			jpiv.getSettings().pivDefaultDestFileName = salida;
			jpiv.getSettings().pivUseDefaultDestFileName = true;
			jpiv.getListFrame().appendElement(input1);
			jpiv.getListFrame().appendElement(input2);
			jpiv.getListFrame().selectElements(0, 1);
			jpiv2.PivEvaluation evaluation = new jpiv2.PivEvaluation(jpiv);
			evaluation.run();
			return new MapaVectores(fileToMatrix(salida + "1.jvc"));

		} catch (IOException e) {
			throw new WrapperException(e);
		}
	}

	/**
	 * Filtros PostProcesamiento
	 */
	public static MapaVectores normalizedMedianTestFilter(MapaVectores mapaVectores, double nivelRuido, double umbral) {
		PivData pivData = new PivData(mapaVectores.getMapaVectores());
		pivData.normalizedMedianTest(nivelRuido, umbral);
		return new MapaVectores(pivData.getPivData());
	}

	public static MapaVectores replaceByMedianFilter(MapaVectores mapaVectores) {
		PivData pivData = new PivData(mapaVectores.getMapaVectores());
		pivData.replaceByMedian(false, false);
		return new MapaVectores(pivData.getPivData());
	}

	public static MapaVectores medianFilter(MapaVectores mapaVectores, boolean all) {
		PivData pivData = new PivData(mapaVectores.getMapaVectores());
		pivData.replaceByMedian(true, all);
		return new MapaVectores(pivData.getPivData());
	}

	public static MapaVectores invalidateIsolatedVectors(MapaVectores mapaVectores, int vecindad) {
		PivData pivData = new PivData(mapaVectores.getMapaVectores());
		pivData.invalidateIsolatedVectors(vecindad);
		return new MapaVectores(pivData.getPivData());
	}

	public static MapaVectores removeInvalidVectors(MapaVectores mapaVectores) {
		PivData pivData = new PivData(mapaVectores.getMapaVectores());
		pivData.removeInvalidVectors();
		return new MapaVectores(pivData.getPivData());
	}

	public static MapaVectores smooth(MapaVectores mapaVectores, boolean all) {
		PivData pivData = new PivData(mapaVectores.getMapaVectores());
		pivData.smooth(all);
		return new MapaVectores(pivData.getPivData());
	}

	private static double[][] fileToMatrix(String filePath) throws FileNotFoundException, IOException {
		return FileHandling.readArrayFromFile(filePath);
	}

	public static void visualizar(MapaVectores mapaVectores) throws IOException {
		MainPruebaAdapter.matrixToFile(mapaVectores.getMapaVectores(), "C:/Users/Seba/Desktop/PIV/Salidas/salidaFiltrada");
		new DisplayVecFrame(new JPiv(), "C:/Users/Seba/Desktop/PIV/Salidas/salidaFiltrada.jvc");
	}

	/**
	 * Traduccion de la informacion
	 */
	@SuppressWarnings("unused")
	private static void matrixToFile(double[][] data, String pathname) throws IOException {
		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.US);
		df.applyPattern("+0.0000E00;-0.0000E00");
		FileHandling.writeArrayToFile(data, pathname + ".jvc", df, "");
	}

}
