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
import javax.swing.event.*;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends JFrame{

    final JFileChooser fileChooser = new JFileChooser();

    private JPanel topPanel,bottomPanel, centeredPanel,leftPanel;
    private JButton chargerButton;
    private JTextField fieldFile; 
    private JList<String> listClasses, listAttributes, listMethodes, listSubclasses, listRelations, listDetails;
    DefaultListModel<String> mClasses, mAttr, mMeth, mSubC, mRel, mDet;
    private int returnValue;

    private File file;

    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 700;

    public GUI(){
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

            fieldFile.setText("");
            mClasses.clear();
            mAttr.clear();
            mMeth.clear();
            mSubC.clear();
            mRel.clear();
            mDet.clear();

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
        mClasses = new DefaultListModel<String>();
        for (int i = 0;i < data.length; i++) {
            mClasses.addElement(data[i]);
        }

        leftPanel = new JPanel();
        SpringLayout layoutLeft= new SpringLayout();		
        leftPanel.setLayout(layoutLeft);
        listClasses = new JList<>(mClasses);
        

        listClasses.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listClasses.setLayoutOrientation(JList.VERTICAL);
        listClasses.setVisibleRowCount(-1);

        listClasses.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    //get the name of the class selected --> listClasses.getSelectedValue().toString();
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

        String[] attr = {"att", "att1", "att2"};
        String[] meth = {"meth", "meth2"};
        String[] sub = {"sub", "sub2"};
        String[] rel = {"rel", "rel2"};

        int i;
        mAttr = new DefaultListModel<>();
        for (i = 0;i < attr.length; i++) {
            mAttr.addElement(attr[i]);
        }

        mMeth = new DefaultListModel<>();
        for (i = 0;i < meth.length; i++) {
            mMeth.addElement(meth[i]);
        }

        mSubC = new DefaultListModel<>();
        for (i = 0;i < sub.length; i++) {
            mSubC.addElement(sub[i]);
        }

        mRel = new DefaultListModel<>();
        for (i = 0;i < rel.length; i++) {
            mRel.addElement(rel[i]);
        }

        listAttributes = new JList<>(mAttr);
        listMethodes = new JList<>(mMeth);
        listSubclasses = new JList<>(mSubC);
        listRelations = new JList<>(mRel);

        listRelations.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
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

        String[] data = {"details", "details1"};
        mDet = new DefaultListModel<>();
        for (int i = 0;i < data.length; i++) {
            mDet.addElement(data[i]);
        }

        listDetails = new JList<>(mDet);
        JScrollPane listScroller = new JScrollPane(listDetails);
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
