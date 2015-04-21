package filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pivLayer.ElementoProcesable;
import pivLayer.FiltroPostProcesamiento;
import pivLayer.MapaVectores;

import wapper.JPIVWrapper;

public class FiltroRemplazoVectoresInvalidosPorMediana extends FiltroPostProcesamiento {

	public FiltroRemplazoVectoresInvalidosPorMediana() {
		this.cantElementosProcesables = 1;
	}

	@Override
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) {
		List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
		elementosFiltrados.add(JPIVWrapper.replaceByMedianFilter((MapaVectores) input.get(0)));
		return elementosFiltrados;
	}

	@Override
	public HashMap<String, String> getParametros() {
		return null;
	}

	@Override
	public void setParametros(HashMap<String, String> parameters) {
		//Nothing
	}
}
