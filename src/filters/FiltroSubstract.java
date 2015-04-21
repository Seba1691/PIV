package filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pivLayer.ElementoProcesable;
import pivLayer.FiltroPreProcesamiento;
import pivLayer.Imagen;

import wapper.ImageJWrapper;
import wapper.WrapperException;

public class FiltroSubstract extends FiltroPreProcesamiento {

	public FiltroSubstract() {
		this.cantElementosProcesables = 2;
	}

	@Override
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws WrapperException {
		List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
		elementosFiltrados.add(ImageJWrapper.substractFilter((Imagen) input.get(0), (Imagen) input.get(1)));
		return elementosFiltrados;
	}

	@Override
	public HashMap<String, String> getParametros() {
		return null;
	}

	@Override
	public void setParametros(HashMap<String, String> parameters) {
		// Nothing
	}

}
