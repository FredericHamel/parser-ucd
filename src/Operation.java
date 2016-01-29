import java.util.*;

public class Operation {
	private String methodeName, typeReturn;
	private ArrayList<Parameter> p = new ArrayList<Parameter>();
	
	public Operation(String methode, String typeReturn, ArrayList<Parameter> p){
		this.methodeName = methode;
		this.typeReturn = typeReturn;
		this.p = p;
	}
	
	public String getMethode(){
		return this.methodeName;
	}
	
	public String getTypeReturn(){
		return this.typeReturn;
	}
	
	public ArrayList<Parameter> getParam(){
		return this.p;
	}
}
