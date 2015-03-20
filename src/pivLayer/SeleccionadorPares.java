package pivLayer;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

public class SeleccionadorPares extends Seleccionador {

	public SeleccionadorPares(String configuracion) {
		super(configuracion);
	}

	@Override
	public List<ElementoProcesable> seleccionar(List<ElementoProcesable> input, int iteracion, Filtro filtro) {
		List<ElementoProcesable> seleccionados = new ArrayList<ElementoProcesable>();
		int cantElementos = filtro.getCantElementosProcesables();

		boolean reverse = false;
		if (getConfiguracion().equals(SELECCIONADOR_DOBLE) && cantElementos > 1 && iteracion % cantElementos != 0) {
			iteracion = iteracion - 1;
			reverse = true;
		}		

		if (iteracion * cantElementos > input.size() - cantElementos)
			return null;

		for (int i = iteracion * cantElementos; i < (iteracion * cantElementos) + cantElementos; i++)
			seleccionados.add(input.get(i));

		if (reverse)
			seleccionados = Lists.reverse(seleccionados);

		return seleccionados.size() == 0 ? null : seleccionados;
	}

}
