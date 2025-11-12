package Vista;

import Controlador.ControladorUsuario;
import Modelo.Constantes;
import Modelo.TipoRol;
import javax.swing.*;
import java.awt.*;

public class InterfazLoginMejorada extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JButton btnCancelar;
    
    // Componentes para registro
    private JTextField txtNuevoUsuario;
    private JPasswordField txtNuevaContrasena;
    private JPasswordField txtConfirmarContrasena;
    private JTextField txtNombreCompleto;
    private JComboBox<String> cmbRol;
    private JButton btnRegistrar;
    private JButton btnMostrarRegistro;
    private JPanel panelRegistro;
    private boolean mostrandoRegistro = false;
    
    private ControladorUsuario controladorUsuario;
    
    public InterfazLoginMejorada() {
        super("Sistema de Gestion Contable - Inicio de Sesion");
        controladorUsuario = ControladorUsuario.getInstancia();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 680);
        setLocationRelativeTo(null);
        setResizable(false);
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(236, 240, 241));
        
        // Panel Superior - Logo y Titulo
        JPanel panelTitulo = crearPanelTitulo();
        
        // Panel Central - Login
        JPanel panelLogin = crearPanelLogin();
        
        // Panel de Registro (inicialmente oculto)
        panelRegistro = crearPanelRegistro();
        panelRegistro.setVisible(false);
        
        // Panel de informacion
        JPanel panelInfo = crearPanelInformacion();
        
        // Boton para mostrar/ocultar registro
        JPanel panelToggle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelToggle.setOpaque(false);
        btnMostrarRegistro = new JButton("Crear Nueva Cuenta de Usuario");
        btnMostrarRegistro.setFont(new Font("Arial", Font.BOLD, 12));
        btnMostrarRegistro.setBackground(new Color(52, 152, 219));
        btnMostrarRegistro.setForeground(Color.WHITE);
        btnMostrarRegistro.setFocusPainted(false);
        btnMostrarRegistro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMostrarRegistro.addActionListener(e -> togglePanelRegistro());
        panelToggle.add(btnMostrarRegistro);
        
        // Ensamblar todo
        JPanel panelCentral = new JPanel(new BorderLayout(5, 5));
        panelCentral.setOpaque(false);
        panelCentral.add(panelLogin, BorderLayout.NORTH);
        panelCentral.add(panelToggle, BorderLayout.CENTER);
        panelCentral.add(panelRegistro, BorderLayout.SOUTH);
        
        JPanel panelSur = new JPanel(new BorderLayout(0, 5));
        panelSur.setOpaque(false);
        panelSur.add(panelInfo, BorderLayout.NORTH);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);
        
        getContentPane().add(panelPrincipal);
    }
    
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(41, 128, 185));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        
        JPanel panelTextos = new JPanel(new GridLayout(4, 1, 0, 5));
        panelTextos.setOpaque(false);
        
        JLabel lblTitulo = new JLabel("Sistema de Gestion Contable y Tributaria", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        
        JLabel lblSubtitulo = new JLabel("Comercial el mejor vendedor S.A.", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(236, 240, 241));
        
        JLabel lblPropositoLinea1 = new JLabel("Control de Facturas de Venta y Gastos de Compra", SwingConstants.CENTER);
        lblPropositoLinea1.setFont(new Font("Arial", Font.ITALIC, 12));
        lblPropositoLinea1.setForeground(new Color(189, 195, 199));
        
        JLabel lblPropositoLinea2 = new JLabel("Calculo de Retencion de IVA (30%) - Bitacora de Auditoria", SwingConstants.CENTER);
        lblPropositoLinea2.setFont(new Font("Arial", Font.ITALIC, 11));
        lblPropositoLinea2.setForeground(new Color(189, 195, 199));
        
        panelTextos.add(lblTitulo);
        panelTextos.add(lblSubtitulo);
        panelTextos.add(lblPropositoLinea1);
        panelTextos.add(lblPropositoLinea2);
        
        panel.add(panelTextos, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(41, 128, 185), 2),
                "  INICIAR SESION - Credenciales de Acceso  ",
                0, 0, new Font("Arial", Font.BOLD, 13), new Color(41, 128, 185)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // USUARIO
        GridBagConstraints gbcUsuarioLabel = new GridBagConstraints();
        gbcUsuarioLabel.gridx = 0;
        gbcUsuarioLabel.gridy = 0;
        gbcUsuarioLabel.insets = new Insets(10, 10, 10, 10);
        gbcUsuarioLabel.anchor = GridBagConstraints.EAST;
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblUsuario, gbcUsuarioLabel);
        
        GridBagConstraints gbcUsuarioField = new GridBagConstraints();
        gbcUsuarioField.gridx = 1;
        gbcUsuarioField.gridy = 0;
        gbcUsuarioField.insets = new Insets(10, 10, 10, 10);
        gbcUsuarioField.anchor = GridBagConstraints.WEST;
        gbcUsuarioField.fill = GridBagConstraints.HORIZONTAL;
        txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsuario.setToolTipText("Ingrese su nombre de usuario");
        estilizarCampo(txtUsuario);
        panel.add(txtUsuario, gbcUsuarioField);
        
        // CONTRASEÑA
        GridBagConstraints gbcContrasenaLabel = new GridBagConstraints();
        gbcContrasenaLabel.gridx = 0;
        gbcContrasenaLabel.gridy = 1;
        gbcContrasenaLabel.insets = new Insets(10, 10, 10, 10);
        gbcContrasenaLabel.anchor = GridBagConstraints.EAST;
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblContrasena, gbcContrasenaLabel);
        
        GridBagConstraints gbcContrasenaField = new GridBagConstraints();
        gbcContrasenaField.gridx = 1;
        gbcContrasenaField.gridy = 1;
        gbcContrasenaField.insets = new Insets(10, 10, 10, 10);
        gbcContrasenaField.anchor = GridBagConstraints.WEST;
        gbcContrasenaField.fill = GridBagConstraints.HORIZONTAL;
        txtContrasena = new JPasswordField(20);
        txtContrasena.setFont(new Font("Arial", Font.PLAIN, 14));
        txtContrasena.setToolTipText("Ingrese su contrasena");
        estilizarCampo(txtContrasena);
        panel.add(txtContrasena, gbcContrasenaField);
        
        // BOTONES
        GridBagConstraints gbcBotones = new GridBagConstraints();
        gbcBotones.gridx = 0;
        gbcBotones.gridy = 2;
        gbcBotones.gridwidth = 2;
        gbcBotones.insets = new Insets(20, 10, 10, 10);
        gbcBotones.anchor = GridBagConstraints.CENTER;
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setOpaque(false);
        
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 13));
        btnIngresar.setBackground(new Color(39, 174, 96));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setPreferredSize(new Dimension(180, 35));

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancelar.setBackground(new Color(231, 76, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setPreferredSize(new Dimension(180, 35));
        
        btnIngresar.addActionListener(e -> intentarLogin());
        btnCancelar.addActionListener(e -> System.exit(0));
        
        txtUsuario.addActionListener(e -> txtContrasena.requestFocus());
        txtContrasena.addActionListener(e -> intentarLogin());
        
        panelBotones.add(btnIngresar);
        panelBotones.add(btnCancelar);
        panel.add(panelBotones, gbcBotones);
        
        return panel;
    }
    
    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new BorderLayout(10, 10)); // Cambié a BorderLayout
        panel.setBackground(new Color(255, 250, 240));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(243, 156, 18), 2),
                "  REGISTRAR NUEVO USUARIO  ",
                0, 0, new Font("Arial", Font.BOLD, 13), new Color(243, 156, 18)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Panel para los campos del formulario
        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCampos.setBackground(new Color(255, 250, 240));
        
        Font labelFont = new Font("Arial", Font.BOLD, 12);
        
        // Nombre Completo
        JLabel lblNombre = new JLabel("Nombre Completo:");
        lblNombre.setFont(labelFont);
        panelCampos.add(lblNombre);
        
        txtNombreCompleto = new JTextField();
        txtNombreCompleto.setFont(new Font("Arial", Font.PLAIN, 12));
        estilizarCampo(txtNombreCompleto);
        panelCampos.add(txtNombreCompleto);
        
        // Usuario
        JLabel lblNuevoUsuario = new JLabel("Nombre de Usuario:");
        lblNuevoUsuario.setFont(labelFont);
        panelCampos.add(lblNuevoUsuario);
        
        txtNuevoUsuario = new JTextField();
        txtNuevoUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        estilizarCampo(txtNuevoUsuario);
        panelCampos.add(txtNuevoUsuario);
        
        // Contraseña
        JLabel lblNuevaPass = new JLabel("Contrasena:");
        lblNuevaPass.setFont(labelFont);
        panelCampos.add(lblNuevaPass);
        
        txtNuevaContrasena = new JPasswordField();
        txtNuevaContrasena.setFont(new Font("Arial", Font.PLAIN, 12));
        estilizarCampo(txtNuevaContrasena);
        panelCampos.add(txtNuevaContrasena);
        
        // Confirmar Contraseña
        JLabel lblConfirmar = new JLabel("Confirmar Contrasena:");
        lblConfirmar.setFont(labelFont);
        panelCampos.add(lblConfirmar);
        
        txtConfirmarContrasena = new JPasswordField();
        txtConfirmarContrasena.setFont(new Font("Arial", Font.PLAIN, 12));
        estilizarCampo(txtConfirmarContrasena);
        panelCampos.add(txtConfirmarContrasena);
        
        // Rol
        JLabel lblRol = new JLabel("Rol de Usuario:");
        lblRol.setFont(labelFont);
        panelCampos.add(lblRol);
        
        cmbRol = new JComboBox<>(new String[]{
            TipoRol.ASISTENTE_CONTABLE,
            TipoRol.JEFATURA_FINANCIERA
        });
        cmbRol.setFont(new Font("Arial", Font.PLAIN, 12));
        panelCampos.add(cmbRol);
        
        // Panel para el botón (centrado)
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(new Color(255, 250, 240));
        
        btnRegistrar = new JButton("Registrar Usuario");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 13));
        btnRegistrar.setBackground(new Color(52, 152, 219));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setPreferredSize(new Dimension(180, 35));
        btnRegistrar.addActionListener(e -> registrarNuevoUsuario());
        panelBoton.add(btnRegistrar); // ✅ SOLO SE AÑADE UNA VEZ
        
        // Ensamblar todo
        panel.add(panelCampos, BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 5));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219)),
                "Informacion del Sistema"),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        JLabel lblQueHace = new JLabel("<html><b>QUE HACE:</b> Registra facturas de venta y gastos de compra, " +
                                       "calcula retencion de IVA (30% del IVA en compras), aprueba/rechaza transacciones segun roles, " +
                                       "genera respaldos anuales y mantiene bitacora de auditoria completa. " +
                                       "<b>Ahora con persistencia JSON de usuarios.</b></html>");
        lblQueHace.setFont(new Font("Arial", Font.PLAIN, 10));
        lblQueHace.setForeground(new Color(44, 62, 80));
        
        JLabel lblQueNoHace = new JLabel("<html><b>QUE NO HACE:</b> No gestiona inventarios, no calcula nomina, " +
                                         "no genera estados financieros completos (solo retencion de IVA), " +
                                         "no maneja bancos ni conciliaciones.</html>");
        lblQueNoHace.setFont(new Font("Arial", Font.PLAIN, 10));
        lblQueNoHace.setForeground(new Color(44, 62, 80));
        
        panel.add(lblQueHace);
        panel.add(lblQueNoHace);
        
        return panel;
    }
    
    private void estilizarCampo(JTextField campo) {
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)));
    }
    
    private void togglePanelRegistro() {
        mostrandoRegistro = !mostrandoRegistro;
        panelRegistro.setVisible(mostrandoRegistro);
        
        if (mostrandoRegistro) {
            btnMostrarRegistro.setText("Ocultar Formulario de Registro");
            btnMostrarRegistro.setBackground(new Color(231, 76, 60));
            setSize(750, 850);
        } else {
            btnMostrarRegistro.setText("Crear Nueva Cuenta de Usuario");
            btnMostrarRegistro.setBackground(new Color(52, 152, 219));
            setSize(750, 680);
        }
        
        setLocationRelativeTo(null);
    }
    
    private void intentarLogin() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "CAMPOS INCOMPLETOS\n\n" +
                "Complete los siguientes campos:\n" +
                "- Usuario\n" +
                "- Contrasena\n\n" +
                "INSTRUCCION: Ingrese su usuario y contrasena en los campos correspondientes.",
                "Error - Campos Vacios", JOptionPane.WARNING_MESSAGE);
            txtUsuario.requestFocus();
            return;
        }
        
        if (controladorUsuario.autenticar(usuario, contrasena)) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                InterfazPrincipal principal = new InterfazPrincipal();
                principal.setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, 
                "CREDENCIALES INCORRECTAS\n\n" +
                "El usuario o la contrasena que ingreso no son validos.\n\n" +
                "INSTRUCCIONES:\n" +
                "1. Verifique que el nombre de usuario este escrito correctamente\n" +
                "2. Verifique que la contrasena sea correcta (distingue mayusculas/minusculas)\n" +
                "3. Si no tiene cuenta, haga clic en 'Crear Nueva Cuenta de Usuario'\n" +
                "4. Si olvido su contrasena, contacte al administrador del sistema\n\n" +
                "Usuarios predefinidos:\n" +
                "- admin / admin123 (Jefatura Financiera)\n" +
                "- asistente / asistente123 (Asistente Contable)\n" +
                "- carlos / carlos123 (Asistente Contable)", 
                "Error de Autenticacion", JOptionPane.ERROR_MESSAGE);
            txtContrasena.setText("");
            txtUsuario.selectAll();
            txtUsuario.requestFocus();
        }
    }
    
    private void registrarNuevoUsuario() {
        String nombreCompleto = txtNombreCompleto.getText().trim();
        String usuario = txtNuevoUsuario.getText().trim();
        String contrasena = new String(txtNuevaContrasena.getPassword());
        String confirmar = new String(txtConfirmarContrasena.getPassword());
        String rol = (String) cmbRol.getSelectedItem();
        
        // Validaciones
        if (nombreCompleto.isEmpty() || usuario.isEmpty() || 
            contrasena.isEmpty() || confirmar.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "FORMULARIO INCOMPLETO\n\n" +
                "Todos los campos son obligatorios.\n\n" +
                "INSTRUCCION: Complete los siguientes campos:\n" +
                "- Nombre Completo\n" +
                "- Nombre de Usuario\n" +
                "- Contrasena\n" +
                "- Confirmar Contrasena\n" +
                "- Rol de Usuario",
                "Error - Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (usuario.contains(" ")) {
            JOptionPane.showMessageDialog(this,
                "NOMBRE DE USUARIO INVALIDO\n\n" +
                "El nombre de usuario NO puede contener espacios.\n\n" +
                "INSTRUCCION:\n" +
                "Ingrese un nombre de usuario sin espacios.\n" +
                "Ejemplos validos: jperez, maria.lopez, carlos123",
                "Error - Usuario Invalido", JOptionPane.WARNING_MESSAGE);
            txtNuevoUsuario.requestFocus();
            txtNuevoUsuario.selectAll();
            return;
        }
        
        if (contrasena.length() < Constantes.LONGITUD_MINIMA_CONTRASENA) {
            JOptionPane.showMessageDialog(this,
                "CONTRASENA DEBIL\n\n" +
                "La contrasena debe tener al menos " + Constantes.LONGITUD_MINIMA_CONTRASENA + " caracteres.\n\n" +
                "INSTRUCCION:\n" +
                "Ingrese una contrasena mas larga para mayor seguridad.\n" +
                "Recomendacion: Use una combinacion de letras, numeros y simbolos.",
                "Error - Contrasena Insegura", JOptionPane.WARNING_MESSAGE);
            txtNuevaContrasena.requestFocus();
            txtNuevaContrasena.selectAll();
            return;
        }
        
        if (!contrasena.equals(confirmar)) {
            JOptionPane.showMessageDialog(this,
                "LAS CONTRASENAS NO COINCIDEN\n\n" +
                "La contrasena y la confirmacion deben ser identicas.\n\n" +
                "INSTRUCCION:\n" +
                "1. Verifique que ambas contrasenas sean exactamente iguales\n" +
                "2. Vuelva a ingresar la contrasena en ambos campos\n" +
                "3. Asegurese de que no haya espacios adicionales",
                "Error - Confirmacion Incorrecta", JOptionPane.WARNING_MESSAGE);
            txtConfirmarContrasena.setText("");
            txtNuevaContrasena.requestFocus();
            return;
        }
        
        // Intentar agregar usuario
        if (controladorUsuario.agregarUsuario(usuario, contrasena, nombreCompleto, rol)) {
            JOptionPane.showMessageDialog(this,
                "USUARIO REGISTRADO EXITOSAMENTE\n\n" +
                "Se ha creado la siguiente cuenta:\n\n" +
                "Usuario: " + usuario + "\n" +
                "Nombre: " + nombreCompleto + "\n" +
                "Rol: " + rol + "\n\n" +
                "Los datos se guardaron en usuarios.json\n\n" +
                "PROXIMO PASO:\n" +
                "Ya puede iniciar sesion con este usuario y contrasena.",
                "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar formulario de registro
            txtNombreCompleto.setText("");
            txtNuevoUsuario.setText("");
            txtNuevaContrasena.setText("");
            txtConfirmarContrasena.setText("");
            cmbRol.setSelectedIndex(0);
            
            // Ocultar panel de registro
            togglePanelRegistro();
            
        } else {
            JOptionPane.showMessageDialog(this,
                "USUARIO YA EXISTE\n\n" +
                "El nombre de usuario '" + usuario + "' ya esta registrado en el sistema.\n\n" +
                "INSTRUCCIONES:\n" +
                "1. Elija un nombre de usuario diferente\n" +
                "2. Intente agregar numeros o caracteres adicionales\n" +
                "   Ejemplos: " + usuario + "1, " + usuario + "2025, " + usuario + "_user\n" +
                "3. Si este usuario es suyo, inicie sesion normalmente\n" +
                "4. Si olvido su contrasena, contacte al administrador",
                "Error - Usuario Duplicado", JOptionPane.ERROR_MESSAGE);
            txtNuevoUsuario.requestFocus();
            txtNuevoUsuario.selectAll();
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            InterfazLoginMejorada login = new InterfazLoginMejorada();
            login.setVisible(true);
        });
    }
}
