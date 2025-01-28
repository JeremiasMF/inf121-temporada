package UI;

import Clases.Persona;
import Persistencia.DBConecction;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import UI.MenuGUI;

public class PersonaGUI extends javax.swing.JFrame {

    private JTextField txtIdPersona, txtNombre, txtPaterno, txtMaterno, txtTelefono, txtFNacimiento;
    private JButton btnAgregar, btnModificar, btnListar, btnEliminar;
    private JTable tblPersonas;
    private JScrollPane scrollPane;
    private JDialog dialogForm;
    private String operacionActual;
    private JButton btnVolverMenu;

    public PersonaGUI() {
        initComponents1();
        cargarPersonas();
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
        setTitle("Gestión de Personas");
        setSize(1000, 700);
        setLayout(new BorderLayout());

        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        JLabel titleLabel = new JLabel("Sistema de Gestión de Personas");
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

        // Tabla de personas
        tblPersonas = new JTable();
        tblPersonas.setFont(new Font("Arial", Font.PLAIN, 14));
        tblPersonas.setRowHeight(25);
        scrollPane = new JScrollPane(tblPersonas);
        add(scrollPane, BorderLayout.CENTER);

        // Eventos de botones
        btnAgregar.addActionListener(e -> mostrarFormulario("Agregar"));
        btnModificar.addActionListener(e -> mostrarFormulario("Modificar"));
        btnListar.addActionListener(e -> cargarPersonas());
        btnEliminar.addActionListener(e -> eliminarPersona());
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
        dialogForm = new JDialog(this, "Formulario de Persona", true);
        dialogForm.setSize(500, 400);
        dialogForm.setLocationRelativeTo(this);

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtIdPersona = createStyledTextField();
        txtIdPersona.setEditable(false);
        txtNombre = createStyledTextField();
        txtPaterno = createStyledTextField();
        txtMaterno = createStyledTextField();
        txtTelefono = createStyledTextField();
        txtFNacimiento = createStyledTextField();

        addFormField(panelFormulario, "ID Persona:", txtIdPersona);
        addFormField(panelFormulario, "Nombre:", txtNombre);
        addFormField(panelFormulario, "Apellido Paterno:", txtPaterno);
        addFormField(panelFormulario, "Apellido Materno:", txtMaterno);
        addFormField(panelFormulario, "Teléfono:", txtTelefono);
        addFormField(panelFormulario, "Fecha de Nacimiento (YYYY-MM-DD):", txtFNacimiento);

        JButton btnGuardar = createStyledButton("Guardar", new Color(46, 204, 113));
        JButton btnCancelar = createStyledButton("Cancelar", new Color(231, 76, 60));

        btnGuardar.addActionListener(e -> {
            if (operacionActual.equals("Agregar")) {
                agregarPersona();
            } else if (operacionActual.equals("Modificar")) {
                actualizarPersona();
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
            int filaSeleccionada = tblPersonas.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una persona para modificar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            cargarDatosSeleccionados(filaSeleccionada);
        } else {
            limpiarCampos();
        }
        dialogForm.setTitle(operacion + " Persona");
        dialogForm.setVisible(true);
    }

private void agregarPersona() {
        try {
            Connection conn = DBConecction.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO persona (nombre_p, paterno_p, materno_p, telefono, f_nacimiento, tipo) VALUES (?, ?, ?, ?, ?, 'persona')"
            );
            pstmt.setString(1, txtNombre.getText().trim());
            pstmt.setString(2, txtPaterno.getText().trim());
            pstmt.setString(3, txtMaterno.getText().trim());
            pstmt.setString(4, txtTelefono.getText().trim());
            pstmt.setString(5, txtFNacimiento.getText().trim());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Persona agregada con éxito.");
            dialogForm.dispose();
            cargarPersonas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar persona: " + ex.getMessage());
        }
    }

    private void actualizarPersona() {
        try {
            Connection conn = DBConecction.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE persona SET nombre_p = ?, paterno_p = ?, materno_p = ?, telefono = ?, f_nacimiento = ? WHERE id_persona = ?"
            );
            pstmt.setString(1, txtNombre.getText().trim());
            pstmt.setString(2, txtPaterno.getText().trim());
            pstmt.setString(3, txtMaterno.getText().trim());
            pstmt.setString(4, txtTelefono.getText().trim());
            pstmt.setString(5, txtFNacimiento.getText().trim());
            pstmt.setInt(6, Integer.parseInt(txtIdPersona.getText()));
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Persona actualizada con éxito.");
            dialogForm.dispose();
            cargarPersonas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar persona: " + ex.getMessage());
        }
    }

    private void eliminarPersona() {
        int row = tblPersonas.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una persona para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar esta persona?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int idPersona = Integer.parseInt(tblPersonas.getValueAt(row, 0).toString());
                Connection conn = DBConecction.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "DELETE FROM persona WHERE id_persona = ?"
                );
                pstmt.setInt(1, idPersona);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Persona eliminada con éxito.");
                cargarPersonas();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar persona: " + ex.getMessage());
            }
        }
    }

    private void cargarPersonas() {
        try {
            Connection conn = DBConecction.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM persona");

            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            model.addColumn("ID Persona");
            model.addColumn("Nombre");
            model.addColumn("Apellido Paterno");
            model.addColumn("Apellido Materno");
            model.addColumn("Teléfono");
            model.addColumn("Fecha Nacimiento");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_persona"),
                    rs.getString("nombre_p"),
                    rs.getString("paterno_p"),
                    rs.getString("materno_p"),
                    rs.getString("telefono"),
                    rs.getString("f_nacimiento")
                });
            }

            tblPersonas.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar personas: " + ex.getMessage());
        }
    }

    private void cargarDatosSeleccionados(int row) {
        if (row != -1) {
            txtIdPersona.setText(tblPersonas.getValueAt(row, 0).toString());
            txtNombre.setText(tblPersonas.getValueAt(row, 1).toString());
            txtPaterno.setText(tblPersonas.getValueAt(row, 2).toString());
            txtMaterno.setText(tblPersonas.getValueAt(row, 3).toString());
            txtTelefono.setText(tblPersonas.getValueAt(row, 4).toString());
            txtFNacimiento.setText(tblPersonas.getValueAt(row, 5).toString());
        }
    }

    private void limpiarCampos() {
        txtIdPersona.setText("");
        txtNombre.setText("");
        txtPaterno.setText("");
        txtMaterno.setText("");
        txtTelefono.setText("");
        txtFNacimiento.setText("");
    }

    private void volverAlMenu() {
        this.dispose();
        MenuGUI mn= new MenuGUI();
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
            PersonaGUI gui = new PersonaGUI();
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
