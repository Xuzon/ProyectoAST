import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Goten {
	
	public int comprobarApuestaPartido(int id_a){
		return -1;
	}
	public int comprobarApuestaPichichi(int id_a){
		return -1;
	}
	public int realizarApuestaPartido(int id_p, int goles_e1, int goles_e2){
		return -1;
	}
	public int realizarApuestaPichichi(int id_j){
		return -1;
	}
	
	class hiloComprobacion implements Runnable{	
		public void run(){
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			int id = -1;
			try{
			while(true){
				System.out.println("Introduzca id de partido a terminar, pulse 999 para acabar competición");
				id = Integer.parseInt(br.readLine());
			}
			}catch(IOException io){
				
			}
		}
	}
}