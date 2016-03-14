package jp.co.worksap.rfm.excel.bean;

import java.text.Format;
import java.util.List;

public class ScatteredGraph {
	private List<Double> x;
	private List<Double> y;
	private Format numberFormat;
	private double xMax;
	private double xMin;
	private double yMax;
	private double yMin;
	
	public List<Double> getX() {
		return x;
	}
	public void setX(List<Double> x) {
		this.x = x;
	}
	public List<Double> getY() {
		return y;
	}
	public void setY(List<Double> y) {
		this.y = y;
	}
	public Format getNumberFormat() {
		return numberFormat;
	}
	public void setNumberFormat(Format numberFormat) {
		this.numberFormat = numberFormat;
	}
	public double getxMax() {
		return xMax;
	}
	public void setxMax(double xMax) {
		this.xMax = xMax;
	}
	public double getxMin() {
		return xMin;
	}
	public void setxMin(double xMin) {
		this.xMin = xMin;
	}
	public double getyMax() {
		return yMax;
	}
	public void setyMax(double yMax) {
		this.yMax = yMax;
	}
	public double getyMin() {
		return yMin;
	}
	public void setyMin(double yMin) {
		this.yMin = yMin;
	}
}
