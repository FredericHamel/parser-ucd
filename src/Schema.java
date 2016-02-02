
public class Schema {
	String filename;
	private Model m;
	
	public Schema(String fn) {
		this.filename =fn;
		this.m = null;
	}
	public void setModel(Model m) {
		this.m = m;
	}
	
	public Model getModel() {
		return m;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	
}
