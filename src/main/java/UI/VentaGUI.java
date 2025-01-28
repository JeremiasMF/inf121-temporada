package UI;

import Clases.*;
import Persistencia.DBConecction;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import UI.MenuGUI;

public class VentaGUI extends javax.swing.JFrame {

    private JComboBox<Cliente> cmbCliente;
    private JComboBox<Empleado> cmbEmpleado;
    private JTable tblProductos;
    private JTable tblVentas;
    private JButton btnAgregar, btnEliminar, btnNuevaVenta;
    private JLabel lblTotal;
    private JDialog dialogVenta;
    private DefaultTableModel modelProductos;
    private DefaultTableModel modelVentas;
    private List<Producto> listaProductos;
    private Map<Integer, JSpinner> spinnersPorProducto;
    private JButton btnVolverMenu;

    public VentaGUI() {
        initComponents1();
        spinnersPorProducto = new HashMap<>();
        cargarDatos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initComponents1() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Gestión de Ventas");
        setSize(1000, 800);
        setLayout(new BorderLayout());

        // Panel superior
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 10, 0));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Combo boxes para cliente y empleado
        JPanel panelSeleccion = new JPanel(new GridLayout(2, 2, 5, 5));
        cmbCliente = new JComboBox<>();
        cmbEmpleado = new JComboBox<>();
        panelSeleccion.add(createStyledLabel("Cliente:"));
        panelSeleccion.add(cmbCliente);
        panelSeleccion.add(createStyledLabel("Empleado:"));
        panelSeleccion.add(cmbEmpleado);

        // Configurar renders personalizados
        cmbCliente.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente) {
                    Cliente cliente = (Cliente) value;
                    setText(cliente.getNombre_p() + " " + cliente.getPaterno_p());
                }
                return this;
            }
        });

        cmbEmpleado.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Empleado) {
                    Empleado empleado = (Empleado) value;
                    setText(empleado.getNombre_p() + " " + empleado.getPaterno_p());
                }
                return this;
            }
        });

        panelSuperior.add(panelSeleccion);

        // Panel de productos
        JPanel panelProductos = new JPanel(new BorderLayout());
        panelProductos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Productos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 16)));
        modelProductos = new DefaultTableModel(
                new Object[]{"ID", "Producto", "Precio", "Cantidad"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Solo la columna de cantidad es editable
            }
        };

        tblProductos = new JTable(modelProductos);
        tblProductos.setFont(new Font("Arial", Font.PLAIN, 14));
        tblProductos.setRowHeight(25);
        JScrollPane scrollProductos = new JScrollPane(tblProductos);
        panelProductos.add(scrollProductos, BorderLayout.CENTER);

        // Panel inferior con total y botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        btnAgregar = createStyledButton("Registrar Venta", new Color(46, 204, 113));
        btnNuevaVenta = createStyledButton("Nueva Venta", new Color(52, 152, 219));

        panelInferior.add(lblTotal);
        panelInferior.add(btnAgregar);
        panelInferior.add(btnNuevaVenta);

        btnVolverMenu = createStyledButton("Volver al Menú", new Color(52, 73, 94));
        panelInferior.add(btnVolverMenu);

        // Panel de ventas realizadas
        JPanel panelVentas = new JPanel(new BorderLayout());
        panelVentas.setBorder(BorderFactory.createTitledBorder("Ventas Realizadas"));
        modelVentas = new DefaultTableModel(
                new Object[]{"ID Venta", "Cliente", "Empleado", "Total", "Fecha"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna celda es editable
            }
        };

        tblVentas = new JTable(modelVentas);
        tblVentas.setFont(new Font("Arial", Font.PLAIN, 14));
        tblVentas.setRowHeight(25);
        JScrollPane scrollVentas = new JScrollPane(tblVentas);
        panelVentas.add(scrollVentas, BorderLayout.CENTER);

        // Agregar todos los paneles al frame
        add(panelSuperior, BorderLayout.NORTH);
        add(panelProductos, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        add(panelVentas, BorderLayout.EAST);

        // Eventos
        btnAgregar.addActionListener(e -> registrarVenta());
        btnNuevaVenta.addActionListener(e -> nuevaVenta());
        btnVolverMenu.addActionListener(e -> volverAlMenu());

        // Listener para actualizar el total cuando cambian las cantidades
        tblProductos.getModel().addTableModelListener(e -> actualizarTotal());
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    private void cargarDatos() {
        cargarClientes();
        cargarEmpleados();
        cargarProductos();
        cargarVentas();
    }

    private void cargarClientes() {
        try {
            Connection conn = DBConecction.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT p.*, c.correo, c.direccion FROM persona p "
                    + "INNER JOIN cliente c ON p.id_persona = c.id_persona "
                    + "WHERE p.tipo = 'cliente'"
            );

            cmbCliente.removeAllItems();
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id_persona"),
                        rs.getString("nombre_p"),
                        rs.getString("paterno_p"),
                        rs.getString("materno_p"),
                        rs.getString("telefono"),
                        rs.getString("f_nacimiento"),
                        rs.getString("correo"),
                        rs.getString("direccion")
                );
                cmbCliente.addItem(cliente);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + ex.getMessage());
        }
    }

    private void cargarEmpleados() {
        try {
            Connection conn = DBConecction.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT p.*, e.puesto, e.salario FROM persona p "
                    + "INNER JOIN empleado e ON p.id_persona = e.id_persona "
                    + "WHERE p.tipo = 'empleado'"
            );

            cmbEmpleado.removeAllItems();
            while (rs.next()) {
                Empleado empleado = new Empleado(
                        rs.getInt("id_persona"),
                        rs.getString("nombre_p"),
                        rs.getString("paterno_p"),
                        rs.getString("materno_p"),
                        rs.getString("telefono"),
                        rs.getString("f_nacimiento"),
                        rs.getString("puesto"),
                        rs.getDouble("salario")
                );
                cmbEmpleado.addItem(empleado);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + ex.getMessage());
        }
    }

    private void cargarProductos() {
        try {
            Connection conn = DBConecction.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT p.*, c.nombre_c FROM producto p "
                    + "INNER JOIN categoria c ON p.id_categoria = c.id_categoria"
            );

            modelProductos.setRowCount(0);
            listaProductos = new ArrayList<>();
            spinnersPorProducto.clear();

            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_c"),
                        ""
                );

                Producto producto = new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre_prod"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad"),
                        categoria
                );

                listaProductos.add(producto);

                SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, producto.getCantidad(), 1);
                JSpinner spinner = new JSpinner(spinnerModel);
                spinner.setFont(new Font("Arial", Font.PLAIN, 14));
                spinnersPorProducto.put(producto.getId_producto(), spinner);

                modelProductos.addRow(new Object[]{
                    producto.getId_producto(),
                    producto.getNombre_prod(),
                    producto.getPrecio(),
                    spinner
                });
            }

            // Configurar el renderer para el spinner en la tabla
            tblProductos.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    if (value instanceof JSpinner) {
                        return (JSpinner) value;
                    }
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
            });

            tblProductos.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JTextField()) {
                @Override
                public Component getTableCellEditorComponent(JTable table, Object value,
                        boolean isSelected, int row, int column) {
                    return spinnersPorProducto.get(table.getValueAt(row, 0));
                }
            });

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + ex.getMessage());
        }
    }

    private void cargarVentas() {
        try {
            Connection conn = DBConecction.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT v.id_venta, c.nombre_p AS cliente_nombre, c.paterno_p AS cliente_apellido, "
                    + "e.nombre_p AS empleado_nombre, e.paterno_p AS empleado_apellido, "
                    + "v.total, v.fecha_venta "
                    + "FROM venta v "
                    + "INNER JOIN persona c ON v.id_cliente = c.id_persona "
                    + "INNER JOIN persona e ON v.id_empleado = e.id_persona "
                    + "ORDER BY v.fecha_venta DESC"
            );

            modelVentas.setRowCount(0);

            while (rs.next()) {
                modelVentas.addRow(new Object[]{
                    rs.getInt("id_venta"),
                    rs.getString("cliente_nombre") + " " + rs.getString("cliente_apellido"),
                    rs.getString("empleado_nombre") + " " + rs.getString("empleado_apellido"),
                    String.format("$%.2f", rs.getDouble("total")),
                    rs.getTimestamp("fecha_venta")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar ventas: " + ex.getMessage());
        }
    }

    private void actualizarTotal() {
        double total = 0;
        for (int i = 0; i < modelProductos.getRowCount(); i++) {
            int idProducto = (int) modelProductos.getValueAt(i, 0);
            double precio = (double) modelProductos.getValueAt(i, 2);
            JSpinner spinner = spinnersPorProducto.get(idProducto);
            int cantidad = (int) spinner.getValue();
            total += precio * cantidad;
        }
        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    private void registrarVenta() {
        try {
            if (cmbCliente.getSelectedItem() == null || cmbEmpleado.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente y un empleado");
                return;
            }

            // Verificar que al menos un producto tenga cantidad mayor a 0
            boolean hayProductos = false;
            for (JSpinner spinner : spinnersPorProducto.values()) {
                if ((int) spinner.getValue() > 0) {
                    hayProductos = true;
                    break;
                }
            }

            if (!hayProductos) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un producto");
                return;
            }

            Connection conn = DBConecction.getConnection();
            conn.setAutoCommit(false);

            try {
                // Insertar la venta
                Cliente cliente = (Cliente) cmbCliente.getSelectedItem();
                Empleado empleado = (Empleado) cmbEmpleado.getSelectedItem();
                double total = Double.parseDouble(lblTotal.getText().replace("Total: $", ""));

                PreparedStatement pstmtVenta = conn.prepareStatement(
                        "INSERT INTO venta (id_empleado, id_cliente, total) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                pstmtVenta.setInt(1, empleado.getId_persona());
                pstmtVenta.setInt(2, cliente.getId_persona());
                pstmtVenta.setDouble(3, total);
                pstmtVenta.executeUpdate();

                ResultSet rs = pstmtVenta.getGeneratedKeys();
                if (rs.next()) {
                    int idVenta = rs.getInt(1);

                    // Insertar los productos de la venta
                    PreparedStatement pstmtDetalle = conn.prepareStatement(
                            "INSERT INTO venta_producto (id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)"
                    );

                    PreparedStatement pstmtUpdateStock = conn.prepareStatement(
                            "UPDATE producto SET cantidad = cantidad - ? WHERE id_producto = ?"
                    );

                    for (int i = 0; i < modelProductos.getRowCount(); i++) {
                        int idProducto = (int) modelProductos.getValueAt(i, 0);
                        double precio = (double) modelProductos.getValueAt(i, 2);
                        JSpinner spinner = spinnersPorProducto.get(idProducto);
                        int cantidad = (int) spinner.getValue();

                        if (cantidad > 0) {
                            // Insertar detalle de venta
                            pstmtDetalle.setInt(1, idVenta);
                            pstmtDetalle.setInt(2, idProducto);
                            pstmtDetalle.setInt(3, cantidad);
                            pstmtDetalle.setDouble(4, precio);
                            pstmtDetalle.executeUpdate();

                            // Actualizar stock
                            pstmtUpdateStock.setInt(1, cantidad);
                            pstmtUpdateStock.setInt(2, idProducto);
                            pstmtUpdateStock.executeUpdate();
                        }
                    }

                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Venta registrada con éxito");
                    nuevaVenta();
                    cargarVentas(); // Actualizar la tabla de ventas
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar la venta: " + ex.getMessage());
        }
    }

    private void nuevaVenta() {
        cmbCliente.setSelectedIndex(-1);
        cmbEmpleado.setSelectedIndex(-1);
        for (JSpinner spinner : spinnersPorProducto.values()) {
            spinner.setValue(0);
        }
        actualizarTotal();
        cargarProductos(); // Recargar productos para actualizar stock
    }

    private void volverAlMenu() {
        this.dispose();
        MenuGUI mn = new MenuGUI();
        mn.setVisible(true);
        mn.setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            VentaGUI gui = new VentaGUI();
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
