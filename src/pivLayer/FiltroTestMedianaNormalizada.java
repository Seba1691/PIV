package pivLayer;

import java.util.ArrayList;
import java.util.List;

import wapper.JPIVWrapper;

public class FiltroTestMedianaNormalizada extends FiltroPostProcesamiento {

	private double umbral;
	private double nivelRuido;

	public FiltroTestMedianaNormalizada(double umbral, double nivelRuido) {
		this.cantElementosProcesables = 1;
		this.umbral = umbral;
		this.nivelRuido = nivelRuido;
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

}
