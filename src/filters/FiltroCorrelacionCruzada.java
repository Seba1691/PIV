package filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import pivLayer.ElementoProcesable;
import pivLayer.FilterException;
import pivLayer.FiltroPIV;
import pivLayer.Imagen;
import wapper.JPIVWrapper;
import wapper.WrapperException;

public class FiltroCorrelacionCruzada extends FiltroPIV {

	private static final String MULTI_PASS = "multiPass";
	private static final String INTER_WINDOWS_WIDTH = "interWindowsWidth";
	private static final String INTER_WINDOWS_HEIGHT = "interWindowsHeight";
	private static final String SEARCH_DOMAIN_WIDTH = "searchDomainWidth";
	private static final String SEARCH_DOMAIN_HEIGHT = "searchDomainHeigth";
	private static final String HORIZONTAL_VECTOR_SPACING = "horizontalVertorSpacing";
	private static final String VERTICAL_VECTOR_SPACING = "verticalVertorSpacing";
	private static final String ROI = "roi";
	private static final String ROI_MATRIX = "roiMatrix";
	private static final String HORIZONTAL_PRESHIFT = "horizontalPreShift";
	private static final String VERTICAL_PRESHIFT = "verticalPreShift";
	private static final String NORMALIZED_MEDIAN_TEST = "normalizedMedianTest";
	private static final String REPLACE_INVALID_VECTOR_BY_MEDIAN = "replaceInvalidVectorByMedian";
	private static final String MEDIAN_FILTER = "medianFilter";
	private static final String SMOOTHING = "smoothing";
	private static final String DEFORM_INTERROGATION_WINDOWS = "deformInterrogationWindows";
	private static final String EXPORT_CORRELATION_FUNCTIONS = "exportCorrelationFunctions";
	private static final String EXPORT_CORRELATION_VECTORS = "exportCorrelationVector";
	private static final String EXPORT_CORRELATION_PASS = "exportCorrelationPass";
	private static final String ONLY_SUM_OF_CORRELATION = "onlySumOfCorrelation";

	private int multiPass;
	private Integer[] interWindowsWidth;
	private Integer[] interWindowsHeight;
	private Integer[] searchDomainWidth;
	private Integer[] searchDomainHeigth;
	private Integer[] horizontalVertorSpacing;
	private Integer[] verticalVertorSpacing;

	private boolean roi;
	private Integer[][] roiMatrix;

	private int horizontalPreShift;
	private int verticalPreShift;

	private boolean normalizedMedianTest;
	private boolean replaceInvalidVectorByMedian;
	private boolean medianFilter;
	private boolean smoothing;

	private boolean deformInterrogationWindows;

	private boolean exportCorrelationFunctions;
	private int exportCorrelationVector;
	private int exportCorrelationPass;

	private boolean onlySumOfCorrelation;

	public FiltroCorrelacionCruzada(int multiPass, Integer[] interWindowsWidth, Integer[] interWindowsHeight, Integer[] searchDomainWidth, Integer[] searchDomainHeigth, Integer[] horizontalVertorSpacing, Integer[] verticalVertorSpacing, boolean roi, Integer[][] roiMatrix, int horizontalPreShift,
			int verticalPreShift, boolean normalizedMedianTest, boolean replaceInvalidVectorByMedian, boolean medianFilter, boolean smoothing, boolean deformInterrogationWindows, boolean exportCorrelationFunctions, int exportCorrelationVector, int exportCorrelationPass, boolean onlySumOfCorrelation) {
		this.multiPass = multiPass;
		this.interWindowsWidth = interWindowsWidth;
		this.interWindowsHeight = interWindowsHeight;
		this.searchDomainWidth = searchDomainWidth;
		this.searchDomainHeigth = searchDomainHeigth;
		this.horizontalVertorSpacing = horizontalVertorSpacing;
		this.verticalVertorSpacing = verticalVertorSpacing;
		this.roi = roi;
		this.roiMatrix = roiMatrix;
		this.horizontalPreShift = horizontalPreShift;
		this.verticalPreShift = verticalPreShift;
		this.normalizedMedianTest = normalizedMedianTest;
		this.replaceInvalidVectorByMedian = replaceInvalidVectorByMedian;
		this.medianFilter = medianFilter;
		this.smoothing = smoothing;
		this.deformInterrogationWindows = deformInterrogationWindows;
		this.exportCorrelationFunctions = exportCorrelationFunctions;
		this.exportCorrelationVector = exportCorrelationVector;
		this.exportCorrelationPass = exportCorrelationPass;
		this.onlySumOfCorrelation = onlySumOfCorrelation;
	}

	public FiltroCorrelacionCruzada() {
		this(3, new Integer[] { 64, 32, 32 }, new Integer[] { 64, 32, 32 }, new Integer[] { 32, 8, 8 }, new Integer[] { 32, 8, 8 }, //
				new Integer[] { 32, 16, 12 }, new Integer[] { 32, 16, 12 }, false, new Integer[][] { { 0, 512 }, { 0, 512 } }, 0, 0, //
				true, true, false, true, false, false, 0, 0, false);
		this.cantElementosProcesables = 2;
	}

	@Override
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws FilterException {
		try {
			List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
			elementosFiltrados.add(JPIVWrapper.doPiv((Imagen) input.get(0), (Imagen) input.get(1), //
					multiPass, interWindowsWidth, interWindowsHeight, searchDomainWidth, searchDomainHeigth, //
					horizontalVertorSpacing, verticalVertorSpacing, roi, roiMatrix, horizontalPreShift, verticalPreShift, //
					normalizedMedianTest, replaceInvalidVectorByMedian, medianFilter, smoothing, deformInterrogationWindows, //
					exportCorrelationFunctions, exportCorrelationVector, exportCorrelationPass, onlySumOfCorrelation));
			return elementosFiltrados;
		} catch (WrapperException e) {
			throw new FilterException("Error al ejecutar el filtro de correlacion cruzada", e);
		}
	}

	@Override
	public HashMap<String, Object> getParametros() {
		HashMap<String, Object> parameters = new LinkedHashMap<String, Object>();
		parameters.put(MULTI_PASS, multiPass);
		parameters.put(INTER_WINDOWS_WIDTH, interWindowsWidth);
		parameters.put(INTER_WINDOWS_HEIGHT, interWindowsHeight);
		parameters.put(SEARCH_DOMAIN_WIDTH, searchDomainWidth);
		parameters.put(SEARCH_DOMAIN_HEIGHT, searchDomainHeigth);
		parameters.put(HORIZONTAL_VECTOR_SPACING, horizontalVertorSpacing);
		parameters.put(VERTICAL_VECTOR_SPACING, verticalVertorSpacing);
		parameters.put(ROI, roi);
		parameters.put(ROI_MATRIX, roiMatrix);
		parameters.put(HORIZONTAL_PRESHIFT, horizontalPreShift);
		parameters.put(VERTICAL_PRESHIFT, verticalPreShift);
		parameters.put(NORMALIZED_MEDIAN_TEST, normalizedMedianTest);
		parameters.put(REPLACE_INVALID_VECTOR_BY_MEDIAN, replaceInvalidVectorByMedian);
		parameters.put(MEDIAN_FILTER, medianFilter);
		parameters.put(SMOOTHING, smoothing);
		parameters.put(DEFORM_INTERROGATION_WINDOWS, deformInterrogationWindows);
		parameters.put(EXPORT_CORRELATION_FUNCTIONS, exportCorrelationFunctions);
		parameters.put(EXPORT_CORRELATION_PASS, exportCorrelationPass);
		parameters.put(EXPORT_CORRELATION_VECTORS, exportCorrelationVector);
		parameters.put(ONLY_SUM_OF_CORRELATION, onlySumOfCorrelation);
		return parameters;
	}

	public void saveParametros(HashMap<String, Object> parameters) {
		for (String key : parameters.keySet())
			switch (key) {
			case MULTI_PASS:
				this.multiPass = (int) parameters.get(MULTI_PASS);
				break;
			case INTER_WINDOWS_WIDTH:
				this.interWindowsWidth = (Integer[]) parameters.get(INTER_WINDOWS_WIDTH);
				break;
			case INTER_WINDOWS_HEIGHT:
				this.interWindowsHeight = (Integer[]) parameters.get(INTER_WINDOWS_HEIGHT);
				break;
			case SEARCH_DOMAIN_WIDTH:
				this.searchDomainWidth = (Integer[]) parameters.get(SEARCH_DOMAIN_WIDTH);
				break;
			case SEARCH_DOMAIN_HEIGHT:
				this.searchDomainHeigth = (Integer[]) parameters.get(SEARCH_DOMAIN_HEIGHT);
				break;
			case HORIZONTAL_VECTOR_SPACING:
				this.horizontalVertorSpacing = (Integer[]) parameters.get(HORIZONTAL_VECTOR_SPACING);
				break;
			case VERTICAL_VECTOR_SPACING:
				this.verticalVertorSpacing = (Integer[]) parameters.get(VERTICAL_VECTOR_SPACING);
				break;
			case ROI:
				this.roi = (boolean) parameters.get(ROI);
				break;
			case ROI_MATRIX:
				this.roiMatrix = (Integer[][]) parameters.get(ROI_MATRIX);
				break;
			case HORIZONTAL_PRESHIFT:
				this.horizontalPreShift = (int) parameters.get(HORIZONTAL_PRESHIFT);
				break;
			case VERTICAL_PRESHIFT:
				this.verticalPreShift = (int) parameters.get(VERTICAL_PRESHIFT);
				break;
			case NORMALIZED_MEDIAN_TEST:
				this.normalizedMedianTest = (boolean) parameters.get(NORMALIZED_MEDIAN_TEST);
				break;
			case REPLACE_INVALID_VECTOR_BY_MEDIAN:
				this.replaceInvalidVectorByMedian = (boolean) parameters.get(REPLACE_INVALID_VECTOR_BY_MEDIAN);
				break;
			case MEDIAN_FILTER:
				this.medianFilter = (boolean) parameters.get(MEDIAN_FILTER);
				break;
			case SMOOTHING:
				this.smoothing = (boolean) parameters.get(SMOOTHING);
				break;
			case DEFORM_INTERROGATION_WINDOWS:
				this.deformInterrogationWindows = (boolean) parameters.get(DEFORM_INTERROGATION_WINDOWS);
				break;
			case EXPORT_CORRELATION_FUNCTIONS:
				this.exportCorrelationFunctions = (boolean) parameters.get(EXPORT_CORRELATION_FUNCTIONS);
				break;
			case EXPORT_CORRELATION_PASS:
				this.exportCorrelationPass = (int) parameters.get(EXPORT_CORRELATION_PASS);
				break;
			case EXPORT_CORRELATION_VECTORS:
				this.exportCorrelationVector = (int) parameters.get(EXPORT_CORRELATION_VECTORS);
				break;
			case ONLY_SUM_OF_CORRELATION:
				this.onlySumOfCorrelation = (boolean) parameters.get(ONLY_SUM_OF_CORRELATION);
				break;
			}
	}

	@Override
	public void validateParametros(HashMap<String, Object> parameters) throws FilterException {
		int passCount = (int) parameters.get(MULTI_PASS);
		if (((Integer[]) parameters.get(INTER_WINDOWS_WIDTH)).length != passCount)
			throw new FilterException("El tamaño del arreglo del parametro " + INTER_WINDOWS_WIDTH + " debe ser igual al numero de pasadas (" + passCount + ")");
		if (((Integer[]) parameters.get(INTER_WINDOWS_HEIGHT)).length != passCount)
			throw new FilterException("El tamaño del arreglo del parametro " + INTER_WINDOWS_HEIGHT + " debe ser igual al numero de pasadas (" + passCount + ")");
		if (((Integer[]) parameters.get(SEARCH_DOMAIN_WIDTH)).length != passCount)
			throw new FilterException("El tamaño del arreglo del parametro " + SEARCH_DOMAIN_WIDTH + " debe ser igual al numero de pasadas (" + passCount + ")");
		if (((Integer[]) parameters.get(SEARCH_DOMAIN_HEIGHT)).length != passCount)
			throw new FilterException("El tamaño del arreglo del parametro " + SEARCH_DOMAIN_HEIGHT + " debe ser igual al numero de pasadas (" + passCount + ")");
		if (((Integer[]) parameters.get(HORIZONTAL_VECTOR_SPACING)).length != passCount)
			throw new FilterException("El tamaño del arreglo del parametro " + HORIZONTAL_VECTOR_SPACING + " debe ser igual al numero de pasadas (" + passCount + ")");
		if (((Integer[]) parameters.get(VERTICAL_VECTOR_SPACING)).length != passCount)
			throw new FilterException("El tamaño del arreglo del parametro " + VERTICAL_VECTOR_SPACING + " debe ser igual al numero de pasadas (" + passCount + ")");
		if (((Integer[][]) parameters.get(ROI_MATRIX)).length != 2)
			throw new FilterException("El parametro " + ROI_MATRIX + " debe ser una matriz de 2x2");
		if (((Integer[][]) parameters.get(ROI_MATRIX))[0].length != 2)
			throw new FilterException("El parametro " + ROI_MATRIX + " debe ser una matriz de 2x2");
		if (((Integer[][]) parameters.get(ROI_MATRIX))[1].length != 2)
			throw new FilterException("El parametro " + ROI_MATRIX + " debe ser una matriz de 2x2");

	}

}
