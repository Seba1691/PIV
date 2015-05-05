package cache;

import java.util.List;

import pivLayer.ElementoProcesable;
import pivLayer.Filtro;
import pivLayer.Imagen;

public class cacheManager {
	private List<ElementoProcesable> intermedialResults;
	private List<Filtro> filtersGroup;
	
	public cacheManager(List<ElementoProcesable> intermedialResults, List<Filtro> filtersGroup) {
		this.intermedialResults = intermedialResults;
		this.filtersGroup = filtersGroup;
	}

	public ElementoProcesable getIntermedialResult(List<Imagen> imagesIn, List<Filtro> filters) {
		return null;
	}

	public List<ElementoProcesable> getIntermedialResults() {
		return intermedialResults;
	}

	public void setIntermedialResults(List<ElementoProcesable> intermedialResults) {
		this.intermedialResults = intermedialResults;
	}

	public List<Filtro> getFiltersGroup() {
		return filtersGroup;
	}

	public void setFiltersGroup(List<Filtro> filtersGroup) {
		this.filtersGroup = filtersGroup;
	}
}
