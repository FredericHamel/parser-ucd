
public class Metrique {
	private String name;
	private String definition;
	private double value;
	
	public Metrique(String name,double value){
		this.name = name;
		for(Definition d : Definition.values()){
			if(d.equals(this.name)) {
				this.definition = d.getDefinition();
                break;
            }
		}
		this.value = value;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDefinition(){
		return this.definition;
	}
	
	public double getValue(){
		return this.value;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append(" = ");
        sb.append(this.value);
        sb.append("\n");
        return sb.toString();
	}
}
