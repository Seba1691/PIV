package pivLayer;

import java.util.ArrayList;
import java.util.List;

import wapper.JPIVWrapper;
import wapper.WrapperException;

public class FiltroCorrelacionCruzada extends FiltroPIV {

	public FiltroCorrelacionCruzada() {
		this.cantElementosProcesables = 2;
	}

	@Override
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws WrapperException {
		List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
		elementosFiltrados.add(JPIVWrapper.doPiv((Imagen) input.get(0), (Imagen) input.get(1)));
		return elementosFiltrados;
	}

}
