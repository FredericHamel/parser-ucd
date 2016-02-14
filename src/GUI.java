import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class GUI extends JFrame{
	
	final JFileChooser fileChooser = new JFileChooser();
	
	private JPanel topPanel,bottomPanel, centeredPanel,leftPanel;
	private JButton chargerButton;
	private JTextField fieldFile; 
	private JList<String> listClasses, listAttributes, listMethodes, listSubclasses, listRelations, listDetails;
	private int returnValue;
	
	private File file;
	
	private static final int FRAME_WIDTH = 700;
	private static final int FRAME_HEIGHT = 700;
	
	public GUI(){
		super();
		//Creer les composants
		createComponents();
		
		//Taille de l'interface
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
	
	private void createComponents(){
		
		createTopPanel();
		createLeftPanel();
		createCenteredPanel();
		createBottomPanel();
		
	}
	
	/**
	 * Creation du topPanel qui contient le bouton de chargement
	 * du fichier et un textField contenant le nom du fichier.
	 * https://docs.oracle.com/javase/tutorial/uiswing/layout/spring.html
	 */
	public void createTopPanel(){
		
		//Creation du Container topPanel et utilisation du layout SpringLayout
		topPanel = new JPanel();//this.getContentPane();		
		SpringLayout layoutTop = new SpringLayout();		
		topPanel.setLayout(layoutTop);
		
		chargerButton = new JButton("Charger fichier");		
		fieldFile = new JTextField();
		
		topPanel.add(chargerButton);
		topPanel.add(fieldFile);
		
		//Contrainte de position avec SpringLayout pour le bouton
		layoutTop.putConstraint(SpringLayout.WEST, chargerButton, 5, SpringLayout.WEST, topPanel);
		layoutTop.putConstraint(SpringLayout.NORTH, chargerButton, 5, SpringLayout.NORTH, topPanel);

		SpringUtilities.makeCompactGrid(topPanel, 1, 2, 6, 6, 6, 6);
		
		add(topPanel, BorderLayout.NORTH);
		
		ActionListener listener = new AddFileListener();
		chargerButton.addActionListener(listener);
		
	}
	
	/**
	 * Classe interne allant chercher le fichier ucd a charger.
	 * https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
	 */
	class AddFileListener implements ActionListener{
                private FileFilter FILTER = new FileNameExtensionFilter("UCD File", "ucd");
		@Override
		/**
		 * Gestion de l'action du bouton chargerButton:
		 * ouverture d'un file chooser à partir du repertoire courant de l'application
		 * et filtre de selection d'un fichier .ucd uniquement. 
		 * Sinon, ouverture d'une fenetre alertant l'extension ucd desiree.
		 */
		public void actionPerformed(ActionEvent e) {
			fileChooser.setFileFilter(FILTER);
			if (e.getSource() == chargerButton) {
				fileChooser.setCurrentDirectory(new java.io.File("."));
		        int returnVal = fileChooser.showOpenDialog(topPanel);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            file = fileChooser.getSelectedFile();
		            if(getExtension(file).equals("ucd")){
		            	fieldFile.setText(file.getName());
		            }else{
		            	JOptionPane.showMessageDialog(null, "Le fichier selectionne n'est pas un .ucd. Reessayez.");
		            }
		        }
		   } 		
		}
		
		/**
		 * @param f de type File, le fichier selectionne.
		 * @return ext de type String, l'extension du fichier.
		 */
		 public String getExtension(File f) {
		        String ext = null;
		        String s = f.getName();
		        int i = s.lastIndexOf('.');

		        if (i > 0 &&  i < s.length() - 1) {
		            ext = s.substring(i+1).toLowerCase();
		        }
		        return ext;
		 }
		
	}
	
	/**
	 * Creation du container de gauche qui affiche toutes les classes 
	 * du fichier prealablement selectionne.
	 */
	public void createLeftPanel(){
		String[] data = {"Bonjour", "lala", "lolo"};
		leftPanel = new JPanel();
		SpringLayout layoutLeft= new SpringLayout();		
		leftPanel.setLayout(layoutLeft);
		listClasses = new JList<>(data);
		
		listClasses.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listClasses.setLayoutOrientation(JList.VERTICAL);
		listClasses.setVisibleRowCount(-1);
		
		JScrollPane listScroller = new JScrollPane(listClasses);
                listScroller.setBorder(BorderFactory.createTitledBorder("Classes"));
		listScroller.setPreferredSize(new Dimension(130, 80));
		
		leftPanel.add(listScroller);

		SpringUtilities.makeCompactGrid(leftPanel, 1, 1, 6, 6, 6, 6);
		add(leftPanel, BorderLayout.WEST);
		
	}
	
	public void createCenteredPanel(){
		centeredPanel = new JPanel();
		centeredPanel.setLayout(new SpringLayout());
		
		listAttributes = new JList<>();
		listMethodes = new JList<>();
		listSubclasses = new JList<>();
		listRelations = new JList<>();
		
		JScrollPane listAttributesScroller = new JScrollPane(listAttributes);
		//listScroller1.setPreferredSize(new Dimension(80, 80));
		
		JScrollPane listMethodesScroller = new JScrollPane(listMethodes);
		//listScroller2.setPreferredSize(new Dimension(80, 80));
		
		JScrollPane listSubClassesScroller = new JScrollPane(listSubclasses);
		//listScroller3.setPreferredSize(new Dimension(80, 80));
		
		JScrollPane listRelationsScroller = new JScrollPane(listRelations);
		//listScroller4.setPreferredSize(new Dimension(80, 80));
		
                listAttributesScroller.setBorder(BorderFactory.createTitledBorder("Attributes"));
                listMethodesScroller.setBorder(BorderFactory.createTitledBorder("Methodes"));
                listSubClassesScroller.setBorder(BorderFactory.createTitledBorder("Subclasses"));
                listRelationsScroller.setBorder(BorderFactory.createTitledBorder("Association/Aggregation"));
                
                
		centeredPanel.add(listAttributesScroller);
		centeredPanel.add(listMethodesScroller);
		centeredPanel.add(listSubClassesScroller);
		centeredPanel.add(listRelationsScroller);
		
		SpringUtilities.makeCompactGrid(centeredPanel, 2, 2, 6, 6, 6, 6);

		add(centeredPanel, BorderLayout.CENTER);
	}
	
	public void createBottomPanel(){
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new SpringLayout());
		
		listDetails = new JList<>();
		JScrollPane listScroller = new JScrollPane(listDetails);

		bottomPanel.add(listScroller);
		SpringUtilities.makeCompactGrid(bottomPanel, 1, 1, 10, 10, 6, 6);

		add(bottomPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * @return file de type File, le fichier selectionne.
	 */
	public File getFile(){
		return file;
	}
	
	
}
