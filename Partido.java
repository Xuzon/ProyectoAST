
public class Partido {

	int id_partido;
	String equipo_local;
	String equipo_visitante;
	
	public Partido(){}
	
	public Partido(int id_p, String equipo1, String equipo2)
	 {
		id_partido = id_p;
		equipo_local = equipo1;
		equipo_visitante = equipo2;
	 }

	public Partido(int id_p, String participantes)
	 {
		id_partido = id_p;
		equipo_local = participantes.split(" - ")[0];
		equipo_visitante = participantes.split(" - ")[1];
	 }
	
	public int getId_partido() {
		return id_partido;
	}

	public void setId_partido(int id_partido) {
		this.id_partido = id_partido;
	}

	public String getEquipo_local() {
		return equipo_local;
	}

	public void setEquipo_local(String equipo_local) {
		this.equipo_local = equipo_local;
	}

	public String getEquipo_visitante() {
		return equipo_visitante;
	}

	public void setEquipo_visitante(String equipo_visitante) {
		this.equipo_visitante = equipo_visitante;
	}
	
	public String getParticipantes() {
		return equipo_local+" VS "+equipo_visitante;
	}
	
	public String toString() {
		return id_partido+" --> "+equipo_local+" - "+equipo_visitante;
	}

}
