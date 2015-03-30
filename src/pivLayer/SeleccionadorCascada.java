package pivLayer;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class SeleccionadorCascada extends Seleccionador {

	public SeleccionadorCascada(String configuracion) {
		super(configuracion);
	}

	@Override
	public List<List<ElementoProcesable>> seleccionar(List<ElementoProcesable> input, Filtro filtro) {
		List<List<ElementoProcesable>> seleccionados = new ArrayList<List<ElementoProcesable>>();
		int cantElementos = filtro.getCantElementosProcesables();

		for (int i = 0; i < input.size() - cantElementos + 1; i++) {
			List<ElementoProcesable> elementos = new ArrayList<ElementoProcesable>();

			for (int j = 0; j < cantElementos; j++)
				elementos.add(input.get(i + j));

			seleccionados.add(elementos);

			if (getConfiguracion().equals(SELECCIONADOR_DOBLE))
				seleccionados.add(Lists.reverse(elementos));
		}

		return seleccionados;
	}

}
