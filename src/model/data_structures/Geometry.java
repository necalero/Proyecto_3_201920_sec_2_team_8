package model.data_structures;

public class Geometry {
	private String type;
	private double[][] coordinates;
	
	public Geometry(String pType, String[] pCoordinates)
	{
		setType(pType);
		coordinates = new double[2][pCoordinates.length/2];
		for(int i = 0; i < pCoordinates.length; i++)
		{
			if( i%2 == 0)
			{
				coordinates[0][(int)i/2] = Double.parseDouble(pCoordinates[i]);
			}
			else
			{
				coordinates[1][(int)i/2] = Double.parseDouble(pCoordinates[i]);
			}
		}
		
	}
	
	public String darType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public double[] darCoordinates(short numeroColumna) {
		double[] aDevolver = new double[2];
		aDevolver[0] = coordinates[0][numeroColumna];
		aDevolver[1] = coordinates[1][numeroColumna];
		return aDevolver;
	}
	public int darCoordinatesSize()
	{
		return (coordinates.length * coordinates[0].length);
	}

	public void setCoordinates(double[][] coordinates) {
		this.coordinates = coordinates;
	}
	
}
