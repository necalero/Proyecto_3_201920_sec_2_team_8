package model.data_structures;

public class ZonaUber <T extends Comparable<Integer>>
{
	/**---------------------------------------
	 * Atributos
	 * ---------------------------------------
	 */
	private String type;
	private Geometry geometry;
	private Properties properties;
	
	
	/**---------------------------------------
	 * Constructor
	 * ---------------------------------------
	 */
	public ZonaUber(String type, Geometry geometry, Properties properties)
	{
		this.type = type;
		this.setGeometry(geometry);
		this.setProperties(properties);
		
	}
	
	/**---------------------------------------
	 * Getters & setters
	 * ---------------------------------------
	 */

	public Properties darProperties() {
		return properties;
	}


	public void setProperties(Properties properties) {
		this.properties = properties;
	}


	public Geometry darGeometry() {
		return geometry;
	}


	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	
	
	
	
}
