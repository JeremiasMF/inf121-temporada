package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGUI extends javax.swing.JFrame {

    private JButton btnPersona, btnCliente, btnEmpleado, btnCategoria, btnProducto, btnVenta, btnFactura, btnTienda;

    public MenuGUI() {
        initComponents1();
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
        setTitle("Sistema de Gestión de Tienda - Menú Principal");
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("Menú Principal");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(4, 2, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnPersona = createStyledButton("Gestión de Personas", new Color(52, 152, 219));
        btnCliente = createStyledButton("Gestión de Clientes", new Color(46, 204, 113));
        btnEmpleado = createStyledButton("Gestión de Empleados", new Color(155, 89, 182));
        btnCategoria = createStyledButton("Gestión de Categorías", new Color(231, 76, 60));
        btnProducto = createStyledButton("Gestión de Productos", new Color(241, 196, 15));
        btnVenta = createStyledButton("Gestión de Ventas", new Color(230, 126, 34));
        btnFactura = createStyledButton("Gestión de Facturas", new Color(52, 73, 94));
        btnTienda = createStyledButton("Gestión de Tienda", new Color(149, 165, 166));

        panelBotones.add(btnPersona);
        panelBotones.add(btnCliente);
        panelBotones.add(btnEmpleado);
        panelBotones.add(btnCategoria);
        panelBotones.add(btnProducto);
        panelBotones.add(btnVenta);
        panelBotones.add(btnFactura);
        panelBotones.add(btnTienda);

        add(panelBotones, BorderLayout.CENTER);

        addActionListeners();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 60));
        return button;
    }

    private void addActionListeners() {
        btnPersona.addActionListener(e -> openGUI(new PersonaGUI()));
        btnCliente.addActionListener(e -> openGUI(new ClienteGUI()));
        btnEmpleado.addActionListener(e -> openGUI(new EmpleadoGUI()));
        btnCategoria.addActionListener(e -> openGUI(new CategoriaGUI()));
        btnProducto.addActionListener(e -> openGUI(new ProductoGUI()));
        btnVenta.addActionListener(e -> openGUI(new VentaGUI()));
        btnFactura.addActionListener(e -> openGUI(new FacturaGUI()));
        btnTienda.addActionListener(e -> openGUI(new TiendaGUI()));
    }

    private void openGUI(JFrame gui) {
        gui.setVisible(true);
        this.setVisible(false);
        gui.setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            MenuGUI menu = new MenuGUI();
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
