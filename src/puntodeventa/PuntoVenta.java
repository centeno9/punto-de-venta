package puntodeventa;

import com.mysql.jdbc.Connection;
import java.awt.Color;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dell
 */
public class PuntoVenta extends javax.swing.JFrame {

    private static Connection con;
    // Declaramos los datos de conexion a la bd
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "javier123";
    private static final String url = "jdbc:mysql://localhost:3306/punto_de_venta";
    public LinkedList<Integer> cods = new LinkedList<Integer>();//lista de los ids
    public LinkedList<Integer> lcantidades = new LinkedList<Integer>();
    public LinkedList<String[]> imprimir = new LinkedList<String[]>();
    double tot;

    DefaultTableModel dtm = new DefaultTableModel();

    public Connection conexion() {
        // Reseteamos a null la conexion a la bd
        con = null;
        try {
            Class.forName(driver);
            // Nos conectamos a la bd
            con = (Connection) DriverManager.getConnection(url, user, pass);
        } // Si la conexion NO fue exitosa mostramos un mensaje de error
        catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion " + e);
            jLabel1.setText("Error de conexion" + e);
        }
        return con;
    }

    public PuntoVenta() throws SQLException {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Punto de Venta | Equipo 3 ");
        this.getContentPane().setBackground(new java.awt.Color(225, 233, 243));
        mostrarTabla();

    }

    public int numVentas() {
        Connection c = conexion();
        int x = 0;
        String sql = "SELECT count(id) FROM venta";
        Statement st;

        try {
            st = c.createStatement();
            ResultSet r = st.executeQuery(sql);
            while (r.next()) {
                x = Integer.parseInt(r.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return x;
    }

    public void mostrarTabla() {
        dtm.addColumn("Codigo");
        dtm.addColumn("Nombre");
        dtm.addColumn("Cantidad");
        dtm.addColumn("Precio");
        dtm.addColumn("Total");
        jTable1.setModel(dtm);

    }

    public void imprimir() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("C:\\Users\\cente\\Desktop\\prueba.txt");
            pw = new PrintWriter(fichero);
            pw.println("Codigo \t \tProducto \t \tCantidad \t \tPrecio \t \tTotal");
            for (int i = 0; i < this.imprimir.size(); i++) {
                String[] im = this.imprimir.get(i);
                for (int j = 0; j < im.length; j++) {
                    pw.print(im[j] + " \t \t");
                }
                pw.println();
            }
            pw.println("\t \t \t \t \t \t \t \t----");
            pw.print("\t \t \t \t \t \t \t \t" + tot);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void actualizarTabla(int q, int id) {
        Connection c = conexion();
        String[] datos = new String[5];
        String sql = "SELECT * FROM productos WHERE id = " + id;
        double x;

        Statement st;
        try {
            st = c.createStatement();
            ResultSet r = st.executeQuery(sql);
            while (r.next()) {
                datos[0] = r.getString(1);
                datos[1] = r.getString(2);
                datos[2] = String.valueOf(q);
                datos[3] = r.getString(3);
                x = Double.parseDouble(r.getString(3));
                x = x * q;
                datos[4] = String.valueOf(x);
                imprimir.add(datos);
                dtm.addRow(datos);
                tot = tot + x;
                total.setText(String.valueOf(tot));
            }
            jTable1.setModel(dtm);
        } catch (SQLException e) {

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        TimeIn24 = new javax.swing.JLabel();
        btn_pagar = new javax.swing.JButton();
        id = new javax.swing.JTextField();
        total = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cantidad = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Lucida Bright", 1, 48)); // NOI18N
        jLabel1.setText("Punto de Venta");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(610, 70, 368, 50);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.setAlignmentX(0.0F);
        jTable1.setName("tabla"); // NOI18N
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(90, 200, 1170, 300);

        TimeIn24.setFont(new java.awt.Font("Lucida Bright", 3, 22)); // NOI18N
        TimeIn24.setText("Hora");
        getContentPane().add(TimeIn24);
        TimeIn24.setBounds(90, 140, 230, 50);

        btn_pagar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_pagar.setText("Pagar");
        btn_pagar.setEnabled(false);
        btn_pagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pagarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_pagar);
        btn_pagar.setBounds(1050, 650, 230, 40);

        id.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        id.setToolTipText("");
        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });
        getContentPane().add(id);
        id.setBounds(90, 560, 340, 40);

        total.setEditable(false);
        total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalActionPerformed(evt);
            }
        });
        getContentPane().add(total);
        total.setBounds(1130, 520, 135, 30);

        jLabel3.setFont(new java.awt.Font("Arial Narrow", 0, 24)); // NOI18N
        jLabel3.setText("Total:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(1080, 520, 50, 30);

        jButton2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton2.setText("Agregar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(440, 560, 120, 40);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("Producto a buscar(codigo):");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(90, 530, 340, 29);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Cantidad:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(90, 610, 120, 30);

        cantidad.setText("1");
        getContentPane().add(cantidad);
        cantidad.setBounds(90, 650, 100, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int i, can;
        String texto = id.getText();
        String textoCantidad = cantidad.getText();
        i = Integer.parseInt(texto);
        can = Integer.parseInt(textoCantidad);
        actualizarTabla(can, i);
        this.cods.add(i);
        this.lcantidades.add(can);
        id.setText("");
        cantidad.setText("1");
        btn_pagar.setEnabled(true);


    }//GEN-LAST:event_jButton2ActionPerformed

    private void totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalActionPerformed

    }//GEN-LAST:event_totalActionPerformed

    private void btn_pagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pagarActionPerformed
        Connection c = conexion();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String sql = "INSERT INTO venta(total,fecha) VALUES(0,'" + dtf.format(now) + "')";
        Statement st;
        try {
            st = c.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        int ventaId = numVentas();
        for (int i = 0; i < cods.size(); i++) {
            String sql2 = "INSERT INTO productos_ventas(venta_id,producto_id,cantidad) VALUES(" + ventaId + ", " + this.cods.get(i) + ", " + this.lcantidades.get(i) + ")";
            Statement st2;
            try {
                st2 = c.createStatement();
                st2.executeUpdate(sql2);
            } catch (SQLException e) {
                System.out.println("error: " + e);
            }
        }

        imprimir();
        imprimir.clear();
        this.cods.clear();
        this.lcantidades.clear();
        dtm.setRowCount(0);
        jTable1.setModel(dtm);
        this.tot = 0;
        total.setText("0");
        btn_pagar.setEnabled(false);

    }//GEN-LAST:event_btn_pagarActionPerformed

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
            java.util.logging.Logger.getLogger(PuntoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PuntoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PuntoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PuntoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    new PuntoVenta().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(PuntoVenta.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TimeIn24;
    private javax.swing.JButton btn_pagar;
    private javax.swing.JTextField cantidad;
    private javax.swing.JTextField id;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField total;
    // End of variables declaration//GEN-END:variables
}
