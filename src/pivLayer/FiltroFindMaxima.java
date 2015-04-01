package pivLayer;

import java.util.ArrayList;
import java.util.List;

import wapper.ImageJWrapper;
import wapper.WrapperException;

public class FiltroFindMaxima extends FiltroPreProcesamiento {

	private int noiseTolerance;
	private int outputType;
	private boolean excludeEdges;

	public FiltroFindMaxima(int noiseTolerance, int outputType, boolean excludeEdges) {
		this.cantElementosProcesables = 1;
		this.noiseTolerance = noiseTolerance;
		this.outputType = outputType;
		this.excludeEdges = excludeEdges;
	}

	@Override
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws WrapperException {
		List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
		elementosFiltrados.add(ImageJWrapper.findMaxima((Imagen) input.get(0), noiseTolerance, outputType, excludeEdges));
		return elementosFiltrados;
	}

}
