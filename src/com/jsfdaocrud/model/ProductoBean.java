package com.jsfdaocrud.model;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.jsfdaocrud.model.ProductoBean;
import com.jsfdaocrudodbconnection.operations.DatabaseOperation;
@ManagedBean (name="productoBean")
@RequestScoped
public class ProductoBean 
{
	private int idProductoBean;
	private String nombreProductoBean;
	private double precioProductoBean;
	
	public ArrayList ListaProductoFromDB;
	
	public ProductoBean()
	{
		this.idProductoBean=0;
		this.nombreProductoBean="";
		this.precioProductoBean=0.0;
		
	}
	
	public int getIdProductoBean()
	{
		return idProductoBean;
	}
	public void setIdProductoBean(int idProductoBean)
	{
		this.idProductoBean=idProductoBean;
	}
	
	public String getNombreProductoBean()
	{
		return nombreProductoBean;
	}
	public void setNombreProductoBean(String nombreProductoBean)
	{
		this.nombreProductoBean=nombreProductoBean;
	}
	
	public double getPrecioProductoBean()
	{
		return precioProductoBean;
	}
	public void setPrecioProductoBean(double precioProductoBean)
	{
		this.precioProductoBean=precioProductoBean;
	}
	
	@PostConstruct
	public void init()
	{
		ListaProductoFromDB = DatabaseOperation.ListaProductosFromDB();
	}
	public ArrayList ListaProductos()
	{
		return ListaProductoFromDB;
	}
	public String guardarDetallesProductos(ProductoBean objNuevoProducto)
	{
		return DatabaseOperation.guardarDetallesProductoBeanInDB(objNuevoProducto);
	}
	public String editarRegistroProducto(int idProductoBean)
	{
		return DatabaseOperation.editarRegistroProductoInDB(idProductoBean);
	}
	
	public String actualizarDetallesProducto(ProductoBean objActualizarProducto)
	{
		return DatabaseOperation.actualizarDetallesProductoInDB(objActualizarProducto);
	}
	
	public String borrarRegistroProducto(int idProductoBean)
	{
		return DatabaseOperation.borrarRegistroProductoInDB(idProductoBean); 
	
	}
	
	
}
