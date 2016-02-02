
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.HTMLEditorKit.Parser;

public class ParserUCD {
	public static final String IDENTIFIER_FORMAT = "[A-Za-z_]+[0-9A-Za-z_]*";
	public static final Pattern TOKEN_FORMAT = Pattern.compile("([0-9A-Za-z_]+|[,:;()])");
	
	// Keywork declaration
	public static final String KEYWORD_MODEL = "MODEL";
	public static final String KEYWORD_CLASS = "CLASS";
	public static final String KEYWORD_ATTRIBUTES = "ATTRIBUTES";
	public static final String KEYWORD_OPERATIONS = "OPERATIONS";
	public static final String KEYWORD_GENERALIZATION = "GENERALIZATION";
	public static final String KEYWORD_SUBCLASSES = "SUBCLASSES";
	public static final String KEYWORD_RELATION = "RELATION";
	public static final String KEYWORD_ROLES = "ROLES";
	public static final String KEYWORD_AGGREGATION = "AGGREGATION";
	public static final String KEYWORD_CONTAINER = "CONTAINER";
	public static final String KEYWORD_PARTS = "PARTS";
	
	// Multiciplicite keyword
	public static final String KEYWORD_ONE = "ONE";
	public static final String KEYWORD_MANY = "MANY";
	public static final String KEYWORD_ONE_OR_MANY = "ONE_OR_MANY";
	public static final String KEYWORD_OPTIONALLY_ONE = "OPTIONALLY_ONE";
	public static final String KEYWORD_UNDEFINED = "UNDEFINED";
	
	public static final int STATE_IDENTIFIER = 0;
	public static final int STATE_TYPE_SEPARATOR = 1;
	public static final int STATE_TYPE_IDENTIFIER = 2;
	public static final int STATE_TYPE_NEXT = 3;
	public static final int STATE_PARAMETER = 4;
	public static final int STATE_READ = 5;
	
	private Scanner sc;
	
	private char caractere;
	private String token;
	private int state;
	
	private Matcher pool;
	
	public ParserUCD() {
		this.sc = null;
		this.token = null;
		this.pool = null;
		
		this.state = ParserUCD.STATE_READ;
	}
	
	public String nextToken() {
		String tmp;
		if(pool == null || !pool.find()) {
			tmp = sc.hasNext() ? sc.next(): null;
			if(tmp != null) {
				pool = TOKEN_FORMAT.matcher(tmp);
				if(!pool.find())
					throw new SyntaxException("Invalid token");
				this.token = pool.group(1);
			} else {
				this.token = null;
			}
		}else{
			this.token = pool.group(1);
		}
		
		return this.token;
	}

	public void parse(Schema schema) throws IOException {
		this.sc = new Scanner(new File(schema.getFilename()));
		this.sc.useDelimiter(Pattern.compile("[ \t\n]+"));
		String token = nextToken();
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
		int state = ParserUCD.STATE_TYPE_SEPARATOR;
		Classe classe = null;
		boolean needNext = false;
		String token = nextToken();
		if(token == null || !token.matches(ParserUCD.IDENTIFIER_FORMAT))
			throw new SyntaxException(String.format("Invalid classe name %s", token == null ? "null": token));
		classe = m.findClasse(token, true);
		token = nextToken();
		if(token == null || !token.equals(ParserUCD.KEYWORD_ATTRIBUTES))
			throw new SyntaxException(String.format("Expected ATTRIBUTES in classe %s", classe.getName()));
		token = nextToken();
		if(token == null)
			throw new SyntaxException(String.format("Invalid class definition %s", classe.getName()));
		
		
		if(!token.equals(ParserUCD.KEYWORD_OPERATIONS)) {
			this.state = ParserUCD.STATE_TYPE_SEPARATOR;
			parseAttributes(classe, token);
		}
		
		token = nextToken();
		if(token == null)
			throw new SyntaxException(String.format("Invalid class definition %s", classe.getName()));
		if(!token.equals(";")) {
			this.state = ParserUCD.STATE_PARAMETER;
			parseOperations(classe, token);
		}
	}
	
	// nom:type
	// 
	private void parseAttributes(Classe c, String name) {
		String type = "";
		System.out.println("CLASS " + c.getName());
		do {
			this.token = nextToken();
			if(token == null)
				throw new SyntaxException(String.format("Invalid attribute declaration %s", token));
			switch(this.state) {
				case ParserUCD.STATE_IDENTIFIER:
					if(!token.matches(ParserUCD.IDENTIFIER_FORMAT))
						throw new SyntaxException(String.format("Invalid identifier %s", token));
					name = token;
					this.state = ParserUCD.STATE_TYPE_SEPARATOR;
					break;
				case ParserUCD.STATE_TYPE_SEPARATOR:
					if(!token.equals(":"))
						throw new SyntaxException(String.format("Expected ':' after %s", name));
					this.state = ParserUCD.STATE_TYPE_IDENTIFIER;
					break;
				case ParserUCD.STATE_TYPE_IDENTIFIER:
					if(!token.matches(ParserUCD.IDENTIFIER_FORMAT))
						throw new SyntaxException(String.format("Invalid type %s", token));
					type = token;
					this.state = ParserUCD.STATE_TYPE_NEXT;
					break;
				case ParserUCD.STATE_TYPE_NEXT:
					Attribut attr = new Attribut(name, type);
					c.addAttribut(attr);
					System.out.println(name + " : " + type);
					if(token.equals(","))
						this.state = ParserUCD.STATE_IDENTIFIER;
					else if(token.equals(ParserUCD.KEYWORD_OPERATIONS))
						this.state = ParserUCD.STATE_READ;
					else
						throw new SyntaxException(String.format("Invalid attribute declaration of %s : %s", name, type));
					break;
			}
		} while(this.state != ParserUCD.STATE_READ);
	}
	
	public void parseOperations(Classe c, String name) {
		String type = "";
		do {
			this.token = nextToken();
			if(token == null)
				throw new SyntaxException(String.format("Invalid operation declaration %s", token));
			switch(this.state) {
				case ParserUCD.STATE_IDENTIFIER:
					if(!token.matches(ParserUCD.IDENTIFIER_FORMAT))
						throw new SyntaxException(String.format("Invalid identifier %s", token));
					name = token;
					this.state = ParserUCD.STATE_PARAMETER;
					break;
				case ParserUCD.STATE_PARAMETER:
					
					while(!nextToken().equals(")")); // TODO parseParameters();
					this.state = ParserUCD.STATE_TYPE_SEPARATOR;
					break;
				case ParserUCD.STATE_TYPE_SEPARATOR:
					if(!token.equals(":"))
						throw new SyntaxException(String.format("Expected ':' after %s()", name));
					this.state = ParserUCD.STATE_TYPE_IDENTIFIER;
					break;
				case ParserUCD.STATE_TYPE_IDENTIFIER:
					if(!token.matches(ParserUCD.IDENTIFIER_FORMAT))
						throw new SyntaxException(String.format("Invalid type %s", token));
					type = token;
					this.state = ParserUCD.STATE_TYPE_NEXT;
					break;
				case ParserUCD.STATE_TYPE_NEXT:
					System.out.println(name + "() : " + type);
					if(token.equals(","))
						this.state = ParserUCD.STATE_IDENTIFIER;
					else if(token.equals(";"))
						this.state = ParserUCD.STATE_READ;
					else
						throw new SyntaxException(String.format("Invalid operation declaration of %s : %s", name, type));
					break;
			}
		} while(this.state != ParserUCD.STATE_READ);
	}
	
	public static void main(String[] args) {
		ParserUCD p = new ParserUCD();
		/*String text = "ABC:ABC";
		Matcher m = ParserUCD.TOKEN_FORMAT.matcher(text);
		while(m.find())
		{
			System.out.println(m.group(1));
		}
		System.exit(0);*/
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
