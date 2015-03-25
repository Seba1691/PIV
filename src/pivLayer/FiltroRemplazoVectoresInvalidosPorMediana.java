package pivLayer;

import java.util.ArrayList;
import java.util.List;

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
}