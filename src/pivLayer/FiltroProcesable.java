package pivLayer;

import java.util.List;

public abstract class FiltroProcesable extends Filtro {

	public abstract List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws FilterException;

}
