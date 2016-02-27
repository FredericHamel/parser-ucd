import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.event.*;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import java.awt.Container;
import java.awt.Color;

public class GUI extends JFrame{

    private final JFileChooser fileChooser = new JFileChooser();

    private JPanel topPanel,bottomPanel, centeredPanel,leftPanel, firstCenteredPanel, firstInfoPanel, secondInfoPanel, rightPanel;
    private JButton chargerButton, metriquesButton;
    private JTextField fieldFile; 
    private JList<String> listClasses, listAttributes, listMethodes, listSubclasses, listRelations, listMetriques;
    private DefaultListModel<String> mClasses, mAttr, mMeth, mSubC, mRel, mMetriques;
    private JTextArea mDet;
    private int returnValue;
    private Container content;
    private SpringLayout layout;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    
    private File file, fileSelected;

    private final Admin admin;

    public GUI(){
        this.admin = Admin.getInstance();

        //Creer les composants
        fileChooser.setFileFilter(new FileNameExtensionFilter("UCD File", "ucd"));
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        
        this.content = new JPanel();
        layout = new SpringLayout();
        content.setLayout(layout);
        
        createComponents();
    }
    
    /**
     * Appel des methodes creatrices des differents panels
     */
    private void createComponents(){

        createTopPanel();
        createCenteredPanel();
        createBottomPanel();
        
        SpringUtilities.makeCompactGrid(content, 3, 1, 6, 6, 6, 6);
        this.add(content, BorderLayout.CENTER);
    }

    /**
     * Creation du topPanel qui contient le bouton de chargement
     * du fichier et un textField contenant le nom du fichier.
     * https://docs.oracle.com/javase/tutorial/uiswing/layout/spring.html
     */
    public void createTopPanel(){

        //Creation du Container topPanel et utilisation du layout SpringLayout
        topPanel = new JPanel();	
        SpringLayout layoutTop = new SpringLayout();		
        topPanel.setLayout(layoutTop);

        chargerButton = new JButton("Charger fichier");		
        fieldFile = new JTextField(20);
        fieldFile.setEditable(false);
        metriquesButton = new JButton("Charger métriques");
        
        topPanel.setMinimumSize(new Dimension(0, 30));
        topPanel.setMaximumSize(new Dimension(0, 30));
        topPanel.add(chargerButton);
        topPanel.add(fieldFile);
        topPanel.add(metriquesButton);
        
        //Contrainte de position avec SpringLayout pour le bouton
        layoutTop.putConstraint(SpringLayout.WEST, chargerButton, 5, SpringLayout.WEST, topPanel);
        layoutTop.putConstraint(SpringLayout.NORTH, chargerButton, 5, SpringLayout.NORTH, topPanel);
        layoutTop.putConstraint(SpringLayout.WEST, metriquesButton, 8, SpringLayout.EAST, topPanel);
        layoutTop.putConstraint(SpringLayout.NORTH, metriquesButton, 5, SpringLayout.NORTH, topPanel);
        
        SpringUtilities.makeCompactGrid(topPanel, 1, 3, 6, 6, 6, 6);
        topPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        content.add(topPanel);

        ActionListener listener = new AddFileListener();
        chargerButton.addActionListener(listener);
        
        ActionListener listenerMetriques = new AddMetriquesListener();
        metriquesButton.addActionListener(listenerMetriques);
    }
    
    private class AddMetriquesListener implements ActionListener{
    	
    	@Override
    	public void actionPerformed(ActionEvent e){
    		
    	}
    }

    /**
     * Classe interne allant chercher le fichier ucd a charger.
     * https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
     */
    private class AddFileListener implements ActionListener{

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
                    fileSelected = fileChooser.getSelectedFile();
                    boolean same;
                    if(file == null){
                    	same = false;
                    }else{
                    	same = compareFile(fileSelected, file);
                    }
                    if(!same){
                    	file = fileSelected;
                    	if(getExtension(file).equals("ucd")){

                            SwingUtilities.invokeLater(new Runnable() {
                            	
                            	/**
                            	 * Contact entre le GUI et l'admin.
                            	 * Demande a l'admin d'aller chercher les informations
                            	 * necessaires a afficher.
                            	 */
                                @Override
                                public void run() {
                                    String fn;
                                    fn = file.getAbsolutePath();
                                    fieldFile.setText(file.getName());
                                    try{
                                        admin.parseModel(fn);

                                        clearList();

                                        ArrayList<String> classes = admin.getClassesName();
                                        for(String c : classes)
                                            mClasses.addElement(c);
                                        listClasses.addSelectionInterval(0, 0);//Premier item selectionne
                                        
                                        Map<String, String> m = admin.getRelations();
                                        Set<String> keySet = m.keySet();
                                        for(String key :keySet)
                                            mRel.addElement(key);
                                        listRelations.addSelectionInterval(0, 0);//Premier item selectionne
                                        
                                    }catch(IOException exception) {
                                        System.out.println(exception.getMessage()); 
                                    }
                                }
                            });
                        }else{
                            JOptionPane.showMessageDialog(null, "Le fichier selectionne n'est pas un .ucd. Reessayez.");
                        }	
                    }
                }
            } 		
        }

        /**
         * Comparaison des paths de fichiers.
         * Permet de distinguer si c'est un nouveau fichier qui est charge
         * ou non, selon le path.
         */
        public boolean compareFile(File newFile, File currentFile){
        	String path1 = newFile.getAbsolutePath();
            String path2 = currentFile.getAbsolutePath();

            if (path1.equals(path2)) {
			    return true;
			} else {
			    return false;
			}
        }
        
        /**
         * Vider les listes, textfields et textarea.
         */
        private void clearList() {
            mClasses.clear();
            mAttr.clear();
            mMeth.clear();
            mSubC.clear();
            mRel.clear();
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
     * Creation du panel de centre/est qui est compose des listes
     * d'attributs, de methodes et de sous classes correspondant
     * a la classe selectionnee dans la liste des classes.
     */
    public void createCenteredPanel(){
    	centeredPanel = new JPanel();
        centeredPanel.setLayout(new SpringLayout());
        
        centeredPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        
    	createClassePanel();
    	createInfoPanel();
    	createRightPanel();
    	
    	SpringUtilities.makeCompactGrid(centeredPanel, 1, 3, 6, 6, 6, 6);
    	
        content.add(centeredPanel);
    }
    
    /**
     * Creation du panneau de gauche contenant la liste des classes.
     * S'ajoute au panneau du centre: centeredPanel.
     */
    public void createClassePanel(){

        mClasses = new DefaultListModel<>();

        listClasses = new JList<>(mClasses);
        listClasses.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));

        listClasses.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listClasses.setLayoutOrientation(JList.VERTICAL);
        listClasses.setVisibleRowCount(-1);
        
        //ActionListener appele lors d'un clic sur une selection de nom de classe.
        listClasses.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            admin.search(listClasses.getSelectedValue());
                            mAttr.clear();
                            mMeth.clear();
                            mSubC.clear();
                            for(String attr: admin.getAttributesOfCurrentClass())
                                mAttr.addElement(attr);
                            for(String mth:admin.getMethodsOfCurrentClass())
                                mMeth.addElement(mth);
                            for(String subc : admin.getSubClasses())
                                mSubC.addElement(subc);
                        }
                    });
                    
                }
            }
        });

        JScrollPane listScroller = new JScrollPane(listClasses);
        addTitle(listScroller, "Classes");
        listScroller.setMaximumSize(new Dimension(40, 60));
        centeredPanel.add(listScroller);
    }
    	
    /**
     * Creation d'un panneau d'une ligne et deux colonnes regroupant attributs et sous-classe.
     * Creation d'un panneau plus large contenant les methodes.
     * Creation d'un troisieme panneau de deux lignes et une colonne qui regroupe les deux
     * derniers panneau.
     * Ce dernier panneau est ajoute a centeredPanel.
     */
    public void createInfoPanel(){
    	firstCenteredPanel = new JPanel();
    	firstCenteredPanel.setLayout(new SpringLayout());
    	
    	//Panneau regroupant les informations sur les attributs et sous-classes.
    	firstInfoPanel = new JPanel();
    	firstInfoPanel.setLayout(new SpringLayout());
        
        mAttr = new DefaultListModel<>();
        listAttributes = new JList<>(mAttr);
        listAttributes.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
        JScrollPane listScroller1 = new JScrollPane(listAttributes);
        addTitle(listScroller1, "Attributs");
        
        mSubC = new DefaultListModel<>();
        listSubclasses = new JList<>(mSubC);
        listSubclasses.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
        JScrollPane listScroller2 = new JScrollPane(listSubclasses);
        addTitle(listScroller2, "Sous-classes");
        
        firstInfoPanel.add(listScroller1);
        firstInfoPanel.add(listScroller2);
        SpringUtilities.makeCompactGrid(firstInfoPanel, 1, 2, 6, 6, 6, 6);

        firstCenteredPanel.add(firstInfoPanel);
        
        //Panneau plus large pour un meilleur affichage des methodes
    	secondInfoPanel = new JPanel();
    	secondInfoPanel.setLayout(new SpringLayout());

    	mMeth = new DefaultListModel<>();
        listMethodes = new JList<>(mMeth);
        listMethodes.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
        JScrollPane listScroller3 = new JScrollPane(listMethodes);
        addTitle(listScroller3, "Methodes");
        listScroller3.setMinimumSize(new Dimension(120, 80));
        
        //Ajout de la liste de methodes au panneau secondInfoPanel
        secondInfoPanel.add(listScroller3);
        SpringUtilities.makeCompactGrid(secondInfoPanel, 1, 1, 6, 6, 6, 6);

        //Ajout du panneau des methodes au panneau qui regroupe attribut, sous-classe et methode
        firstCenteredPanel.add(secondInfoPanel);
        SpringUtilities.makeCompactGrid(firstCenteredPanel, 2, 1, 6, 6, 6, 6);

        //Ajout du panneau de groupe au panneau central qui contient les classes.
        centeredPanel.add(firstCenteredPanel);
    }

    
    public void createRightPanel(){
    	rightPanel = new JPanel();
    	rightPanel.setLayout(new SpringLayout());
    	rightPanel.setMinimumSize(new Dimension(80, 40));
    	
    	mMetriques = new DefaultListModel<>();
    	listMetriques = new JList<>(mMetriques);
    	listMetriques.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));

    	listMetriques.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    	listMetriques.setLayoutOrientation(JList.VERTICAL);
    	listMetriques.setVisibleRowCount(-1);
    	
    	JScrollPane listScroller = new JScrollPane(listMetriques);
        addTitle(listScroller, "Métriques");
        listScroller.setMaximumSize(new Dimension(40, 60));
        rightPanel.add(listScroller);
        SpringUtilities.makeCompactGrid(rightPanel, 1, 1, 6, 6, 6, 6);
        centeredPanel.add(rightPanel);
    }
    

    /**
     * Creation du panel composant le bas du GUI:
     * liste des relations ou chaque element est selectionnable et 
     * textearea contenant les details des relations correspondantes.
     */
    public void createBottomPanel(){
    	//Composant du bas
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new SpringLayout());
        bottomPanel.setMinimumSize(new Dimension(80, 40));
        
        //Liste des relations du model
        mRel = new DefaultListModel<>();
        listRelations = new JList<>(mRel);
        listRelations.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listRelations.setLayoutOrientation(JList.VERTICAL);
        listRelations.setVisibleRowCount(-1);
        listRelations.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
        JScrollPane listScrollerRelation = new JScrollPane(listRelations);
        addTitle(listScrollerRelation, "Association/Aggregation");
        listScrollerRelation.setMaximumSize(new Dimension(40, 80));
        
        //Liste des details de relations
        mDet = new JTextArea();
        mDet.setEditable(false);
        mDet.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
        JScrollPane listScroller = new JScrollPane(mDet);
        addTitle(listScroller, "Details");
        listScroller.setPreferredSize(new Dimension(80, 80));
        listScroller.getViewport().setBackground(Color.blue);

        
        /**
         * Listener de la liste des relations
         */
        listRelations.addListSelectionListener(new ListSelectionListener() {

        	/**
        	 * Detection du changement de selection de la liste des relations.
        	 * Affichage des details correspondants a la relation selectionnee.
        	 */
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                        	//Affichage des details de la relation
                            mDet.setText(admin.getRelations().get(listRelations.getSelectedValue()));
                        }
                    });
                }
            }
        });
        
        //Ajout de la liste des relations et des details au panel du bas
        bottomPanel.add(listScrollerRelation);
        bottomPanel.add(listScroller);
        SpringUtilities.makeCompactGrid(bottomPanel, 1, 2, 5, 5, 6, 6);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        //Ajout du panel du bas au frame principal
        content.add(bottomPanel);
    }
    

    /**
     * @return file de type File, le fichier selectionne.
     */
    public File getFile(){
        return file;
    }

    /**
     * Ajouter un titre aux differents composants
     */
    private void addTitle(JComponent comp, String title) {
        comp.setBorder(BorderFactory.createTitledBorder(title));
        comp.setOpaque(false);
    }

}
