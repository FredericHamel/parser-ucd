import java.util.*;

public class Operation {
	private String methodeName, typeReturn;
	private Set<Parametre> params;
	
	public Operation(String methode){
		this.methodeName = methode;
		this.typeReturn = "";
		this.params = new TreeSet<Parametre>(); // chaque param unique
	}

    public void setreturnType(String t) {
        this.typeReturn = t;
    }

    public void addParam(Parametre p) {
        this.params.add(p);
    }

	public String getMethode(){
		return this.methodeName;
	}
	
	public String getTypeReturn(){
		return this.typeReturn;
	}
	
    public Parametre findParametre(String name, boolean create) {
        Parametre p = null;
        Iterator<Parametre> it = this.getParamIterator();
        while(it.hasNext()) {
            p = it.next();
            if(p.getName().equals(name))
                return p;
        }
        return create ? new Parametre(name) : null;
    }

    public Iterator<Parametre> getParamIterator() {
        return this.params.iterator();
    }
}

