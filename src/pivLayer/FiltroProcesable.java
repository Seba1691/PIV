package pivLayer;

import java.util.List;

public abstract class FiltroProcesable extends Filtro {
	
	protected int cantElementosGenerados;
	
	public abstract List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws FilterException;

	public int getCantElementosGenerados(){
		return cantElementosGenerados;
	}

}
