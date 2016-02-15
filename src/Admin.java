import java.io.IOException;
import java.util.*;

public class Admin {
    private Model model;
    private static Admin instance;
    private final ParserUCD parser;

    private static final Object lock = new Object();

    public Admin(){
        this.model = null;
        this.parser = ParserUCD.getInstance();
    }

    public static Admin getInstance(){
        if(instance == null){
            synchronized(lock) {
                if(instance == null)
                    instance = new Admin();
            }
        }
        return instance;
    }

    public void parseModel(String filename) throws IOException {

        this.model = parser.parse(filename);
    }

    public ArrayList<String> getClassesName(){
        Classe c = null;
        ArrayList<String> classes = new ArrayList<>();

        Iterator<Classe> it = model.getClasseIterator();
        while(it.hasNext()) {
            c = it.next();
            classes.add(c.getName());
        }
        return classes;
    }
}
