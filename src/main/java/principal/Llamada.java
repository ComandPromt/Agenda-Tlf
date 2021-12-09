package principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.LinkedList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import utils.FormatoTabla;

public class Llamada extends javax.swing.JFrame {

	static FormatoTabla ft;

	private static final long serialVersionUID = 1L;

	private JPanel jPanel1;

	private JScrollPane jScrollPane1;

	static JTable jTable1;

	private JTextField jTextField1;

	BufferedReader br;

	BufferedWriter bw;

	public static DefaultTableModel model;

	DefaultTableModel model2;

	String[][] dataStok;

	String path;

	File file;

	LinkedList<String> vencimientos = new LinkedList<String>();

	static int tipoSeguro = 0;

	static JLabel lblNewLabel_1 = new JLabel("");

	public Llamada(int tipo) {

		setIconImage(Toolkit.getDefaultToolkit().getImage(Llamada.class.getResource("/imagenes/telefono.png")));

		vencimientos.clear();

		tipoSeguro = tipo;

		setAlwaysOnTop(true);

		setTitle("Llamadas de Vencimientos");

		setResizable(false);

		initComponents();

		this.setSize(new Dimension(780, 650));

		setLocationRelativeTo(null);

		vencimientos = Agenda.vencimientosDecesos;

		model = (DefaultTableModel) jTable1.getModel();

		loadData(vencimientos);

		verTabla();

	}

	static void verTabla() {

		ft = new FormatoTabla();

		jTable1.setDefaultRenderer(Object.class, ft);

		jTable1.getTableHeader().setEnabled(false);

	}

	public void loadData(LinkedList<String> lista) {

		model.getDataVector().removeAllElements();

		model = (DefaultTableModel) jTable1.getModel();

		try {
			String tlf = "";
			String contactoVto = "";

			ft = new FormatoTabla();

			for (int i = 0; i < lista.size(); i++) {

				switch (tipoSeguro) {

				case 1:
					contactoVto = Agenda.contactos.get(Vencimiento.getIndiceDeceso().get(i));

					tlf = Agenda.telefonos.get(Vencimiento.getIndiceDeceso().get(i));
					break;

				case 2:
					contactoVto = Agenda.contactos.get(Vencimiento.getIndiceVida().get(i));
					tlf = Agenda.telefonos.get(Vencimiento.getIndiceVida().get(i));
					break;

				case 3:
					contactoVto = Agenda.contactos.get(Vencimiento.getIndiceHogar().get(i));
					tlf = Agenda.telefonos.get(Vencimiento.getIndiceVida().get(i));
					break;

				case 4:
					contactoVto = Agenda.contactos.get(Vencimiento.getIndiceCoche().get(i));
					tlf = Agenda.telefonos.get(Vencimiento.getIndiceVida().get(i));
					break;

				case 5:
					contactoVto = Agenda.contactos.get(Vencimiento.getIndiceComercio().get(i));
					tlf = Agenda.telefonos.get(Vencimiento.getIndiceVida().get(i));
					break;

				case 6:
					contactoVto = Agenda.contactos.get(Vencimiento.getIndiceComunidad().get(i));
					tlf = Agenda.telefonos.get(Vencimiento.getIndiceVida().get(i));
					break;

				}

				model.addRow(new Object[] { contactoVto, tlf, lista.get(i), "" });

			}
		}

		catch (Exception e) {
			//
		}

	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();

		jScrollPane1 = new javax.swing.JScrollPane();

		jTable1 = new javax.swing.JTable();

		jTable1.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				try {
					model.getDataVector().removeAllElements();
					model = (DefaultTableModel) jTable1.getModel();
					loadData(vencimientos);
					verTabla();

				} catch (Exception e1) {
					//
				}
			}
		});

		jTextField1 = new javax.swing.JTextField();
		jTextField1.setToolTipText("sdfsd");
		jTextField1.setFont(new Font("Tahoma", Font.BOLD, 16));
		jTextField1.setHorizontalAlignment(SwingConstants.CENTER);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		jPanel1.setBackground(Color.WHITE);
		jTable1.setFont(new Font("Arial", Font.PLAIN, 15));
		jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Nombre", "Telefono", "Vencimiento", "Estado" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[] { java.lang.String.class, java.lang.String.class, java.lang.String.class,
					java.lang.String.class };
			boolean[] canEdit = new boolean[] { false, false, false, false, false };

			public Class<?> getColumnClass(int columnIndex) {

				if (columnIndex >= 4) {
					columnIndex = 3;
				}

				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}

		});

		jScrollPane1.setViewportView(jTable1);

		jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				jTextField1KeyReleased(evt);
			}
		});

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/filtro.png")));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));

		JButton btnNewButton = new JButton("Ver");

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				lblNewLabel_1.setText(Agenda.nuevosVencimientos.getText());

				dispose();

				verLlamadas(1);

			}

			private void verLlamadas(int seguro) {

				try {

					if (seguro > 0) {

						new Llamada(seguro).setVisible(true);

					}

					else {

						new Llamada(0).setVisible(true);

					}

				}

				catch (Exception e) {
					//
				}

			}

		});

		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 16));

		btnNewButton.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/view.png")));

		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Llamada.class.getResource("/imagenes/lupa.png")));
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 16));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING).addGroup(jPanel1Layout
				.createSequentialGroup().addContainerGap()
				.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 752, GroupLayout.PREFERRED_SIZE)
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 64,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
										.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 116,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(jTextField1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 693,
												Short.MAX_VALUE))))
				.addGap(253)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout
				.createSequentialGroup()
				.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(jPanel1Layout.createSequentialGroup().addGap(18)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
								.addGap(90)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
						.addGroup(jPanel1Layout.createSequentialGroup().addGap(29)
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel)))
				.addGap(34).addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(446, Short.MAX_VALUE)));
		jPanel1.setLayout(jPanel1Layout);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(jPanel1,
				GroupLayout.DEFAULT_SIZE, 1026, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		getContentPane().setLayout(layout);

		pack();
		setLocationRelativeTo(null);
	}

	private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTextField1KeyReleased

		try {

			String cari = jTextField1.getText();

			TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);

			jTable1.setRowSorter(tr);

			tr.setRowFilter(RowFilter.regexFilter(cari));

			verTabla();

		} catch (Exception e) {

			try {

				jTextField1.setText("");

				loadData(vencimientos);

				verTabla();

			} catch (Exception e1) {

			}
		}

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {

		try {

			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {

				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}

			}

		}

		catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Llamada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Llamada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Llamada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Llamada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new Llamada(0).setVisible(true);
			}

		});

	}
}
