
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserUCD {
	public static final String IDENTIFIER_FORMAT = "[A-Za-z_]+[0-9A-Za-z_]*";
	public static final Pattern TOKEN_FORMAT = Pattern.compile("([0-9A-Za-z_]+|[,:;()])");
	private Scanner sc;
	
	private char caractere;
	private String token;
	
	private Matcher pool;
	
	public ParserUCD() {
		this.sc = null;
		this.token = null;
		this.pool = null;
	}
	
	public String nextToken() {
		String tmp;
		if(pool == null || !pool.find()) {
			this.token = sc.hasNext() ? sc.next(): null;
			if(this.token != null) {
				pool = TOKEN_FORMAT.matcher(token);
				if(!pool.find())
					throw new SyntaxException("Invalid token");
				tmp = pool.group();
				pool = pool.region(pool.end(1), this.token.length());
			} else {
				tmp = null;
			}
		}else {
			tmp = pool.group();
			pool = pool.region(pool.end(1), this.token.length());
		}
		
		return tmp;
	}

	public void parse(Schema schema) throws IOException {
		this.sc = new Scanner(new File(schema.getFilename()));
		this.sc.useDelimiter(Pattern.compile("[ \t\n]+"));
		String token = nextToken();
		while(token != null) {
			System.out.println(token);
			token = nextToken();
		}
		System.exit(0);
		if(this.token!=null)
		{
			if(!token.equals("MODEL"))
				throw new SyntaxException("Expected MODEL");
			if(!sc.hasNext())
				throw new SyntaxException("Reaching invalid end of file");
			parseModel(schema);
		}
		sc.close();
		
	}
	
	private void parseModel(Schema schema) {
		Model m = new Model();
		String token = nextToken();
		if(!token.matches(IDENTIFIER_FORMAT))
			throw new SyntaxException(String.format("Invalid model name: %s", token));
		m.setName(token);
		token = nextToken();
		while(token != null) {
			if(token.equals("CLASS")) {
				parseClasse(m);
			}else if(token.equals("GENERALISATION")) {
				
			}else if(token.equals("RELATION")) {
				
			}else if(token.equals("AGGREGATION")) {
				
			}else{
				throw new SyntaxException("Syntax error");
			}
			token = nextToken();
		}
		schema.setModel(m);
	}
	
	private void parseClasse(Model m) {
		Classe classe = null;
		String token = nextToken();
		System.out.println(token);
		if(token == null || !token.matches(IDENTIFIER_FORMAT))
			throw new SyntaxException(String.format("Invalid classe name %s", token == null ? "null": token));
		classe = m.findClasse(token, true);
		token = nextToken();
		if(token == null || !token.equals("ATTRIBUTES"))
			throw new SyntaxException(String.format("Expected ATTRIBUTES in classe %s", classe.getName()));
		
		token = nextToken();
		if(token == null)
			throw new SyntaxException("Unexpected end of file");
		
		while(!token.equals("OPERATIONS")) {
			parseAttribute(classe, token);
			
		}
	}
	
	// nom:type
	// 
	private void parseAttribute(Classe c, String name) {
		Scanner attr;
		String type;
		
		token = nextToken();
		if(token == null)
			throw new SyntaxException(String.format("Invalid attribute definition %s", name));
		if(token.length() == 1)
			type = nextToken();
	}
	
	public static void main(String[] args) {
		ParserUCD p = new ParserUCD();
		try {
			Schema schema = new Schema("Ligue.ucd");
			p.parse(schema);
			
		}catch(SyntaxException e) {
			System.err.println(e.getMessage());
		}catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
