package filters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import pivLayer.ElementoProcesable;
import pivLayer.FilterException;
import pivLayer.FiltroVisualizacion;
import pivLayer.MapaVectores;

public class FiltroVelocidadX extends FiltroVisualizacion {

	private static final String TAMANO_TOTAL = "TamañoTotal";
	private static final String TAMANO_PIXEL = "TamañoPixel";

	public FiltroVelocidadX() {
		this(1, 1);
	}

	public FiltroVelocidadX(double tiempo, double tamanoPixel) {
		super(1);
		parametros = new HashMap<String, Object>();
		parametros.put(TAMANO_TOTAL, tiempo);
		parametros.put(TAMANO_PIXEL, tamanoPixel);
	}

	@Override
	public void visualizar(ElementoProcesable input) {
		try {
			double[][] mat = ((MapaVectores) input).getMapaVectores();

			int i = 0;
			while (mat[i][1] == mat[0][1]) {
				i++;
			}
			
			int xMedio = (int) mat[i / 2][0];
			int xAnt = (int) mat[(i / 2)-1][0];
			int xSig = (int) mat[(i / 2)+1][0];
			
			System.out.println(xAnt + "  " + xMedio + "   " + xSig);

			HashMap<Double, Double> result = new LinkedHashMap<Double, Double>();

			FileWriter file = new FileWriter("tablaVelocidades.csv");
			BufferedWriter fop = new BufferedWriter(file);

			for (int j = 0; j < mat.length; j++)
				if (mat[j][0] == xMedio || mat[j][0] == xAnt || mat[j][0] == xSig) {
					Double r = result.get(mat[j][1] * 0.00014 / 0.1);
					if (r == null)
						r = 0.0;
					r = r + mat[j][5];
					result.put(mat[j][1] * 0.00014 / 0.1, r);
				}

			for (Double k : result.keySet()) {
				fop.write(String.valueOf(k).replace(".", ",") + "; " + String.valueOf(result.get(k) / 3).replace(".", ","));
				fop.newLine();
			}

			fop.close();

			/*
			 * List<Double[]> result = new ArrayList<Double[]>(); for (int i =
			 * 0; i < mat.length; i++) if (mat[i][1] == yMedio) { result.add(new
			 * Double[] { mat[i][0] * 0.00133 / 1, mat[i][6] });
			 * System.out.println(mat[i][0] * 0.00133 / 1 + ", " + mat[i][6]); }
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void validateParametros(HashMap<String, Object> parameters) throws FilterException {

	}

}
