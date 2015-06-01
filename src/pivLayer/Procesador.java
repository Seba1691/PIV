package pivLayer;

import java.util.ArrayList;
import java.util.List;

import cache.CacheManager;

public abstract class Procesador {

	protected List<FiltroProcesable> filtros;
	protected List<Seleccionador> seleccionadores;

	public Procesador() {
		
	}
	
	public Procesador(List<FiltroProcesable> filtros, List<Seleccionador> seleccionadores) {
		this.filtros = filtros;
		this.seleccionadores = seleccionadores;
	}

	public List<ElementoProcesable> procesar(List<ElementoProcesable> input) throws FilterException {
		List<ElementoProcesable> result = input;
		for (int i = 0; i < filtros.size(); i++) {
			FiltroProcesable filtro = filtros.get(i);
			List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
			for (List<ElementoProcesable> elementosSeleccionados : seleccionadores.get(i).seleccionar(result, filtro)) {
				List<ElementoProcesable> elementosProcesar = CacheManager.getInstance().get(elementosSeleccionados, filtro);
				if (elementosProcesar == null) {
					elementosProcesar = filtro.filtrar(elementosSeleccionados);
					CacheManager.getInstance().add(elementosSeleccionados, filtro, elementosProcesar);
				}
				elementosFiltrados.addAll(elementosProcesar);
			}
			result = elementosFiltrados;
		}
		return result;
	}

}
