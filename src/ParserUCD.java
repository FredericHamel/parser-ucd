
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
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
        nextToken();
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
        nextToken();
        if(!token.matches(IDENTIFIER_FORMAT))
            throw new SyntaxException(String.format("Invalid model name: %s", token));
        m.setName(token);
        nextToken();
        while(token != null) {
            if(token.equals("CLASS")) {
                parseClasse(m);
            }else if(token.equals("GENERALISATION")) {
                parseGeneralisation(m);
            }else if(token.equals("RELATION")) {

            }else if(token.equals("AGGREGATION")) {

            }else{
                throw new SyntaxException("Syntax error");
            }
            nextToken();
        }
        schema.setModel(m);
    }
    
    private void parseGeneralisation(Model m){
    	int state = ParserUCD.STATE_TYPE_SEPARATOR;
    	Generalization g = null;
        nextToken();

        if(token == null || !token.matches(ParserUCD.IDENTIFIER_FORMAT))
            throw new SyntaxException(String.format("Invalid generalization name %s", token == null ? "null": token));
        g = m.findGeneralization(token, true);
        
        nextToken();

        if(token == null || !token.equals(ParserUCD.KEYWORD_SUBCLASSES))
            throw new SyntaxException(String.format("Expected SUBCLASSES in generalization %s", g.getName()));
        nextToken();

        if(token == null)
            throw new SyntaxException(String.format("Invalid class definition in generalization %s", g.getName()));
        

        // soit ';' ou identifiant operation attendu.
        if(!token.equals(";")) {
            this.state = STATE_TYPE_IDENTIFIER;
            if(!token.matches(IDENTIFIER_FORMAT))
                throw new SyntaxException(String.format("Invalid classe definition %s in generalization %s", token, g.getName()));
            parseSubClasses(g, token, m);
        }
    }
    
    private void parseSubClasses(Generalization g, String name, Model m){
        System.out.println("GENERALIZATION " + g.getName());
        do {
            nextToken();
            if(token == null)
                throw new SyntaxException(String.format("Invalid classe declaration %s", token));
            switch(this.state) {
                case ParserUCD.STATE_IDENTIFIER:
                    if(!token.matches(ParserUCD.IDENTIFIER_FORMAT))
                        throw new SyntaxException(String.format("Invalid identifier %s", token));
                    name = token;
                    this.state = ParserUCD.STATE_TYPE_NEXT;
                    break;
                case ParserUCD.STATE_TYPE_NEXT:
                    Classe c = m.findClasse(name, true);
                    g.addSubClasses(c);
                    System.out.println("\t" + name);
                    if(token.equals(","))
                        this.state = ParserUCD.STATE_IDENTIFIER;
                    else if(token.equals(";"))
                        this.state = ParserUCD.STATE_READ;
                    else
                        throw new SyntaxException(String.format("Invalid generalization declaration %s", g));
                    break;
            }
        } while(this.state != ParserUCD.STATE_READ);

    }
    private void parseClasse(Model m) {
        int state = ParserUCD.STATE_TYPE_SEPARATOR;
        Classe classe = null;
        boolean needNext = false;
        nextToken();

        if(token == null || !token.matches(ParserUCD.IDENTIFIER_FORMAT))
            throw new SyntaxException(String.format("Invalid classe name %s", token == null ? "null": token));
        classe = m.findClasse(token, true);
        nextToken();

        if(token == null || !token.equals(ParserUCD.KEYWORD_ATTRIBUTES))
            throw new SyntaxException(String.format("Expected ATTRIBUTES in classe %s", classe.getName()));
        nextToken();

        if(token == null)
            throw new SyntaxException(String.format("Invalid class definition %s", classe.getName()));


        if(!token.equals(ParserUCD.KEYWORD_OPERATIONS)) {
            this.state = ParserUCD.STATE_TYPE_SEPARATOR;
            parseAttributes(classe, token);
        }

        // OPERATIONS
        nextToken();
        if(token == null)
            throw new SyntaxException(String.format("Invalid class definition %s", classe.getName()));

        // soit ';' ou identifiant operation attendu.
        if(!token.equals(";")) {
            this.state = ParserUCD.STATE_PARAMETER;
            if(!token.matches(IDENTIFIER_FORMAT))
                throw new SyntaxException(String.format("Invalid operation identifier %s in class %s", token, classe.getName()));
            parseOperations(classe, token);
        }
    }

    // nom:type
    // 
    private void parseAttributes(Classe c, String name) {
        String type = "";
        System.out.println("CLASS " + c.getName());
        do {
            nextToken();
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
                    System.out.println("\t" + name + " : " + type);
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
        Set<Parametre> params = null;
        Operation op = null;
        do {
            nextToken();
            if(token == null)
                throw new SyntaxException(String.format("Invalid operation declaration in class %s", c.getName()));
            switch(this.state) {
                case ParserUCD.STATE_IDENTIFIER:
                    if(!token.matches(ParserUCD.IDENTIFIER_FORMAT))
                        throw new SyntaxException(String.format("Invalid identifier %s", token));
                    name = token;
                    this.state = ParserUCD.STATE_PARAMETER;
                    break;
                case ParserUCD.STATE_PARAMETER:

                    if(!token.equals("("))
                        throw new SyntaxException(String.format("Expected '(' after %s in class %s", token, c.getName()));
                    params = parseParameters(name);
                    this.state = ParserUCD.STATE_TYPE_SEPARATOR;
                    break;
                case ParserUCD.STATE_TYPE_SEPARATOR:
                    if(!token.equals(":"))
                        throw new SyntaxException(String.format("Expected ':' after %s() in class name", name, c.getName()));
                    this.state = ParserUCD.STATE_TYPE_IDENTIFIER;
                    break;
                case ParserUCD.STATE_TYPE_IDENTIFIER:
                    if(!token.matches(ParserUCD.IDENTIFIER_FORMAT))
                        throw new SyntaxException(String.format("Invalid type %s", token));
                    type = token;
                    this.state = ParserUCD.STATE_TYPE_NEXT;
                    break;
                case ParserUCD.STATE_TYPE_NEXT:
                    op = new Operation(name, type);
                    c.addOperation(op);
                    
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

    public Set<Parametre> parseParameters(String opName) {
        Set<Parametre> params = new TreeSet<>();
        String name, sep, type;
        Parametre p;
        boolean next;
        
        System.out.printf("\t%s(", opName);
        
        do {
            nextToken();
            if(token.equals(")")) {
                System.err.println(")");
                return params;
            }
            name = token;
            sep = nextToken();
            type = nextToken();
            System.out.printf("%s %s %s", name, sep, type);
            if((name.matches(IDENTIFIER_FORMAT) || sep.equals(":"))
                    || type.matches(IDENTIFIER_FORMAT)) {
            } else {
                throw new SyntaxException(String.format("Invalid param %s in operation %s", name, opName));
            }
            p = new Parametre(name, type);
            
            if(!params.add(p))
                throw new SyntaxException(String.format("Duplicate parameter %s in operation %s", name, opName));
            nextToken();
            next = token.equals(",");
            if(!(next || token.equals(")"))) {
                throw new SyntaxException(String.format("Expected ',' or ')' after %s in class %s", opName, opName));
            }
            System.out.printf("%s", token);
        }while(next);
        System.out.println();
        return params;
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
