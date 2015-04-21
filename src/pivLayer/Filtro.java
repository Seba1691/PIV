package pivLayer;

import java.util.HashMap;
import java.util.List;

import wapper.WrapperException;

public abstract class Filtro {

	protected int cantElementosProcesables;

	public abstract List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws WrapperException;

	public abstract HashMap<String, String> getParametros();

	public abstract void setParametros(HashMap<String, String> parameters);

	public int getCantElementosProcesables() {
		return cantElementosProcesables;
	}

	public void setCantElementosProcesables(int cantElementosProcesables) {
		this.cantElementosProcesables = cantElementosProcesables;
	}

}
