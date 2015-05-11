package pivLayer;
import java.util.ArrayList;
import java.util.List;


public class PostProcesador extends Procesador {

	public PostProcesador(List<FiltroPostProcesamiento> filtros, Seleccionador seleccionador) {
		super(seleccionador);
		this.filtros = new ArrayList<FiltroProcesable>(filtros);
	}
	
	public void setFiltros(List<FiltroPostProcesamiento> filtros){
		this.filtros = new ArrayList<FiltroProcesable>(filtros);
	}

}
