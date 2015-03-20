package pivLayer;

public abstract class FiltroPreProcesamiento extends Filtro {

	public static final String FILTRO_SIMPLE = "filtroSimple";
	public static final String FILTRO_DOBLE = "filtroDoble";

	protected String configuracion;

	public FiltroPreProcesamiento(String configuracion) {
		this.configuracion = configuracion;
	}

}
