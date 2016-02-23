package com.example.ferreteria;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class Articulo implements Serializable {
	  private String txt_ARTICULO_SKU;     
	  private String txt_DESCRIPCION;
	  private String txt_ART_MARCA;     
	  
	  
	public Articulo( String txt_ARTICULO_SKU,String txt_DESCRIPCION, String txt_ART_MARCA)
	{
		  this.txt_ARTICULO_SKU=txt_ARTICULO_SKU;     
		  this.txt_DESCRIPCION=txt_DESCRIPCION;
		  this.txt_ART_MARCA=txt_ART_MARCA;     
	}
	  
	public Articulo(){}
	
	public String getTxt_ARTICULO_SKU() {
		return txt_ARTICULO_SKU;
	}
	public void setTxt_ARTICULO_SKU(String txt_ARTICULO_SKU) {
		this.txt_ARTICULO_SKU = txt_ARTICULO_SKU;
	}
	public String getTxt_DESCRIPCION() {
		return txt_DESCRIPCION;
	}
	public void setTxt_DESCRIPCION(String txt_DESCRIPCION) {
		this.txt_DESCRIPCION = txt_DESCRIPCION;
	}
	public String getTxt_ART_MARCA() {
		return txt_ART_MARCA;
	}
	public void setTxt_ART_MARCA(String txt_ART_MARCA) {
		this.txt_ART_MARCA = txt_ART_MARCA;
	}
	  


}
