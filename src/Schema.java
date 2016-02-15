import java.util.ArrayList;
import java.util.Iterator;

public class Schema {
	private String filename;
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
	
        public ArrayList<String> getClassesName() {
            ArrayList<String> list = new ArrayList<>();
            Iterator<Classe> it = m.getClasseIterator();
            while(it.hasNext())
                list.add(it.next().getName());
            return list;
        }
        
	public String getFilename() {
		return this.filename;
	}
	
	
}
