package UI;

import Clases.Factura;
import Clases.Venta;
import Persistencia.DBConecction;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import UI.MenuGUI;
import javax.swing.table.DefaultTableCellRenderer;

public class FacturaGUI extends javax.swing.JFrame {

    private JTable tblFacturas;
    private JButton btnGenerarFactura, btnVerDetalle, btnVolverMenu;
    private DefaultTableModel modelFacturas;
    private List<Factura> listaFacturas;

    public FacturaGUI() {
        listaFacturas = new ArrayList<>();
        initComponents1();
        cargarFacturas();
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
        setTitle("Gestión de Facturas");
        setSize(1000, 800);
        setLayout(new BorderLayout());

        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("Sistema de Gestión de Facturas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Tabla de facturas
        modelFacturas = new DefaultTableModel(
                new Object[]{"ID Factura", "ID Venta", "Fecha", "Total"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblFacturas = new JTable(modelFacturas);
        tblFacturas.setFont(new Font("Arial", Font.PLAIN, 14));
        tblFacturas.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tblFacturas);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnGenerarFactura = createStyledButton("Generar Factura", new Color(46, 204, 113));
        btnVerDetalle = createStyledButton("Ver Detalle", new Color(52, 152, 219));

        panelBotones.add(btnGenerarFactura);
        panelBotones.add(btnVerDetalle);

        btnVolverMenu = createStyledButton("Volver al Menú", new Color(52, 73, 94));
        panelBotones.add(btnVolverMenu);

        add(panelBotones, BorderLayout.SOUTH);

        // Eventos de botones
        btnGenerarFactura.addActionListener(e -> generarFactura());
        btnVerDetalle.addActionListener(e -> verDetalleFactura());
        btnVolverMenu.addActionListener(e -> volverAlMenu());
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

    private void cargarFacturas() {
        try {
            Connection conn = DBConecction.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT f.id_factura, f.id_venta, f.fecha, f.total "
                    + "FROM factura f "
                    + "ORDER BY f.fecha DESC"
            );

            modelFacturas.setRowCount(0);
            listaFacturas.clear();

            while (rs.next()) {
                Factura factura = new Factura(
                        rs.getInt("id_factura"),
                        new Venta(rs.getInt("id_venta"), null, null, null, 0), // Venta simplificada
                        rs.getString("fecha"),
                        rs.getDouble("total")
                );
                listaFacturas.add(factura);

                modelFacturas.addRow(new Object[]{
                    factura.getId_factura(),
                    factura.getVenta().getId_venta(),
                    factura.getFecha(),
                    String.format("$%.2f", factura.getTotal())
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar facturas: " + ex.getMessage());
        }
    }

    private void generarFactura() {
        try {
            // Mostrar diálogo para seleccionar una venta
            String idVentaStr = JOptionPane.showInputDialog(this, "Ingrese el ID de la venta para generar la factura:");
            if (idVentaStr == null || idVentaStr.trim().isEmpty()) {
                return;
            }

            int idVenta = Integer.parseInt(idVentaStr);

            Connection conn = DBConecction.getConnection();

            // Verificar si la venta existe y obtener su total
            PreparedStatement pstmtVenta = conn.prepareStatement(
                    "SELECT total FROM venta WHERE id_venta = ?"
            );
            pstmtVenta.setInt(1, idVenta);
            ResultSet rsVenta = pstmtVenta.executeQuery();

            if (!rsVenta.next()) {
                JOptionPane.showMessageDialog(this, "La venta especificada no existe.");
                return;
            }

            double totalVenta = rsVenta.getDouble("total");

            // Insertar la nueva factura
            PreparedStatement pstmtFactura = conn.prepareStatement(
                    "INSERT INTO factura (id_venta, total) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            pstmtFactura.setInt(1, idVenta);
            pstmtFactura.setDouble(2, totalVenta);
            pstmtFactura.executeUpdate();

            ResultSet rsFactura = pstmtFactura.getGeneratedKeys();
            if (rsFactura.next()) {
                int idFactura = rsFactura.getInt(1);
                JOptionPane.showMessageDialog(this, "Factura generada con éxito. ID de factura: " + idFactura);
                cargarFacturas();
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al generar factura: " + ex.getMessage());
        }
    }

    private void verDetalleFactura() {
        int filaSeleccionada = tblFacturas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una factura para ver su detalle.");
            return;
        }

        int idFactura = (int) tblFacturas.getValueAt(filaSeleccionada, 0);
        int idVenta = (int) tblFacturas.getValueAt(filaSeleccionada, 1);

        try {
            Connection conn = DBConecction.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT p.nombre_prod, vp.cantidad, vp.precio_unitario, (vp.cantidad * vp.precio_unitario) as subtotal "
                    + "FROM venta_producto vp "
                    + "INNER JOIN producto p ON vp.id_producto = p.id_producto "
                    + "WHERE vp.id_venta = ?"
            );
            pstmt.setInt(1, idVenta);
            ResultSet rs = pstmt.executeQuery();

            // Crear un modelo de tabla para el detalle
            DefaultTableModel modelDetalle = new DefaultTableModel(
                    new Object[]{"Producto", "Cantidad", "Precio Unitario", "Subtotal"}, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            double total = 0;
            while (rs.next()) {
                String producto = rs.getString("nombre_prod");
                int cantidad = rs.getInt("cantidad");
                double precioUnitario = rs.getDouble("precio_unitario");
                double subtotal = rs.getDouble("subtotal");
                total += subtotal;

                modelDetalle.addRow(new Object[]{
                    producto,
                    cantidad,
                    String.format("$%.2f", precioUnitario),
                    String.format("$%.2f", subtotal)
                });
            }

            // Crear la tabla con el modelo
            JTable tblDetalle = new JTable(modelDetalle);
            tblDetalle.setFont(new Font("Arial", Font.PLAIN, 14));
            tblDetalle.setRowHeight(25);
            tblDetalle.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

            // Configurar un renderizador personalizado para alinear los números a la derecha
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
            tblDetalle.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
            tblDetalle.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
            tblDetalle.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

            // Crear un panel para contener la tabla y el total
            JPanel panelDetalle = new JPanel(new BorderLayout());
            panelDetalle.add(new JScrollPane(tblDetalle), BorderLayout.CENTER);

            // Agregar el total en la parte inferior
            JLabel lblTotal = new JLabel(String.format("Total: $%.2f", total));
            lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
            lblTotal.setHorizontalAlignment(JLabel.RIGHT);
            lblTotal.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
            panelDetalle.add(lblTotal, BorderLayout.SOUTH);

            // Mostrar el detalle en un JOptionPane
            JOptionPane.showMessageDialog(this, panelDetalle,
                    "Detalle de Factura #" + idFactura, JOptionPane.PLAIN_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener el detalle de la factura: " + ex.getMessage());
        }
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
            FacturaGUI gui = new FacturaGUI();
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
