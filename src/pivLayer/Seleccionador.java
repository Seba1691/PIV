package pivLayer;
import java.util.List;


public abstract class Seleccionador {

	public abstract List<ElementoProcesable> seleccionar(List<ElementoProcesable> input, int iteracion, Filtro filtro);
}
