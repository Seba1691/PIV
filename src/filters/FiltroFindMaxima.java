package filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pivLayer.ElementoProcesable;
import pivLayer.FilterException;
import pivLayer.FiltroPreProcesamiento;
import pivLayer.Imagen;
import wapper.ImageJWrapper;

public class FiltroFindMaxima extends FiltroPreProcesamiento {

	// Parametros

	private static final String NOISE_TOLERANCE = "NoiseTolerance";
	private static final String OUTPUT_TYPE = "OutputType";
	private static final String EXCLUDE_EDGES = "ExcludeEdges";

	private int noiseTolerance;
	private int outputType;
	private boolean excludeEdges;

	public FiltroFindMaxima(int noiseTolerance, int outputType, boolean excludeEdges) {
		this.cantElementosProcesables = 1;
		this.noiseTolerance = noiseTolerance;
		this.outputType = outputType;
		this.excludeEdges = excludeEdges;
	}

	public FiltroFindMaxima() {
		this(5, 1, false);
	}

	@Override
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws FilterException {
		List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
		elementosFiltrados.add(ImageJWrapper.findMaxima((Imagen) input.get(0), noiseTolerance, outputType, excludeEdges));
		return elementosFiltrados;
	}

	@Override
	public HashMap<String, Object> getParametros() {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(NOISE_TOLERANCE, noiseTolerance);
		parameters.put(OUTPUT_TYPE, outputType);
		parameters.put(EXCLUDE_EDGES, excludeEdges);
		return parameters;
	}

	@Override
	public void saveParametros(HashMap<String, Object> parameters) {
		for (String key : parameters.keySet())
			switch (key) {
			case NOISE_TOLERANCE:
				this.noiseTolerance = (Integer) parameters.get(key);
				break;
			case OUTPUT_TYPE:
				this.outputType = (Integer) parameters.get(key);
				break;
			case EXCLUDE_EDGES:
				this.excludeEdges = (Boolean) parameters.get(key);
				break;
			}
	}

	@Override
	public void validateParametros(HashMap<String, Object> parameters) throws FilterException {
		// TODO Auto-generated method stub
	}

}
