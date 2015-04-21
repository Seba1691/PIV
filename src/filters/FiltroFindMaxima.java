package filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pivLayer.ElementoProcesable;
import pivLayer.FiltroPreProcesamiento;
import pivLayer.Imagen;

import wapper.ImageJWrapper;
import wapper.WrapperException;

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
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws WrapperException {
		List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
		elementosFiltrados.add(ImageJWrapper.findMaxima((Imagen) input.get(0), noiseTolerance, outputType, excludeEdges));
		return elementosFiltrados;
	}

	@Override
	public HashMap<String, String> getParametros() {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(NOISE_TOLERANCE, String.valueOf(noiseTolerance));
		parameters.put(OUTPUT_TYPE, String.valueOf(outputType));
		parameters.put(EXCLUDE_EDGES, String.valueOf(excludeEdges));
		return parameters;
	}

	@Override
	public void setParametros(HashMap<String, String> parameters) {
		for (String key : parameters.keySet())
			switch (key) {
			case NOISE_TOLERANCE:
				this.noiseTolerance = Integer.valueOf(parameters.get(key));
				break;
			case OUTPUT_TYPE:
				this.outputType = Integer.valueOf(parameters.get(key));
				break;
			case EXCLUDE_EDGES:
				this.excludeEdges = Boolean.valueOf(parameters.get(key));
				break;
			}
	}

}
