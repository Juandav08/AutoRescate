package autorescate.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import autorescate.controller.AutoRescateController;
import autorescate.model.entidades.Cliente;
import autorescate.model.entidades.KitAtencion;
import autorescate.model.entidades.SolicitudServicio;
import autorescate.model.entidades.Tecnico;
import autorescate.model.entidades.UnidadServicio;
import autorescate.model.funciones.ArregloDinamico;
import autorescate.model.programa.EstadoUnidad;
import autorescate.model.programa.TipoCliente;
import autorescate.model.programa.TipoIncidente;
import autorescate.model.programa.TipoServicio;
import autorescate.model.programa.TipoUnidad;

/**
 * Vista principal de AutoRescate 24/7 organizada por flujos de trabajo.
 */
public class AutoRescateFrame extends JFrame {

    private AutoRescateController controller;
    private JLabel resultadoTitulo;
    private JTextArea salida;

    private JComboBox<String> selectorRegistro;
    private CardLayout registroCards;
    private JPanel registroPanelCards;

    private JTextField clienteNombre;
    private JTextField clienteTelefono;
    private JComboBox<TipoCliente> clienteTipo;
    private JTextField tecnicoNombre;
    private JTextField tecnicoEspecialidad;
    private JTextField tecnicoZona;
    private JComboBox<TipoUnidad> unidadTipo;
    private JTextField unidadZona;
    private JTextField unidadPlaca;

    private JTextField busquedaClienteSolicitud;
    private JComboBox<Cliente> clientesEncontrados;
    private JComboBox<TipoIncidente> solicitudIncidente;
    private JTextField solicitudOtroIncidente;
    private JComboBox<TipoServicio> solicitudTipo;
    private JTextField solicitudDetalle;
    private JTextField solicitudUbicacion;
    private JTextField solicitudPrioridad;

    private JComboBox<SolicitudServicio> solicitudesPendientes;
    private JComboBox<Tecnico> tecnicosDisponibles;
    private JComboBox<UnidadServicio> unidadesDisponibles;

    private JComboBox<UnidadServicio> unidadesEstado;
    private JComboBox<EstadoUnidad> nuevoEstadoUnidad;
    private JComboBox<SolicitudServicio> solicitudesEnEjecucion;

    private JTextField kitDescripcion;
    private JCheckBox kitReposicion;
    private JComboBox<KitAtencion> kitsRevision;
    private JTextField movimientoDetalle;

    /**
     * Construye la ventana principal.
     *
     * @param controller Controlador MVC.
     */
    public AutoRescateFrame(AutoRescateController controller) {
        super("AutoRescate 24/7");
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1220, 760);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
        setResultado("Inicio", "Sistema listo.\n\nRegistra primero clientes, tecnicos y unidades.");
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(32, 43, 56));
        panel.setBorder(new EmptyBorder(14, 18, 14, 18));

        JLabel titulo = new JLabel("AutoRescate 24/7");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        JLabel subtitulo = new JLabel("Gestion visual de clientes, solicitudes, recursos, kits y movimientos");
        subtitulo.setForeground(new Color(218, 229, 241));

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);

        JButton resumen = botonClaro("Resumen general");
        resumen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { mostrarResumen(); }
        });

        panel.add(textos, BorderLayout.WEST);
        panel.add(resumen, BorderLayout.EAST);
        return panel;
    }

    private JSplitPane crearCuerpo() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Registrar", crearTabRegistrar());
        tabs.addTab("Nueva solicitud", crearTabNuevaSolicitud());
        tabs.addTab("Asignar recursos", crearTabAsignar());
        tabs.addTab("Unidades", crearTabUnidades());
        tabs.addTab("Cerrar servicios", crearTabCierres());
        tabs.addTab("Kits", crearTabKits());
        tabs.addTab("Movimientos", crearTabMovimientos());
        tabs.addTab("Consultas", crearTabConsultas());

        JPanel resultados = new JPanel(new BorderLayout());
        resultados.setBorder(new EmptyBorder(12, 12, 12, 12));
        resultadoTitulo = new JLabel("Resultado");
        resultadoTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        resultadoTitulo.setBorder(new EmptyBorder(0, 0, 8, 0));
        salida = new JTextArea();
        salida.setEditable(false);
        salida.setFont(new Font("Consolas", Font.PLAIN, 13));
        salida.setLineWrap(true);
        salida.setWrapStyleWord(true);
        salida.setBorder(new EmptyBorder(10, 10, 10, 10));
        resultados.add(resultadoTitulo, BorderLayout.NORTH);
        resultados.add(new JScrollPane(salida), BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabs, resultados);
        split.setDividerLocation(650);
        split.setResizeWeight(0.55);
        return split;
    }

    private JPanel crearTabRegistrar() {
        JPanel panel = panelBase();
        panel.add(titulo("Registrar datos maestros"), gbc(0, 0, 1));
        JPanel selector = card("Que quieres registrar?");
        selectorRegistro = new JComboBox<String>();
        selectorRegistro.addItem("Cliente");
        selectorRegistro.addItem("Tecnico");
        selectorRegistro.addItem("Unidad");
        selectorRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registroCards.show(registroPanelCards, (String) selectorRegistro.getSelectedItem());
            }
        });
        agregarCampo(selector, 0, "Tipo de registro", selectorRegistro);
        panel.add(selector, gbc(0, 1, 1));

        registroCards = new CardLayout();
        registroPanelCards = new JPanel(registroCards);
        registroPanelCards.add(cardCliente(), "Cliente");
        registroPanelCards.add(cardTecnico(), "Tecnico");
        registroPanelCards.add(cardUnidad(), "Unidad");
        panel.add(registroPanelCards, gbc(0, 2, 1));
        return panel;
    }

    private JPanel cardCliente() {
        JPanel card = card("Nuevo cliente");
        clienteNombre = new JTextField(22);
        clienteTelefono = new JTextField(16);
        clienteTipo = new JComboBox<TipoCliente>(TipoCliente.values());
        agregarCampo(card, 0, "Nombre", clienteNombre);
        agregarCampo(card, 1, "Telefono", clienteTelefono);
        agregarCampo(card, 2, "Tipo", clienteTipo);
        JButton registrar = botonPrincipal("Registrar cliente");
        registrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { registrarCliente(); }
        });
        card.add(registrar, gbcBoton(3));
        return card;
    }

    private JPanel cardTecnico() {
        JPanel card = card("Nuevo tecnico");
        tecnicoNombre = new JTextField(22);
        tecnicoEspecialidad = new JTextField(18);
        tecnicoZona = new JTextField(16);
        agregarCampo(card, 0, "Nombre", tecnicoNombre);
        agregarCampo(card, 1, "Especialidad", tecnicoEspecialidad);
        agregarCampo(card, 2, "Zona", tecnicoZona);
        JButton registrar = botonPrincipal("Registrar tecnico");
        registrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { registrarTecnico(); }
        });
        card.add(registrar, gbcBoton(3));
        return card;
    }

    private JPanel cardUnidad() {
        JPanel card = card("Nueva unidad");
        unidadTipo = new JComboBox<TipoUnidad>(TipoUnidad.values());
        unidadZona = new JTextField(16);
        unidadPlaca = new JTextField(16);
        agregarCampo(card, 0, "Tipo", unidadTipo);
        agregarCampo(card, 1, "Zona", unidadZona);
        agregarCampo(card, 2, "Placa/codigo", unidadPlaca);
        JButton registrar = botonPrincipal("Registrar unidad");
        registrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { registrarUnidad(); }
        });
        card.add(registrar, gbcBoton(3));
        return card;
    }

    private JPanel crearTabNuevaSolicitud() {
        JPanel panel = panelBase();
        panel.add(titulo("Nueva solicitud"), gbc(0, 0, 1));
        JPanel card = card("Buscar cliente y describir incidente");

        busquedaClienteSolicitud = new JTextField(24);
        clientesEncontrados = new JComboBox<Cliente>();
        busquedaClienteSolicitud.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { actualizarClientesEncontrados(); }
            public void removeUpdate(DocumentEvent e) { actualizarClientesEncontrados(); }
            public void changedUpdate(DocumentEvent e) { actualizarClientesEncontrados(); }
        });
        solicitudIncidente = new JComboBox<TipoIncidente>(TipoIncidente.values());
        solicitudOtroIncidente = new JTextField(22);
        solicitudTipo = new JComboBox<TipoServicio>(TipoServicio.values());
        solicitudDetalle = new JTextField(24);
        solicitudUbicacion = new JTextField(22);
        solicitudPrioridad = new JTextField(5);
        solicitudPrioridad.setText(String.valueOf(((TipoIncidente) solicitudIncidente.getSelectedItem()).getPrioridadSugerida()));
        solicitudIncidente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { actualizarPrioridadSugerida(); }
        });

        agregarCampo(card, 0, "Buscar por nombre, telefono o ID", busquedaClienteSolicitud);
        agregarCampo(card, 1, "Cliente encontrado", clientesEncontrados);
        agregarCampo(card, 2, "Incidente", solicitudIncidente);
        agregarCampo(card, 3, "Incidente personalizado opcional", solicitudOtroIncidente);
        agregarCampo(card, 4, "Recurso requerido", solicitudTipo);
        agregarCampo(card, 5, "Ubicacion", solicitudUbicacion);
        agregarCampo(card, 6, "Detalle adicional", solicitudDetalle);
        agregarCampo(card, 7, "Prioridad 1-5", solicitudPrioridad);
        JButton crear = botonPrincipal("Crear solicitud");
        crear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { crearSolicitud(); }
        });
        card.add(crear, gbcBoton(8));
        panel.add(card, gbc(0, 1, 1));
        actualizarClientesEncontrados();
        return panel;
    }

    private JPanel crearTabAsignar() {
        JPanel panel = panelBase();
        panel.add(titulo("Asignar recursos disponibles"), gbc(0, 0, 1));
        JPanel card = card("Seleccionar solicitud, tecnico y unidad");
        solicitudesPendientes = new JComboBox<SolicitudServicio>();
        tecnicosDisponibles = new JComboBox<Tecnico>();
        unidadesDisponibles = new JComboBox<UnidadServicio>();
        solicitudesPendientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { actualizarUnidadesCompatibles(); }
        });
        JButton refrescar = botonClaro("Actualizar disponibles");
        refrescar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { actualizarCombosAsignacion(); }
        });
        JButton asignar = botonPrincipal("Asignar recursos");
        asignar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { asignarRecursos(); }
        });
        agregarCampo(card, 0, "Solicitud pendiente", solicitudesPendientes);
        agregarCampo(card, 1, "Tecnico disponible", tecnicosDisponibles);
        agregarCampo(card, 2, "Unidad disponible", unidadesDisponibles);
        card.add(refrescar, gbcBoton(3));
        card.add(asignar, gbcBoton(4));
        panel.add(card, gbc(0, 1, 1));
        actualizarCombosAsignacion();
        return panel;
    }

    private JPanel crearTabUnidades() {
        JPanel panel = panelBase();
        panel.add(titulo("Estado de unidades"), gbc(0, 0, 1));
        JPanel card = card("Cambiar estado operativo");
        unidadesEstado = new JComboBox<UnidadServicio>();
        nuevoEstadoUnidad = new JComboBox<EstadoUnidad>();
        nuevoEstadoUnidad.addItem(EstadoUnidad.DISPONIBLE);
        nuevoEstadoUnidad.addItem(EstadoUnidad.MANTENIMIENTO);
        nuevoEstadoUnidad.addItem(EstadoUnidad.FUERA_DE_SERVICIO);
        JButton refrescar = botonClaro("Actualizar unidades");
        refrescar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { actualizarCombosOperacion(); }
        });
        JButton cambiar = botonPrincipal("Aplicar estado");
        cambiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { cambiarEstadoUnidad(); }
        });
        agregarCampo(card, 0, "Unidad", unidadesEstado);
        agregarCampo(card, 1, "Nuevo estado", nuevoEstadoUnidad);
        card.add(refrescar, gbcBoton(2));
        card.add(cambiar, gbcBoton(3));
        panel.add(card, gbc(0, 1, 1));
        actualizarCombosOperacion();
        return panel;
    }

    private JPanel crearTabCierres() {
        JPanel panel = panelBase();
        panel.add(titulo("Cerrar servicios"), gbc(0, 0, 1));
        JPanel card = card("Servicios en ejecucion");
        solicitudesEnEjecucion = new JComboBox<SolicitudServicio>();
        JButton refrescar = botonClaro("Actualizar servicios");
        refrescar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { actualizarCombosOperacion(); }
        });
        JButton cerrar = botonPrincipal("Cerrar servicio");
        cerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { cerrarServicio(); }
        });
        agregarCampo(card, 0, "Solicitud en ejecucion", solicitudesEnEjecucion);
        card.add(refrescar, gbcBoton(1));
        card.add(cerrar, gbcBoton(2));
        panel.add(card, gbc(0, 1, 1));
        actualizarCombosOperacion();
        return panel;
    }

    private JPanel crearTabKits() {
        JPanel panel = panelBase();
        panel.add(titulo("Kits en revision"), gbc(0, 0, 1));
        JPanel card = card("Revision LIFO");
        kitDescripcion = new JTextField(24);
        kitReposicion = new JCheckBox("Requiere reposicion o reparacion");
        kitsRevision = new JComboBox<KitAtencion>();
        agregarCampo(card, 0, "Descripcion", kitDescripcion);
        GridBagConstraints ck = gbcCampo(1);
        ck.gridx = 0;
        ck.gridwidth = 2;
        card.add(kitReposicion, ck);
        JButton registrar = botonPrincipal("Registrar kit");
        registrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { registrarKit(); }
        });
        agregarCampo(card, 2, "Kit a reparar/reponer", kitsRevision);
        JButton reparar = botonClaro("Marcar seleccionado como reparado/repuesto");
        reparar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { repararKit(); }
        });
        JButton retirar = botonPrincipal("Retirar ultimo kit apto");
        retirar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { retirarKit(); }
        });
        card.add(registrar, gbcBoton(3));
        card.add(reparar, gbcBoton(4));
        card.add(retirar, gbcBoton(5));
        panel.add(card, gbc(0, 1, 1));
        actualizarKitsRevision();
        return panel;
    }

    private JPanel crearTabMovimientos() {
        JPanel panel = panelBase();
        panel.add(titulo("Movimientos operativos"), gbc(0, 0, 1));
        JPanel card = card("Bitacora y acciones");
        movimientoDetalle = new JTextField(28);
        agregarCampo(card, 0, "Nota operativa (no cambia datos)", movimientoDetalle);
        JPanel acciones = new JPanel(new GridLayout(1, 3, 8, 8));
        JButton registrar = botonPrincipal("Guardar nota");
        registrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { registrarMovimiento(); }
        });
        JButton revertir = botonClaro("Deshacer ultima accion reversible");
        revertir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { revertir(); }
        });
        JButton csv = botonClaro("Exportar CSV");
        csv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { exportarCsv(); }
        });
        acciones.add(registrar);
        acciones.add(revertir);
        acciones.add(csv);
        GridBagConstraints ga = gbcBoton(1);
        card.add(acciones, ga);
        panel.add(card, gbc(0, 1, 1));
        return panel;
    }

    private JPanel crearTabConsultas() {
        JPanel panel = panelBase();
        panel.add(titulo("Consultas"), gbc(0, 0, 1));
        JPanel card = card("Elige que quieres ver");
        agregarBotonConsulta(card, 0, "Clientes", new ActionListener() {
            public void actionPerformed(ActionEvent e) { setResultado("Clientes registrados", controller.listarClientes()); }
        });
        agregarBotonConsulta(card, 1, "Tecnicos", new ActionListener() {
            public void actionPerformed(ActionEvent e) { setResultado("Tecnicos registrados", controller.listarTecnicos()); }
        });
        agregarBotonConsulta(card, 2, "Unidades", new ActionListener() {
            public void actionPerformed(ActionEvent e) { setResultado("Unidades registradas", controller.listarUnidades()); }
        });
        agregarBotonConsulta(card, 3, "Tecnicos disponibles", new ActionListener() {
            public void actionPerformed(ActionEvent e) { setResultado("Tecnicos disponibles", controller.listarTecnicosDisponibles()); }
        });
        agregarBotonConsulta(card, 4, "Unidades disponibles", new ActionListener() {
            public void actionPerformed(ActionEvent e) { setResultado("Unidades disponibles", controller.listarUnidadesDisponibles()); }
        });
        agregarBotonConsulta(card, 5, "Solicitudes pendientes ordinarias", new ActionListener() {
            public void actionPerformed(ActionEvent e) { setResultado("Pendientes ordinarias", controller.listarPendientesOrdinarios()); }
        });
        agregarBotonConsulta(card, 6, "Solicitudes criticas", new ActionListener() {
            public void actionPerformed(ActionEvent e) { setResultado("Pendientes criticas", controller.listarCriticos()); }
        });
        agregarBotonConsulta(card, 7, "Todas las solicitudes", new ActionListener() {
            public void actionPerformed(ActionEvent e) { setResultado("Historico de solicitudes", controller.listarSolicitudes()); }
        });
        agregarBotonConsulta(card, 8, "Recursos por estado", new ActionListener() {
            public void actionPerformed(ActionEvent e) { setResultado("Recursos por estado", controller.listarRecursosPorEstado()); }
        });
        agregarBotonConsulta(card, 9, "Kits en revision", new ActionListener() {
            public void actionPerformed(ActionEvent e) { setResultado("Kits en revision", controller.listarKitsRevision()); }
        });
        agregarBotonConsulta(card, 10, "Movimientos recientes", new ActionListener() {
            public void actionPerformed(ActionEvent e) { setResultado("Movimientos recientes", controller.listarMovimientos()); }
        });
        panel.add(card, gbc(0, 1, 1));
        return panel;
    }

    private void registrarCliente() {
        try {
            Cliente cliente = controller.registrarCliente(clienteNombre.getText(), clienteTelefono.getText(),
                    (TipoCliente) clienteTipo.getSelectedItem());
            limpiar(clienteNombre, clienteTelefono);
            actualizarClientesEncontrados();
            setResultado("Cliente registrado", "Se registro:\n" + formatoCliente(cliente));
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void registrarTecnico() {
        try {
            Tecnico tecnico = controller.registrarTecnico(tecnicoNombre.getText(), tecnicoEspecialidad.getText(),
                    tecnicoZona.getText());
            limpiar(tecnicoNombre, tecnicoEspecialidad, tecnicoZona);
            actualizarCombosAsignacion();
            setResultado("Tecnico registrado", tecnico.toString());
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void registrarUnidad() {
        try {
            UnidadServicio unidad = controller.registrarUnidad((TipoUnidad) unidadTipo.getSelectedItem(),
                    unidadZona.getText(), unidadPlaca.getText());
            limpiar(unidadZona, unidadPlaca);
            actualizarCombosAsignacion();
            actualizarCombosOperacion();
            setResultado("Unidad registrada", formatoUnidad(unidad));
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void crearSolicitud() {
        try {
            Cliente cliente = (Cliente) clientesEncontrados.getSelectedItem();
            if (cliente == null) {
                throw new IllegalArgumentException("Busca y selecciona un cliente.");
            }
            int prioridad = Integer.parseInt(solicitudPrioridad.getText().trim());
            TipoIncidente incidente = (TipoIncidente) solicitudIncidente.getSelectedItem();
            String textoIncidente = incidente.getDescripcion();
            if (incidente == TipoIncidente.OTRO && solicitudOtroIncidente.getText().trim().length() > 0) {
                textoIncidente = solicitudOtroIncidente.getText().trim();
            }
            String descripcion = textoIncidente;
            if (incidente != TipoIncidente.OTRO && solicitudOtroIncidente.getText().trim().length() > 0) {
                descripcion += " | Incidente relacionado: " + solicitudOtroIncidente.getText().trim();
            }
            if (solicitudDetalle.getText().trim().length() > 0) {
                descripcion += " | " + solicitudDetalle.getText().trim();
            }
            SolicitudServicio solicitud = controller.crearSolicitud(cliente,
                    (TipoServicio) solicitudTipo.getSelectedItem(), descripcion,
                    solicitudUbicacion.getText(), prioridad);
            limpiar(busquedaClienteSolicitud, solicitudOtroIncidente, solicitudDetalle, solicitudUbicacion);
            actualizarClientesEncontrados();
            actualizarCombosAsignacion();
            setResultado("Solicitud creada", formatoSolicitud(solicitud)
                    + "\n\nAhora ve a la pestana 'Asignar recursos' para escoger tecnico y unidad disponibles.");
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void asignarRecursos() {
        try {
            SolicitudServicio solicitud = (SolicitudServicio) solicitudesPendientes.getSelectedItem();
            Tecnico tecnico = (Tecnico) tecnicosDisponibles.getSelectedItem();
            UnidadServicio unidad = (UnidadServicio) unidadesDisponibles.getSelectedItem();
            if (solicitud == null || tecnico == null || unidad == null) {
                throw new IllegalArgumentException("Debes seleccionar solicitud, tecnico y unidad disponible.");
            }
            controller.asignarRecursos(solicitud, tecnico, unidad);
            actualizarCombosAsignacion();
            actualizarCombosOperacion();
            setResultado("Recursos asignados", formatoSolicitud(solicitud)
                    + "\n\nTecnico: " + tecnico.getId() + " - " + tecnico.getNombre()
                    + "\nUnidad: " + formatoUnidad(unidad));
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void cambiarEstadoUnidad() {
        try {
            UnidadServicio unidad = (UnidadServicio) unidadesEstado.getSelectedItem();
            if (unidad == null) {
                throw new IllegalArgumentException("No hay unidad seleccionada.");
            }
            controller.cambiarEstadoUnidad(unidad, (EstadoUnidad) nuevoEstadoUnidad.getSelectedItem());
            actualizarCombosAsignacion();
            actualizarCombosOperacion();
            setResultado("Estado de unidad actualizado", formatoUnidad(unidad));
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void cerrarServicio() {
        try {
            SolicitudServicio solicitud = (SolicitudServicio) solicitudesEnEjecucion.getSelectedItem();
            if (solicitud == null) {
                throw new IllegalArgumentException("No hay solicitud en ejecucion seleccionada.");
            }
            controller.cerrarServicio(solicitud);
            actualizarCombosAsignacion();
            actualizarCombosOperacion();
            setResultado("Servicio cerrado", formatoSolicitud(solicitud));
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void registrarKit() {
        try {
            controller.registrarKitEnRevision(kitDescripcion.getText(), kitReposicion.isSelected());
            limpiar(kitDescripcion);
            kitReposicion.setSelected(false);
            actualizarKitsRevision();
            setResultado("Kit registrado en revision", controller.listarKitsRevision());
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void repararKit() {
        try {
            KitAtencion kit = (KitAtencion) kitsRevision.getSelectedItem();
            if (kit == null) {
                throw new IllegalArgumentException("Selecciona el kit que quieres reparar o reponer.");
            }
            controller.repararKitEnRevision(kit);
            actualizarKitsRevision();
            setResultado("Kit reparado o repuesto", controller.listarKitsRevision());
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void retirarKit() {
        try {
            controller.retirarUltimoKitRevisado();
            actualizarKitsRevision();
            setResultado("Kit retirado", controller.listarKitsRevision());
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void registrarMovimiento() {
        try {
            controller.registrarMovimiento(movimientoDetalle.getText());
            limpiar(movimientoDetalle);
            setResultado("Nota operativa registrada",
                    "\n\n"
                    + controller.listarMovimientos());
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void revertir() {
        try {
            String mensaje = controller.revertirUltimaOperacion();
            actualizarCombosAsignacion();
            actualizarCombosOperacion();
            setResultado("Accion reversible deshecha",
                    mensaje + "\n\nDeshacer aplica sobre asignaciones, cierres y cambios de estado de unidad.\n"
                    + "Las notas manuales solo se retiran de la bitacora.\n\nMovimientos:\n"
                    + controller.listarMovimientos());
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void exportarCsv() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new java.io.File("casos_atendidos.csv"));
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                int cantidad = controller.exportarCsv(chooser.getSelectedFile().getAbsolutePath());
                setResultado("CSV exportado", "Casos atendidos exportados: " + cantidad
                        + "\nArchivo: " + chooser.getSelectedFile().getAbsolutePath());
            }
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void actualizarClientesEncontrados() {
        if (clientesEncontrados == null || busquedaClienteSolicitud == null) {
            return;
        }
        llenarCombo(clientesEncontrados, controller.buscarClientes(busquedaClienteSolicitud.getText()));
    }

    private void actualizarCombosAsignacion() {
        if (solicitudesPendientes != null) {
            llenarCombo(solicitudesPendientes, controller.obtenerSolicitudesPendientes());
        }
        if (tecnicosDisponibles != null) {
            llenarCombo(tecnicosDisponibles, controller.obtenerTecnicosDisponibles());
        }
        actualizarUnidadesCompatibles();
    }

    private void actualizarUnidadesCompatibles() {
        if (unidadesDisponibles != null) {
            SolicitudServicio solicitud = null;
            if (solicitudesPendientes != null) {
                solicitud = (SolicitudServicio) solicitudesPendientes.getSelectedItem();
            }
            llenarCombo(unidadesDisponibles, controller.obtenerUnidadesDisponiblesPara(solicitud));
        }
    }

    private void actualizarCombosOperacion() {
        if (unidadesEstado != null) {
            llenarCombo(unidadesEstado, controller.getSistema().getUnidades());
        }
        if (solicitudesEnEjecucion != null) {
            llenarCombo(solicitudesEnEjecucion, controller.obtenerSolicitudesEnEjecucion());
        }
    }

    private void actualizarKitsRevision() {
        if (kitsRevision != null) {
            llenarCombo(kitsRevision, controller.obtenerKitsPendientesDeReparacion());
        }
    }

    private <T> void llenarCombo(JComboBox<T> combo, ArregloDinamico<T> datos) {
        Object seleccionado = combo.getSelectedItem();
        combo.removeAllItems();
        boolean restaurado = false;
        for (int i = 0; i < datos.tamano(); i++) {
            T item = datos.obtener(i);
            combo.addItem(item);
            if (item == seleccionado) {
                restaurado = true;
            }
        }
        if (restaurado) {
            combo.setSelectedItem(seleccionado);
        }
    }

    private void actualizarPrioridadSugerida() {
        TipoIncidente incidente = (TipoIncidente) solicitudIncidente.getSelectedItem();
        solicitudPrioridad.setText(String.valueOf(incidente.getPrioridadSugerida()));
    }

    private void mostrarResumen() {
        setResultado("Resumen general", "CLIENTES\n" + controller.listarClientes()
                + "\n\nTECNICOS\n" + controller.listarTecnicos()
                + "\n\nUNIDADES\n" + controller.listarUnidades()
                + "\n\nSOLICITUDES\n" + controller.listarSolicitudes());
    }

    private void setResultado(String titulo, String texto) {
        resultadoTitulo.setText(titulo);
        salida.setText(texto == null || texto.length() == 0 ? "Sin datos para mostrar." : texto);
        salida.setCaretPosition(0);
    }

    private String formatoCliente(Cliente cliente) {
        return cliente.getId() + " | " + cliente.getNombre() + " | Tel: "
                + cliente.getTelefono() + " | " + cliente.getTipo();
    }

    private String formatoUnidad(UnidadServicio unidad) {
        return unidad.getPlaca() + " | " + unidad.getTipo() + " | " + unidad.getEstado()
                + " | Zona: " + unidad.getZona() + "\nUUID: " + unidad.getUuid();
    }

    private String formatoSolicitud(SolicitudServicio solicitud) {
        return solicitud.getId() + " | " + solicitud.getTipoServicio() + " | "
                + solicitud.getEstado() + " | Prioridad: " + solicitud.getPrioridad()
                + "\nCliente: " + solicitud.getCliente().getNombre()
                + "\nUbicacion: " + solicitud.getUbicacion()
                + "\nCaso: " + solicitud.getDescripcion();
    }

    private JPanel panelBase() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(new EmptyBorder(14, 14, 14, 14));
        return panel;
    }

    private JPanel card(String titulo) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(202, 211, 222)),
                        titulo, TitledBorder.LEFT, TitledBorder.TOP),
                new EmptyBorder(10, 10, 10, 10)));
        return panel;
    }

    private JLabel titulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.LEFT);
        label.setFont(new Font("SansSerif", Font.BOLD, 17));
        return label;
    }

    private void agregarCampo(JPanel panel, int fila, String etiqueta, Component campo) {
        GridBagConstraints l = gbcCampo(fila);
        l.gridx = 0;
        l.weightx = 0;
        panel.add(new JLabel(etiqueta), l);
        GridBagConstraints c = gbcCampo(fila);
        c.gridx = 1;
        c.weightx = 1;
        panel.add(campo, c);
    }

    private void agregarBotonConsulta(JPanel panel, int fila, String texto, ActionListener listener) {
        JButton boton = botonClaro(texto);
        boton.addActionListener(listener);
        GridBagConstraints c = gbcCampo(fila);
        c.gridx = fila % 2;
        c.gridy = fila / 2;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(boton, c);
    }

    private GridBagConstraints gbc(int x, int y, int ancho) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = ancho;
        c.insets = new Insets(7, 7, 7, 7);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        return c;
    }

    private GridBagConstraints gbcCampo(int fila) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = fila;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

    private GridBagConstraints gbcBoton(int fila) {
        GridBagConstraints c = gbcCampo(fila);
        c.gridx = 0;
        c.gridwidth = 2;
        return c;
    }

    private JButton botonPrincipal(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(26, 115, 232));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        return boton;
    }

    private JButton botonClaro(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        return boton;
    }

    private void limpiar(JTextField a) {
        a.setText("");
    }

    private void limpiar(JTextField a, JTextField b) {
        a.setText("");
        b.setText("");
    }

    private void limpiar(JTextField a, JTextField b, JTextField c) {
        a.setText("");
        b.setText("");
        c.setText("");
    }

    private void limpiar(JTextField a, JTextField b, JTextField c, JTextField d) {
        a.setText("");
        b.setText("");
        c.setText("");
        d.setText("");
    }

    private void mostrarError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
