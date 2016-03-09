package metric;


public enum Definition {
	
	ANA("ANA(ci): Nombre moyen d’arguments des méthodes locales pour la classe ci."),
	NOM("NOM(ci): Nombre de méthodes locales/héritées de la classe ci. Dans le cas"
			+ " où une méthode est héritée et redéfinie localement (même nom, même"
			+ " ordre et types des arguments et même type de retour), elle ne compte "
			+ "qu’une fois."),
	NOA("NOA(ci): Nombre d’attributs locaux/hérités de la classe ci."),
	ITC("ITC(ci): Nombre de fois où d’autres classes du diagramme apparaissent"
			+ " comme types des arguments des méthodes de ci."),
	ETC("ETC(ci): Nombre de fois où ci apparaît comme type des arguments dans les"
			+ " méthodes des autres classes du diagramme."),
	CAC("CAC(ci): Nombre d’associations (incluant les agrégations) locales/héritées"
			+ " auxquelles participe une classe ci."),
	DIT("DIT(ci): Taille du chemin le plus long reliant une classe ci à une classe"
			+ " racine dans le graphe d’héritage."),
	CLD("CLD(ci): Taille du chemin le plus long reliant une classe ci à une classe"
			+ " feuille dans le graphe d’héritage."),
	NOC("NOC(ci): Nombre de sous-classes directes de ci."),
	NOD("NOD(ci) : Nombre de sous-classes directes et indirectes de ci.");
	
	private String definition;
	
	private Definition(String def) {
		this.definition = def;
	}
	
	public String getDefinition() {
		return this.definition;
	}
	
}
