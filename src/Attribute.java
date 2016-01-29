
public final class Attribute {
	private final String name, type;
	
	public Attribute(String name, String type){
		this.name = name;
		this.type = type;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getType(){
		return this.type;
	}
}
