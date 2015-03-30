package pivLayer;

import java.util.ArrayList;
import java.util.List;

import wapper.WrapperException;

public abstract class Procesador {

	private Seleccionador seleccionador;

	protected List<Filtro> filtros;

	public Procesador(Seleccionador seleccionador) {
		this.seleccionador = seleccionador;
	}

	public List<ElementoProcesable> procesar(List<ElementoProcesable> input) throws WrapperException {
		List<ElementoProcesable> result = input;
		for (Filtro filtro : getFiltros()) {
			List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
			for (List<ElementoProcesable> elementosSeleccionados : seleccionador.seleccionar(result, filtro)) {
				elementosFiltrados.addAll(filtro.filtrar(elementosSeleccionados));
			}
			result = elementosFiltrados;
		}
		return result;
	}

	public Seleccionador getSeleccionador() {
		return seleccionador;
	}

	public void setSeleccionador(Seleccionador seleccionador) {
		this.seleccionador = seleccionador;
	}

	private List<Filtro> getFiltros() {
		return filtros;
	}

}
