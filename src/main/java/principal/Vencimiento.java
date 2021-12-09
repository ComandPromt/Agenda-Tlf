package principal;

import java.util.Date;
import java.util.LinkedList;

import utils.FormatoTabla;
import utils.Metodos;

public class Vencimiento {

	static LinkedList<String> arrayList1 = new LinkedList<String>();

	static LinkedList<String> arrayList2 = new LinkedList<String>();

	public static LinkedList<String> contactosVencimientos = new LinkedList<String>();

	static LinkedList<Integer> indiceDeceso = new LinkedList<Integer>();
	static LinkedList<Integer> indiceVida = new LinkedList<Integer>();
	static LinkedList<Integer> indiceHogar = new LinkedList<Integer>();
	static LinkedList<Integer> indiceComercio = new LinkedList<Integer>();
	static LinkedList<Integer> indiceComunidad = new LinkedList<Integer>();
	static LinkedList<Integer> indiceCoche = new LinkedList<Integer>();
	static LinkedList<Integer> vencimientos = new LinkedList<Integer>();

	static LinkedList<Integer> vencimientosVerdes = new LinkedList<Integer>();

	public static LinkedList<Integer> getIndiceHogar() {
		return indiceHogar;
	}

	public static LinkedList<Integer> getIndiceComercio() {
		return indiceComercio;
	}

	public static LinkedList<Integer> getIndiceComunidad() {
		return indiceComunidad;
	}

	private static String nuevoVencimiento = "Por llamar: ";

	public static LinkedList<Integer> getIndiceCoche() {
		return indiceCoche;
	}

	public static LinkedList<String> colores = new LinkedList<String>();

	int contador = 0;

	public static LinkedList<Integer> getIndiceVida() {
		return indiceVida;
	}

	public static LinkedList<Integer> getIndiceDeceso() {
		return indiceDeceso;
	}

	public static void actualizarTituloVencimientos() {

		try {

			int numDeceso = Agenda.vencimientosDecesos.size();

			if (numDeceso > 0) {

				Agenda.nuevosVencimientos.setText(nuevoVencimiento);

				if (!Agenda.vencimientosDecesos.isEmpty() && numDeceso > 0) {

					Agenda.nuevosVencimientos.setText(Agenda.nuevosVencimientos.getText() + numDeceso);

				}

				if (Agenda.nuevosVencimientos.getText().indexOf(" || ") == Agenda.nuevosVencimientos.getText().length()
						- 4) {

					Agenda.nuevosVencimientos.setText(Agenda.nuevosVencimientos.getText().substring(0,
							Agenda.nuevosVencimientos.getText().lastIndexOf(" || ")));

				}

			}

			if (numDeceso == 0) {

				Agenda.nuevosVencimientos.setText("");
			}

			String textoVencimientos = Agenda.nuevosVencimientos.getText().trim();

			if (textoVencimientos.substring(textoVencimientos.length() - 2, textoVencimientos.length()).equals("||")) {

				Agenda.nuevosVencimientos.setText(textoVencimientos.substring(0, textoVencimientos.lastIndexOf("||")));

			}

		}

		catch (Exception e) {

			//

		}

	}

	public static void verTablaVencimientos() {

		try {

			FormatoTabla.FechasVencimientosRojos.clear();

			FormatoTabla.FechasVencimientosVerdes.clear();

			FormatoTabla.FechasVencimientosAmarillos.clear();

			FormatoTabla.FechasVencimientosNaranjas.clear();

			vencimientos.clear();

			indiceDeceso.clear();

			indiceVida.clear();

			Agenda.vencimientosDecesos.clear();

			Date fecha = new Date();

			String hoy = Metodos.convertirFecha(fecha.toString(), false);

			indiceDeceso = buscarColoresVencimientos(hoy, Agenda.fechaDecesos);

			if (indiceDeceso.size() > 0) {
				ponerVencimientos(1);
			}

			if (indiceVida.size() > 0) {
				ponerVencimientos(2);
			}

			if (indiceHogar.size() > 0) {
				ponerVencimientos(3);
			}

			if (indiceCoche.size() > 0) {
				ponerVencimientos(4);
			}

			if (indiceComercio.size() > 0) {
				ponerVencimientos(5);
			}

			if (indiceComunidad.size() > 0) {
				ponerVencimientos(6);
			}

			actualizarTituloVencimientos();

		}

		catch (Exception e) {
			//
		}
	}

	public static LinkedList<Integer> buscarColoresVencimientos(String fecha, LinkedList<String> tipoVencimiento) {

		LinkedList<Integer> todosLosVencimientos = new LinkedList<Integer>();

		LinkedList<Integer> indiceVtos = new LinkedList<Integer>();

		todosLosVencimientos = Metodos.buscarVencimientosRojos(tipoVencimiento, Metodos.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = Metodos.buscarVencimientosNaranja(tipoVencimiento, Metodos.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = Metodos.buscarVencimientosAmarillo(tipoVencimiento, fecha);

		for (int i = 0; i < todosLosVencimientos.size(); i++) {

			indiceVtos.add(todosLosVencimientos.get(i));
		}

		todosLosVencimientos = Metodos.buscarVencimientosVerdes(tipoVencimiento, Metodos.convertirFecha(fecha, false));

		for (int i = 0; i < todosLosVencimientos.size(); i++) {
			indiceVtos.add(todosLosVencimientos.get(i));
		}

		return indiceVtos;

	}

	static void ponerVencimientos(int tipo) {

		arrayList1.clear();

		LinkedList<Integer> indices = new LinkedList<Integer>();

		String indicevtoDeceso;

		String fechaVto = "";

		int indice1 = 0;

		int indice2 = 0;

		Agenda.vencimientosDecesos.clear();

		indices = indiceDeceso;

		for (int i = 0; i < indices.size(); i++) {

			if (indices.get(i).toString() != null) {

				if (indices.get(i) < Agenda.vencimientos.size()) {

					indicevtoDeceso = Agenda.vencimientos.get(indices.get(i));

					if (indice1 > -1 && indice2 > -1) {

						indice1 += 2;

						fechaVto = indicevtoDeceso.trim();
					}

					Agenda.vencimientosDecesos.add(fechaVto);

				}

			}

		}
	}

}
