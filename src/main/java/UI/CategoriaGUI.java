package UI;

import Clases.Categoria;
import Persistencia.DBConecction;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import UI.MenuGUI;

public class CategoriaGUI extends javax.swing.JFrame {

    private JTextField txtIdCategoria, txtNombre, txtDescripcion;
    private JButton btnAgregar, btnModificar, btnListar, btnEliminar;
    private JTable tblCategorias;
    private JScrollPane scrollPane;
    private JDialog dialogForm;
    private String operacionActual;
    private List<Categoria> listaCategorias;
    private JButton btnVolverMenu;

    public CategoriaGUI() {
        listaCategorias = new ArrayList<>();
        initComponents1();
        cargarCategorias();
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
        setTitle("Gestión de Categorías");
        setSize(1000, 700);
        setLayout(new BorderLayout());

        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        JLabel titleLabel = new JLabel("Sistema de Gestión de Categorías");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitulo.add(titleLabel);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel de botones
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

        // Tabla de categorías
        tblCategorias = new JTable();
        tblCategorias.setFont(new Font("Arial", Font.PLAIN, 14));
        tblCategorias.setRowHeight(25);
        scrollPane = new JScrollPane(tblCategorias);
        add(scrollPane, BorderLayout.CENTER);

        // Eventos de botones
        btnAgregar.addActionListener(e -> mostrarFormulario("Agregar"));
        btnModificar.addActionListener(e -> mostrarFormulario("Modificar"));
        btnListar.addActionListener(e -> cargarCategorias());
        btnEliminar.addActionListener(e -> eliminarCategoria());
        btnVolverMenu.addActionListener(e -> volverAlMenu());

        // Inicializar el diálogo del formulario
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
        dialogForm = new JDialog(this, "Formulario de Categoría", true);
        dialogForm.setSize(400, 250);
        dialogForm.setLocationRelativeTo(this);

        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtIdCategoria = createStyledTextField();
        txtIdCategoria.setEditable(false);
        txtNombre = createStyledTextField();
        txtDescripcion = createStyledTextField();

        addFormField(panelFormulario, "ID Categoría:", txtIdCategoria);
        addFormField(panelFormulario, "Nombre:", txtNombre);
        addFormField(panelFormulario, "Descripción:", txtDescripcion);

        JButton btnGuardar = createStyledButton("Guardar", new Color(46, 204, 113));
        JButton btnCancelar = createStyledButton("Cancelar", new Color(231, 76, 60));

        btnGuardar.addActionListener(e -> {
            if (operacionActual.equals("Agregar")) {
                agregarCategoria();
            } else if (operacionActual.equals("Modificar")) {
                actualizarCategoria();
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

    private void addFormField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label);
        panel.add(textField);
    }

    private void mostrarFormulario(String operacion) {
        operacionActual = operacion;
        if (operacion.equals("Modificar")) {
            int filaSeleccionada = tblCategorias.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una categoría para modificar.");
                return;
            }
            cargarDatosSeleccionados(filaSeleccionada);
        } else {
            limpiarCampos();
        }
        dialogForm.setVisible(true);
    }

    private void agregarCategoria() {
        try {
            Categoria categoria = new Categoria(
                    0, // El ID se generará automáticamente
                    txtNombre.getText().trim(),
                    txtDescripcion.getText().trim()
            );

            Connection conn = DBConecction.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO categoria (nombre_c, descripcion_c) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            pstmt.setString(1, categoria.getNombre_c());
            pstmt.setString(2, categoria.getDescripcion_c());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                categoria.setId_categoria(rs.getInt(1));
                listaCategorias.add(categoria);
            }

            JOptionPane.showMessageDialog(this, "Categoría agregada con éxito.");
            dialogForm.dispose();
            cargarCategorias();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar categoría: " + ex.getMessage());
        }
    }

    private void actualizarCategoria() {
        try {
            int idCategoria = Integer.parseInt(txtIdCategoria.getText());

            Categoria categoriaActualizada = new Categoria(
                    idCategoria,
                    txtNombre.getText().trim(),
                    txtDescripcion.getText().trim()
            );

            Connection conn = DBConecction.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE categoria SET nombre_c = ?, descripcion_c = ? WHERE id_categoria = ?"
            );
            pstmt.setString(1, categoriaActualizada.getNombre_c());
            pstmt.setString(2, categoriaActualizada.getDescripcion_c());
            pstmt.setInt(3, categoriaActualizada.getId_categoria());
            pstmt.executeUpdate();

            // Actualizar la lista de categorías
            for (int i = 0; i < listaCategorias.size(); i++) {
                if (listaCategorias.get(i).getId_categoria() == idCategoria) {
                    listaCategorias.set(i, categoriaActualizada);
                    break;
                }
            }

            JOptionPane.showMessageDialog(this, "Categoría actualizada con éxito.");
            dialogForm.dispose();
            cargarCategorias();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar categoría: " + ex.getMessage());
        }
    }

    private void eliminarCategoria() {
        int row = tblCategorias.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una categoría para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar esta categoría?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int idCategoria = Integer.parseInt(tblCategorias.getValueAt(row, 0).toString());
                Connection conn = DBConecction.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "DELETE FROM categoria WHERE id_categoria = ?"
                );
                pstmt.setInt(1, idCategoria);
                pstmt.executeUpdate();

                // Eliminar de la lista de categorías
                listaCategorias.removeIf(categoria -> categoria.getId_categoria() == idCategoria);

                JOptionPane.showMessageDialog(this, "Categoría eliminada con éxito.");
                cargarCategorias();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar categoría: " + ex.getMessage());
            }
        }
    }

    private void cargarCategorias() {
        try {
            Connection conn = DBConecction.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categoria");

            listaCategorias.clear();
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            model.addColumn("ID Categoría");
            model.addColumn("Nombre");
            model.addColumn("Descripción");

            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_c"),
                        rs.getString("descripcion_c")
                );
                listaCategorias.add(categoria);

                model.addRow(new Object[]{
                    categoria.getId_categoria(),
                    categoria.getNombre_c(),
                    categoria.getDescripcion_c()
                });
            }

            tblCategorias.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar categorías: " + ex.getMessage());
        }
    }

    private void cargarDatosSeleccionados(int row) {
        if (row != -1) {
            txtIdCategoria.setText(tblCategorias.getValueAt(row, 0).toString());
            txtNombre.setText(tblCategorias.getValueAt(row, 1).toString());
            txtDescripcion.setText(tblCategorias.getValueAt(row, 2).toString());
        }
    }

    private void limpiarCampos() {
        txtIdCategoria.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
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
            CategoriaGUI gui = new CategoriaGUI();
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
