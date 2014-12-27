/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package meteoinfo.forms;

import bsh.util.JConsole;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import meteoinfo.classes.PythonInteractiveInterpreter;
import org.meteoinfo.global.util.GlobalUtil;
import org.python.core.Py;

/**
 *
 * @author yaqiang
 */
public class FrmConsole extends javax.swing.JFrame {
    
    private FrmMain frmMain = null;
    
    /**
     * Creates new form FrmConsole
     * @param parent
     */
    public FrmConsole(java.awt.Frame parent) {
        super();
        initComponents();   
        this.setSize(600, 400);
        
        frmMain = (FrmMain)parent;
        
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("/meteoinfo/resources/MeteoInfo_1_16x16x8.png"));
        } catch (Exception e) {
        }
        this.setIconImage(image);
    }
    
    /**
     * Initialize console
     */
    public void InitializeConsole(){
        JConsole console = new JConsole();
        console.setLocale(Locale.getDefault());
        System.out.println(console.getFont());
        console.setPreferredSize(new Dimension(600, 400));
        console.println(new ImageIcon(this.getClass().getResource("/meteoinfo/resources/jython_small_c.png")));
        this.getContentPane().add(console, BorderLayout.CENTER);
        
        boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
                        getInputArguments().toString().contains("jdwp");
        String pluginPath = this.frmMain.getStartupPath() + File.separator + "plugins";
        List<String> jarfns = GlobalUtil.getFiles(pluginPath, ".jar");
        
        Py.getSystemState().setdefaultencoding("utf-8");
        PythonInteractiveInterpreter interp = new PythonInteractiveInterpreter(console);  
        String path = GlobalUtil.getAppPath(FrmMain.class) + File.separator + "script";
        if (isDebug)
            path = "D:/MyProgram/Distribution/Java/MeteoInfo/MeteoInfo/script";
        
        //MeteoInfoScript mis = new MeteoInfoScript(path);
        interp.exec("import sys"); 
        //interp.set("mis", mis);
        interp.exec("sys.path.append('" + path + "')");
        interp.exec("import miscript");
        interp.exec("from miscript import MeteoInfoScript");
        interp.exec("mis = MeteoInfoScript()");
        interp.set("miapp", frmMain);        
        for (String jarfn : jarfns)
            interp.exec("sys.path.append('" + jarfn + "')");
        
        new Thread(interp).start();       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(FrmConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                FrmConsole dialog = new FrmConsole(new javax.swing.JFrame());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
