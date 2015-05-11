package filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import pivLayer.ElementoProcesable;
import pivLayer.FiltroVisualizacion;
import pivLayer.MapaVectores;
import wapper.JPIVWrapper;

public class FiltroVisualizacionVectores extends FiltroVisualizacion {

	@Override
	public void visualizar(List<ElementoProcesable> input) {
		try {
			JPIVWrapper.visualizar((MapaVectores) input.get(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public HashMap<String, Object> getParametros() {
		return null;
	}

	@Override
	public void setParametros(HashMap<String, Object> parameters) {
		
	}

}
