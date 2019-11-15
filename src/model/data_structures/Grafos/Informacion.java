package model.data_structures.Grafos;

public class Informacion {
	private double lati;
	private double longi;
	private int moveID;

	public Informacion(double plat, double plon, int pmove)
	{
		lati = plat;
		longi = plon;
		moveID=pmove;
	}

	public double darLatitud()
	{
		return lati;
	}

	public double darLongitud()
	{
		return longi;
	}

	public int darMovementeID()
	{
		return moveID;
	}

	public boolean marcar() {
		return true;
		// TODO Auto-generated method stub
		
	}

	public boolean estaMarcado() {
		boolean respuesta= true;
		while (marcar()==false)
		{
			respuesta = false;
		}
		return respuesta;
		// TODO Auto-generated method stub
	}

	public Interseccion darConexion() {
		// TODO Auto-generated method stub
		return null;
	}
}