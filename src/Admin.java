import java.util.*;

public class Admin {
	private final Set<Schema> schemas;
	private static Admin instance;
	
	public Admin(){
		this.schemas = new TreeSet<>();
	}
	
	public static Admin getInstance(){
		if(instance == null){
			instance = new Admin();
		}
		return instance;
	}
	
	public void addSchema(String filename){
		Schema s = new Schema(filename);
		this.schemas.add(s);
		ParserUCD p = new ParserUCD();
        p.parse(s.getFilename());
	}
	
	public ArrayList<String> getClassesName(){
		ArrayList<String> classes = new ArrayList<>();
		Classe c = null;
		Iterator<Classe> it = schema.getModel().getClasseIterator();
		while(it.hasNext()) {
			c = it.next();
			classes.add(c.getName());
		}
		return classes;
	}
}
