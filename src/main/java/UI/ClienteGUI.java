package UI;

import Clases.Cliente;
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

public class ClienteGUI extends javax.swing.JFrame {

    private JTextField txtIdPersona, txtNombre, txtPaterno, txtMaterno,
            txtTelefono, txtFNacimiento, txtCorreo, txtDireccion;
    private JButton btnAgregar, btnModificar, btnListar, btnEliminar;
    private JTable tblClientes;
    private JScrollPane scrollPane;
    private JDialog dialogForm;
    private String operacionActual;
    private List<Cliente> listaClientes;
    private JButton btnVolverMenu;

    public ClienteGUI() {
        listaClientes = new ArrayList<>();
        initComponents1();
        cargarClientes();
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
        setTitle("Gestión de Clientes");
        setSize(1000, 700);
        setLayout(new BorderLayout());

        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        JLabel titleLabel = new JLabel("Sistema de Gestión de Clientes");
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

        // Tabla de clientes
        tblClientes = new JTable();
        tblClientes.setFont(new Font("Arial", Font.PLAIN, 14));
        tblClientes.setRowHeight(25);
        scrollPane = new JScrollPane(tblClientes);
        add(scrollPane, BorderLayout.CENTER);

        // Eventos de botones
        btnAgregar.addActionListener(e -> mostrarFormulario("Agregar"));
        btnModificar.addActionListener(e -> mostrarFormulario("Modificar"));
        btnListar.addActionListener(e -> cargarClientes());
        btnEliminar.addActionListener(e -> eliminarCliente());
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
        dialogForm = new JDialog(this, "Formulario de Cliente", true);
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
        txtCorreo = createStyledTextField();
        txtDireccion = createStyledTextField();

        addFormField(panelFormulario, "ID Persona:", txtIdPersona);
        addFormField(panelFormulario, "Nombre:", txtNombre);
        addFormField(panelFormulario, "Apellido Paterno:", txtPaterno);
        addFormField(panelFormulario, "Apellido Materno:", txtMaterno);
        addFormField(panelFormulario, "Teléfono:", txtTelefono);
        addFormField(panelFormulario, "Fecha de Nacimiento (YYYY-MM-DD):", txtFNacimiento);
        addFormField(panelFormulario, "Correo:", txtCorreo);
        addFormField(panelFormulario, "Dirección:", txtDireccion);

        JButton btnGuardar = createStyledButton("Guardar", new Color(46, 204, 113));
        JButton btnCancelar = createStyledButton("Cancelar", new Color(231, 76, 60));

        btnGuardar.addActionListener(e -> {
            if (operacionActual.equals("Agregar")) {
                agregarCliente();
            } else if (operacionActual.equals("Modificar")) {
                actualizarCliente();
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
            int filaSeleccionada = tblClientes.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente para modificar.");
                return;
            }
            cargarDatosSeleccionados(filaSeleccionada);
        } else {
            limpiarCampos();
        }
        dialogForm.setVisible(true);
    }

    private void agregarCliente() {
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
                        "cliente"
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

                    // Crear objeto Cliente
                    Cliente cliente = new Cliente(
                            persona.getId_persona(),
                            persona.getNombre_p(),
                            persona.getPaterno_p(),
                            persona.getMaterno_p(),
                            persona.getTelefono(),
                            persona.getF_nacimiento(),
                            txtCorreo.getText().trim(),
                            txtDireccion.getText().trim()
                    );

                    // Insertar en la tabla cliente
                    PreparedStatement pstmtCliente = conn.prepareStatement(
                            "INSERT INTO cliente (id_persona, correo, direccion) VALUES (?, ?, ?)"
                    );
                    pstmtCliente.setInt(1, cliente.getId_persona());
                    pstmtCliente.setString(2, cliente.getCorreo());
                    pstmtCliente.setString(3, cliente.getDireccion());
                    pstmtCliente.executeUpdate();

                    conn.commit();
                    listaClientes.add(cliente);
                    JOptionPane.showMessageDialog(this, "Cliente agregado con éxito.");
                    dialogForm.dispose();
                    cargarClientes();
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar cliente: " + ex.getMessage());
        }
    }

    private void actualizarCliente() {
        try {
            Connection conn = DBConecction.getConnection();
            conn.setAutoCommit(false);
            try {
                int idPersona = Integer.parseInt(txtIdPersona.getText());

                // Crear objeto Cliente actualizado
                Cliente clienteActualizado = new Cliente(
                        idPersona,
                        txtNombre.getText().trim(),
                        txtPaterno.getText().trim(),
                        txtMaterno.getText().trim(),
                        txtTelefono.getText().trim(),
                        txtFNacimiento.getText().trim(),
                        txtCorreo.getText().trim(),
                        txtDireccion.getText().trim());

                // Actualizar la tabla persona
                PreparedStatement pstmtPersona = conn.prepareStatement(
                        "UPDATE persona SET nombre_p = ?, paterno_p = ?, materno_p = ?, telefono = ?, f_nacimiento = ? WHERE id_persona = ?"
                );
                pstmtPersona.setString(1, clienteActualizado.getNombre_p());
                pstmtPersona.setString(2, clienteActualizado.getPaterno_p());
                pstmtPersona.setString(3, clienteActualizado.getMaterno_p());
                pstmtPersona.setString(4, clienteActualizado.getTelefono());
                pstmtPersona.setString(5, clienteActualizado.getF_nacimiento());
                pstmtPersona.setInt(6, clienteActualizado.getId_persona());
                pstmtPersona.executeUpdate();

                // Actualizar la tabla cliente
                PreparedStatement pstmtCliente = conn.prepareStatement(
                        "UPDATE cliente SET correo = ?, direccion = ? WHERE id_persona = ?"
                );
                pstmtCliente.setString(1, clienteActualizado.getCorreo());
                pstmtCliente.setString(2, clienteActualizado.getDireccion());
                pstmtCliente.setInt(3, clienteActualizado.getId_persona());
                pstmtCliente.executeUpdate();

                conn.commit();

                // Actualizar la lista de clientes
                for (int i = 0; i < listaClientes.size(); i++) {
                    if (listaClientes.get(i).getId_persona() == idPersona) {
                        listaClientes.set(i, clienteActualizado);
                        break;
                    }
                }

                JOptionPane.showMessageDialog(this, "Cliente actualizado con éxito.");
                dialogForm.dispose();
                cargarClientes();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar cliente: " + ex.getMessage());
        }
    }

    private void eliminarCliente() {
        int row = tblClientes.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este cliente?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int idPersona = Integer.parseInt(tblClientes.getValueAt(row, 0).toString());
                Connection conn = DBConecction.getConnection();

                // La eliminación en cascada se encargará de eliminar el registro en la tabla cliente
                PreparedStatement pstmt = conn.prepareStatement(
                        "DELETE FROM persona WHERE id_persona = ?"
                );
                pstmt.setInt(1, idPersona);
                pstmt.executeUpdate();

                // Eliminar de la lista de clientes
                listaClientes.removeIf(cliente -> cliente.getId_persona() == idPersona);

                JOptionPane.showMessageDialog(this, "Cliente eliminado con éxito.");
                cargarClientes();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar cliente: " + ex.getMessage());
            }
        }
    }

    private void cargarClientes() {
        try {
            Connection conn = DBConecction.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT p.*, c.correo, c.direccion "
                    + "FROM persona p "
                    + "INNER JOIN cliente c ON p.id_persona = c.id_persona "
                    + "WHERE p.tipo = 'cliente'"
            );

            listaClientes.clear();
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
            model.addColumn("Correo");
            model.addColumn("Dirección");

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id_persona"),
                        rs.getString("nombre_p"),
                        rs.getString("paterno_p"),
                        rs.getString("materno_p"),
                        rs.getString("telefono"),
                        rs.getString("f_nacimiento"),
                        rs.getString("correo"),
                        rs.getString("direccion"));
                listaClientes.add(cliente);

                model.addRow(new Object[]{
                    cliente.getId_persona(),
                    cliente.getNombre_p(),
                    cliente.getPaterno_p(),
                    cliente.getMaterno_p(),
                    cliente.getTelefono(),
                    cliente.getF_nacimiento(),
                    cliente.getCorreo(),
                    cliente.getDireccion()
                });
            }

            tblClientes.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + ex.getMessage());
        }
    }

    private void cargarDatosSeleccionados(int row) {
        if (row != -1) {
            txtIdPersona.setText(tblClientes.getValueAt(row, 0).toString());
            txtNombre.setText(tblClientes.getValueAt(row, 1).toString());
            txtPaterno.setText(tblClientes.getValueAt(row, 2).toString());
            txtMaterno.setText(tblClientes.getValueAt(row, 3).toString());
            txtTelefono.setText(tblClientes.getValueAt(row, 4).toString());
            txtFNacimiento.setText(tblClientes.getValueAt(row, 5).toString());
            txtCorreo.setText(tblClientes.getValueAt(row, 6).toString());
            txtDireccion.setText(tblClientes.getValueAt(row, 7).toString());
        }
    }

    private void limpiarCampos() {
        txtIdPersona.setText("");
        txtNombre.setText("");
        txtPaterno.setText("");
        txtMaterno.setText("");
        txtTelefono.setText("");
        txtFNacimiento.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
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
            ClienteGUI gui = new ClienteGUI();
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
