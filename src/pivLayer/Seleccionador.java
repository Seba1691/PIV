package pivLayer;

import java.util.List;

public abstract class Seleccionador {

	public abstract List<List<ElementoProcesable>> seleccionar(List<ElementoProcesable> input, Filtro filtro);
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

}
