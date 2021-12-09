package principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rojeru_san.componentes.RSDateChooser;
import utils.Metodos;

@SuppressWarnings("all")

public class Actualizador extends javax.swing.JFrame implements ActionListener, ChangeListener {
	private boolean guardar = true;

	RSDateChooser decesos = new RSDateChooser();

	MaterialTextField localidad = new MaterialTextField();

	MaterialTextField provincia = new MaterialTextField();

	MaterialTextField direccion = new MaterialTextField();

	MaterialTextField codPostal = new MaterialTextField();

	MaterialTextField nombre = new MaterialTextField();

	MaterialTextField telefono = new MaterialTextField();

	MaterialTextField email = new MaterialTextField();

	String vto = "";

	String fechaDecesos = "";

	String datoDireccion = "";

	String datoLocalidad = "";

	String datoProvincia = "";

	String datoCodigoPostal = "";

	String datoDireccionLarga = "";

	private int indiceVto = 0;

	private final JLabel infoNombre = new JLabel("*");

	private final JLabel infoTelefono = new JLabel("*");

	private void actualizarDatos() {

		String contacto = nombre.getText();

		String tlfContacto = telefono.getText();

		String emailContacto = email.getText();

		String codigoPostalContacto = codPostal.getText();

		boolean comprobarEmail = Metodos.comprobarPatron(emailContacto,
				"^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");

		boolean comprobarCodPostal = Metodos.comprobarPatron(codigoPostalContacto, "^[0-9]{5}");

		if (contacto.isEmpty() || tlfContacto.isEmpty()) {

			guardar = false;

			Metodos.mensaje("Por favor, introduzca el nombre y el teléfono", 1, false);

		}

		if (!codPostal.getText().isEmpty() && !comprobarCodPostal) {

			Metodos.mensaje("Código postal incorrecto", 1, false);

			guardar = false;

		}

		if (!emailContacto.isEmpty() && !comprobarEmail) {

			Metodos.mensaje("Email incorrecto", 1, false);

			guardar = false;

		}

		if (guardar) {

			try {

				fechaDecesos = "";

				Object datoFechaDeceso = null;
				Object datoFechaCoche = null;
				Object datoFechaVida = null;
				Object datoFechaHogar = null;
				Object datoFechaComercio = null;
				Object datoFechaComunidad = null;

				fechaDecesos = Metodos.convertirFecha(decesos.getDatoFecha().toString(), true);
				datoFechaDeceso = new Date(fechaDecesos);

				ArrayList<Objeto> arrayList1;

				arrayList1 = Agenda.leer("contactos.dat");

				arrayList1.set(indiceVto,
						new Objeto(contacto + "«" + email.getText() + "»" + Agenda.observaciones.get(indiceVto) + "¬"
								+ tlfContacto + "═" + direccion.getText() + "▓" + localidad.getText() + "░"
								+ codPostal.getText() + "┤" + provincia.getText() + "▒" + datoFechaDeceso + "╣"
								+ datoFechaVida + "║" + datoFechaHogar + "╝" + datoFechaCoche + "¥" + datoFechaComercio
								+ "¶" + datoFechaComunidad));

				ObjectOutputStream escribiendoFichero = new ObjectOutputStream(new FileOutputStream("contactos.dat"));

				escribiendoFichero.writeObject(arrayList1);

				escribiendoFichero.close();

				arrayList1.clear();

				Agenda.limpiarContactos();

				Agenda.vaciarCampos();

				Agenda.verNotas();

				Vencimiento.verTablaVencimientos();

				dispose();

			}

			catch (Exception e) {
				//
			}

		}

		else {
			guardar = true;
		}
	}

	public Actualizador(int indice) {

		getContentPane().addKeyListener(new KeyAdapter() {

			@Override

			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					actualizarDatos();
				}

			}

		});

		infoTelefono.setForeground(Color.BLUE);
		infoTelefono.setFont(new Font("Arial", Font.PLAIN, 20));
		infoNombre.setForeground(Color.BLUE);
		infoNombre.setFont(new Font("Arial", Font.PLAIN, 20));

		this.indiceVto = indice;

		setAutoRequestFocus(false);

		setIconImage(Toolkit.getDefaultToolkit().getImage(Actualizador.class.getResource("/imagenes/actualizar.png")));

		setTitle("Modificar contacto");

		setType(Type.UTILITY);

		initComponents();

		vto = Agenda.vencimientos.get(indice);

		decesos.setFormatoFecha("dd/MM/Y");

		nombre.setText(Agenda.getNombre().getText());

		telefono.setText(Agenda.getTlf().getText());

		email.setText(Agenda.getEmail().getText());

		datoDireccionLarga = Agenda.getDireccion().getText();

		try {

			if (!datoDireccionLarga.isEmpty()) {

				datoDireccion = datoDireccionLarga.substring(0, datoDireccionLarga.indexOf("\n\n"));

				datoLocalidad = datoDireccionLarga.substring(datoDireccionLarga.indexOf("\n\n") + 2,
						datoDireccionLarga.indexOf("("));

				datoCodigoPostal = datoDireccionLarga.substring(datoDireccionLarga.indexOf("(") + 1,
						datoDireccionLarga.indexOf(")"));

				datoProvincia = datoDireccionLarga.substring(datoDireccionLarga.lastIndexOf("\n\n") + 2,
						datoDireccionLarga.length());

			}

		} catch (Exception e) {
			//
		}

		fechaDecesos = vto;

		decesos.setDatoFecha(Metodos.calcularFecha(fechaDecesos, 1));

		direccion.setText(datoDireccion);

		localidad.setText(datoLocalidad);

		codPostal.setText(datoCodigoPostal);

		provincia.setText(datoProvincia);

		this.setVisible(true);

	}

	private void initComponents() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		setResizable(false);

		JLabel lblNewLabel_3 = new JLabel("Dirección");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/geo.png")));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));

		direccion.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_3_2 = new JLabel("Provincia");
		lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_2.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/city.png")));
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		provincia.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_3_1_2 = new JLabel("Cod Postal");
		lblNewLabel_3_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_1_2.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/tag.png")));
		lblNewLabel_3_1_2.setFont(new Font("Tahoma", Font.PLAIN, 15));

		codPostal.setHorizontalAlignment(SwingConstants.CENTER);

		localidad.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_3_1 = new JLabel("Localidad");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_1.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/city.png")));
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel jLabel5_2 = new JLabel();
		jLabel5_2.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel5_2.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/telefono.png")));
		jLabel5_2.setText("Fecha llamada");
		jLabel5_2.setFont(new Font("Tahoma", Font.PLAIN, 16));

		MaterialButtomRectangle btnNewButton = new MaterialButtomRectangle();

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				actualizarDatos();

			}

		});

		btnNewButton.setText("Actualizar");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBackground(new java.awt.Color(0, 102, 0));

		btnNewButton.setForeground(new java.awt.Color(255, 255, 255));

		JLabel lblNewLabel_3_3 = new JLabel("Nombre");
		lblNewLabel_3_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_3.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/user.png")));
		lblNewLabel_3_3.setFont(new Font("Tahoma", Font.PLAIN, 15));

		nombre.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_3_3_1 = new JLabel("Teléfono");
		lblNewLabel_3_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_3_1.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/telefono.png")));
		lblNewLabel_3_3_1.setFont(new Font("Tahoma", Font.PLAIN, 15));

		telefono.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_3_2_1 = new JLabel("Email");
		lblNewLabel_3_2_1.setIcon(new ImageIcon(Actualizador.class.getResource("/imagenes/email.png")));
		lblNewLabel_3_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));

		email.setHorizontalAlignment(SwingConstants.CENTER);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
								.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(lblNewLabel_3_3, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 162,
														GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
										.addComponent(infoNombre, GroupLayout.PREFERRED_SIZE, 8,
												GroupLayout.PREFERRED_SIZE)
										.addGap(12))
								.addGroup(layout.createSequentialGroup()
										.addComponent(jLabel5_2, GroupLayout.PREFERRED_SIZE, 192,
												GroupLayout.PREFERRED_SIZE)
										.addGap(27))
								.addGroup(
										Alignment.LEADING,
										layout.createSequentialGroup()
												.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 172,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(provincia, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 146,
												Short.MAX_VALUE)
										.addComponent(nombre, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 146,
												Short.MAX_VALUE)
										.addComponent(direccion, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 146,
												Short.MAX_VALUE)
										.addGroup(layout.createSequentialGroup()
												.addComponent(decesos, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(Alignment.TRAILING)
														.addComponent(lblNewLabel_3_1_2, GroupLayout.DEFAULT_SIZE, 137,
																Short.MAX_VALUE)
														.addGroup(layout.createSequentialGroup()
																.addComponent(lblNewLabel_3_3_1,
																		GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(infoTelefono, GroupLayout.PREFERRED_SIZE,
																		8, GroupLayout.PREFERRED_SIZE))
														.addComponent(
																lblNewLabel_3_1, GroupLayout.DEFAULT_SIZE, 137,
																Short.MAX_VALUE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(Alignment.LEADING)
														.addComponent(codPostal, GroupLayout.DEFAULT_SIZE, 165,
																Short.MAX_VALUE)
														.addComponent(localidad, GroupLayout.DEFAULT_SIZE, 165,
																Short.MAX_VALUE)
														.addComponent(telefono, GroupLayout.DEFAULT_SIZE, 165,
																Short.MAX_VALUE)
														.addComponent(email, GroupLayout.DEFAULT_SIZE, 165,
																Short.MAX_VALUE))
												.addGap(43))
										.addGroup(layout.createSequentialGroup()
												.addComponent(lblNewLabel_3_2_1, GroupLayout.PREFERRED_SIZE, 111,
														GroupLayout.PREFERRED_SIZE)
												.addContainerGap())))
						.addGroup(layout.createSequentialGroup().addGap(274)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(312, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
								.addGap(38)
								.addGroup(layout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_3_3, GroupLayout.PREFERRED_SIZE, 47,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(nombre, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(telefono, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_3_3_1, GroupLayout.PREFERRED_SIZE, 47,
												GroupLayout.PREFERRED_SIZE))
								.addGap(25)
								.addGroup(layout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 47,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(direccion, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_3_1).addComponent(localidad,
												GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
								.addGroup(layout.createSequentialGroup().addGap(44).addComponent(infoTelefono,
										GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
						.addGap(18)
						.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(layout.createParallelGroup(Alignment.BASELINE)
										.addComponent(provincia, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_3_2, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(layout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_3_1_2, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(codPostal, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))))
						.addGroup(layout.createSequentialGroup().addGap(44)
								.addComponent(infoNombre, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
				.addGap(30)
				.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addComponent(jLabel5_2, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_3_2_1, GroupLayout.PREFERRED_SIZE, 48,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(email, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(decesos, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
				.addGap(18).addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
				.addGap(328)));
		getContentPane().setLayout(layout);
		setSize(new Dimension(740, 455));
		setLocationRelativeTo(null);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
