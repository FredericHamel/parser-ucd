
public final class Attribut implements Comparable<Attribut> {
	private final String name, type;
	
	public Attribut(String name, String type){
		this.name = name;
		this.type = type;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getType(){
		return this.type;
	}
	
	@Override
	public int compareTo(Attribut o) {
		return this.name.compareTo(o.getName());
	}
        
        @Override
        public String toString(){
            return String.format("%s : %s", name, type);
        }
}
