package principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utils.Metodos;

@SuppressWarnings("all")

public class Agenda extends JFrame {

	public static JButton btnNewButton = new JButton("");

	private static JLabel iconoSeguro = new JLabel("");

	private static JLabel lblNewLabel_2 = new JLabel("");

	public static JLabel nuevosVencimientos = new JLabel("");

	private JButton observacion = new JButton("Obs");
	private int indice;
	private static JTextPane vtos = new JTextPane();

	private ArrayList<Objeto> arrayList1 = new ArrayList<Objeto>();
	private static int seguro = 0;
	private int paso = 0;
	private JButton buscar;
	private static JButton contacto;
	private JButton editar;

	public static JTextField getNombre() {
		return nombre;
	}

	public static JList<String> jList1;
	private static String directorioActual, separador, os;
	private static JTextField nombre;
	public static DefaultListModel<String> modelo = new DefaultListModel<>();
	private String iduser;
	private transient ResultSet rs;
	private transient Statement s;
	private String cnombre;
	private String ctipo;
	private String cnota;
	public static JTextPane tlf = new JTextPane();
	private static JTextPane direccion = new JTextPane();
	public static LinkedList<String> vencimientosDecesos = new <String>LinkedList();

	public static JTextPane getDireccion() {
		return direccion;
	}

	public static JTextField getEmail() {
		return email;
	}

	public static LinkedList<String> fechaDecesos = new <String>LinkedList();

	private static LinkedList<String> emails = new <String>LinkedList();

	public static LinkedList<String> fechaVida = new <String>LinkedList();

	public static LinkedList<String> fechaHogar = new <String>LinkedList();

	public static LinkedList<String> fechaComercio = new <String>LinkedList();

	public static LinkedList<String> fechaComunidad = new <String>LinkedList();

	public static LinkedList<String> fechaCoche = new <String>LinkedList();

	public static LinkedList<String> observaciones = new <String>LinkedList();

	public static LinkedList<String> contactos = new <String>LinkedList();

	public static void vaciarCampos() {

		Agenda.nombre.setText("");
		Agenda.tlf.setText("");
		Agenda.vtos.setText("");
		Agenda.direccion.setText("");
		Agenda.email.setText("");
	}

	private void actualizarArchivo(String archivo, LinkedList<String> lista) throws IOException, FileNotFoundException {
		Metodos.eliminarFichero(archivo);

		ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream(archivo));

		escribiendoFichero.writeObject(lista);

		escribiendoFichero.close();
	}

	private static void verTodasLasLlamadas() {

		iconoSeguro.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/name.png")));

		lblNewLabel_2.setIcon(null);

		btnNewButton.setEnabled(false);

		seguro = 0;

		verNotas();

		Vencimiento.verTablaVencimientos();

		Vencimiento.ponerVencimientos(1);

		Vencimiento.ponerVencimientos(2);

		Vencimiento.ponerVencimientos(3);

		Vencimiento.ponerVencimientos(4);

		Vencimiento.ponerVencimientos(5);

		Vencimiento.ponerVencimientos(6);

	}

	private void verVencimientoLlamada(int tipoSeguro)
			throws FileNotFoundException, ClassNotFoundException, IOException {

		verNotas();

		Vencimiento.verTablaVencimientos();

		Vencimiento.ponerVencimientos(tipoSeguro);

		int vencimientos = 0;

		vencimientos = vencimientosDecesos.size();

		if (vencimientos == 0) {

			mensajeNoHayVencimiento();

		}

		else {

			mostrarLlamadaConSeguro(tipoSeguro);

		}

	}

	private void mostrarLlamadaConSeguro(int tipoSeguro) {
		new Llamada(tipoSeguro).setVisible(true);

		Llamada.lblNewLabel_1.setText(nuevosVencimientos.getText());
	}

	private String saberIcono() {

		String icono = "";

		try {
			icono = lblNewLabel_2.getIcon().toString();

			icono = icono.substring(icono.lastIndexOf("/") + 1, icono.length());
		} catch (Exception e) {

		}

		return icono;
	}

	public static int saberArraySeguro(int seguro) {

		int resultado = 0;

		resultado = vencimientosDecesos.size();

		return resultado;
	}

	public static void mensajeNoHayVencimiento() {

		Agenda.iconoSeguro.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/name.png")));

		Agenda.lblNewLabel_2.setIcon(null);

		seguro = 0;

		Metodos.mensaje("No hay resultados", 3, true);

		verTodasLasLlamadas();

	}

	private LinkedList<String> saberLista(int tipo, int seguro) {

		LinkedList<String> lista = new LinkedList<String>();

		int vueltas = 0;

		switch (seguro) {

		case 1:
			vueltas = Vencimiento.getIndiceDeceso().size();
			break;

		case 2:
			vueltas = Vencimiento.getIndiceVida().size();
			break;
		case 3:
			vueltas = Vencimiento.getIndiceHogar().size();
			break;

		case 4:
			vueltas = Vencimiento.getIndiceCoche().size();
			break;

		case 5:
			vueltas = Vencimiento.getIndiceComercio().size();
			break;

		case 6:
			vueltas = Vencimiento.getIndiceComunidad().size();
			break;

		}

		int indiceVencimiento = -1;

		for (int i = 0; i < vueltas; i++) {

			switch (tipo) {

			case 1:

				switch (seguro) {

				case 1:
					indiceVencimiento = Vencimiento.getIndiceDeceso().get(i);
					break;

				case 2:
					indiceVencimiento = Vencimiento.getIndiceVida().get(i);
					break;
				case 3:
					indiceVencimiento = Vencimiento.getIndiceHogar().get(i);
					break;

				case 4:
					indiceVencimiento = Vencimiento.getIndiceCoche().get(i);
					break;

				case 5:
					indiceVencimiento = Vencimiento.getIndiceComercio().get(i);
					break;

				case 6:
					indiceVencimiento = Vencimiento.getIndiceComunidad().get(i);
					break;

				}

				if (indiceVencimiento < contactos.size()) {

					lista.add(contactos.get(indiceVencimiento));
				}

				break;

			case 2:

				switch (seguro) {

				case 1:
					indiceVencimiento = Vencimiento.getIndiceDeceso().get(i);
					break;

				case 2:
					indiceVencimiento = Vencimiento.getIndiceVida().get(i);
					break;
				case 3:
					indiceVencimiento = Vencimiento.getIndiceHogar().get(i);
					break;

				case 4:
					indiceVencimiento = Vencimiento.getIndiceCoche().get(i);
					break;

				case 5:
					indiceVencimiento = Vencimiento.getIndiceComercio().get(i);
					break;

				case 6:
					indiceVencimiento = Vencimiento.getIndiceComunidad().get(i);
					break;

				}

				if (indiceVencimiento < telefonos.size()) {

					lista.add(telefonos.get(indiceVencimiento));
				}

				break;

			case 3:

				lista.add(vencimientosDecesos.get(i));

				break;

			case 4:
				switch (seguro) {

				case 1:
					indiceVencimiento = Vencimiento.getIndiceDeceso().get(i);
					break;

				case 2:
					indiceVencimiento = Vencimiento.getIndiceVida().get(i);
					break;
				case 3:
					indiceVencimiento = Vencimiento.getIndiceHogar().get(i);
					break;

				case 4:
					indiceVencimiento = Vencimiento.getIndiceCoche().get(i);
					break;

				case 5:
					indiceVencimiento = Vencimiento.getIndiceComercio().get(i);
					break;

				case 6:
					indiceVencimiento = Vencimiento.getIndiceComunidad().get(i);
					break;

				}

				if (indiceVencimiento < direcciones.size()) {

					lista.add(direcciones.get(indiceVencimiento));
				}

				break;
			}

		}

		return lista;
	}

	private void ponerEnAgenda(int tipo, boolean filtro) {

		btnNewButton.setEnabled(true);

		verNotas();

		Vencimiento.verTablaVencimientos();

		Vencimiento.ponerVencimientos(tipo);

		int vueltas = 0;

		if (!filtro) {

			iconoSeguro.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/name.png")));

		}

		vueltas = vencimientosDecesos.size();

		if (vueltas == 0) {

			if (tipo > 0 && filtro) {
				Metodos.mensaje("No hay vencimientos", 3, true);

			}

			iconoSeguro.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/name.png")));

		}

		else {

			tlf.setText("");

			vtos.setText("");

			LinkedList<String> contactoTelefono = new LinkedList();

			LinkedList<String> contactoDirecciones = new LinkedList();
			LinkedList<String> contactoEmail = new LinkedList();
			LinkedList<String> contactoObservaciones = new LinkedList();
			LinkedList<String> fechasDecesos = new LinkedList();

			DefaultListModel model = new DefaultListModel();

			model.clear();

			jList1.setModel(model);

			int indiceVencimiento = -1;

			for (int i = 0; i < vueltas; i++) {

				indiceVencimiento = Vencimiento.getIndiceDeceso().get(i);

				fechasDecesos.add(vencimientosDecesos.get(i));

				if (indiceVencimiento < telefonos.size()) {

					contactoTelefono.add(telefonos.get(indiceVencimiento));

					contactoDirecciones.add(direcciones.get(indiceVencimiento));

					contactoEmail.add(emails.get(indiceVencimiento));

					contactoObservaciones.add(observaciones.get(indiceVencimiento));

				}

				if (indiceVencimiento < contactos.size()) {

					model.addElement(contactos.get(indiceVencimiento));
				}

			}

			telefonos.clear();

			direcciones.clear();

			contactodirecciones.clear();

			contactocodigopostal.clear();

			contactolocalidades.clear();

			contactoprovincias.clear();

			emails.clear();

			emails = contactoEmail;

			direcciones = contactoDirecciones;

			telefonos = contactoTelefono;

			vencimientos.clear();

			vencimientos = fechasDecesos;

			jList1.setModel(model);

		}
	}

	public static ArrayList<Objeto> leer(String file)
			throws IOException, FileNotFoundException, ClassNotFoundException {

		ArrayList<Objeto> arrayList2 = null;

		File archivo = new File(file);

		if (archivo.exists()) {

			ObjectInputStream leyendoFichero = new ObjectInputStream(new FileInputStream(file));

			arrayList2 = (ArrayList<Objeto>) leyendoFichero.readObject();

			leyendoFichero.close();

		}

		return arrayList2;
	}

	private void verContactoAgenda(int indice, String contacto, boolean filtro) {

		if (indice > -1) {

			try {

				if (observaciones.get(indice).isEmpty()) {
					observacion.setEnabled(false);
				}

				else {
					observacion.setEnabled(true);
				}

				nombre.setText(contacto);

				tlf.setText(telefonos.get(indice));

				email.setText(emails.get(indice));

				vtos.setText(vencimientos.get(indice));

				direccion.setText(direcciones.get(indice));

				if (Vencimiento.contactosVencimientos.contains(contacto)) {
					lblNewLabel_2.setIcon((new ImageIcon(Agenda.class.getResource("/imagenes/llamar.png"))));
					btnNewButton.setEnabled(true);
				}

				else {
					lblNewLabel_2.setIcon((new ImageIcon(Agenda.class.getResource("/imagenes/yallamado.png"))));
					btnNewButton.setEnabled(false);
				}

			} catch (Exception e1) {
				//
			}
		}
	}

	private String sumar1Year(String fechaVto) {

		String dia = "";

		String mes = "";

		dia = fechaVto.substring(0, fechaVto.indexOf("/"));

		mes = fechaVto.substring(fechaVto.indexOf("/") + 1, fechaVto.lastIndexOf("/"));

		int year = 0;

		year = Integer.parseInt(fechaVto.substring(fechaVto.lastIndexOf("/") + 1, fechaVto.length()));

		if (year < 3344 && dia.equals("29") && mes.equals("02") && Metodos.esBisiesto(year)) {
			dia = "01";
			mes = "03";
		}

		++year;

		fechaVto = dia + "/" + mes + "/" + year;

		return fechaVto;
	}

	public static String getDirectorioActual() {
		return directorioActual;
	}

	public static void setDirectorioActual(String directorioActual) {
		Agenda.directorioActual = directorioActual;
	}

	public static String getSeparador() {
		return separador;
	}

	public static void setSeparador(String separador) {
		Agenda.separador = separador;
	}

	public LinkedList<String> getContactos() {
		return contactos;
	}

	public static LinkedList<String> telefonos = new <String>LinkedList();

	public static LinkedList<String> direcciones = new <String>LinkedList();
	private static LinkedList<String> direccionesPlano = new <String>LinkedList();
	static LinkedList<String> contactodirecciones = new <String>LinkedList();
	static LinkedList<String> contactolocalidades = new <String>LinkedList();
	static LinkedList<String> contactoprovincias = new <String>LinkedList();
	static LinkedList<String> contactocodigopostal = new <String>LinkedList();
	static LinkedList<String> vencimientos = new <String>LinkedList();

	private static JTextField email;

	protected static void limpiarContactos() {
		modelo.removeAllElements();

		jList1.setModel(modelo);
	}

	public Agenda() throws IOException, SQLException {

		arrayList1 = new ArrayList<Objeto>();

		setIconImage(Toolkit.getDefaultToolkit().getImage(Agenda.class.getResource("/imagenes/agenda.png")));

		os = System.getProperty("os.name");

		separador = Metodos.saberSeparador(os);

		initComponents();

		Metodos.cearCarpetas();

		verTodasLasLlamadas();

		btnNewButton.setEnabled(false);

		directorioActual = new File(".").getCanonicalPath() + separador;

		setResizable(false);

		setAutoRequestFocus(false);

		this.setSize(new Dimension(870, 750));

		buscar.setToolTipText("Buscar");

		contacto.setToolTipText("Mostrar todos los contactos");

		editar.setToolTipText("Editar");

		this.setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();

		setJMenuBar(menuBar);

		JMenu mnAcciones = new JMenu("Acciones");
		mnAcciones.setForeground(Color.BLACK);
		mnAcciones.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnAcciones.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/utilities.png")));
		menuBar.add(mnAcciones);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Insertar");
		mnAcciones.add(mntmNewMenuItem_1);

		mntmNewMenuItem_1.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/insert.png")));

		mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {
					new Nuevo().setVisible(true);
				}

				catch (Exception e1) {

				}

			}

		});

		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		JSeparator separator_2 = new JSeparator();
		mnAcciones.add(separator_2);

		JMenuItem mntmNewMenuItem = new JMenuItem("Actualizar");
		mnAcciones.add(mntmNewMenuItem);

		mntmNewMenuItem.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				if (!nombre.getText().isEmpty()) {

					try {

						int indice = contactos.indexOf(nombre.getText());

						String datoContacto = Metodos.eliminarEspacios(nombre.getText());

						if (indice >= 0) {

							Actualizador editarContacto = new Actualizador(indice);

							editarContacto.setVisible(true);

						}
					}

					catch (Exception e1) {

					}
				}

			}

		});

		mntmNewMenuItem.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/actualizar.png")));
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		JSeparator separator_4 = new JSeparator();
		mnAcciones.add(separator_4);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Eliminar");
		mnAcciones.add(mntmNewMenuItem_5);
		mntmNewMenuItem_5.setSelectedIcon(null);
		mntmNewMenuItem_5.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/delete.png")));

		mntmNewMenuItem_5.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				String icono = iconoSeguro.getIcon().toString();

				icono = icono.substring(icono.lastIndexOf("/"), icono.length());

				String contacto = jList1.getSelectedValue();

				if (contacto != null) {

					if (JOptionPane.showConfirmDialog(null, "¿Quieres borrar a " + contacto + " ?", "Borrar contacto",
							JOptionPane.YES_NO_OPTION) == 0) {

						vaciarCampos();

						vencimientosDecesos.clear();

						int indice = jList1.getSelectedIndex();

						if (indice >= 0 && contactos.get(indice).equals(contacto)) {

							verNotas();

							nombre.setText("");

							arrayList1.clear();

							if (contactos.size() > 0) {

								Metodos.eliminarFichero("contactos.dat");

								Metodos.eliminarFichero("fechasDecesos.dat");
								Metodos.eliminarFichero("fechasVida.dat");
								Metodos.eliminarFichero("fechasHogar.dat");
								Metodos.eliminarFichero("fechasCoche.dat");
								Metodos.eliminarFichero("fechasComercio.dat");
								Metodos.eliminarFichero("fechasComunidad.dat");

								for (int i = 0; i < contactos.size(); i++) {

									if (!contacto.equals(contactos.get(i))) {

										arrayList1.add(new Objeto(contactos.get(i) + "«" + emails.get(i) + "»"
												+ observaciones.get(i) + "¬" + telefonos.get(i) + "═"
												+ contactodirecciones.get(i) + "▓" + contactolocalidades.get(i) + "░"
												+ contactocodigopostal.get(i) + "┤" + contactoprovincias.get(i) + "▒"
												+ fechaDecesos.get(i) + "╣" + fechaVida.get(i) + "║" + fechaHogar.get(i)
												+ "╝" + fechaCoche.get(i) + "¥" + fechaComercio.get(i) + "¶"
												+ fechaComunidad.get(i)));

									}

								}

								ObjectOutputStream escribiendoFichero;

								try {

									escribiendoFichero = new ObjectOutputStream(new FileOutputStream("contactos.dat"));

									escribiendoFichero.writeObject(arrayList1);

									escribiendoFichero.close();

									vaciarCampos();

									verNotas();

									for (int x = 1; x <= 6; x++) {
										ponerEnAgenda(x, false);
									}

								}

								catch (Exception e1) {
									Metodos.mensaje("Error", 1, true);
								}

							}

						}
					}
				}

				if (icono.equals("name.png")) {
					btnNewButton.setEnabled(false);
				}

				else {
					btnNewButton.setEnabled(true);
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mntmNewMenuItem_5.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/delete_1.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mntmNewMenuItem_5.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/delete.png")));

			}

		});

		mntmNewMenuItem_5.setFont(new Font("Segoe UI", Font.PLAIN, 16));

		JMenu mnNewMenu_2 = new JMenu("Llamadas");
		mnNewMenu_2.setForeground(Color.BLACK);
		mnNewMenu_2.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_2.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/llamada.png")));
		menuBar.add(mnNewMenu_2);

		JMenu mnNewMenu_3 = new JMenu("Llamadas");
		mnNewMenu_3.setFont(new Font("Dialog", Font.PLAIN, 16));
		mnNewMenu_3.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/telefono.png")));
		mnNewMenu_2.add(mnNewMenu_3);

		JMenuItem mntmNewMenuItem_8 = new JMenuItem("En Agenda");
		mntmNewMenuItem_8.setFont(new Font("Dialog", Font.PLAIN, 16));

		mnNewMenu_3.add(mntmNewMenuItem_8);

		mntmNewMenuItem_8.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				seguro = 1;

				ponerEnAgenda(seguro, true);

			}

		});

		mntmNewMenuItem_8.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/agenda.png")));

		JSeparator separator_11 = new JSeparator();

		mnNewMenu_3.add(separator_11);

		JMenuItem mntmNewMenuItem_16 = new JMenuItem("En Llamadas");

		mntmNewMenuItem_16.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {

					seguro = 0;
					verVencimientoLlamada(1);
				} catch (Exception e1) {
					//
				}

			}

		});

		mntmNewMenuItem_16.setFont(new Font("Dialog", Font.PLAIN, 16));

		mntmNewMenuItem_16.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/llamada.png")));

		mnNewMenu_3.add(mntmNewMenuItem_16);

		JMenuItem mntmNewMenuItem_21 = new JMenuItem("Sobre");
		
		mntmNewMenuItem_21.setFont(new Font("Dialog", Font.PLAIN, 16));

		mntmNewMenuItem_21.addMouseListener(new MouseAdapter() {

			@Override
			
			public void mousePressed(MouseEvent e) {
				
				try {
					
					About sobre = new About();

					sobre.andar = true;

					sobre.start();

				}
				
				catch (Exception e1) {

				}
				
			}

		});

		mntmNewMenuItem_21.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/about.png")));

		menuBar.add(mntmNewMenuItem_21);

	}

	public static String getOs() {
		return os;
	}

	protected String saberPlantilla() {

		String plantilla = "";

		if (nuevosVencimientos.getText().isEmpty()) {
			Metodos.mensaje("No hay vencimientos", 2, true);
		}

		else {

			plantilla = "all.html";

		}

		return plantilla;
	}

	private void initComponents() throws SQLException, IOException {

		JLabel jLabel1;
		JPanel jPanel3;
		JPanel jPanel4;
		JPanel jPanel5;

		jList1 = new JList<>();

		jList1.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {

				verContactoAgenda(jList1.getSelectedIndex(), jList1.getSelectedValue(), false);

			}

		});

		jPanel4 = new JPanel();
		jPanel5 = new JPanel();
		jLabel1 = new JLabel();
		contacto = new JButton();
		contacto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		editar = new JButton();
		editar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		buscar = new JButton();
		buscar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		jPanel3 = new JPanel();
		JScrollPane jScrollPane1;
		JPanel panelCasa;
		jScrollPane1 = new JScrollPane();
		jList1.setFont(new Font("Dialog", Font.PLAIN, 18));
		jList1.setFixedCellHeight(40);

		jPanel5.setBackground(new java.awt.Color(88, 205, 170));

		jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
		jLabel1.setText("Nombre");

		GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
						.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 481, GroupLayout.PREFERRED_SIZE)));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE));

		GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel4Layout
				.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(jPanel4Layout
								.createSequentialGroup().addComponent(jPanel5, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)));
		setTitle("Contactos");
		setBackground(new java.awt.Color(123, 123, 123));

		jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane1.setDoubleBuffered(true);

		jList1.setBackground(new java.awt.Color(254, 254, 254));

		jScrollPane1.setViewportView(jList1);
		JLabel jLabel3;
		JLabel jLabel5;
		panelCasa = new JPanel();
		jLabel5 = new JLabel();
		jLabel5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jLabel5.setText("Llamadas");

		panelCasa.setBackground(new Color(240, 240, 240));

		jLabel5.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/tiempo.png")));

		jLabel3 = new JLabel();
		jLabel3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jLabel3.setText("Nombre");

		jLabel3.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/user.png")));

		nombre = new JTextField();
		nombre.setEditable(false);
		nombre.setHorizontalAlignment(SwingConstants.LEFT);
		nombre.setBackground(new Color(255, 255, 255));
		nombre.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel lblNewLabel = new JLabel("Tlfs");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/telefono.png")));
		vtos.setEditable(false);

		vtos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tlf.setEditable(false);

		tlf.setFont(new Font("Tahoma", Font.PLAIN, 16));
		direccion.setEditable(false);

		direccion.setFont(new Font("Tahoma", Font.PLAIN, 14));

		email = new JTextField();
		email.setEditable(false);
		email.setFont(new Font("Tahoma", Font.PLAIN, 14));
		email.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/email.png")));

		JLabel lblNewLabel_1_1 = new JLabel("Dirección");
		lblNewLabel_1_1.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/city.png")));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		observacion.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/view.png")));

		observacion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				indice = contactos.indexOf(nombre.getText());

				if (indice > -1) {
					Metodos.mensaje(observaciones.get(indice), 2, true);
				}

			}

		});

		observacion.setFont(new Font("Tahoma", Font.BOLD, 16));

		JButton btnAadirObservacion = new JButton("+- Obs");

		btnAadirObservacion.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/obs.png")));

		btnAadirObservacion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					verTodasLasLlamadas();

					int indiceObs = contactos.indexOf(nombre.getText());

					String dato = (String) JOptionPane.showInputDialog(null, "Cambiar Observacion", "",
							JOptionPane.QUESTION_MESSAGE, null, null, observaciones.get(indiceObs));

					if (dato != null) {

						observaciones.set(indiceObs, dato);

						arrayList1.clear();

						arrayList1 = leer("contactos.dat");

						arrayList1.set(indiceObs,
								new Objeto(contactos.get(indiceObs) + "«" + email.getText() + "»"
										+ observaciones.get(indiceObs) + "¬" + tlf.getText() + "═" + direccion.getText()
										+ "▓" + "" + "░" + "" + "┤" + "" + "▒" + fechaDecesos.get(indiceObs) + "╣"
										+ fechaVida.get(indiceObs) + "║" + fechaHogar.get(indiceObs) + "╝"
										+ fechaCoche.get(indiceObs) + "¥" + fechaComercio.get(indiceObs) + "¶"
										+ fechaComunidad.get(indiceObs)));

						ObjectOutputStream escribiendoFichero = new ObjectOutputStream(
								new FileOutputStream("contactos.dat"));

						escribiendoFichero.writeObject(arrayList1);

					}

					if (dato.isEmpty()) {
						observacion.setEnabled(false);
					}

					else {
						observacion.setEnabled(true);
					}

				} catch (Exception e1) {
					//

				}

			}

		});

		btnAadirObservacion.setFont(new Font("Tahoma", Font.BOLD, 16));

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try {

					String nombreContacto = jList1.getSelectedValue();

					int indice = jList1.getSelectedIndex();

					if (indice > -1) {

						String icono = "";

						icono = saberIcono();

						if (icono.equals("llamar.png") && JOptionPane.showConfirmDialog(null,
								"<html><h2>¿Está seguro de que desea marcar a " + nombreContacto
										+ " como contacto ya llamado?</h2></html>",
								"Ya llamado", JOptionPane.YES_NO_OPTION) == 0

						) {

							LinkedList<String> contactosLlamados = new LinkedList<String>();

							LinkedList<String> fechasVtos = new LinkedList<String>();

							contactosLlamados = Metodos.leer("contactos.dat");

							contactosLlamados = Metodos.formatearArray(contactosLlamados.get(0));

							fechasVtos = Metodos.sacarFechas(contactosLlamados.get(indice));

							String fechaDeceso = "";
							String fechaVida = "";
							String fechaHogar = "";
							String fechaCoche = "";
							String fechaComercio = "";
							String fechaComunidad = "";

							if (indice == vencimientosDecesos.size()) {

								--indice;

							}

							vencimientosDecesos.set(indice, sumar1Year(vencimientosDecesos.get(indice)));

							fechaDeceso = vencimientosDecesos.get(indice);

							if (vencimientosDecesos.size() > 0) {

								fechaDeceso = vencimientosDecesos.get(indice);

							}

							vencimientos.clear();

							String localidad = "";
							String codigopostal = "";
							String provincia = "";

							arrayList1.clear();

							arrayList1 = Agenda.leer("contactos.dat");

							int indiceContacto = contactos.indexOf(nombre.getText());

							if (fechaDeceso.isEmpty()) {

								fechaDeceso = fechasVtos.get(0);
							}

							if (fechaVida.isEmpty()) {
								fechaVida = fechasVtos.get(1);
							}

							if (fechaHogar.isEmpty()) {
								fechaHogar = fechasVtos.get(2);
							}

							if (fechaCoche.isEmpty()) {
								fechaCoche = fechasVtos.get(3);
							}

							if (fechaComercio.isEmpty()) {
								fechaComercio = fechasVtos.get(4);
							}

							if (fechaComunidad.isEmpty()) {
								fechaComunidad = fechasVtos.get(5);
							}

							fechaDeceso = Metodos.convertirFecha(fechaDeceso, true);
							fechaVida = Metodos.convertirFecha(fechaVida, true);
							fechaHogar = Metodos.convertirFecha(fechaHogar, true);
							fechaCoche = Metodos.convertirFecha(fechaCoche, true);
							fechaComercio = Metodos.convertirFecha(fechaComercio, true);
							fechaComunidad = Metodos.convertirFecha(fechaComunidad, true);

							Object datoFechaDeceso = null;
							Object datoFechaCoche = null;
							Object datoFechaVida = null;
							Object datoFechaHogar = null;
							Object datoFechaComercio = null;
							Object datoFechaComunidad = null;

							if (!fechaDeceso.isEmpty()) {
								datoFechaDeceso = new Date(fechaDeceso);
							}

							if (!fechaVida.isEmpty()) {
								datoFechaVida = new Date(fechaVida);
							}

							if (!fechaHogar.isEmpty()) {
								datoFechaHogar = new Date(fechaHogar);
							}

							if (!fechaCoche.isEmpty()) {
								datoFechaCoche = new Date(fechaCoche);
							}

							if (!fechaComercio.isEmpty()) {
								datoFechaComercio = new Date(fechaComercio);

							}

							if (!fechaComunidad.isEmpty()) {
								datoFechaComunidad = new Date(fechaComunidad);
							}

							arrayList1.set(indiceContacto, new Objeto(nombreContacto + "«" + email.getText() + "»"
									+ observaciones.get(indiceContacto) + "¬" + tlf.getText() + "═"
									+ direccion.getText() + "▓" + localidad + "░" + codigopostal + "┤" + provincia + "▒"
									+ datoFechaDeceso + "╣" + datoFechaVida + "║" + datoFechaHogar + "╝"
									+ datoFechaCoche + "¥" + datoFechaComercio + "¶" + datoFechaComunidad));

							ObjectOutputStream escribiendoFichero = new ObjectOutputStream(
									new FileOutputStream("contactos.dat"));

							escribiendoFichero.writeObject(arrayList1);

							escribiendoFichero.close();

							vaciarCampos();

						}

						Vencimiento.contactosVencimientos.clear();

						Vencimiento.verTablaVencimientos();

						verTodasLasLlamadas();

					}

				}

				catch (Exception e) {

				}

			}

			private void guardarLlamadas(String archivo, boolean borrar)
					throws IOException, FileNotFoundException, ClassNotFoundException {

				try {

					String archivoVto = "";

					String fechaVto = "";

					LinkedList<String> llamadasVto = new LinkedList<String>();

					LinkedList<String> contactosVto = new LinkedList<String>();

					llamadasVto = Metodos.leer(archivo);

					llamadasVto = Metodos.formatearArray(llamadasVto.get(0));

					String contacto = jList1.getSelectedValue();

					int indiceContacto = jList1.getSelectedIndex();

					if (borrar) {

						llamadasVto.remove(contacto);

						contactosVto = Metodos.leer("contactos.dat");

						contactosVto = Metodos.formatearArray(contactosVto.get(0));

						Date fecha = new Date();

						actualizarArchivo(archivo, contactosVto);
					}

					else {
						llamadasVto.add(contacto);

					}

					actualizarArchivo(archivo, llamadasVto);

				}

				catch (Exception e) {

				}

			}

		});

		btnNewButton.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/llamada.png")));

		GroupLayout panelCasaLayout = new GroupLayout(panelCasa);
		panelCasaLayout.setHorizontalGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(panelCasaLayout.createSequentialGroup().addContainerGap().addGroup(panelCasaLayout
						.createParallelGroup(Alignment.LEADING).addComponent(jLabel3)
						.addComponent(btnAadirObservacion, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel).addComponent(jLabel5)
						.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)).addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(vtos, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
								.addComponent(tlf, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, panelCasaLayout.createSequentialGroup()
										.addComponent(observacion).addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblNewLabel_2)
										.addGap(5))
								.addComponent(nombre, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 210,
										Short.MAX_VALUE)
								.addComponent(direccion, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
								.addComponent(email, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
						.addGap(52)));
		panelCasaLayout.setVerticalGroup(panelCasaLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(panelCasaLayout.createSequentialGroup().addContainerGap().addGroup(panelCasaLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAadirObservacion, GroupLayout.PREFERRED_SIZE, 41,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(observacion, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel_2)).addGap(22)
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addComponent(nombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(panelCasaLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(panelCasaLayout.createSequentialGroup().addGap(14)
										.addComponent(tlf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
										.addComponent(vtos, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
										.addGap(42)
										.addComponent(direccion, GroupLayout.PREFERRED_SIZE, 87,
												GroupLayout.PREFERRED_SIZE)
										.addGap(101))
								.addGroup(panelCasaLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblNewLabel)
										.addGap(39).addComponent(jLabel5).addGap(67)
										.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 48,
												GroupLayout.PREFERRED_SIZE)
										.addGap(76)
										.addGroup(panelCasaLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 49,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(email, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)))
						.addGap(43)));
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (saberIcono().equals("llamar.png")) {
					Metodos.mensaje("Debes llamar a este contacto", 2, true);
				} else {
					Metodos.mensaje("Ya has llamado a este contacto", 2, true);
				}

			}
		});
		lblNewLabel_2.setIcon(null);

		panelCasa.setLayout(panelCasaLayout);
		nuevosVencimientos.setFont(new Font("Dialog", Font.BOLD, 14));

		nuevosVencimientos.setHorizontalAlignment(SwingConstants.RIGHT);

		iconoSeguro.setIcon(new ImageIcon(Agenda.class.getResource("/imagenes/name.png")));

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel3Layout
				.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(jPanel3Layout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(nuevosVencimientos, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout
								.createParallelGroup(Alignment.LEADING)
								.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)
								.addGroup(jPanel3Layout.createSequentialGroup().addGap(157).addComponent(iconoSeguro)))
								.addGap(12)
								.addComponent(panelCasa, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)))
				.addContainerGap(119, Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(Alignment.TRAILING).addGroup(jPanel3Layout
				.createSequentialGroup()
				.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel3Layout
						.createSequentialGroup().addComponent(nuevosVencimientos)
						.addGroup(jPanel3Layout.createParallelGroup(Alignment.LEADING)
								.addGroup(jPanel3Layout.createSequentialGroup().addGap(18).addComponent(panelCasa,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
								.addComponent(jScrollPane1, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 541,
										GroupLayout.PREFERRED_SIZE)))
						.addComponent(iconoSeguro))
				.addContainerGap(26, Short.MAX_VALUE)));
		jPanel3.setLayout(jPanel3Layout);

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, 942, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(153, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, 622, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(50, Short.MAX_VALUE)));
		getContentPane().setLayout(layout);

		if (jList1.getModel().getSize() == 0) {

			try {

				Agenda.limpiarContactos();

				verNotas();

				Vencimiento.verTablaVencimientos();

			} catch (Exception e) {
				//
			}
		}

		pack();
	}

	public static void setfechaDecesos(String fecha) {
		Agenda.fechaDecesos.add(fecha);
	}

	public static void verNotas() {

		String nota;

		ArrayList<Objeto> arrayList1 = new ArrayList<Objeto>();

		ArrayList<Objeto> arrayList2;

		LinkedList<String> agenda = new <String>LinkedList();

		try {

			limpiarContactos();

			contactos.clear();

			observaciones.clear();

			fechaDecesos.clear();

			emails.clear();

			fechaVida.clear();

			fechaHogar.clear();

			fechaCoche.clear();
			fechaComercio.clear();
			fechaComunidad.clear();
			telefonos.clear();

			direcciones.clear();
			direccionesPlano.clear();
			vencimientos.clear();

			contactodirecciones.clear();
			contactocodigopostal.clear();
			contactolocalidades.clear();
			contactoprovincias.clear();

			arrayList2 = leer("contactos.dat");

			String correo, cadena, fechaDeceso, datoVida, datoHogar, datoCoche, datoComercio, datoComunidad, obs,
					telefono;

			String cliente, direccion, localidad, codPostal, provincia;
			String codigoPostal = "";
			if (arrayList2 != null) {

				for (int i = 0; i < arrayList2.size(); i++) {

					agenda.add(arrayList2.get(i).toString());
				}

				for (int i = 0; i < agenda.size(); i++) {

					cadena = agenda.get(i);

					fechaDeceso = cadena.substring(cadena.indexOf("▒") + 1, cadena.indexOf("╣"));

					datoVida = cadena.substring(cadena.indexOf("╣") + 1, cadena.indexOf("║"));

					datoHogar = cadena.substring(cadena.indexOf("║") + 1, cadena.indexOf("╝"));

					datoCoche = cadena.substring(cadena.indexOf("╝") + 1, cadena.indexOf("¥"));

					datoComercio = cadena.substring(cadena.indexOf("¥") + 1, cadena.indexOf("¶"));

					datoComunidad = cadena.substring(cadena.indexOf("¶") + 1, cadena.length());

					obs = cadena.substring(cadena.indexOf("»") + 1, cadena.indexOf("¬"));

					telefono = cadena.substring(cadena.indexOf("¬") + 1, cadena.indexOf("═"));

					cliente = cadena.substring(0, cadena.indexOf("«"));

					correo = cadena.substring(cadena.indexOf("«") + 1, cadena.indexOf("»"));

					direccion = cadena.substring(cadena.indexOf("═") + 1, cadena.indexOf("▓"));

					provincia = cadena.substring(cadena.indexOf("┤") + 1, cadena.indexOf("▒"));

					localidad = cadena.substring(cadena.indexOf("▓") + 1, cadena.indexOf("░"));

					codPostal = cadena.substring(cadena.indexOf("░") + 1, cadena.indexOf("┤"));

					modelo.addElement(cliente);

					contactos.add(cliente);

					telefonos.add(telefono);

					observaciones.add(obs);

					emails.add(correo);

					fechaDecesos.add(Metodos.convertirFecha(fechaDeceso, false));

					fechaVida.add(Metodos.convertirFecha(datoVida, false));

					fechaHogar.add(Metodos.convertirFecha(datoHogar, false));

					fechaCoche.add(Metodos.convertirFecha(datoCoche, false));

					fechaComercio.add(Metodos.convertirFecha(datoComercio, false));

					fechaComunidad.add(Metodos.convertirFecha(datoComunidad, false));

					vencimientos.add(fechaDecesos.getLast());

					if (direccion.isEmpty() || direccion.isEmpty() && localidad.isEmpty()
							|| direccion.isEmpty() && codPostal.isEmpty()
							|| direccion.isEmpty() && provincia.isEmpty()) {
						direcciones.add("");
						direccionesPlano.add("");
						contactodirecciones.add("");
						contactocodigopostal.add("");
						contactolocalidades.add("");
						contactoprovincias.add("");
					}

					else {

						if (!codPostal.isEmpty()) {
							codigoPostal = " (" + codPostal + ") ";
						}

						else {
							codigoPostal = "";
						}

						direcciones.add(direccion + "\n\n" + localidad + codigoPostal + "\n\n" + provincia);
						direccionesPlano.add(direccion + "||" + localidad + codigoPostal + "||" + provincia);

						contactodirecciones.add(direccion);
						contactocodigopostal.add(codPostal);
						contactolocalidades.add(localidad);
						contactoprovincias.add(provincia);
					}

				}

			}

			jList1.setModel(modelo);

		} catch (Exception e) {

		}
	}

	public static LinkedList<String> getfechaDecesos() {
		return fechaDecesos;
	}

	public boolean controlarSeleccion() {
		try {
			jList1.getSelectedValue().toString();
			return false;
		} catch (NullPointerException e1) {
			return true;
		}
	}

	public static LinkedList<String> getFechaComercio() {
		return fechaComercio;
	}

	public static LinkedList<String> getFechaComunidad() {
		return fechaComunidad;
	}

	public static void main(String[] args) {

		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					new Agenda().setVisible(true);
				} catch (IOException | SQLException e) {
					//
				}

			}
		});

	}

	public static JTextPane getTlf() {
		return tlf;
	}

	public void setTlf(JTextPane tlf) {
		this.tlf = tlf;
	}

	public static LinkedList<String> getFechaDecesos() {
		return fechaDecesos;
	}

	public static void setFechaDecesos(LinkedList<String> fechaDecesos) {
		Agenda.fechaDecesos = fechaDecesos;
	}

	public static LinkedList<String> getEmails() {
		return emails;
	}

	public static void setEmails(LinkedList<String> emails) {
		Agenda.emails = emails;
	}

	public static LinkedList<String> getFechaVida() {
		return fechaVida;
	}

	public static void setFechaVida(String fecha) {
		Agenda.fechaVida.add(fecha);
	}

	public static LinkedList<String> getFechaHogar() {
		return fechaHogar;
	}

	public static void setFechaHogar(String fecha) {
		Agenda.fechaHogar.add(fecha);
	}

	public static void setFechaComunidad(String fecha) {
		Agenda.fechaComunidad.add(fecha);
	}

	public static void setFechaComercio(String fecha) {
		Agenda.fechaComercio.add(fecha);
	}

	public static LinkedList<String> getFechaCoche() {
		return fechaCoche;
	}

	public static void setFechaCoche(String fecha) {
		Agenda.fechaCoche.add(fecha);

	}

	public static LinkedList<String> getTelefonos() {
		return telefonos;
	}

	public static void setTelefonos(LinkedList<String> telefonos) {
		Agenda.telefonos = telefonos;
	}

}
