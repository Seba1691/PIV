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
		List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
		for (Filtro f : getFiltros()) {
			int i = 0;
			List<ElementoProcesable> elementosSeleccionados;
			while ((elementosSeleccionados = seleccionador.seleccionar(input, i, f)) != null) {
				elementosFiltrados.addAll(f.filtrar(elementosSeleccionados));
				i++;
			}
		}
		return elementosFiltrados;
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
