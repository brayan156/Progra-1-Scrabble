package Circular_Letras;

public class Tuple{ 
	public  String x; 
	public  final int y;
	
	public Tuple(String x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getY() {
		return this.y;
	}
	
	public String getX() {
		return this.x;
	}

	public void setX(String x) {
		this.x = x;
	}

}