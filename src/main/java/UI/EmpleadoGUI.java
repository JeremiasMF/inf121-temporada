package UI;

import Clases.Empleado;
import Clases.Persona;
import Persistencia.DBConecction;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import UI.MenuGUI;

public class EmpleadoGUI extends javax.swing.JFrame {

    private JTextField txtIdPersona, txtNombre, txtPaterno, txtMaterno,
            txtTelefono, txtFNacimiento, txtPuesto, txtSalario;
    private JButton btnAgregar, btnModificar, btnListar, btnEliminar;
    private JTable tblEmpleados;
    private JScrollPane scrollPane;
    private JDialog dialogForm;
    private String operacionActual;
    private List<Empleado> listaEmpleados;
    private JButton btnVolverMenu;

    public EmpleadoGUI() {
        listaEmpleados = new ArrayList<>();
        initComponents1();
        cargarEmpleados();
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
        setTitle("Gestión de Empleados");
        setSize(1000, 700);
        setLayout(new BorderLayout());

        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        JLabel titleLabel = new JLabel("Sistema de Gestión de Empleados");
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

        // Tabla de empleados
        tblEmpleados = new JTable();
        tblEmpleados.setFont(new Font("Arial", Font.PLAIN, 14));
        tblEmpleados.setRowHeight(25);
        scrollPane = new JScrollPane(tblEmpleados);
        add(scrollPane, BorderLayout.CENTER);

        // Eventos de botones
        btnAgregar.addActionListener(e -> mostrarFormulario("Agregar"));
        btnModificar.addActionListener(e -> mostrarFormulario("Modificar"));
        btnListar.addActionListener(e -> cargarEmpleados());
        btnEliminar.addActionListener(e -> eliminarEmpleado());

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
        dialogForm = new JDialog(this, "Formulario de Empleado", true);
        dialogForm.setSize(500, 450);
        dialogForm.setLocationRelativeTo(this);

        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtIdPersona = createStyledTextField();
        txtIdPersona.setEditable(false);
        txtNombre = createStyledTextField();
        txtPaterno = createStyledTextField();
        txtMaterno = createStyledTextField();
        txtTelefono = createStyledTextField();
        txtFNacimiento = createStyledTextField();
        txtPuesto = createStyledTextField();
        txtSalario = createStyledTextField();

        addFormField(panelFormulario, "ID Persona:", txtIdPersona);
        addFormField(panelFormulario, "Nombre:", txtNombre);
        addFormField(panelFormulario, "Apellido Paterno:", txtPaterno);
        addFormField(panelFormulario, "Apellido Materno:", txtMaterno);
        addFormField(panelFormulario, "Teléfono:", txtTelefono);
        addFormField(panelFormulario, "Fecha de Nacimiento (YYYY-MM-DD):", txtFNacimiento);
        addFormField(panelFormulario, "Puesto:", txtPuesto);
        addFormField(panelFormulario, "Salario:", txtSalario);

        JButton btnGuardar = createStyledButton("Guardar", new Color(46, 204, 113));
        JButton btnCancelar = createStyledButton("Cancelar", new Color(231, 76, 60));

        btnGuardar.addActionListener(e -> {
            if (operacionActual.equals("Agregar")) {
                agregarEmpleado();
            } else if (operacionActual.equals("Modificar")) {
                actualizarEmpleado();
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
            int filaSeleccionada = tblEmpleados.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un empleado para modificar.");
                return;
            }
            cargarDatosSeleccionados(filaSeleccionada);
        } else {
            limpiarCampos();
        }
        dialogForm.setVisible(true);
    }

    private void agregarEmpleado() {
        try {
            Connection conn = DBConecction.getConnection();
            conn.setAutoCommit(false);
            try {
                // Crear objeto Persona
                Persona persona = new Persona(
                        0, // El ID se generará automáticamente
                        txtNombre.getText().trim(),
                        txtPaterno.getText().trim(),
                        txtMaterno.getText().trim(),
                        txtTelefono.getText().trim(),
                        txtFNacimiento.getText().trim(),
                        "empleado"
                );

                // Insertar en la tabla persona
                PreparedStatement pstmtPersona = conn.prepareStatement(
                        "INSERT INTO persona (nombre_p, paterno_p, materno_p, telefono, f_nacimiento, tipo) VALUES (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                pstmtPersona.setString(1, persona.getNombre_p());
                pstmtPersona.setString(2, persona.getPaterno_p());
                pstmtPersona.setString(3, persona.getMaterno_p());
                pstmtPersona.setString(4, persona.getTelefono());
                pstmtPersona.setString(5, persona.getF_nacimiento());
                pstmtPersona.setString(6, persona.getTipo());
                pstmtPersona.executeUpdate();

                // Obtener el ID generado
                ResultSet rs = pstmtPersona.getGeneratedKeys();
                if (rs.next()) {
                    int idPersona = rs.getInt(1);
                    persona.setId_persona(idPersona);

                    // Crear objeto Empleado
                    Empleado empleado = new Empleado(
                            persona.getId_persona(),
                            persona.getNombre_p(),
                            persona.getPaterno_p(),
                            persona.getMaterno_p(),
                            persona.getTelefono(),
                            persona.getF_nacimiento(),
                            txtPuesto.getText().trim(),
                            Double.parseDouble(txtSalario.getText().trim()));

                    // Insertar en la tabla empleado
                    PreparedStatement pstmtEmpleado = conn.prepareStatement(
                            "INSERT INTO empleado (id_persona, puesto, salario) VALUES (?, ?, ?)"
                    );
                    pstmtEmpleado.setInt(1, empleado.getId_persona());
                    pstmtEmpleado.setString(2, empleado.getPuesto());
                    pstmtEmpleado.setDouble(3, empleado.getSalario());
                    pstmtEmpleado.executeUpdate();

                    conn.commit();
                    listaEmpleados.add(empleado);
                    JOptionPane.showMessageDialog(this, "Empleado agregado con éxito.");
                    dialogForm.dispose();
                    cargarEmpleados();
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar empleado: " + ex.getMessage());
        }
    }

    private void actualizarEmpleado() {
        try {
            Connection conn = DBConecction.getConnection();
            conn.setAutoCommit(false);
            try {
                int idPersona = Integer.parseInt(txtIdPersona.getText());

                // Crear objeto Empleado actualizado
                Empleado empleadoActualizado = new Empleado(
                        idPersona,
                        txtNombre.getText().trim(),
                        txtPaterno.getText().trim(),
                        txtMaterno.getText().trim(),
                        txtTelefono.getText().trim(),
                        txtFNacimiento.getText().trim(),
                        txtPuesto.getText().trim(),
                        Double.parseDouble(txtSalario.getText().trim()));

                // Actualizar la tabla persona
                PreparedStatement pstmtPersona = conn.prepareStatement(
                        "UPDATE persona SET nombre_p = ?, paterno_p = ?, materno_p = ?, telefono = ?, f_nacimiento = ? WHERE id_persona = ?"
                );
                pstmtPersona.setString(1, empleadoActualizado.getNombre_p());
                pstmtPersona.setString(2, empleadoActualizado.getPaterno_p());
                pstmtPersona.setString(3, empleadoActualizado.getMaterno_p());
                pstmtPersona.setString(4, empleadoActualizado.getTelefono());
                pstmtPersona.setString(5, empleadoActualizado.getF_nacimiento());
                pstmtPersona.setInt(6, empleadoActualizado.getId_persona());
                pstmtPersona.executeUpdate();

                // Actualizar la tabla empleado
                PreparedStatement pstmtEmpleado = conn.prepareStatement(
                        "UPDATE empleado SET puesto = ?, salario = ? WHERE id_persona = ?"
                );
                pstmtEmpleado.setString(1, empleadoActualizado.getPuesto());
                pstmtEmpleado.setDouble(2, empleadoActualizado.getSalario());
                pstmtEmpleado.setInt(3, empleadoActualizado.getId_persona());
                pstmtEmpleado.executeUpdate();

                conn.commit();

                // Actualizar la lista de empleados
                for (int i = 0; i < listaEmpleados.size(); i++) {
                    if (listaEmpleados.get(i).getId_persona() == idPersona) {
                        listaEmpleados.set(i, empleadoActualizado);
                        break;
                    }
                }

                JOptionPane.showMessageDialog(this, "Empleado actualizado con éxito.");
                dialogForm.dispose();
                cargarEmpleados();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar empleado: " + ex.getMessage());
        }
    }

    private void eliminarEmpleado() {
        int row = tblEmpleados.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un empleado para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este empleado?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int idPersona = Integer.parseInt(tblEmpleados.getValueAt(row, 0).toString());
                Connection conn = DBConecction.getConnection();

                // La eliminación en cascada se encargará de eliminar el registro en la tabla empleado
                PreparedStatement pstmt = conn.prepareStatement(
                        "DELETE FROM persona WHERE id_persona = ?"
                );
                pstmt.setInt(1, idPersona);
                pstmt.executeUpdate();

                // Eliminar de la lista de empleados
                listaEmpleados.removeIf(empleado -> empleado.getId_persona() == idPersona);

                JOptionPane.showMessageDialog(this, "Empleado eliminado con éxito.");
                cargarEmpleados();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar empleado: " + ex.getMessage());
            }
        }
    }

    private void cargarEmpleados() {
        try {
            Connection conn = DBConecction.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT p.*, e.puesto, e.salario "
                    + "FROM persona p "
                    + "INNER JOIN empleado e ON p.id_persona = e.id_persona "
                    + "WHERE p.tipo = 'empleado'"
            );

            listaEmpleados.clear();
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
            model.addColumn("Puesto");
            model.addColumn("Salario");

            while (rs.next()) {
                Empleado empleado = new Empleado(
                        rs.getInt("id_persona"),
                        rs.getString("nombre_p"),
                        rs.getString("paterno_p"),
                        rs.getString("materno_p"),
                        rs.getString("telefono"),
                        rs.getString("f_nacimiento"),
                        rs.getString("puesto"),
                        rs.getDouble("salario"));

                listaEmpleados.add(empleado);

                model.addRow(new Object[]{
                    empleado.getId_persona(),
                    empleado.getNombre_p(),
                    empleado.getPaterno_p(),
                    empleado.getMaterno_p(),
                    empleado.getTelefono(),
                    empleado.getF_nacimiento(),
                    empleado.getPuesto(),
                    empleado.getSalario()
                });
            }

            tblEmpleados.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + ex.getMessage());
        }
    }

    private void cargarDatosSeleccionados(int row) {
        if (row != -1) {
            txtIdPersona.setText(tblEmpleados.getValueAt(row, 0).toString());
            txtNombre.setText(tblEmpleados.getValueAt(row, 1).toString());
            txtPaterno.setText(tblEmpleados.getValueAt(row, 2).toString());
            txtMaterno.setText(tblEmpleados.getValueAt(row, 3).toString());
            txtTelefono.setText(tblEmpleados.getValueAt(row, 4).toString());
            txtFNacimiento.setText(tblEmpleados.getValueAt(row, 5).toString());
            txtPuesto.setText(tblEmpleados.getValueAt(row, 6).toString());
            txtSalario.setText(tblEmpleados.getValueAt(row, 7).toString());
        }
    }

    private void limpiarCampos() {
        txtIdPersona.setText("");
        txtNombre.setText("");
        txtPaterno.setText("");
        txtMaterno.setText("");
        txtTelefono.setText("");
        txtFNacimiento.setText("");
        txtPuesto.setText("");
        txtSalario.setText("");
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
            EmpleadoGUI gui = new EmpleadoGUI();
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
