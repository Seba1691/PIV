package pivLayer;

import java.util.ArrayList;

public class ProcesadorPIV extends Procesador {

	public ProcesadorPIV(FiltroPIV filtro, Seleccionador seleccionador) {
		super(seleccionador);
		this.filtros = new ArrayList<FiltroProcesable>();
		this.filtros.add(filtro);
	}

	public void setFiltros(FiltroPIV filtro) {
		this.filtros = new ArrayList<FiltroProcesable>();
		this.filtros.add(filtro);
	}
}
