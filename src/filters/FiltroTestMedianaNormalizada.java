package filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pivLayer.ElementoProcesable;
import pivLayer.FiltroPostProcesamiento;
import pivLayer.MapaVectores;

import wapper.JPIVWrapper;

public class FiltroTestMedianaNormalizada extends FiltroPostProcesamiento {

	private static final String UMBRAL = "Umbral";
	private static final String NIVEL_RUIDO = "NivelRuido";

	private double umbral;
	private double nivelRuido;

	public FiltroTestMedianaNormalizada(double umbral, double nivelRuido) {
		this.cantElementosProcesables = 1;
		this.umbral = umbral;
		this.nivelRuido = nivelRuido;
	}
	
	public FiltroTestMedianaNormalizada() {
		this(2.0, 0.1);
	}

	@Override
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) {
		List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
		elementosFiltrados.add(JPIVWrapper.normalizedMedianTestFilter((MapaVectores) input.get(0), nivelRuido, umbral));
		return elementosFiltrados;
	}

	public double getUmbral() {
		return umbral;
	}

	public void setUmbral(double umbral) {
		this.umbral = umbral;
	}

	public double getNivelRuido() {
		return nivelRuido;
	}

	public void setNivelRuido(double nivelRuido) {
		this.nivelRuido = nivelRuido;
	}

	@Override
	public HashMap<String, String> getParametros() {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(UMBRAL, String.valueOf(umbral));
		parameters.put(NIVEL_RUIDO, String.valueOf(nivelRuido));
		return parameters;
	}

	@Override
	public void setParametros(HashMap<String, String> parameters) {

		for (String key : parameters.keySet())
			switch (key) {
			case UMBRAL:
				this.umbral = Integer.valueOf(parameters.get(key));
				break;
			case NIVEL_RUIDO:
				this.nivelRuido = Integer.valueOf(parameters.get(key));
				break;
			}
	}
}
