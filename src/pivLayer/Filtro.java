package pivLayer;

import java.util.HashMap;
import java.util.List;

import wapper.WrapperException;

public abstract class Filtro {

	protected int cantElementosProcesables;

	public abstract List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws WrapperException;

	public abstract HashMap<String, Object> getParametros();

	public abstract void setParametros(HashMap<String, Object> parameters);

	public int getCantElementosProcesables() {
		return cantElementosProcesables;
	}

	public void setCantElementosProcesables(int cantElementosProcesables) {
		this.cantElementosProcesables = cantElementosProcesables;
	}

	@Override
	public boolean equals(Object f) {
		Filtro filter = (Filtro) f;
		return getParametros().equals(filter.getParametros()) && //
				filter.getCantElementosProcesables() == getCantElementosProcesables() && //
				filter.getClass().getName().equals(this.getClass().getName());
	}

}
