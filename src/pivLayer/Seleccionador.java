package pivLayer;

import java.util.List;

public abstract class Seleccionador {

	public static final String SELECCIONADOR_SIMPLE = "seleccionadorSimple";
	public static final String SELECCIONADOR_DOBLE = "seleccionadorDoble";

	private String configuracion;

	public Seleccionador(String configuracion) {
		this.setConfiguracion(configuracion);
	}

	public abstract List<ElementoProcesable> seleccionar(List<ElementoProcesable> input, int iteracion, Filtro filtro);

	public String getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(String configuracion) {
		this.configuracion = configuracion;
	}
}
