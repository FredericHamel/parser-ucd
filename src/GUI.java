import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
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


public class GUI extends JFrame{
	
	final JFileChooser fileChooser = new JFileChooser();
	
	//private Container topPanel;
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
		//Creer les composents
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

		@Override
		/**
		 * Gestion de l'action du bouton chargerButton:
		 * ouverture d'un file chooser à partir du repertoire courant de l'application
		 * et filtre de selection d'un fichier .ucd uniquement. 
		 * Sinon, ouverture d'une fenetre alertant l'extension ucd desiree.
		 */
		public void actionPerformed(ActionEvent e) {
			
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
		leftPanel = new JPanel();//this.getContentPane();
		SpringLayout layoutLeft= new SpringLayout();		
		leftPanel.setLayout(layoutLeft);
		listClasses = new JList<>(data);
		
		listClasses.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listClasses.setLayoutOrientation(JList.VERTICAL);
		listClasses.setVisibleRowCount(-1);
		
		JScrollPane listScroller = new JScrollPane(listClasses);
		listScroller.setPreferredSize(new Dimension(130, 80));
		
		leftPanel.add(listScroller);

		SpringUtilities.makeCompactGrid(leftPanel, 1, 1, 6, 6, 6, 6);
		add(leftPanel, BorderLayout.WEST);
		
	}
	
	public void createCenteredPanel(){
		centeredPanel = new JPanel();
		centeredPanel.setLayout(new SpringLayout());
		
		String[] attr = {"att", "att1", "att2"};
		String[] meth = {"meth", "meth2"};
		String[] sub = {"sub", "sub2"};
		String[] rel = {"rel", "rel2"};
		
		listAttributes = new JList<>(attr);
		listMethodes = new JList<>(meth);
		listSubclasses = new JList<>(sub);
		listRelations = new JList<>(rel);
		
		JScrollPane listScroller1 = new JScrollPane(listAttributes);
		//listScroller1.setPreferredSize(new Dimension(80, 80));
		
		JScrollPane listScroller2 = new JScrollPane(listMethodes);
		//listScroller2.setPreferredSize(new Dimension(80, 80));
		
		JScrollPane listScroller3 = new JScrollPane(listSubclasses);
		//listScroller3.setPreferredSize(new Dimension(80, 80));
		
		JScrollPane listScroller4 = new JScrollPane(listRelations);
		//listScroller4.setPreferredSize(new Dimension(80, 80));
		
		centeredPanel.add(listScroller1);
		centeredPanel.add(listScroller2);
		centeredPanel.add(listScroller3);
		centeredPanel.add(listScroller4);
		
		SpringUtilities.makeCompactGrid(centeredPanel, 2, 2, 6, 6, 6, 6);

		add(centeredPanel, BorderLayout.CENTER);
	}
	
	public void createBottomPanel(){
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new SpringLayout());
		
		String[] data = {"details", "details1"};
		
		listDetails = new JList<>(data);
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