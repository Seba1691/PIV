package wrapper;

import java.util.ArrayList;
import java.util.List;

import matpiv.Core;
import pivLayer.Imagen;
import pivLayer.MapaVectores;

import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.mathworks.toolbox.javabuilder.MWStructArray;

public class MatLabWrapper {

	public static MapaVectores CorrealcionCruzadaSimple(Imagen image1, Imagen image2, Double windowSize, Double arg1, Double arg2) {
		try {
			Core core = new Core();

			Object[] in = { "Img1_CLAHEc.png", "Img2_CLAHEc.png", new Double[][] { new Double[] { 64.0, 64.0 } }, (Double) 1.0, (Double) 0.5, "multin" };
			Object[] out = new Object[4];
			out = core.matpiv(1, in);
			MWArray x = ((MWStructArray) out[0]).getField(1);
			MWArray y = ((MWStructArray) out[0]).getField(2);
			MWArray ux = ((MWStructArray) out[0]).getField(3);
			MWArray uy = ((MWStructArray) out[0]).getField(4);

			return mWArraysMultiToMapaVectores(x, y, ux, uy, 3);

		} catch (MWException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static MapaVectores globalFilter(MapaVectores mapa) {
		try {
			Core core = new Core();
			List<double[][]> matlabArray = mapaVectoresToMWArrays(mapa);
			Object[] out = new Object[2];
			
			out = core.globfilt(2, new Object[] { matlabArray.get(0), matlabArray.get(1), matlabArray.get(2), matlabArray.get(3),(double) 3.0 });

			MWArray ux = ((MWNumericArray)out[0]);
			MWArray uy =((MWNumericArray) out[1]);

			return mWArraysMultiToMapaVectores(matlabArray.get(0), matlabArray.get(1), ux, uy, 1);

		} catch (MWException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static MapaVectores localFilter(MapaVectores mapa) {
		try {
			Core core = new Core();
			List<double[][]> matlabArray = mapaVectoresToMWArrays(mapa);
			Object[] out = new Object[2];
			out = core.localfilt(2, new Object[] { matlabArray.get(0), matlabArray.get(1), matlabArray.get(2), matlabArray.get(3),(double) 3.0,"median",(double) 3.0 });

			MWArray ux = ((MWNumericArray)out[0]);
			MWArray uy =((MWNumericArray) out[1]);

			return mWArraysMultiToMapaVectores(matlabArray.get(0), matlabArray.get(1), ux, uy, 1);

		} catch (MWException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void visualizarStreamlines(MapaVectores mapa) {
		try {
			Core core = new Core();
			List<double[][]> matlabArray = mapaVectoresToMWArrays(mapa);
			core.mstreamline(1, new Object[] { matlabArray.get(0), matlabArray.get(1), matlabArray.get(2), matlabArray.get(3), 2 });
		} catch (MWException e) {
			e.printStackTrace();
		}

	}

	public static MapaVectores mWArraysMultiToMapaVectores(double[][] x, double[][] y, MWArray ux, MWArray uy, int numMapas) {
		double[][] mapVec = new double[ux.numberOfElements() / numMapas][5];
		int columnIndex = ux.columnIndex()[ux.numberOfElements() - 1];
		int rowIndex = ux.rowIndex()[ux.numberOfElements() - 1];
		int k = 0;
		for (int i = 0; i < rowIndex; i++)
			for (int j = 0; j < columnIndex / numMapas; j++) {
				int[] index = new int[] { i + 1, j + 1 };
				mapVec[k] = new double[] { x[i][j], y[i][j], (double) ux.get(index), (double) uy.get(index), 1.0 };
				System.out.println(x[i][j] + " - " + y[i][j] + " - " + (double) ux.get(index) + " - " + (double) uy.get(index));
				k++;
			}
		return new MapaVectores(mapVec);
	}

	public static MapaVectores mWArraysMultiToMapaVectores(MWArray x, MWArray y, MWArray ux, MWArray uy, int numMapas) {
		double[][] mapVec = new double[x.numberOfElements() / numMapas][5];
		int columnIndex = x.columnIndex()[x.numberOfElements() - 1];
		int rowIndex = x.rowIndex()[x.numberOfElements() - 1];
		int k = 0;
		for (int i = 0; i < rowIndex; i++)
			for (int j = 0; j < columnIndex / numMapas; j++) {
				int[] index = new int[] { i + 1, j + 1 };
				mapVec[k] = new double[] { (double) x.get(index), (double) y.get(index), (double) ux.get(index), (double) uy.get(index), 1.0 };
				System.out.println((double) x.get(index) + " - " + (double) y.get(index) + " - " + (double) ux.get(index) + " - " + (double) uy.get(index));
				k++;
			}
		return new MapaVectores(mapVec);
	}

	public static List<double[][]> mapaVectoresToMWArrays(MapaVectores mapaVectores) {

		int col = 0;
		double[][] vectorMap = mapaVectores.getMapaVectores();
		while (vectorMap[col][1] == vectorMap[0][1])
			col++;
		int fil = vectorMap.length / col;

		double[][] x = new double[fil][col];
		double[][] y = new double[fil][col];
		double[][] ux = new double[fil][col];
		double[][] uy = new double[fil][col];

		for (int i = 0; i < fil; i++)
			for (int j = 0; j < col; j++) {
				x[i][j] = vectorMap[(i * col) + j][0];
				y[i][j] = vectorMap[(i * col) + j][1];
				ux[i][j] = vectorMap[(i * col) + j][2];
				uy[i][j] = vectorMap[(i * col) + j][3];
			}

		List<double[][]> matlabArrays = new ArrayList<double[][]>();
		matlabArrays.add(x);
		matlabArrays.add(y);
		matlabArrays.add(ux);
		;
		matlabArrays.add(uy);
		return matlabArrays;
	}

}
