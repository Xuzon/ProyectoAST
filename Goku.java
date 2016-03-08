import java.util.ArrayList;
import java.util.Map;


public interface Goku {
	
	public int apostarPartido(int id_p, int goles_e1, int goles_e2, double importe, int tarjeta, String f_cad);
	public int apostarPichichi(int id_j, double importe, int tarjeta, String f_cad);
	public boolean cobrarApuesta(int id_a, int tarjeta, String f_cad);
	public ArrayList<String> listarEquipos();
	public Map<Integer, String> listarJugadoresEquipo(String equipo);
	public Map<Integer, String> listarPartidos(String equipo);
	
}
