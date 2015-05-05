package gui.renderers;

public class RendererException extends Exception {

	private static final long serialVersionUID = 1L;

	public RendererException(Exception e) {
		super(e);
	}

	public RendererException(String cause, Exception e) {
		super(cause, e);
	}
	
	public RendererException(String cause) {
		super(cause);
	}

}
