package pivLayer;

import java.util.ArrayList;
import java.util.List;

public class SeleccionadorPares extends Seleccionador {

	@Override
	public List<ElementoProcesable> seleccionar(List<ElementoProcesable> input, int iteracion, Filtro filtro) {
		List<ElementoProcesable> seleccionados = new ArrayList<ElementoProcesable>();
		int cantElementos = filtro.getCantElementosProcesables();

		if (iteracion * cantElementos > input.size() - cantElementos)
			return null;

		for (int i = iteracion * cantElementos; i < (iteracion * cantElementos) + cantElementos; i++)
			seleccionados.add(input.get(i));
		return seleccionados.size() == 0 ? null : seleccionados;
	}

}
