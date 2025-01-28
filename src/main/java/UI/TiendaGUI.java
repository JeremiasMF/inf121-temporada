package UI;

import Clases.*;
import Persistencia.DBConecction;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import UI.MenuGUI;

public class TiendaGUI extends javax.swing.JFrame {

    private JTabbedPane tabbedPane;
    private JPanel panelInfoTienda, panelEmpleados, panelClientes, panelProductos, panelVentas, panelFacturas;
    private JLabel lblNombreTienda, lblDireccionTienda;
    private JTable tblEmpleados, tblClientes, tblProductos, tblVentas, tblFacturas;
    private JButton btnActualizar;
    private JButton btnVolverMenu;

    public TiendaGUI() {
        initComponents1();
        cargarDatosTienda();
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
        setTitle("Sistema de Gestión de Tienda");
        setSize(1000, 800);
        setLayout(new BorderLayout());

        // Crear el panel de pestañas
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Inicializar paneles
        panelInfoTienda = new JPanel(new GridBagLayout());
        panelEmpleados = new JPanel(new BorderLayout());
        panelClientes = new JPanel(new BorderLayout());
        panelProductos = new JPanel(new BorderLayout());
        panelVentas = new JPanel(new BorderLayout());
        panelFacturas = new JPanel(new BorderLayout());

        // Configurar panel de información de la tienda
        lblNombreTienda = new JLabel();
        lblNombreTienda.setFont(new Font("Arial", Font.BOLD, 24));
        lblDireccionTienda = new JLabel();
        lblDireccionTienda.setFont(new Font("Arial", Font.PLAIN, 18));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        panelInfoTienda.add(new JLabel(new ImageIcon("path/to/store/icon.png")), gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        panelInfoTienda.add(lblNombreTienda, gbc);

        gbc.gridy = 2;
        panelInfoTienda.add(lblDireccionTienda, gbc);

        // Configurar tablas
        tblEmpleados = createStyledTable();
        tblClientes = createStyledTable();
        tblProductos = createStyledTable();
        tblVentas = createStyledTable();
        tblFacturas = createStyledTable();

        panelEmpleados.add(new JScrollPane(tblEmpleados), BorderLayout.CENTER);
        panelClientes.add(new JScrollPane(tblClientes), BorderLayout.CENTER);
        panelProductos.add(new JScrollPane(tblProductos), BorderLayout.CENTER);
        panelVentas.add(new JScrollPane(tblVentas), BorderLayout.CENTER);
        panelFacturas.add(new JScrollPane(tblFacturas), BorderLayout.CENTER);

        // Añadir pestañas
        tabbedPane.addTab("Información", new ImageIcon("path/to/info/icon.png"), panelInfoTienda);
        tabbedPane.addTab("Empleados", new ImageIcon("path/to/employee/icon.png"), panelEmpleados);
        tabbedPane.addTab("Clientes", new ImageIcon("path/to/client/icon.png"), panelClientes);
        tabbedPane.addTab("Productos", new ImageIcon("path/to/product/icon.png"), panelProductos);
        tabbedPane.addTab("Ventas", new ImageIcon("path/to/sale/icon.png"), panelVentas);
        tabbedPane.addTab("Facturas", new ImageIcon("path/to/invoice/icon.png"), panelFacturas);

        add(tabbedPane, BorderLayout.CENTER);

        // Botón para actualizar los datos
        btnActualizar = createStyledButton("Actualizar Datos", new Color(52, 152, 219));
        btnActualizar.addActionListener(e -> cargarDatosTienda());
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnActualizar);

        btnVolverMenu = createStyledButton("Volver al Menú", new Color(52, 73, 94));
        panelBoton.add(btnVolverMenu);
        btnVolverMenu.addActionListener(e -> volverAlMenu());

        add(panelBoton, BorderLayout.SOUTH);
    }

    private JTable createStyledTable() {
        JTable table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(25);
        return table;
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

    private void cargarDatosTienda() {
        try {
            Connection conn = DBConecction.getConnection();
            cargarInfoTienda(conn);
            cargarEmpleados(conn);
            cargarClientes(conn);
            cargarProductos(conn);
            cargarVentas(conn);
            cargarFacturas(conn);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos de la tienda: " + ex.getMessage());
        }
    }

    private void cargarInfoTienda(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM tienda LIMIT 1");
        if (rs.next()) {
            lblNombreTienda.setText(rs.getString("nombre_t"));
            lblDireccionTienda.setText(rs.getString("direccion_t"));
        }
    }

    private void cargarEmpleados(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT p.*, e.puesto, e.salario FROM persona p "
                + "INNER JOIN empleado e ON p.id_persona = e.id_persona "
                + "WHERE p.tipo = 'empleado'"
        );
        ResultSet rs = pstmt.executeQuery();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Apellidos", "Puesto", "Salario"}, 0
        );
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id_persona"),
                rs.getString("nombre_p"),
                rs.getString("paterno_p") + " " + rs.getString("materno_p"),
                rs.getString("puesto"),
                rs.getDouble("salario")
            });
        }
        tblEmpleados.setModel(model);
    }

    private void cargarClientes(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT p.*, c.correo, c.direccion FROM persona p "
                + "INNER JOIN cliente c ON p.id_persona = c.id_persona "
                + "WHERE p.tipo = 'cliente'"
        );
        ResultSet rs = pstmt.executeQuery();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Apellidos", "Correo", "Dirección"}, 0
        );
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id_persona"),
                rs.getString("nombre_p"),
                rs.getString("paterno_p") + " " + rs.getString("materno_p"),
                rs.getString("correo"),
                rs.getString("direccion")
            });
        }
        tblClientes.setModel(model);
    }

    private void cargarProductos(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT p.*, c.nombre_c FROM producto p "
                + "INNER JOIN categoria c ON p.id_categoria = c.id_categoria"
        );
        ResultSet rs = pstmt.executeQuery();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Precio", "Cantidad", "Categoría"}, 0
        );
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id_producto"),
                rs.getString("nombre_prod"),
                rs.getDouble("precio"),
                rs.getInt("cantidad"),
                rs.getString("nombre_c")
            });
        }
        tblProductos.setModel(model);
    }

    private void cargarVentas(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT v.*, p.nombre_p, p.paterno_p FROM venta v "
                + "INNER JOIN persona p ON v.id_cliente = p.id_persona"
        );
        ResultSet rs = pstmt.executeQuery();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID Venta", "Cliente", "Total", "Fecha"}, 0
        );
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id_venta"),
                rs.getString("nombre_p") + " " + rs.getString("paterno_p"),
                rs.getDouble("total"),
                rs.getTimestamp("fecha_venta")
            });
        }
        tblVentas.setModel(model);
    }

    private void cargarFacturas(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT f.*, v.id_venta FROM factura f "
                + "INNER JOIN venta v ON f.id_venta = v.id_venta"
        );
        ResultSet rs = pstmt.executeQuery();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID Factura", "ID Venta", "Total", "Fecha"}, 0
        );
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id_factura"),
                rs.getInt("id_venta"),
                rs.getDouble("total"),
                rs.getTimestamp("fecha")
            });
        }
        tblFacturas.setModel(model);
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
            TiendaGUI gui = new TiendaGUI();
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
