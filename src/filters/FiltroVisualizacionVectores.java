package filters;

import java.io.IOException;
import java.util.HashMap;

import pivLayer.ElementoProcesable;
import pivLayer.FilterException;
import pivLayer.FiltroVisualizacion;
import pivLayer.MapaVectores;
import wrapper.JPIVWrapper;

public class FiltroVisualizacionVectores extends FiltroVisualizacion {

	@Override
	public void visualizar(ElementoProcesable input) {
		try {
			JPIVWrapper.visualizar((MapaVectores) input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public HashMap<String, Object> getParametros() {
		return null;
	}

	@Override
	public void saveParametros(HashMap<String, Object> parameters) {

	}

	@Override
	public void validateParametros(HashMap<String, Object> parameters) throws FilterException {
		// TODO Auto-generated method stub
	}

}
