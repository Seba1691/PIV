package filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pivLayer.ElementoProcesable;
import pivLayer.FilterException;
import pivLayer.FiltroPreProcesamiento;
import pivLayer.Imagen;
import wrapper.ImageJWrapper;

public class FiltroAutoLocalTreshold extends FiltroPreProcesamiento {

	// Parametros

	private static final String METODO = "Metodo";
	private static final String RADIO = "Radio";
	private static final String PARAMETRO1 = "Parametro1";
	private static final String PARAMETRO2 = "Parametro2";
	private static final String FONDO_BLANCO = "FondoBlanco";

	private String metodo;
	private int radio;
	private double parametro1;
	private double parametro2;
	private boolean fondoBlanco;

	public FiltroAutoLocalTreshold(String metodo, int radio, double parametro1, double parametro2, boolean fondoBlanco) {
		this.cantElementosProcesables = 1;
		this.metodo = metodo;
		this.radio = radio;
		this.parametro1 = parametro1;
		this.parametro2 = parametro2;
		this.fondoBlanco = fondoBlanco;
	}

	public FiltroAutoLocalTreshold() {
		this("Bernsen", 15, 0, 0, true);
	}

	@Override
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws FilterException {
		List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
		elementosFiltrados.add(ImageJWrapper.autoLocalThreshold((Imagen) input.get(0), metodo, radio, parametro1, parametro2, fondoBlanco));
		return elementosFiltrados;
	}

	@Override
	public HashMap<String, Object> getParametros() {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(METODO, metodo);
		parameters.put(RADIO, radio);
		parameters.put(PARAMETRO1, parametro1);
		parameters.put(PARAMETRO2, parametro2);
		parameters.put(FONDO_BLANCO, fondoBlanco);
		return parameters;
	}

	@Override
	public void saveParametros(HashMap<String, Object> parameters) {
		for (String key : parameters.keySet())
			switch (key) {
			case METODO:
				this.metodo = (String) parameters.get(key);
				break;
			case RADIO:
				this.radio = (Integer) parameters.get(key);
				break;
			case PARAMETRO1:
				this.parametro1 = (Double) parameters.get(key);
				break;
			case PARAMETRO2:
				this.parametro2 = (Double) parameters.get(key);
				break;
			case FONDO_BLANCO:
				this.fondoBlanco = (Boolean) parameters.get(key);
				break;
			}
	}

	@Override
	public void validateParametros(HashMap<String, Object> parameters) throws FilterException {
		// TODO Auto-generated method stub

	}
}
