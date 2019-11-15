package model.data_structures;

public class Properties {
	
	private String cartodb_id;
	private int scacodigo;
	private int scatipo;
	private String scanombre;
	private double shape_leng;
	private double shape_area;
	private int MOVEMENT_ID;
	private String DISPLAY_NAME;
	
	public Properties(String pcartodb_id, int pscacodigo, int pscatipo, String pscanombre, double pshape_leng,	double pshape_area, int pMOVEMENT_ID, String pDISPLAY_NAME)
	{
		cartodb_id = pcartodb_id;
		scacodigo = pscacodigo;
		scatipo = pscatipo;
		scanombre = pscanombre;
		shape_leng = pshape_leng;
		shape_area = pshape_area;
		MOVEMENT_ID = pMOVEMENT_ID;
		DISPLAY_NAME = pDISPLAY_NAME;
	}
	/**---------------------------------------
	 * Getters & setters
	 * ---------------------------------------
	 */
	
	public String darDISPLAY_NAME() {
		return DISPLAY_NAME;
	}
	public void setDISPLAY_NAME(String dISPLAY_NAME) {
		DISPLAY_NAME = dISPLAY_NAME;
	}
	public int darMOVEMENT_ID() {
		return MOVEMENT_ID;
	}
	public void setMOVEMENT_ID(int mOVEMENT_ID) {
		MOVEMENT_ID = mOVEMENT_ID;
	}
	public double darShape_area() {
		return shape_area;
	}
	public void setShape_area(double shape_area) {
		this.shape_area = shape_area;
	}
	public double darShape_leng() {
		return shape_leng;
	}
	public void setShape_leng(double shape_leng) {
		this.shape_leng = shape_leng;
	}
	public String darScanombre() {
		return scanombre;
	}
	public void setScanombre(String scanombre) {
		this.scanombre = scanombre;
	}
	public int darScatipo() {
		return scatipo;
	}
	public void setScatipo(int scatipo) {
		this.scatipo = scatipo;
	}
	public int darScacodigo() {
		return scacodigo;
	}
	public void setScacodigo(int scacodigo) {
		this.scacodigo = scacodigo;
	}
	public String darCartodb_id() {
		return cartodb_id;
	}
	public void setCartodb_id(String cartodb_id) {
		this.cartodb_id = cartodb_id;
	}

}
