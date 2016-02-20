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

    private JPanel topPanel,bottomPanel, centeredPanel,leftPanel;
    private JButton chargerButton;
    private JTextField fieldFile; 
    private JList<String> listClasses, listAttributes, listMethodes, listSubclasses, listRelations;
    private DefaultListModel<String> mClasses, mAttr, mMeth, mSubC, mRel;
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
        
        topPanel.setMinimumSize(new Dimension(0, 30));
        topPanel.setMaximumSize(new Dimension(0, 30));
        topPanel.add(chargerButton);
        topPanel.add(fieldFile);
        
        //Contrainte de position avec SpringLayout pour le bouton
        layoutTop.putConstraint(SpringLayout.WEST, chargerButton, 5, SpringLayout.WEST, topPanel);
        layoutTop.putConstraint(SpringLayout.NORTH, chargerButton, 5, SpringLayout.NORTH, topPanel);
        
        SpringUtilities.makeCompactGrid(topPanel, 1, 2, 6, 6, 6, 6);
        topPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        //add(topPanel, BorderLayout.NORTH);
        content.add(topPanel);
        ActionListener listener = new AddFileListener();
        chargerButton.addActionListener(listener);

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

        public boolean compareFile(File newFile, File currentFile){
        	String path1 = newFile.getAbsolutePath();
            String path2 = currentFile.getAbsolutePath();

            if (path1.equals(path2)) {
			    //System.out.println("The paths locate the same file!");
			    return true;
			} else {
			    //System.out.println("The paths does not locate the same file!");
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
        mClasses = new DefaultListModel<>();

        listClasses = new JList<>(mClasses);
        listClasses.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));

        listClasses.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listClasses.setLayoutOrientation(JList.VERTICAL);
        listClasses.setVisibleRowCount(-1);
        
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
        centeredPanel.add(listScroller);
        

        mAttr = new DefaultListModel<>();
        mMeth = new DefaultListModel<>();
        mSubC = new DefaultListModel<>();

        listAttributes = new JList<>(mAttr);
        listAttributes.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));

        listMethodes = new JList<>(mMeth);
        listMethodes.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
        
        listSubclasses = new JList<>(mSubC);
        listSubclasses.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));

        JScrollPane listScroller1 = new JScrollPane(listAttributes);
        addTitle(listScroller1, "Attributs");

        JScrollPane listScroller2 = new JScrollPane(listMethodes);
        addTitle(listScroller2, "Methodes");

        JScrollPane listScroller3 = new JScrollPane(listSubclasses);
        addTitle(listScroller3, "Sous-classes");

        centeredPanel.add(listScroller1);
        centeredPanel.add(listScroller2);
        centeredPanel.add(listScroller3);

        SpringUtilities.makeCompactGrid(centeredPanel, 1, 4, 6, 6, 6, 6);
        centeredPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        content.add(centeredPanel);
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
        bottomPanel.setMinimumSize(new Dimension(80, 80));
        
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
