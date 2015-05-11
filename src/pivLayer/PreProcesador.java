package pivLayer;
import java.util.ArrayList;
import java.util.List;


public class PreProcesador extends Procesador {
	
	public PreProcesador(List<FiltroPreProcesamiento> filtros, Seleccionador seleccionador) {
		super(seleccionador);
		this.filtros = new ArrayList<FiltroProcesable>(filtros);
	}
	
	public void setFiltros(List<FiltroPreProcesamiento> filtros){
		this.filtros = new ArrayList<FiltroProcesable>(filtros);
	}

}
