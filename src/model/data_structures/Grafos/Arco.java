package model.data_structures.Grafos;

public class Arco<K> {

	private Vertice v1;
	private Vertice v2;
	private double distancia;
	private double tiempo;
	private double velocidad;
	private boolean marked;
	public Arco(Vertice pOrigen, Vertice pDestino, double pDistancia, double pTiempo) {
		v1 = pOrigen;
		v2 = pDestino;
		distancia = pDistancia;
		tiempo = pTiempo;
		actualizarVelocidad();
		marked = false;
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
	
	public Vertice darDestino()
	{
		return v2;
	}
	
	
}