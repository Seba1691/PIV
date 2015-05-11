package pivLayer;

import java.util.List;

import wapper.WrapperException;

public abstract class FiltroProcesable extends Filtro {

	public abstract List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws WrapperException;	

}
