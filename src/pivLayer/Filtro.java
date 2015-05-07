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
	public int hashCode() {
		final int prime = 31;
		HashMap<String, Object> parameters = getParametros();
		int result = 1;
		result = prime * result + cantElementosProcesables;
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Filtro other = (Filtro) obj;
		if (cantElementosProcesables != other.cantElementosProcesables)
			return false;
		if (getCantElementosProcesables() != other.getCantElementosProcesables())
			return false;
		return true;
	}

}
