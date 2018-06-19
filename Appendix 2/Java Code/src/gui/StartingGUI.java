/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import inserting.Main;
import inserting.staticDivEng;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * This class starts the program by generating a GUI and asking the inputs from the user.
 */
public class StartingGUI extends javax.swing.JFrame implements ActionListener{

    /**
     * Creates new form StartingGUI
     */
    public StartingGUI() {
        setTitle("Extracting Important Events");
        initComponents();
        addActionListener();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        runButton = new javax.swing.JButton();
        cf1 = new javax.swing.JButton();
        cf2 = new javax.swing.JButton();
        cf3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cf4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cf5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Logbook CSV FIle:");

        jLabel2.setText("Database Path:");

        jLabel3.setText("Biometric Responses Root:");

        runButton.setText("Run The Program");

        cf1.setText("Choose File");

        cf2.setText("Choose File");

        cf3.setText("Choose File");

        jLabel4.setText("Where do you want to get the Result?");

        cf4.setText("Choose File");

        jLabel5.setText("SnapCam Photoes:");

        cf5.setText("Choose File");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(131, 131, 131)
                                .addComponent(runButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(cf3)
                                                .addComponent(cf2)
                                                .addComponent(cf1))
                                        .addComponent(cf4)
                                        .addComponent(cf5))
                                .addGap(57, 57, 57))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel1)
                                        .addComponent(cf1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cf2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(cf3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cf5))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(cf4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                                .addComponent(runButton)
                                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StartingGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartingGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartingGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartingGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartingGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton cf1;
    private javax.swing.JButton cf2;
    private javax.swing.JButton cf3;
    private javax.swing.JButton cf4;
    private javax.swing.JButton cf5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton runButton;
    // End of variables declaration

    String logbookCSV = "";
    String dbPath = "";
    String bioPath = "";
    String resultPath = "";
    String imagePath = "";

    /**
     * This function gets the user inputs. After that, it runs the code in staticDivEng class.
     * Finally, it creates an object from Main class and calls its appropriate functions.
     * @param e, events of the buttons.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cf1) {
            FileChooseGUI fcgui = new FileChooseGUI();
            logbookCSV = fcgui.getPath();
            fcgui.setVisible(false);
            JOptionPane.showMessageDialog(null, "You chose " + logbookCSV);
        }
        if (e.getSource() == cf2) {
            FileChooseGUI fcgui = new FileChooseGUI();
            dbPath = fcgui.getPath();
            fcgui.setVisible(false);
            JOptionPane.showMessageDialog(null, "You chose " + dbPath);
        }
        if (e.getSource() == cf3) {
            FileChooseGUI fcgui = new FileChooseGUI();
            fcgui.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            bioPath = fcgui.getPath();
            fcgui.setVisible(false);
            JOptionPane.showMessageDialog(null, "You chose " + bioPath);
        }
        if (e.getSource() == cf4) {
            FileChooseGUI fcgui = new FileChooseGUI();
            resultPath = fcgui.getPath();
            fcgui.setVisible(false);
            JOptionPane.showMessageDialog(null, "You chose " + resultPath);
        }
        if (e.getSource() == cf5) {
            FileChooseGUI fcgui = new FileChooseGUI();
            fcgui.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            imagePath = fcgui.getPath();
            fcgui.setVisible(false);
            JOptionPane.showMessageDialog(null, "You chose " + imagePath);
        }
        if (e.getSource() == runButton) {
            this.setVisible(false);
            new staticDivEng(bioPath + "\\", dbPath);
            Main main = new Main(imagePath+ "\\", dbPath, logbookCSV, resultPath);
            main.writeTheLogBook();
            String[][] res = main.getAttributes();
            main.writeCSV(res);
            System.exit(0);
        }
    }

    /**
     * This class adds the class's action listener to the buttons.
     */
    private void addActionListener() {
        cf1.addActionListener(this);
        cf2.addActionListener(this);
        cf3.addActionListener(this);
        cf4.addActionListener(this);
        cf5.addActionListener(this);
        runButton.addActionListener(this);
    }
}