package pivLayer;

import java.util.ArrayList;
import java.util.List;

import cache.CacheManager;

import wapper.WrapperException;

public abstract class Procesador {

	private Seleccionador seleccionador;

	protected List<FiltroProcesable> filtros;

	public Procesador(Seleccionador seleccionador) {
		this.seleccionador = seleccionador;
	}

	public List<ElementoProcesable> procesar(List<ElementoProcesable> input) throws WrapperException {
		List<ElementoProcesable> result = input;
		for (FiltroProcesable filtro : getFiltros()) {
			List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
			for (List<ElementoProcesable> elementosSeleccionados : seleccionador.seleccionar(result, filtro)) {
				List<ElementoProcesable> elementosProcesar = CacheManager.getInstance().get(elementosSeleccionados, filtro);
				if (elementosProcesar == null) {
					System.out.println(filtro.getClass().getName() + " - no cacheado");
					elementosProcesar = filtro.filtrar(elementosSeleccionados);
					CacheManager.getInstance().add(elementosSeleccionados, filtro, elementosProcesar);
				}
				elementosFiltrados.addAll(elementosProcesar);
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

	private List<FiltroProcesable> getFiltros() {
		return filtros;
	}

}
