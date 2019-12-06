package model.data_structures.Grafos;

public class Arco<K> {

	private int idV1;
	private int idV2;
	private double distancia;
	private double tiempo;
	private double velocidad;
	private boolean marked;
	
	public Arco(int pIDOrigen, int pIDDestino, double pDistancia, double pTiempo, boolean pMarked) {
		idV1 = pIDOrigen;
		idV2 = pIDDestino;
		distancia = pDistancia;
		tiempo = pTiempo;
		actualizarVelocidad();
		marked = pMarked;
	}

	public void marcar()
	{
		marked = true;
	}
	public void desmarcar()
	{
		marked = false;
	}
	
	public boolean isMarked()
	{
		return marked;
	}
	public double darDistancia() 
	{
		return distancia;
	}

	public void setDistancia(double distancia) 
	{
		this.distancia = distancia;
		actualizarVelocidad();
	}

	public double darTiempo() 
	{
		return tiempo;
	}

	public void setTiempo(double tiempo) 
	{
		this.tiempo = tiempo;
		actualizarVelocidad();
	}

	public double darVelocidad() 
	{
		return velocidad;
	}

	public void actualizarVelocidad() 
	{
		if(distancia!=-1&&tiempo!=-1&&distancia!=0&&tiempo!=0)
		{
			velocidad= distancia/tiempo;
		}
		
	}
	
	public int darIdDestino()
	{
		return idV2;
	}
	
	
}