/**
 * FmrModuloVentas.java
 */
package proyectofinal;

import control.Control;
import hora.Hora;
import static java.lang.Integer.parseInt;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import objetoNegocio.*;
import objetosServicio_00000216618.Fecha;
import persistencia.PersistenciaBD;

/**
 *
 * @author f_aco
 */
public class FmrModuloVentas extends javax.swing.JFrame {

    private DefaultTableModel modelo = new DefaultTableModel();
    private DetalleVenta ventaDetalle = new DetalleVenta();
    private Control control = new Control();
    private Cliente cliente = new Cliente();
    private Producto producto = new Producto();
    private int cantidad;
    private float precio;
    private float total;
    private Fecha fecha = new Fecha();
    private Float tpagar;

    /**
     * Creates new form ModuloVentas
     */
    public FmrModuloVentas() {
        initComponents();
        setLocationRelativeTo(null);

        Hora hora = new Hora(labelFechaHora);
        hora.start();
        txtNomCajero.setText(PersistenciaBD.cajero.getNombre());
    }

    void buscarCliente() {
        int conecto;
        Control control = new Control();
        String DNI = txtDniCliente.getText();
        if (DNI == null || DNI == "") {
            JOptionPane.showMessageDialog(this, "Error: El campo de cliente esta vacio");
        } else {
            cliente = control.buscarCliente(DNI);
            if (cliente.getDNI() == null) {
                conecto = JOptionPane.showConfirmDialog(this, "El DNI del cliente no esta registrado ¿Lo desea registrar?");
                if (conecto == 0) {
                    DlgNuevoCliente nCliente = new DlgNuevoCliente(this, true);
                    nCliente.setVisible(true);
                }
            } else {
                txtNomCliente.setText(cliente.getNombre());
            }
        }
    }

    /**
     * Se busca el producto que el cagero ingreso
     */
    void buscarProducto() {
        int conecto;
        Control control = new Control();
        String ID = txtCodPro.getText();
        if (ID == null || ID == "") {//si el id esta vacio o null lanza un errror
            JOptionPane.showMessageDialog(this, "Error: El campo de producto esta vacio");
        } else {
            producto = control.buscarProducto(ID);
            if (producto.getID() == null) {
                conecto = JOptionPane.showConfirmDialog(this, "El ID del Producto no esta registrado ¿Lo desea registrar?");
                if (conecto == 0) {
                    DlgNuevoProducto nProducto = new DlgNuevoProducto(this, true);
                    nProducto.setVisible(true);
                }
            } else {//Se pone los datos del producto que se ingreso y ese producto debe de estar registrado 
                txtNomProdu.setText(producto.getNombre());
                txtStock.setText(String.valueOf(producto.getStock()));
                txtPrecioPro.setText(String.valueOf(producto.getPrecio()));
            }
        }
    }

    /**
     * Se agregaran todos los productos que el Cliente esta comprando a la tabla
     */
    void agregarProducto() {
        int item = 0;
        modelo = (DefaultTableModel) tablaDetalleVenta.getModel();
        item++;
        int stock = parseInt(txtStock.getText());
        total = Integer.parseInt(txtCantidad.getValue().toString()) * Float.parseFloat(txtPrecioPro.getText());
        if (stock > 0) {
            Object[] object = new Object[6];
            object[0] = item;
            object[1] = producto.getID();
            object[2] = txtNomProdu.getText();
            object[3] = Integer.parseInt(txtCantidad.getValue().toString());
            object[4] = Float.parseFloat(txtPrecioPro.getText());
            object[5] = total;
            modelo.addRow(object);
            tablaDetalleVenta.setModel(modelo);
            calcularTotal();

        } else {
            JOptionPane.showMessageDialog(this, "Stock producto no Disponible");
        }

    }

    public void calcularTotal() {
        tpagar = 0.00f;
        for (int i = 0; i < tablaDetalleVenta.getRowCount(); i++) {
            cantidad = Integer.parseInt(tablaDetalleVenta.getValueAt(i, 3).toString());
            precio = Float.parseFloat(tablaDetalleVenta.getValueAt(i, 4).toString());
            tpagar += (cantidad * precio);
        }
        txtTotalpaga.setText(String.valueOf(tpagar));
    }

    public void guardarVentas() {
        Cajero cajero = PersistenciaBD.cajero;

        Venta venta = new Venta();
        venta.setTotal(tpagar);
        venta.setFecha(fecha);
        venta.setCajero(cajero);
        venta.setCliente(cliente);
        Control c = new Control();
        c.GuardarVenta(venta);

    }

    public void guardarDetalles() {
        Control control = new Control();
        String ID = control.maximaVenta();
        for (int i = 0; i < tablaDetalleVenta.getRowCount(); i++) {
            //String idpro = ;
            //int can = ;
            //float precio = ;

            ventaDetalle.setProducto(control.buscarProducto(String.valueOf(tablaDetalleVenta.getValueAt(i, 1).toString())));
            ventaDetalle.setVenta(new Venta(ID));
            ventaDetalle.setCantidad(Integer.parseInt(tablaDetalleVenta.getValueAt(i, 3).toString()));
            ventaDetalle.setPrecio(Float.valueOf(tablaDetalleVenta.getValueAt(i, 4).toString()));

            control.GuardarDetalleVenta(ventaDetalle);

        }

    }

    public void inventariar() {
        String ID = null;
        int cantidad = 0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Producto producto = new Producto();
            ID = tablaDetalleVenta.getValueAt(i, 1).toString();
            cantidad = Integer.parseInt(tablaDetalleVenta.getValueAt(i, 3).toString());
            producto = control.buscarProducto(ID);
            int nuevoStock = producto.getStock() - cantidad;
            control.inventariar(nuevoStock, ID);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        Panel = new javax.swing.JPanel();
        jlDniCliene = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDniCliente = new javax.swing.JTextField();
        txtCodPro = new javax.swing.JTextField();
        txtPrecioPro = new javax.swing.JTextField();
        txtNomCliente = new javax.swing.JTextField();
        txtNomProdu = new javax.swing.JTextField();
        txtStock = new javax.swing.JTextField();
        txtNomCajero = new javax.swing.JTextField();
        buscarProducto = new javax.swing.JButton();
        buscarCliente = new javax.swing.JButton();
        txtCantidad = new javax.swing.JSpinner();
        Agregar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        labelFechaHora = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnVenta = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDetalleVenta = new javax.swing.JTable();
        txtTotalpaga = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Modulo de ventas");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(242, 113, 39), 2));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icono/32.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(154, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        Panel.setBackground(new java.awt.Color(255, 255, 255));
        Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(242, 113, 39), 2));

        jlDniCliene.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jlDniCliene.setForeground(new java.awt.Color(242, 113, 39));
        jlDniCliene.setText("DNI Cliente");

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(242, 113, 39));
        jLabel6.setText("Cod.Producto");

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(242, 113, 39));
        jLabel7.setText("Pre.Producto");

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(242, 113, 39));
        jLabel8.setText("Cantidad Producto");

        jLabel9.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(242, 113, 39));
        jLabel9.setText("Cliente");

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(242, 113, 39));
        jLabel10.setText("Producto");

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(242, 113, 39));
        jLabel11.setText("Stock");

        jLabel12.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(242, 113, 39));
        jLabel12.setText("Vendedor");

        txtDniCliente.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtDniCliente.setMinimumSize(new java.awt.Dimension(6, 31));
        txtDniCliente.setPreferredSize(new java.awt.Dimension(101, 31));
        txtDniCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDniClienteActionPerformed(evt);
            }
        });

        txtCodPro.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtCodPro.setMinimumSize(new java.awt.Dimension(101, 31));
        txtCodPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodProActionPerformed(evt);
            }
        });

        txtPrecioPro.setEditable(false);
        txtPrecioPro.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtPrecioPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioProActionPerformed(evt);
            }
        });

        txtNomCliente.setEditable(false);
        txtNomCliente.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtNomCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomClienteActionPerformed(evt);
            }
        });

        txtNomProdu.setEditable(false);
        txtNomProdu.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtNomProdu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomProduActionPerformed(evt);
            }
        });

        txtStock.setEditable(false);
        txtStock.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockActionPerformed(evt);
            }
        });

        txtNomCajero.setEditable(false);
        txtNomCajero.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtNomCajero.setForeground(new java.awt.Color(0, 153, 255));
        txtNomCajero.setPreferredSize(new java.awt.Dimension(6, 31));
        txtNomCajero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCajeroActionPerformed(evt);
            }
        });

        buscarProducto.setBackground(new java.awt.Color(246, 148, 31));
        buscarProducto.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        buscarProducto.setForeground(new java.awt.Color(255, 255, 255));
        buscarProducto.setText("Buscar");
        buscarProducto.setPreferredSize(new java.awt.Dimension(101, 31));
        buscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarProductoActionPerformed(evt);
            }
        });

        buscarCliente.setBackground(new java.awt.Color(246, 148, 31));
        buscarCliente.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        buscarCliente.setForeground(new java.awt.Color(255, 255, 255));
        buscarCliente.setText("Buscar");
        buscarCliente.setPreferredSize(new java.awt.Dimension(101, 31));
        buscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarClienteActionPerformed(evt);
            }
        });

        Agregar.setBackground(new java.awt.Color(246, 148, 31));
        Agregar.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        Agregar.setForeground(new java.awt.Color(255, 255, 255));
        Agregar.setText("Agregar");
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(242, 113, 39));
        jLabel13.setText("Fecha");

        labelFechaHora.setForeground(new java.awt.Color(51, 204, 0));

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(PanelLayout.createSequentialGroup()
                                    .addComponent(jlDniCliene)
                                    .addGap(35, 35, 35)
                                    .addComponent(txtDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLayout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtPrecioPro, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCodPro, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel7)
                            .addGroup(PanelLayout.createSequentialGroup()
                                .addGap(137, 137, 137)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buscarCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                            .addComponent(buscarProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Agregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLayout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(14, 14, 14))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLayout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(27, 27, 27))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(33, 33, 33))))
                            .addGroup(PanelLayout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 30, Short.MAX_VALUE)))
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNomProdu, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNomCajero, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(PanelLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelFechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jlDniCliene)
                                .addComponent(buscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(buscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCodPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtPrecioPro, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Agregar))
                            .addComponent(jLabel7))
                        .addGap(34, 34, 34)
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel8)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(labelFechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNomCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNomProdu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(17, 17, 17)
                        .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtNomCajero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        btnCancelar.setBackground(new java.awt.Color(246, 148, 31));
        btnCancelar.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.setPreferredSize(new java.awt.Dimension(101, 31));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnVenta.setBackground(new java.awt.Color(246, 148, 31));
        btnVenta.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        btnVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnVenta.setText("Generar venta");
        btnVenta.setPreferredSize(new java.awt.Dimension(101, 31));
        btnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(242, 113, 39));
        jLabel14.setText("Total a pagar: ");

        tablaDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NRO", "Codigo", "Producto", "Cantidad", "Precio", "Total"
            }
        ));
        jScrollPane1.setViewportView(tablaDetalleVenta);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        txtTotalpaga.setEditable(false);
        txtTotalpaga.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14)
                            .addGap(3, 3, 3)
                            .addComponent(txtTotalpaga, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtTotalpaga, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDniClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniClienteActionPerformed

    private void txtCodProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodProActionPerformed

    private void txtPrecioProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioProActionPerformed

    private void txtNomClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomClienteActionPerformed

    private void txtNomProduActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomProduActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomProduActionPerformed

    private void txtStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockActionPerformed

    private void txtNomCajeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCajeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomCajeroActionPerformed

    private void buscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarProductoActionPerformed

        buscarProducto();
    }//GEN-LAST:event_buscarProductoActionPerformed

    private void buscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarClienteActionPerformed
        // TODO add your handling code here:
        buscarCliente();
    }//GEN-LAST:event_buscarClienteActionPerformed

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        // TODO add your handling code here:
        agregarProducto();

    }//GEN-LAST:event_AgregarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaActionPerformed
        // TODO add your handling code here:
        if (txtTotalpaga.equals(null)) {
            JOptionPane.showMessageDialog(this, "Error: los campos no pueden estar vacios", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            guardarVentas();
            guardarDetalles();
            inventariar();
            JOptionPane.showMessageDialog(this, "Felicidades, se registro con exito la venta", "Felicidades", JOptionPane.INFORMATION_MESSAGE);
            txtCodPro.setText(null);
            txtDniCliente.setText(null);
            txtNomCliente.setText(null);
            txtPrecioPro.setText(null);
            txtNomProdu.setText(null);
            txtStock.setText(null);
            txtTotalpaga.setText(null);
            txtCantidad.setValue(0);

            for (int i = 0; i < modelo.getRowCount(); i++) {
                modelo.removeRow(i);
                i--;
            }
        }
    }//GEN-LAST:event_btnVentaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Agregar;
    private javax.swing.JPanel Panel;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnVenta;
    private javax.swing.JButton buscarCliente;
    private javax.swing.JButton buscarProducto;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jlDniCliene;
    private javax.swing.JLabel labelFechaHora;
    private javax.swing.JTable tablaDetalleVenta;
    private javax.swing.JSpinner txtCantidad;
    private javax.swing.JTextField txtCodPro;
    private javax.swing.JTextField txtDniCliente;
    private javax.swing.JTextField txtNomCajero;
    private javax.swing.JTextField txtNomCliente;
    private javax.swing.JTextField txtNomProdu;
    private javax.swing.JTextField txtPrecioPro;
    private javax.swing.JTextField txtStock;
    private javax.swing.JTextField txtTotalpaga;
    // End of variables declaration//GEN-END:variables
}
