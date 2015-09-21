package filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pivLayer.ElementoProcesable;
import pivLayer.FilterException;
import pivLayer.FiltroPIV;
import pivLayer.Imagen;
import wrapper.MatLabWrapper;

public class FiltroCorrelacionMatlab extends FiltroPIV {

	public FiltroCorrelacionMatlab() {
		super(2, 1);
	}

	@Override
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws FilterException {
		List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
		elementosFiltrados.add(MatLabWrapper.CorrealcionCruzadaSimple(((Imagen)input.get(0)), ((Imagen)input.get(1)), 32.0, 0.00012, 0.5));
		return elementosFiltrados;
	}

	@Override
	protected void validateParametros(HashMap<String, Object> parameters) throws FilterException {

	}

}
