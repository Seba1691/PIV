package wrapper;

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

	private static final String JPIV_RESOURCES_PATH = System.getProperty("user.dir") + "/resources/jpivlib";

	public static MapaVectores doPiv(Imagen image1, Imagen image2, int multiPass, Integer[] interWindowsWidth, Integer[] interWindowsHeight, Integer[] searchDomainWidth, Integer[] searchDomainHeigth, Integer[] horizontalVertorSpacing, Integer[] verticalVertorSpacing, //
			boolean roi, Integer[][] roiMatrix, int horizontalPreShift, int verticalPreShift, boolean normalizedMedianTest, boolean replaceInvalidVectorByMedian, boolean medianFilter, boolean smoothing, //
			boolean deformInterrogationWindows, boolean exportCorrelationFunctions, int exportCorrelationVector, int exportCorrelationPass, boolean onlySumOfCorrelation) throws WrapperException {
		try {
			String path = JPIV_RESOURCES_PATH + "/tmp/";
			int rnd = (int) (Math.random() * 1000000);
			String input1 = path + "in" + rnd + ".png";
			String input2 = path + "in" + (int) (rnd + 1) + ".png";

			File fileInput1 = new File(input1);
			File fileInput2 = new File(input2);
			ImageIO.write(image1.getImage(), "png", fileInput1);
			ImageIO.write(image2.getImage(), "png", fileInput2);

			JPiv jpiv = new JPiv();

			// Setting
			jpiv2.Settings settings = jpiv.getSettings();
			settings.pivSequence = jpiv2.Settings.PIV_CONSECUTIVE;
			
			settings.pivMultiPass = multiPass;
			settings.pivWindow = new int[][] { integerToIntArray(interWindowsWidth), integerToIntArray(interWindowsHeight), integerToIntArray(searchDomainWidth), integerToIntArray(searchDomainHeigth), integerToIntArray(horizontalVertorSpacing), integerToIntArray(verticalVertorSpacing) };

			settings.pivROI = roi;
			settings.pivROIP1x = roiMatrix[0][0];
			settings.pivROIP2x = roiMatrix[0][1];
			settings.pivROIP1y = roiMatrix[1][0];
			settings.pivROIP2x = roiMatrix[1][1];

			settings.pivHorPreShift = horizontalPreShift;
			settings.pivVerPreShift = verticalPreShift;

			settings.pivNormMedianTest = normalizedMedianTest;
			settings.pivReplace = replaceInvalidVectorByMedian;
			settings.pivMedian = medianFilter;
			settings.pivSmoothing = smoothing;

			settings.pivShearIntWindows = deformInterrogationWindows;

			settings.exportCorrFunction = exportCorrelationFunctions;
			settings.exportCorrFunctionPass = exportCorrelationPass;
			settings.exportCorrFunctionNum = exportCorrelationVector;
			settings.exportCorrFunctionOnlySumOfCorr = onlySumOfCorrelation;

			jpiv.getSettings().jpivLibPath = JPIV_RESOURCES_PATH;
			PrintStream defaultPrintStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(FileDescriptor.out), 128), true);
			System.setOut(defaultPrintStream);
			System.setErr(defaultPrintStream);

			String salida = path + "out" + rnd;
			jpiv.getSettings().pivDefaultDestFileName = salida;
			jpiv.getSettings().pivUseDefaultDestFileName = true;
			jpiv.getListFrame().appendElement(input1);
			jpiv.getListFrame().appendElement(input2);
			jpiv.getListFrame().selectElements(0, 1);
			jpiv2.PivEvaluation evaluation = new jpiv2.PivEvaluation(jpiv);
			evaluation.run();

			MapaVectores result = new MapaVectores(fileToMatrix(salida + "1.jvc"));

			jpiv = null;
			System.gc();

			new File(salida + "1.jvc").delete();
			System.out.println(fileInput1.delete());
			System.out.println(fileInput2.delete());

			return result;

		} catch (IOException e) {
			throw new WrapperException("Ocurrio un error ejecutando invocando a la aplicacion JPIV", e);
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
		String salida = JPIV_RESOURCES_PATH + "/tmp/out" + (int) (Math.random() * 1000000);
		MainPruebaAdapter.matrixToFile(mapaVectores.getMapaVectores(), salida);
		new DisplayVecFrame(new JPiv(), salida + ".jvc");
		new File(salida + ".jvc").delete();
	}

	private static int[] integerToIntArray(Integer[] array) {
		int[] result = new int[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
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
