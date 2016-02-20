package com.example.ferreteria;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pedidos {
	
	private int cliente_id;
	private String sku;
	private int cantidad;
	private String fecha="24/02/2016";
	private int flete=0;
	private int id;
	private Connection con;
	  
	
	public Pedidos (Connection con)
	{
		setCon(con);
	}
	 
	public Pedidos ()
		{}
	 public void add ()
	   {
	       try
	       {
	           Statement stmt=con.createStatement();
	           String query="insert into Ferreteria.T003_PEDIDOS ";
	           query=query+"values('"+ getCliente_id()+"','"+getSku() +"'," +getCantidad()+",'"+getFecha()+"',"+getFlete()+","+getId() +")";
	           
	          // ResultSet rs = null;
	         //  rs=stmt.executeQuery(query);
	           stmt.executeUpdate(query);
	       }catch (SQLException ex)
	                {
	                    System.out.println("Error..." + ex.getMessage());
	                }
	   }
	 
	 
	 
	 
	 
	public int getCliente_id() {
		return cliente_id;
	}
	public void setCliente_id(int cliente_id) {
		this.cliente_id = cliente_id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getFlete() {
		return flete;
	}
	public void setFlete(int flete) {
		this.flete = flete;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Connection getCon() {
		return con;
	}
	public void setCon(Connection con) {
		this.con = con;
	}


	@Override
	public String toString() {
		return "Pedidos [cliente_id=" + cliente_id + ", sku=" + sku
				+ ", cantidad=" + cantidad + ", fecha=" + fecha + ", flete="
				+ flete + ", id=" + id + ", con=" + con + ", getCliente_id()="
				+ getCliente_id() + ", getSku()=" + getSku()
				+ ", getCantidad()=" + getCantidad() + ", getFecha()="
				+ getFecha() + ", getFlete()=" + getFlete() + ", getId()="
				+ getId() + ", getCon()=" + getCon() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}




	public Pedidos(int cliente_id, String sku, int cantidad, String fecha,
				   int flete, int id) {
		super();
		this.cliente_id = cliente_id;
		this.sku = sku;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.flete = flete;
		this.id = id;

	}


}
