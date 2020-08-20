package com.jsfdaocrudodbconnection.operations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.sql.SQLException;


import javax.faces.context.FacesContext;

import com.jsfdaocrud.model.ProductoBean;
/*import com.jsfdaocrud1.operations.Exception;
import com.jsfdaocrud1.operations.Map;
import com.jsfdaocrud1.operations.Object;
import com.jsfdaocrud1.operations.SQLException;*/


public class DatabaseOperation 
{
	//Crear los objetos 
	private static Connection conn;
	private static Statement stmnt;
	private static ResultSet rs;
	private static PreparedStatement pstmnt;
	
	//Metodo para realizar la conexión a la base de datos
	
	public static Connection getConnection()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			String db_url = "jdbc:mysql://localhost:3306/elmacho?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String db_user = "root";
			String db_pass = "rjop2795";
			
			conn = DriverManager.getConnection(db_url, db_user, db_pass);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return conn;
		
	}
		
		public static ArrayList ListaProductosFromDB()
		{
			ArrayList ListaProductos = new ArrayList();
			try
			{
				stmnt = getConnection().createStatement();
				rs = stmnt.executeQuery("select*from productos");
				
				while(rs.next())
				{
					ProductoBean objProducto = new ProductoBean();
					objProducto.setIdProductoBean(rs.getInt("IdProducto"));
					objProducto.setNombreProductoBean(rs.getString("NombreProducto"));
					objProducto.setPrecioProductoBean(rs.getDouble("PrecioProducto"));
					
					ListaProductos.add(objProducto);
					
			    }
				System.out.println("Total de Productos Extraidos: " + ListaProductos.size());
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return ListaProductos;
		}
		public static String guardarDetallesProductoBeanInDB(ProductoBean objNuevoProducto)
		{
			String resultadosNavegacion = "";
			int resultadoGuardar=0;
			try
			{
				pstmnt=getConnection().prepareStatement("insert into productos(IdProducto,NombreProducto,PrecioProducto) values(?,?,?)");
				pstmnt.setInt(1, objNuevoProducto.getIdProductoBean());
				pstmnt.setString(2, objNuevoProducto.getNombreProductoBean());
				pstmnt.setDouble(3, objNuevoProducto.getPrecioProductoBean());
				resultadoGuardar=pstmnt.executeUpdate();
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			if(resultadoGuardar!=0)
			{
				resultadosNavegacion="index.xhtml?faces-redirect=true";
			}
			else
			{
				resultadosNavegacion="agregarproduct.xhtml?faces-redirect=true";
				
			}
			
			return resultadosNavegacion;
		}
		
		public static String editarRegistroProductoInDB(int idProductoBean)
		{
			ProductoBean editarRegistro=new ProductoBean();
			System.out.println("editarRegistroProductoInDB(): Producto Id:" +idProductoBean);
			/*Configurar los respectivos detalles del producto en la sesión*/
			Map<String, Object> objMapSession=FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			try
			{
				stmnt = getConnection().createStatement();
				rs = stmnt.executeQuery("select * from Productos where IdProducto="+idProductoBean);
				if(rs != null)
				{
					rs.next();
					editarRegistro.setIdProductoBean(rs.getInt("IdProducto"));
					editarRegistro.setNombreProductoBean(rs.getString("NombreProducto"));
					editarRegistro.setPrecioProductoBean(rs.getDouble("PrecioProducto"));
				}
				
				objMapSession.put("objEditarRegistro", editarRegistro);
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return "editarProducto.xhtml?faces-redirect=true";
			
			
		}
		
		public static String actualizarDetallesProductoInDB(ProductoBean objActualizarProducto)
		{
			try
			{
				pstmnt = getConnection().prepareStatement("update Productos set IdProducto=?, NombreProducto=?, PrecioProducto=? where IdProducto=?");
				pstmnt.setInt(1, objActualizarProducto.getIdProductoBean());
				pstmnt.setString(2, objActualizarProducto.getNombreProductoBean());
				pstmnt.setDouble(3, objActualizarProducto.getPrecioProductoBean());
				pstmnt.setInt(4, objActualizarProducto.getIdProductoBean());
				pstmnt.executeUpdate();
				conn.close();
				
				
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
			return "index.xhtml?faces-redirect=true";
		}
		
		public static String borrarRegistroProductoInDB(int idProductoBean)
		{
			System.out.println("borrarRegistroProductoInDB(): IdProducto:" +idProductoBean);
			
			try
			{
				pstmnt = getConnection().prepareStatement("delete from Productos where IdProducto=?");
				pstmnt.setInt(1, idProductoBean);
				pstmnt.executeUpdate();
				conn.close();
				
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
			return "index.xhtml?faces-redirect=true";
		}
	}
	
	

