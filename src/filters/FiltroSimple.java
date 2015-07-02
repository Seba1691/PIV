package filters;

import java.util.HashMap;
import java.util.List;

import pivLayer.ElementoProcesable;
import pivLayer.FilterException;
import pivLayer.FiltroPreProcesamiento;

public class FiltroSimple extends FiltroPreProcesamiento {

	public FiltroSimple() {
		this.cantElementosProcesables = 1;
		this.cantElementosGenerados = 1;
	}

	@Override
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) {
		return input;
	}

	@Override
	public void validateParametros(HashMap<String, Object> parameters) throws FilterException {
		// Nothing
	}

}
