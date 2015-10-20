package gui;

import event.Event;
import excel_manager.Loader;
import utils.Mailer;
import utils.Person;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class FrontEnd1 implements ActionListener, PropertyChangeListener {

    private static String[] provinceArray = {"CS", "KR", "CZ", "VV", "RC"}, mesiArray = {"gennaio", "febbraio",
            "marzo", "aprile", "maggio", "giugno", "luglio", "agosto", "settembre", "ottobre", "novembre", "dicembre"},
            giorniArray, anniArray, oreArray, minutiArray;
    private JFrame frame;
    private JTextField textField;
    private JPanel panelIniziale;
    private JPanel panelCreaEvento;
    private String inputFilePath, outputFolderPath, excelOutputFilePath, txtOutputFilePath;
    private JTextField textFieldNome;
    private JTextField textFieldLuogo;
    private JTextField textFieldEmail;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JTextField textFieldSMTP;
    private JTextField textFieldSMTPPort;
    private File file;
    private Event evento;
    private LinkedList<Person> l;
    private Mailer mailer;
    private JTextField textField_1;
    private JTextField textField_2;
    private JPanel panelCaricaPartecipanti;
    private JButton btnNewButton_5;
    private JProgressBar progressBar;

    /**
     * Create the application.
     */
    public FrontEnd1() {
        initialize();
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        giorniArray = new String[31];
        anniArray = new String[50];
        oreArray = new String[24];
        minutiArray = new String[60];

        for (int i = 1; i < 32; i++) {
            giorniArray[i - 1] = Integer.toString(i);
        }

        int j = 0;
        for (int i = 2000; i < 2050; i++) {
            anniArray[j++] = Integer.toString(i);
        }

        for (int i = 0; i < 60; i++) {
            minutiArray[i] = Integer.toString(i);
        }

        for (int i = 0; i < 24; i++) {
            oreArray[i] = Integer.toString(i);
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrontEnd1 window = new FrontEnd1();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("SkAppA");
        frame.setBounds(100, 100, 750, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*  */
        ImageIcon imIcon = new
                ImageIcon(this.getClass().getClassLoader().getResource("res/logo.png"));
        //ImageIcon imIcon = new ImageIcon("C:\\Users\\davem\\workspace\\Skappa\\res\\logo.png");
        Image image = imIcon.getImage();
        Image newImage = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        imIcon = new ImageIcon(newImage);
        frame.getContentPane().setLayout(new CardLayout(0, 0));

        panelIniziale = new JPanel();
        frame.getContentPane().add(panelIniziale, "name_10245570710976");
        panelIniziale.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panelIniziale.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JPanel panelInviaMail = new JPanel();
        frame.getContentPane().add(panelInviaMail, "name_74765522122111");
        frame.setBounds(100, 100, 750, 400);
        panelInviaMail.setLayout(new BorderLayout(0, 0));

        JPanel panel_6 = new JPanel();
        panelInviaMail.add(panel_6, BorderLayout.CENTER);
        GridBagLayout gbl_panel_6 = new GridBagLayout();
        gbl_panel_6.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel_6.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel_6.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_6.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel_6.setLayout(gbl_panel_6);

        JLabel lblIndirizzoEmail = new JLabel("Indirizzo email");
        GridBagConstraints gbc_lblIndirizzoEmail = new GridBagConstraints();
        gbc_lblIndirizzoEmail.insets = new Insets(0, 0, 5, 5);
        gbc_lblIndirizzoEmail.gridx = 3;
        gbc_lblIndirizzoEmail.gridy = 1;
        panel_6.add(lblIndirizzoEmail, gbc_lblIndirizzoEmail);

        textFieldEmail = new JTextField();
        GridBagConstraints gbc_textFieldEmail = new GridBagConstraints();
        gbc_textFieldEmail.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldEmail.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldEmail.gridx = 5;
        gbc_textFieldEmail.gridy = 1;
        panel_6.add(textFieldEmail, gbc_textFieldEmail);
        textFieldEmail.setColumns(10);

        JLabel lblUsername = new JLabel("Username");
        GridBagConstraints gbc_lblUsername = new GridBagConstraints();
        gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
        gbc_lblUsername.gridx = 3;
        gbc_lblUsername.gridy = 3;
        panel_6.add(lblUsername, gbc_lblUsername);

        textFieldUsername = new JTextField();
        GridBagConstraints gbc_textFieldUsername = new GridBagConstraints();
        gbc_textFieldUsername.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldUsername.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldUsername.gridx = 5;
        gbc_textFieldUsername.gridy = 3;
        panel_6.add(textFieldUsername, gbc_textFieldUsername);
        textFieldUsername.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        GridBagConstraints gbc_lblPassword = new GridBagConstraints();
        gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
        gbc_lblPassword.gridx = 3;
        gbc_lblPassword.gridy = 5;
        panel_6.add(lblPassword, gbc_lblPassword);

        passwordField = new JPasswordField();
        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.insets = new Insets(0, 0, 5, 5);
        gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
        gbc_passwordField.gridx = 5;
        gbc_passwordField.gridy = 5;
        panel_6.add(passwordField, gbc_passwordField);

        JLabel lblIndirizzoServerSmtp = new JLabel("Indirizzo server smtp");
        GridBagConstraints gbc_lblIndirizzoServerSmtp = new GridBagConstraints();
        gbc_lblIndirizzoServerSmtp.insets = new Insets(0, 0, 5, 5);
        gbc_lblIndirizzoServerSmtp.gridx = 3;
        gbc_lblIndirizzoServerSmtp.gridy = 7;
        panel_6.add(lblIndirizzoServerSmtp, gbc_lblIndirizzoServerSmtp);

        textFieldSMTP = new JTextField();
        GridBagConstraints gbc_textFieldSMTP = new GridBagConstraints();
        gbc_textFieldSMTP.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldSMTP.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldSMTP.gridx = 5;
        gbc_textFieldSMTP.gridy = 7;
        panel_6.add(textFieldSMTP, gbc_textFieldSMTP);
        textFieldSMTP.setColumns(10);

        JLabel lblPortaServerSmtp = new JLabel("Porta server smtp");
        GridBagConstraints gbc_lblPortaServerSmtp = new GridBagConstraints();
        gbc_lblPortaServerSmtp.insets = new Insets(0, 0, 5, 5);
        gbc_lblPortaServerSmtp.gridx = 3;
        gbc_lblPortaServerSmtp.gridy = 9;
        panel_6.add(lblPortaServerSmtp, gbc_lblPortaServerSmtp);

        textFieldSMTPPort = new JTextField();
        GridBagConstraints gbc_textFieldSMTPPort = new GridBagConstraints();
        gbc_textFieldSMTPPort.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldSMTPPort.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldSMTPPort.gridx = 5;
        gbc_textFieldSMTPPort.gridy = 9;
        panel_6.add(textFieldSMTPPort, gbc_textFieldSMTPPort);
        textFieldSMTPPort.setColumns(10);

        progressBar = new JProgressBar();
        GridBagConstraints gbc_progressBar = new GridBagConstraints();
        gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_progressBar.insets = new Insets(0, 0, 5, 5);
        gbc_progressBar.gridx = 5;
        gbc_progressBar.gridy = 11;
        panel_6.add(progressBar, gbc_progressBar);
        progressBar.setVisible(false);
        
        

        JPanel panel_7 = new JPanel();
        panelInviaMail.add(panel_7, BorderLayout.NORTH);

        JLabel lblInserisciCredenziali = new JLabel("Inserisci credenziali");
        lblInserisciCredenziali.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panel_7.add(lblInserisciCredenziali);

        JPanel panel_8 = new JPanel();
        panelInviaMail.add(panel_8, BorderLayout.SOUTH);

        btnNewButton_5 = new JButton("Invia");
        btnNewButton_5.setPreferredSize(new Dimension(70, 25));
        btnNewButton_5.addActionListener(this); 
       
        panel_8.add(btnNewButton_5);


        JButton btnNewButton_6 = new JButton("Annulla");
        btnNewButton_6.setPreferredSize(new Dimension(70, 25));
        btnNewButton_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelInviaMail.setVisible(false);
                panelCreaEvento.setVisible(true);
                frame.setBounds(100, 100, 750, 400);
            }
        });
        panel_8.add(btnNewButton_6);

        JButton btnNewButton = new JButton("Crea evento");
        btnNewButton.setPreferredSize(new Dimension(150, 30));
        btnNewButton.setMinimumSize(new Dimension(150, 30));
        btnNewButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                panelCreaEvento.setVisible(true);
                panelIniziale.setVisible(false);
                // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setBounds(100, 100, 750, 400);
            }
        });
        panel.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Carica partecipanti");
        btnNewButton_1.setPreferredSize(new Dimension(150, 30));
        btnNewButton_1.setMinimumSize(new Dimension(150, 30));
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCaricaPartecipanti.setVisible(true);
                panelIniziale.setVisible(false);
            }
        });
        panel.add(btnNewButton_1);

        JPanel panel_1 = new JPanel();
        panelIniziale.add(panel_1, BorderLayout.CENTER);
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
        JTextPane txtpnSelezionaLaModalit = new JTextPane();
        txtpnSelezionaLaModalit.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        txtpnSelezionaLaModalit.setEnabled(false);
        txtpnSelezionaLaModalit.setEditable(false);
        txtpnSelezionaLaModalit.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtpnSelezionaLaModalit.setText("Seleziona la modalit\u00E0");
        txtpnSelezionaLaModalit.setParagraphAttributes(attribs, true);
        panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
        panel_1.add(txtpnSelezionaLaModalit);

        JPanel panel_2 = new JPanel();
        panelIniziale.add(panel_2, BorderLayout.NORTH);

        JLabel lblLogo = new JLabel("");
        lblLogo.setIcon(imIcon);
        panel_2.add(lblLogo);

        panelCreaEvento = new JPanel();
        frame.getContentPane().add(panelCreaEvento, "name_10426205657299");

        JPanel panel_5 = new JPanel();

        JPanel panel_3 = new JPanel();
        panelCreaEvento.setLayout(new BorderLayout(0, 0));
        panelCreaEvento.add(panel_5);
        GridBagLayout gbl_panel_5 = new GridBagLayout();
        gbl_panel_5.columnWidths = new int[]{64, 66, 67, 0, 0, 0, 0, 0, 65, 0, 0, 0, 0, 0, 0, 81, 48, 43, 55, 0};
        gbl_panel_5.rowHeights = new int[]{0, 0, 30, 20, 35, 20, 35, 20, 0, 0};
        gbl_panel_5.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_5.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel_5.setLayout(gbl_panel_5);

        Component verticalStrut_1 = Box.createVerticalStrut(20);
        GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
        gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut_1.gridx = 13;
        gbc_verticalStrut_1.gridy = 0;
        panel_5.add(verticalStrut_1, gbc_verticalStrut_1);

        Component verticalStrut_2 = Box.createVerticalStrut(20);
        GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
        gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut_2.gridx = 13;
        gbc_verticalStrut_2.gridy = 1;
        panel_5.add(verticalStrut_2, gbc_verticalStrut_2);

        JLabel lblInserisciDatiEvento = new JLabel("Inserisci dati evento");
        lblInserisciDatiEvento.setFont(new Font("Tahoma", Font.PLAIN, 18));
        GridBagConstraints gbc_lblInserisciDatiEvento = new GridBagConstraints();
        gbc_lblInserisciDatiEvento.gridwidth = 2;
        gbc_lblInserisciDatiEvento.insets = new Insets(0, 0, 5, 5);
        gbc_lblInserisciDatiEvento.gridx = 1;
        gbc_lblInserisciDatiEvento.gridy = 2;
        panel_5.add(lblInserisciDatiEvento, gbc_lblInserisciDatiEvento);

        JLabel lblNomeEvento = new JLabel("Nome evento");
        lblNomeEvento.setFont(new Font("Tahoma", Font.PLAIN, 13));
        GridBagConstraints gbc_lblNomeEvento = new GridBagConstraints();
        gbc_lblNomeEvento.gridwidth = 2;
        gbc_lblNomeEvento.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNomeEvento.insets = new Insets(0, 0, 5, 5);
        gbc_lblNomeEvento.gridx = 1;
        gbc_lblNomeEvento.gridy = 3;
        panel_5.add(lblNomeEvento, gbc_lblNomeEvento);

        textFieldNome = new JTextField();
        textFieldNome.setColumns(10);
        GridBagConstraints gbc_textFieldNome = new GridBagConstraints();
        gbc_textFieldNome.anchor = GridBagConstraints.NORTH;
        gbc_textFieldNome.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldNome.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldNome.gridwidth = 5;
        gbc_textFieldNome.gridx = 3;
        gbc_textFieldNome.gridy = 3;
        panel_5.add(textFieldNome, gbc_textFieldNome);

        JLabel lblLuogoEvento = new JLabel("Luogo evento");
        lblLuogoEvento.setFont(new Font("Tahoma", Font.PLAIN, 13));
        GridBagConstraints gbc_lblLuogoEvento = new GridBagConstraints();
        gbc_lblLuogoEvento.gridwidth = 2;
        gbc_lblLuogoEvento.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblLuogoEvento.insets = new Insets(0, 0, 5, 5);
        gbc_lblLuogoEvento.gridx = 1;
        gbc_lblLuogoEvento.gridy = 5;
        panel_5.add(lblLuogoEvento, gbc_lblLuogoEvento);

        textFieldLuogo = new JTextField();
        textFieldLuogo.setColumns(10);
        GridBagConstraints gbc_textFieldLuogo = new GridBagConstraints();
        gbc_textFieldLuogo.anchor = GridBagConstraints.NORTH;
        gbc_textFieldLuogo.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldLuogo.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldLuogo.gridwidth = 5;
        gbc_textFieldLuogo.gridx = 3;
        gbc_textFieldLuogo.gridy = 5;
        panel_5.add(textFieldLuogo, gbc_textFieldLuogo);

        JLabel lblProvincia = new JLabel("Provincia");
        GridBagConstraints gbc_lblProvincia = new GridBagConstraints();
        gbc_lblProvincia.anchor = GridBagConstraints.WEST;
        gbc_lblProvincia.insets = new Insets(0, 0, 5, 5);
        gbc_lblProvincia.gridx = 9;
        gbc_lblProvincia.gridy = 5;
        panel_5.add(lblProvincia, gbc_lblProvincia);

        JComboBox comboBoxProvincia = new JComboBox(provinceArray);
        GridBagConstraints gbc_comboBoxProvincia = new GridBagConstraints();
        gbc_comboBoxProvincia.anchor = GridBagConstraints.NORTHWEST;
        gbc_comboBoxProvincia.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxProvincia.gridx = 10;
        gbc_comboBoxProvincia.gridy = 5;
        panel_5.add(comboBoxProvincia, gbc_comboBoxProvincia);

        JLabel lblDataEvento = new JLabel("Data evento");
        lblDataEvento.setFont(new Font("Tahoma", Font.PLAIN, 13));
        GridBagConstraints gbc_lblDataEvento = new GridBagConstraints();
        gbc_lblDataEvento.gridwidth = 2;
        gbc_lblDataEvento.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblDataEvento.insets = new Insets(0, 0, 0, 5);
        gbc_lblDataEvento.gridx = 1;
        gbc_lblDataEvento.gridy = 7;
        panel_5.add(lblDataEvento, gbc_lblDataEvento);

        JComboBox comboBoxGiorno = new JComboBox(giorniArray);
        GridBagConstraints gbc_comboBoxGiorno = new GridBagConstraints();
        gbc_comboBoxGiorno.anchor = GridBagConstraints.NORTHWEST;
        gbc_comboBoxGiorno.insets = new Insets(0, 0, 0, 5);
        gbc_comboBoxGiorno.gridx = 3;
        gbc_comboBoxGiorno.gridy = 7;
        panel_5.add(comboBoxGiorno, gbc_comboBoxGiorno);

        JComboBox comboBoxMese = new JComboBox(mesiArray);
        GridBagConstraints gbc_comboBoxMese = new GridBagConstraints();
        gbc_comboBoxMese.anchor = GridBagConstraints.NORTHWEST;
        gbc_comboBoxMese.insets = new Insets(0, 0, 0, 5);
        gbc_comboBoxMese.gridx = 4;
        gbc_comboBoxMese.gridy = 7;
        panel_5.add(comboBoxMese, gbc_comboBoxMese);

        JComboBox comboBoxAnno = new JComboBox(anniArray);
        GridBagConstraints gbc_comboBoxAnno = new GridBagConstraints();
        gbc_comboBoxAnno.insets = new Insets(0, 0, 0, 5);
        gbc_comboBoxAnno.anchor = GridBagConstraints.NORTHEAST;
        gbc_comboBoxAnno.gridx = 5;
        gbc_comboBoxAnno.gridy = 7;
        panel_5.add(comboBoxAnno, gbc_comboBoxAnno);

        JLabel lblOra = new JLabel("Ora");
        GridBagConstraints gbc_lblOra = new GridBagConstraints();
        gbc_lblOra.anchor = GridBagConstraints.EAST;
        gbc_lblOra.insets = new Insets(0, 0, 0, 5);
        gbc_lblOra.gridx = 8;
        gbc_lblOra.gridy = 7;
        panel_5.add(lblOra, gbc_lblOra);

        JComboBox comboBoxOre = new JComboBox(oreArray);
        GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
        gbc_comboBox_1.insets = new Insets(0, 0, 0, 5);
        gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_1.gridx = 10;
        gbc_comboBox_1.gridy = 7;
        panel_5.add(comboBoxOre, gbc_comboBox_1);

        JComboBox comboBoxMinuti = new JComboBox(minutiArray);
        GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
        gbc_comboBox_2.insets = new Insets(0, 0, 0, 5);
        gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_2.gridx = 12;
        gbc_comboBox_2.gridy = 7;
        panel_5.add(comboBoxMinuti, gbc_comboBox_2);
        panelCreaEvento.add(panel_3, BorderLayout.NORTH);
        GridBagLayout gbl_panel_3 = new GridBagLayout();
        gbl_panel_3.columnWidths = new int[]{0, 0, 0, 200, 0, 227, 77, 0, 0, 0};
        gbl_panel_3.rowHeights = new int[]{0, 0, 23, 0};
        gbl_panel_3.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0,
                Double.MIN_VALUE};
        gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel_3.setLayout(gbl_panel_3);

        Component verticalStrut = Box.createVerticalStrut(20);
        GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
        gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut.gridx = 6;
        gbc_verticalStrut.gridy = 0;
        panel_3.add(verticalStrut, gbc_verticalStrut);

        JLabel lblInserisciPercorsoFile = new JLabel("Inserisci percorso file nominativi");
        GridBagConstraints gbc_lblInserisciPercorsoFile = new GridBagConstraints();
        gbc_lblInserisciPercorsoFile.insets = new Insets(0, 0, 5, 5);
        gbc_lblInserisciPercorsoFile.gridx = 3;
        gbc_lblInserisciPercorsoFile.gridy = 1;
        panel_3.add(lblInserisciPercorsoFile, gbc_lblInserisciPercorsoFile);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(6, 25));
        textField.setColumns(10);
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.insets = new Insets(0, 0, 5, 5);
        gbc_textField.gridx = 5;
        gbc_textField.gridy = 1;
        panel_3.add(textField, gbc_textField);

        JButton btnNewButton_2 = new JButton("Apri...");
        btnNewButton_2.setPreferredSize(new Dimension(135, 25));
        btnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Choose the folder");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getCurrentDirectory(): " + chooser.getSelectedFile());
                    file = chooser.getSelectedFile();
                    inputFilePath = file.getAbsolutePath();
                    textField.setText(inputFilePath);
                }
            }
        });
        GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
        gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnNewButton_2.gridx = 6;
        gbc_btnNewButton_2.gridy = 1;
        panel_3.add(btnNewButton_2, gbc_btnNewButton_2);

        Component horizontalStrut = Box.createHorizontalStrut(20);
        GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
        gbc_horizontalStrut.insets = new Insets(0, 0, 0, 5);
        gbc_horizontalStrut.gridx = 2;
        gbc_horizontalStrut.gridy = 2;
        panel_3.add(horizontalStrut, gbc_horizontalStrut);

        Component horizontalStrut_1 = Box.createHorizontalStrut(20);
        GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
        gbc_horizontalStrut_1.insets = new Insets(0, 0, 0, 5);
        gbc_horizontalStrut_1.gridx = 7;
        gbc_horizontalStrut_1.gridy = 2;
        panel_3.add(horizontalStrut_1, gbc_horizontalStrut_1);

        JPanel panel_4 = new JPanel();
        panelCreaEvento.add(panel_4, BorderLayout.SOUTH);

        JButton btnNewButton_3 = new JButton("Crea evento");
        btnNewButton_3.setPreferredSize(new Dimension(95, 25));
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                int giorno = Integer.parseInt((String) comboBoxGiorno.getSelectedItem());
                int mese = comboBoxMese.getSelectedIndex();
                int anno = Integer.parseInt((String) comboBoxAnno.getSelectedItem());
                int ore = Integer.parseInt((String) comboBoxOre.getSelectedItem());
                int minuti = Integer.parseInt((String) comboBoxMinuti.getSelectedItem());
                inputFilePath = textField.getText();
                file = new File(inputFilePath);

                if (!isValidDate(giorno, mese, anno)) {
                    JOptionPane.showMessageDialog(frame, "Data non corretta.", "Errore", JOptionPane.ERROR_MESSAGE);
                } else if (!extensionIs("xlsx", file)) {
                    JOptionPane.showMessageDialog(frame, "Formato file non corretto.", "Errore",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    inputFilePath = textField.getText();
                    /*try {
                        l = Loader.generateList(inputFilePath);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frame, "Errore nel file di input.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }*/

                    evento = new Event(textFieldNome.getText(), textFieldLuogo.getText(), (String) comboBoxProvincia.getSelectedItem(), new GregorianCalendar(anno, mese, giorno, ore, minuti), l);
                    
                    try {
                        evento.generateList(inputFilePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Errore nel file di output.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }

                    evento.toString();
                    File dir = new File(inputFilePath);
                    evento.assignQR(dir.getParent());


                    frame.setBounds(100, 100, 750, 400);
                    panelCreaEvento.setVisible(false);
                    panelInviaMail.setVisible(true);                  
                }
            }


        });
        panel_4.add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("Indietro");
        btnNewButton_4.setPreferredSize(new Dimension(95, 25));
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                panelCreaEvento.setVisible(false);
                panelIniziale.setVisible(true);
            }
        });
        panel_4.add(btnNewButton_4);

        panelCaricaPartecipanti = new JPanel();
        frame.getContentPane().add(panelCaricaPartecipanti, "name_80084471932092");
        panelCaricaPartecipanti.setLayout(new BorderLayout(0, 0));

        JPanel panel_9 = new JPanel();
        panelCaricaPartecipanti.add(panel_9);
        GridBagLayout gbl_panel_9 = new GridBagLayout();
        gbl_panel_9.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel_9.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panel_9.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_9.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel_9.setLayout(gbl_panel_9);

        JLabel lblInserisciFileExcel = new JLabel("Inserisci file Excel di output");
        GridBagConstraints gbc_lblInserisciFileExcel = new GridBagConstraints();
        gbc_lblInserisciFileExcel.insets = new Insets(0, 0, 5, 5);
        gbc_lblInserisciFileExcel.gridx = 2;
        gbc_lblInserisciFileExcel.gridy = 2;
        panel_9.add(lblInserisciFileExcel, gbc_lblInserisciFileExcel);

        textField_1 = new JTextField();
        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        gbc_textField_1.insets = new Insets(0, 0, 5, 5);
        gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_1.gridx = 3;
        gbc_textField_1.gridy = 2;
        panel_9.add(textField_1, gbc_textField_1);
        textField_1.setColumns(10);

        JButton btnNewButton_7 = new JButton("Scegli file...");
        btnNewButton_7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Choose the folder");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getCurrentDirectory(): " + chooser.getSelectedFile());
                    file = chooser.getSelectedFile();
                    excelOutputFilePath = file.getAbsolutePath();
                    textField_1.setText(excelOutputFilePath);
                }
            }
        });
        GridBagConstraints gbc_btnNewButton_7 = new GridBagConstraints();
        gbc_btnNewButton_7.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton_7.gridx = 5;
        gbc_btnNewButton_7.gridy = 2;
        panel_9.add(btnNewButton_7, gbc_btnNewButton_7);

        JLabel lblInserisciFileDelle = new JLabel("Inserisci file delle scansioni");
        GridBagConstraints gbc_lblInserisciFileDelle = new GridBagConstraints();
        gbc_lblInserisciFileDelle.insets = new Insets(0, 0, 0, 5);
        gbc_lblInserisciFileDelle.gridx = 2;
        gbc_lblInserisciFileDelle.gridy = 4;
        panel_9.add(lblInserisciFileDelle, gbc_lblInserisciFileDelle);

        textField_2 = new JTextField();
        GridBagConstraints gbc_textField_2 = new GridBagConstraints();
        gbc_textField_2.insets = new Insets(0, 0, 0, 5);
        gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_2.gridx = 3;
        gbc_textField_2.gridy = 4;
        panel_9.add(textField_2, gbc_textField_2);
        textField_2.setColumns(10);

        JButton btnNewButton_8 = new JButton("Scegli file...");
        btnNewButton_8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Choose the folder");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getCurrentDirectory(): " + chooser.getSelectedFile());
                    file = chooser.getSelectedFile();
                    txtOutputFilePath = file.getAbsolutePath();
                    textField_2.setText(txtOutputFilePath);
                }
            }
        });
        GridBagConstraints gbc_btnNewButton_8 = new GridBagConstraints();
        gbc_btnNewButton_8.insets = new Insets(0, 0, 0, 5);
        gbc_btnNewButton_8.gridx = 5;
        gbc_btnNewButton_8.gridy = 4;
        panel_9.add(btnNewButton_8, gbc_btnNewButton_8);

        JPanel panel_10 = new JPanel();
        panelCaricaPartecipanti.add(panel_10, BorderLayout.NORTH);

        JPanel panel_11 = new JPanel();
        panelCaricaPartecipanti.add(panel_11, BorderLayout.SOUTH);

        JButton btnNewButton_9 = new JButton("Associa");
        btnNewButton_9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtOutputFilePath = textField_2.getText();
                File f = new File(txtOutputFilePath);
                excelOutputFilePath = textField_1.getText();
                Loader.writeInOut(excelOutputFilePath, Loader.loadParticipants(f));
                JOptionPane.showMessageDialog(frame, "Match eseguito!", "", JOptionPane.PLAIN_MESSAGE);
            }
        });
        panel_11.add(btnNewButton_9);

        JButton btnNewButton_10 = new JButton("Annulla");
        panel_11.add(btnNewButton_10);

    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        progressBar.setVisible(true);
        btnNewButton_5.setEnabled(false);
        File dir = new File(inputFilePath);
        mailer = new Mailer(frame, evento, dir.getParent(), textFieldEmail.getText(), textFieldUsername.getText(), passwordField.getPassword(), textFieldSMTP.getText(), textFieldSMTPPort.getText());
        mailer.addPropertyChangeListener(this);
        mailer.execute();
        btnNewButton_5.setEnabled(true);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        }
    }

    private boolean isValidDate(int giorno, int mese, int anno) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String day = Integer.toString(giorno);
        String month = Integer.toString(mese + 1);
        String year = Integer.toString(anno);
        sdf.setLenient(false);
        String date = day + "-" + month + "-" + year;
        try {
            sdf.parse(date.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    private boolean extensionIs(String extension, File f) {
        String fileName = f.getName();

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1).toLowerCase();
        }

        if (!extension.equals(extension)) {
            return false;
        }
        return true;
    }
}
