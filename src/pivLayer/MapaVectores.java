package pivLayer;

public class MapaVectores extends ElementoProcesable {

	private double[][] mapaVectores;

	public MapaVectores(double[][] mapaVectores) {
		this.mapaVectores = mapaVectores;
	}

	public double[][] getMapaVectores() {
		return mapaVectores;
	}

	public void setMapaVectores(double[][] mapaVectores) {
		this.mapaVectores = mapaVectores;
	}
}
