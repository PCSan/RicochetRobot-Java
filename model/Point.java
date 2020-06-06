package model;

/*****  Reimplementation of awt.Point to avoid dependency **/
public class Point{
	private int x,y;
	public Point(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public int getX(){ return x;}
	public int getY(){ return y;}
	@Override
	public int hashCode() {
	    long bits = java.lang.Double.doubleToLongBits(getX());
	    bits ^= java.lang.Double.doubleToLongBits(getY()) * 31;
	    return (((int) bits) ^ ((int) (bits >> 32)));
	}
	@Override
	public boolean equals(Object o){
		if (o instanceof Point){
			Point p = (Point) o;
			return p.x==x && p.y==y;
		}
		return false ;
	}
}
