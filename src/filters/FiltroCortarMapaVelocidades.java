package filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pivLayer.ElementoProcesable;
import pivLayer.FilterException;
import pivLayer.FiltroPostProcesamiento;
import pivLayer.MapaVectores;

public class FiltroCortarMapaVelocidades extends FiltroPostProcesamiento {

	public FiltroCortarMapaVelocidades() {
		super(1,1);
	}

	@Override
	public List<ElementoProcesable> filtrar(List<ElementoProcesable> input) throws FilterException {

		double[][] mat = ((MapaVectores) input.get(0)).getMapaVectores();
		int x1 = 222;
		int x2 = 1419;
		int y1 = 65;
		int y2 = 1251;

		List<double[]> result = new ArrayList<double[]>();
		for (int i = 0; i < mat.length; i++)
			if (mat[i][0] >= x1 && mat[i][0] <= x2 && mat[i][1] >= y1 && mat[i][1] <= y2) {
				result.add(mat[i]);
			}
		
		List<ElementoProcesable> aRetornar = new ArrayList<ElementoProcesable>();
		aRetornar.add(new MapaVectores(toMatrix(result)));
		return aRetornar;

	}

	private double[][] toMatrix(List<double[]> input) {
		double[][] result = new double[input.size()][];
		int i = 0;
		for (double[] fila : input) {
			result[i] = fila;
			i++;
		}
		return result;
	}

	@Override
	protected void validateParametros(HashMap<String, Object> parameters) throws FilterException {

	}

}
