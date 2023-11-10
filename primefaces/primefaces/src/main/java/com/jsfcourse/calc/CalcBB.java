package com.jsfcourse.calc;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named
@RequestScoped
//@SessionScoped
public class CalcBB {
	private String x;
	private String y;
	private String c;
	private Double result;

	@Inject
	FacesContext ctx;

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public boolean doTheMath() {
		try {
			int x = Integer.parseInt(this.x); //kwota
			int y = Integer.parseInt(this.y); //oprocentowanie
			int c = Integer.parseInt(this.c); //lata
			
			int o = y * 1200;		//dzielmy przez 1200 ponieważ potrzebujemy oprocentowania w  miesiącach
			int z = c * 12;         // Przeliczenie liczby okresów płatności na miesiące
			result = (x * o * Math.pow(1 + o, z)) / (Math.pow(1 + o, z) - 1);

			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja wykonana poprawnie", null));
			return true;
		} catch (Exception e) {
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas przetwarzania parametrów", null));
			return false;
		}
	}

	// Go to "showresult" if ok
	public String calc() {
		if (doTheMath()) {
			return "showresult";
		}
		return null;
	}

	// Put result in messages on AJAX call
	public String calc_AJAX() {
		if (doTheMath()) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rata kredytu wynosi: " + result, null));
		}
		return null;
	}

	public String info() {
		return "info";
	}
}
