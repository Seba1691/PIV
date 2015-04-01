package pivLayer;

import java.util.ArrayList;
import java.util.List;

import wapper.ImageJWrapper;
import wapper.WrapperException;

public class FiltroAutoLocalTreshold extends FiltroPreProcesamiento {
    
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
		
	@Override
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws WrapperException {
		List<ElementoProcesable> elementosFiltrados = new ArrayList<ElementoProcesable>();
		elementosFiltrados.add(ImageJWrapper.autoLocalThreshold((Imagen) input.get(0), metodo, radio, parametro1, parametro2, fondoBlanco));
		return elementosFiltrados;
	}

}
