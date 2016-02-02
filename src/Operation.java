import java.util.*;

public class Operation {
	private String methodeName, typeReturn;
	private Set<Parametre> params;
	
	public Operation(String methode, String typeReturn){
		this.methodeName = methode;
		this.typeReturn = typeReturn;
		this.params = new TreeSet<Parametre>(); // chaque param unique
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
	
    public Iterator<Parametre> getParamIterator() {
        return this.params.iterator();
    }
}

