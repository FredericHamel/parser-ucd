import java.util.*;

public class Operation {
	private String methodeName, typeReturn;
	private Set<Parametre> params;
	
	public Operation(String methode, String typeReturn){
		this.methodeName = methode;
		this.typeReturn = typeReturn;
		this.p = new TreeSet<Parametre>();
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

	public ArrayList<Parametre> getParam(){
		return this.p;
	}
}
