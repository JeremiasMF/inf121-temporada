package UI;

import Clases.Producto;
import Clases.Categoria;
import Persistencia.DBConecction;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import UI.MenuGUI;

public class ProductoGUI extends javax.swing.JFrame {

    private JTextField txtIdProducto, txtNombre, txtPrecio, txtCantidad;
    private JComboBox<Categoria> cmbCategoria;
    private JButton btnAgregar, btnModificar, btnListar, btnEliminar;
    private JTable tblProductos;
    private JScrollPane scrollPane;
    private JDialog dialogForm;
    private String operacionActual;
    private List<Producto> listaProductos;
    private List<Categoria> listaCategorias;
    private JButton btnVolverMenu;

    public ProductoGUI() {
        listaProductos = new ArrayList<>();
        listaCategorias = new ArrayList<>();
        initComponents1();
        cargarCategorias();
        cargarProductos();
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
        setTitle("Gestión de Productos");
        setSize(1000, 700);
        setLayout(new BorderLayout());

        JPanel panelTitulo = new JPanel();
        JLabel titleLabel = new JLabel("Sistema de Gestión de Productos");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitulo.add(titleLabel);
        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnAgregar = createStyledButton("Agregar", new Color(46, 204, 113));
        btnModificar = createStyledButton("Modificar", new Color(52, 152, 219));
        btnListar = createStyledButton("Listar", new Color(155, 89, 182));
        btnEliminar = createStyledButton("Eliminar", new Color(231, 76, 60));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnListar);
        panelBotones.add(btnEliminar);

        btnVolverMenu = createStyledButton("Volver al Menú", new Color(52, 73, 94));
        panelBotones.add(btnVolverMenu);

        add(panelBotones, BorderLayout.SOUTH);

        tblProductos = new JTable();
        tblProductos.setFont(new Font("Arial", Font.PLAIN, 14));
        tblProductos.setRowHeight(25);
        scrollPane = new JScrollPane(tblProductos);
        add(scrollPane, BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> mostrarFormulario("Agregar"));
        btnModificar.addActionListener(e -> mostrarFormulario("Modificar"));
        btnListar.addActionListener(e -> cargarProductos());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnVolverMenu.addActionListener(e -> volverAlMenu());

        initFormDialog();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 50));
        return button;
    }

    private void initFormDialog() {
        dialogForm = new JDialog(this, "Formulario de Producto", true);
        dialogForm.setSize(400, 300);
        dialogForm.setLocationRelativeTo(this);

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtIdProducto = createStyledTextField();
        txtIdProducto.setEditable(false);
        txtNombre = createStyledTextField();
        txtPrecio = createStyledTextField();
        txtCantidad = createStyledTextField();
        cmbCategoria = new JComboBox<>();
        cmbCategoria.setFont(new Font("Arial", Font.PLAIN, 14));

        cmbCategoria.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Categoria) {
                    setText(((Categoria) value).getNombre_c());
                }
                return this;
            }
        });

        addFormField(panelFormulario, "ID Producto:", txtIdProducto);
        addFormField(panelFormulario, "Nombre:", txtNombre);
        addFormField(panelFormulario, "Precio:", txtPrecio);
        addFormField(panelFormulario, "Cantidad:", txtCantidad);
        addFormField(panelFormulario, "Categoría:", cmbCategoria);

        JButton btnGuardar = createStyledButton("Guardar", new Color(46, 204, 113));
        JButton btnCancelar = createStyledButton("Cancelar", new Color(231, 76, 60));

        btnGuardar.addActionListener(e -> {
            if (operacionActual.equals("Agregar")) {
                agregarProducto();
            } else if (operacionActual.equals("Modificar")) {
                actualizarProducto();
            }
        });

        btnCancelar.addActionListener(e -> dialogForm.dispose());

        JPanel panelBotonesForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotonesForm.add(btnGuardar);
        panelBotonesForm.add(btnCancelar);

        dialogForm.setLayout(new BorderLayout());
        dialogForm.add(panelFormulario, BorderLayout.CENTER);
        dialogForm.add(panelBotonesForm, BorderLayout.SOUTH);
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        return textField;
    }

    private void addFormField(JPanel panel, String labelText, JComponent component) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label);
        panel.add(component);
    }

    private void mostrarFormulario(String operacion) {
        operacionActual = operacion;
        if (operacion.equals("Modificar")) {
            int filaSeleccionada = tblProductos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto para modificar.");
                return;
            }
            cargarDatosSeleccionados(filaSeleccionada);
        } else {
            limpiarCampos();
        }
        dialogForm.setVisible(true);
    }

    private void agregarProducto() {
        try {
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            Categoria categoriaSeleccionada = (Categoria) cmbCategoria.getSelectedItem();

            Connection conn = DBConecction.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO producto (nombre_prod, precio, cantidad, id_categoria) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            pstmt.setString(1, nombre);
            pstmt.setDouble(2, precio);
            pstmt.setInt(3, cantidad);
            pstmt.setInt(4, categoriaSeleccionada.getId_categoria());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int idProducto = rs.getInt(1);
                Producto nuevoProducto = new Producto(idProducto, nombre, precio, cantidad, categoriaSeleccionada);
                listaProductos.add(nuevoProducto);
            }

            JOptionPane.showMessageDialog(this, "Producto agregado con éxito.");
            dialogForm.dispose();
            cargarProductos();
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar producto: " + ex.getMessage());
        }
    }

    private void actualizarProducto() {
        try {
            int idProducto = Integer.parseInt(txtIdProducto.getText());
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            Categoria categoriaSeleccionada = (Categoria) cmbCategoria.getSelectedItem();

            Connection conn = DBConecction.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE producto SET nombre_prod = ?, precio = ?, cantidad = ?, id_categoria = ? WHERE id_producto = ?"
            );
            pstmt.setString(1, nombre);
            pstmt.setDouble(2, precio);
            pstmt.setInt(3, cantidad);
            pstmt.setInt(4, categoriaSeleccionada.getId_categoria());
            pstmt.setInt(5, idProducto);
            pstmt.executeUpdate();

            // Actualizar la lista de productos
            for (int i = 0; i < listaProductos.size(); i++) {
                if (listaProductos.get(i).getId_producto() == idProducto) {
                    listaProductos.set(i, new Producto(idProducto, nombre, precio, cantidad, categoriaSeleccionada));
                    break;
                }
            }

            JOptionPane.showMessageDialog(this, "Producto actualizado con éxito.");
            dialogForm.dispose();
            cargarProductos();
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar producto: " + ex.getMessage());
        }
    }

    private void eliminarProducto() {
        int row = tblProductos.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int idProducto = Integer.parseInt(tblProductos.getValueAt(row, 0).toString());
                Connection conn = DBConecction.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "DELETE FROM producto WHERE id_producto = ?"
                );
                pstmt.setInt(1, idProducto);
                pstmt.executeUpdate();

                listaProductos.removeIf(producto -> producto.getId_producto() == idProducto);

                JOptionPane.showMessageDialog(this, "Producto eliminado con éxito.");
                cargarProductos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar producto: " + ex.getMessage());
            }
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

            listaProductos.clear();
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Precio");
            model.addColumn("Cantidad");
            model.addColumn("Categoría");

            while (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                String nombre = rs.getString("nombre_prod");
                double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");
                int idCategoria = rs.getInt("id_categoria");
                String nombreCategoria = rs.getString("nombre_c");

                Categoria categoria = new Categoria(idCategoria, nombreCategoria, "");
                Producto producto = new Producto(idProducto, nombre, precio, cantidad, categoria);
                listaProductos.add(producto);

                model.addRow(new Object[]{
                    idProducto,
                    nombre,
                    precio,
                    cantidad,
                    nombreCategoria
                });
            }

            tblProductos.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + ex.getMessage());
        }
    }

    private void cargarCategorias() {
        try {
            Connection conn = DBConecction.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categoria");

            listaCategorias.clear();
            cmbCategoria.removeAllItems();

            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_c"),
                        rs.getString("descripcion_c")
                );
                listaCategorias.add(categoria);
                cmbCategoria.addItem(categoria);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar categorías: " + ex.getMessage());
        }
    }

    private void cargarDatosSeleccionados(int row) {
        if (row != -1) {
            txtIdProducto.setText(tblProductos.getValueAt(row, 0).toString());
            txtNombre.setText(tblProductos.getValueAt(row, 1).toString());
            txtPrecio.setText(tblProductos.getValueAt(row, 2).toString());
            txtCantidad.setText(tblProductos.getValueAt(row, 3).toString());
            String nombreCategoria = tblProductos.getValueAt(row, 4).toString();
            for (int i = 0; i < cmbCategoria.getItemCount(); i++) {
                if (cmbCategoria.getItemAt(i).getNombre_c().equals(nombreCategoria)) {
                    cmbCategoria.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void limpiarCampos() {
        txtIdProducto.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        cmbCategoria.setSelectedIndex(0);
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
            ProductoGUI gui = new ProductoGUI();
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
