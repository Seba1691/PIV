package pivLayer;

import java.util.List;

public class FiltroSimple extends FiltroPreProcesamiento {

	public FiltroSimple(String configuracion) {
		super(configuracion);
		this.cantElementosProcesables = 1;
	}

	@Override
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) {
		return input;
	}

}
