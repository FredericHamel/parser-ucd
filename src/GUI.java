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

public class GUI extends JFrame{

    private final JFileChooser fileChooser = new JFileChooser();

    private JPanel topPanel,bottomPanel, centeredPanel,leftPanel;
    private JButton chargerButton;
    private JTextField fieldFile; 
    private JList<String> listClasses, listAttributes, listMethodes, listSubclasses, listRelations;
    private DefaultListModel<String> mClasses, mAttr, mMeth, mSubC, mRel;
    private JTextArea mDet;
    private int returnValue;

    private File file;

    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 700;

    private final Admin admin;

    public GUI(){
        this.admin = Admin.getInstance();

        //Creer les composants
        fileChooser.setFileFilter(new FileNameExtensionFilter("UCD File", "ucd"));

        createComponents();
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
        fieldFile.setEditable(false);
        
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
                    file = fileChooser.getSelectedFile();
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
                                    
                                    Map<String, String> m = admin.getRelations();
                                    Set<String> keySet = m.keySet();
                                    
                                    for(String key :keySet)
                                        mRel.addElement(key);
                                    throw new IOException();
                                }catch(IOException exception) {
                                    System.out.println(exception.getMessage()); 
                                }
                            }
                        });


                        //Effacer les anciennes donnees



                    }else{
                        JOptionPane.showMessageDialog(null, "Le fichier selectionne n'est pas un .ucd. Reessayez.");
                    }
                }
            } 		
        }


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
     */
    public void createLeftPanel(){
        SpringLayout layoutLeft= new SpringLayout();
        mClasses = new DefaultListModel<>();
        leftPanel = new JPanel();

        leftPanel.setLayout(layoutLeft);
        listClasses = new JList<>(mClasses);

        listClasses.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listClasses.setLayoutOrientation(JList.VERTICAL);
        listClasses.setVisibleRowCount(-1);

        listClasses.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent event) {
                System.out.println("Class: " + listClasses.getSelectedValue());
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
        listScroller.setPreferredSize(new Dimension(130, 80));
        addTitle(listScroller, "Classes");
        leftPanel.add(listScroller);

        SpringUtilities.makeCompactGrid(leftPanel, 1, 1, 6, 6, 6, 6);
        add(leftPanel, BorderLayout.WEST);

    }

    public void createCenteredPanel(){
        centeredPanel = new JPanel();
        centeredPanel.setLayout(new SpringLayout());

        mAttr = new DefaultListModel<>();
        mMeth = new DefaultListModel<>();
        mSubC = new DefaultListModel<>();
        mRel = new DefaultListModel<>();

        listAttributes = new JList<>(mAttr);
        listMethodes = new JList<>(mMeth);
        listSubclasses = new JList<>(mSubC);
        listRelations = new JList<>(mRel);

        listRelations.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            mDet.setText(admin.getRelations().get(listRelations.getSelectedValue()));
                        }
                    });
                    //Get the name of relation selected --> listRelations.getSelectedValue().toString());
                }
            }
        });

        JScrollPane listScroller1 = new JScrollPane(listAttributes);
        addTitle(listScroller1, "Attributes");
        //listScroller1.setPreferredSize(new Dimension(80, 80));

        JScrollPane listScroller2 = new JScrollPane(listMethodes);
        addTitle(listScroller2, "Methodes");
        //listScroller2.setPreferredSize(new Dimension(80, 80));

        JScrollPane listScroller3 = new JScrollPane(listSubclasses);
        addTitle(listScroller3, "SubClasses");
        //listScroller3.setPreferredSize(new Dimension(80, 80));

        JScrollPane listScroller4 = new JScrollPane(listRelations);
        addTitle(listScroller4, "Association/Aggregation");
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

        mDet = new JTextArea();
        mDet.setEditable(false);
        JScrollPane listScroller = new JScrollPane(mDet);
        addTitle(listScroller, "Details");

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


    private void addTitle(JComponent comp, String title) {
        comp.setBorder(BorderFactory.createTitledBorder(title));
    }

}
