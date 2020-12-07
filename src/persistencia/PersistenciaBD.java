/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import objetoNegocio.Cajero;
import objetoNegocio.Categoria;
import objetoNegocio.Cliente;
import objetoNegocio.DetalleVenta;
import objetoNegocio.Producto;
import objetoNegocio.Venta;
import objetosServicio_00000216618.Fecha;
import proyectofinal.FmrVenta;

/**
 *
 * @author f_aco
 */
public class PersistenciaBD {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private int respuesta = 0;
    public static Cajero cajero;

    public Connection conectar() {

        String url = "jdbc:sqlite:src\\basededatos\\PuntoVenta.db";
        try {

            System.out.println("Conectando a la base de datos");
            con = DriverManager.getConnection(url);
            if (con != null) {
                System.out.println("Conexion con exito!");
                return con;
            } else {
                System.out.println("Error: Con es invalido");
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;

    }

    public Cajero agregarCajero(String nombre, String turno, String password) {
        String sql = "INSERT INTO Cajero(Nombre,Turno,Password) VALUES(?,?,?)";
        Cajero cj;

        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, turno);
            ps.setString(3, password);

            ps.executeUpdate();

            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        cj = new Cajero(nombre, turno, password);

        return cj;
    }

    public void validar(String nombre, String password, JFrame cuadro) throws SQLException {
        Cajero cajero = new Cajero();

        if (nombre.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(cuadro, "Debe Ingresar Datos En las Cajas de Texto");
        } else {
            cajero = validarCajero(nombre, password);
            if (cajero.getNombre() != null && cajero.getPassword() != null) {
                PersistenciaBD.cajero = cajero;
                FmrVenta fv = new FmrVenta();
                fv.setVisible(true);
                cuadro.dispose();
            } else {
                JOptionPane.showMessageDialog(cuadro, "Debe de ingresar un usuario valido");
            }
        }
    }

    public void crearCajero(String nombre, String turno, String password, JFrame cuadro) throws SQLException {
        Cajero cajero = new Cajero();

        String passPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
        Pattern p = java.util.regex.Pattern.compile(passPattern);

        Matcher m = p.matcher(password);

        if (m.matches()) {
            cajero = agregarCajero(nombre, turno, password);
            if (cajero.getNombre() != null && cajero.getPassword() != null) {
                PersistenciaBD.cajero = cajero;
                FmrVenta fv = new FmrVenta();
                fv.setVisible(true);
                cuadro.dispose();

            } else {
                JOptionPane.showMessageDialog(cuadro, "Los datos no coinciden");
            }

        } else {
            JOptionPane.showMessageDialog(cuadro, "No contiene los datos requeridos");

            if (nombre.equals("") || password.equals("") || turno.equals("")) {
                JOptionPane.showMessageDialog(cuadro, "Debe Ingresar Datos En las Cajas de Texto");
            }

        }
    }

    public Cajero validarCajero(String nombre, String pass) throws SQLException {
        Cajero cj = new Cajero();
        String sql = "select * from Cajero where Password =? and Nombre=?";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, pass);
            ps.setString(2, nombre);
            rs = ps.executeQuery();
            while (rs.next()) {
                cj.setDNI(rs.getString(1));
                cj.setNombre(rs.getString(2));
                cj.setTurno(rs.getString(3));
                cj.setPassword(rs.getString(4));

            }
            System.out.println("Proceso ejecutado con exito");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        con.close();
        return cj;
    }

    public ArrayList<Cliente> listarCliente() {
        ArrayList<Cliente> lista = new ArrayList();
        String sql = "select * from Cliente";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setDNI(rs.getString(1));
                c.setNombre(rs.getString(2));
                c.setDireccion(rs.getString(3));
                lista.add(c);
            }
            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public void agregarCliente(Cliente c) {
        String sql = "insert into Cliente(DNI,Nombre,Direccion) values(?,?,?)";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getDNI());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getDireccion());

            ps.executeUpdate();
            ps.close();
            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void actualizarCliente(Cliente c) {
        String sql = "UPDATE Cliente SET Nombre=?, Direccion=? WHERE DNI=?";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDireccion());
            ps.setString(3, c.getDNI());

            ps.executeUpdate();
            ps.close();
            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void eliminarCliente(String DNI) {
        String sql = "DELETE FROM Cliente WHERE DNI=?";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, DNI);
            ps.executeUpdate();
            ps.close();
            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Categoria> listarCategoria() {
        ArrayList<Categoria> lista = new ArrayList();
        String sql = "select * from Categoria";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setID(rs.getString(1));
                c.setNombre(rs.getString(2));
                lista.add(c);
            }
            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public void agregarCategoria(Categoria c) {
        String sql = "insert into Categoria(ID,Nombre) values(?,?)";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getID());
            ps.setString(2, c.getNombre());

            ps.executeUpdate();
            ps.close();
            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void actualizarCategoria(Categoria c) {
        String sql = "UPDATE Categoria SET ID=?, Nombre=? WHERE ID=?";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getID());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getID());
            ps.executeUpdate();
            ps.close();
            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void eliminarCategoria(String ID) {
        String sql = "DELETE FROM Categoria WHERE ID=?";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, ID);
            ps.executeUpdate();
            ps.close();
            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Cajero> listarCajero() {
        ArrayList<Cajero> lista = new ArrayList();
        String sql = "select DNI,Nombre,Turno from Cajero";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cajero c = new Cajero();
                c.setDNI(rs.getString(1));
                c.setNombre(rs.getString(2));
                c.setTurno(rs.getString(3));
                lista.add(c);
            }
            System.out.println("Proceso ejecutado con exito");
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public void actualizarCajero(Cajero c) {
        String sql = "UPDATE Cajero SET Nombre=?, Turno=?, Password=? WHERE DNI=?";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTurno());
            ps.setString(3, c.getPassword());
            ps.setString(4, c.getDNI());

            ps.executeUpdate();
            ps.close();
            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void eliminarCajero(String DNI) {
        String sql = "DELETE FROM Cajero WHERE DNI=?";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, DNI);
            ps.executeUpdate();
            ps.close();
            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Producto> listarProducto() {
        ArrayList<Producto> lista = new ArrayList();
        String sql = "select * from Producto";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setID(rs.getString(1));
                p.setNombre(rs.getString(2));
                p.setStock(Integer.parseInt(rs.getString(3)));
                p.setPrecio(Float.parseFloat(rs.getString(4)));
                p.setCategoria(buscarCategoria(rs.getString(5)));
                lista.add(p);
            }
            rs.close();
            System.out.println("Proceso ejecutado con exito");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public void agregarProducto(Producto p) {
        String sql = "insert into Producto(ID,Nombre,Precio,Stock,Categoria) values(?,?,?,?,?)";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getID());
            ps.setString(2, p.getNombre());
            ps.setFloat(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getCategoria().getID());

            ps.executeUpdate();
            ps.close();
            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void actualizarProducto(Producto p) {
        String sql = "UPDATE Producto SET ID=?, Nombre=?, Stock=?, Precio=?, Categoria=? WHERE ID=?";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getID());
            ps.setString(2, p.getNombre());
            ps.setInt(3, p.getStock());
            ps.setFloat(4, p.getPrecio());
            ps.setString(5, p.getCategoria().getID());
            ps.setObject(6, p.getID());
            ps.executeUpdate();
            ps.close();
            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void eliminarProducto(String ID) {
        String sql = "DELETE FROM Producto WHERE ID=?";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, ID);
            ps.executeUpdate();
            ps.close();
            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Categoria> llenarComboboxCategoria() {
        ArrayList<Categoria> lista = new ArrayList();
        String sql = "SELECT * FROM Categoria";
        String ID, nombre;
        Categoria categoria;
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            System.out.println("Proceso ejecutado con exito");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            while (rs.next()) {
                ID = rs.getString(1);
                nombre = rs.getString(2);
                categoria = new Categoria(ID, nombre);
                lista.add(categoria);
            }
            System.out.println("Proceso ejecutado con exito");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

    public Categoria buscarCategoria(String ID) {
        String sql = "SELECT * FROM Categoria WHERE ID=?";
        Categoria c = new Categoria();
        ResultSet resultado;
        PreparedStatement statement;
        Connection conexion;
        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);
            statement.setString(1, ID);
            resultado = statement.executeQuery();
            while (resultado.next()) {

                c.setID(resultado.getString(1));
                c.setNombre(resultado.getString(2));
            }
            resultado.close();
            System.out.println("Proceso ejecutado con exito");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public Cliente buscarCliente(String DNI) {
        String sql = "SELECT * FROM Cliente WHERE DNI=?";
        Cliente c = new Cliente();
        ResultSet resultado;
        PreparedStatement statement;
        Connection conexion;
        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);
            statement.setString(1, DNI);
            resultado = statement.executeQuery();
            while (resultado.next()) {

                c.setDNI(resultado.getString(1));
                c.setNombre(resultado.getString(2));
                c.setDireccion(resultado.getString(3));
            }
            resultado.close();
            System.out.println("Proceso ejecutado con exito");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public Producto buscarProducto(String ID) {
        String sql = "SELECT * FROM Producto WHERE ID=?";
        Producto p = new Producto();
        Categoria cat = new Categoria();
        ResultSet resultado;
        PreparedStatement statement;
        Connection conexion;
        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);
            statement.setString(1, ID);
            resultado = statement.executeQuery();
            while (resultado.next()) {

                p.setID(resultado.getString(1));
                p.setNombre(resultado.getString(2));
                p.setStock(resultado.getInt(3));
                p.setPrecio(resultado.getFloat(4));
                p.setCategoria(buscarCategoria(resultado.getString(5)));
            }
            resultado.close();
            System.out.println("Proceso ejecutado con exito");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    public String maximaVenta() {
        String id = null;
        String sql = "Select max (ID) from Venta";
        ResultSet resultado;
        PreparedStatement statement;
        Connection conexion;
        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);
            resultado = statement.executeQuery();
            while (resultado.next()) {
                id = resultado.getString(1);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return id;

    }

    public void GuardarVenta(Venta v) {
        ResultSet resultado = null;
        PreparedStatement statement;
        Connection conexion;

        // Venta venta = new Venta();
        String sql = "insert into Venta(Total, Fecha, Cajero_DNI, Cliente_DNI)values(?,?,?,?)";
        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);
            //statement.setString(1, venta.getID());
            statement.setFloat(1, v.getTotal());
            statement.setString(2, v.getFecha().toString());
            statement.setString(3, v.getCajero().getDNI());
            statement.setString(4, v.getCliente().getDNI());

            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void GuardarDetalleVenta(DetalleVenta dv) {
        ResultSet resultado = null;
        PreparedStatement statement;
        Connection conexion;

        String sql = "insert into DetalleVenta(ID_Producto, ID_Venta, Cantidad, Precio)values(?,?,?,?)";
        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);
            //statement.setString(1, dv.getID());
            statement.setString(1, dv.getProducto().getID());
            statement.setString(2, dv.getVenta().getID());
            statement.setInt(3, dv.getCantidad());
            statement.setFloat(4, dv.getPrecio());

            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void inventariar(int cantidad, String ID) {
        String sql = "UPDATE Producto SET Stock=? WHERE ID=?";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ps.setString(2, ID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Venta> listarVentas() {
        ArrayList<Venta> lista = new ArrayList();
        String sql = "select * from Venta";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venta v = new Venta();
                v.setID(rs.getString(1));
                v.setTotal(rs.getFloat(2));
                v.setFecha(new Fecha(rs.getString(3)));
                v.setCajero(buscarCajero(rs.getString(4)));
                v.setCliente(buscarCliente(rs.getString(5)));
                lista.add(v);
            }
            rs.close();
            System.out.println("Proceso ejecutado con exito");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }
    public ArrayList<DetalleVenta> listarDetalleVentas() {
        ArrayList<DetalleVenta> lista = new ArrayList();
        String sql = "select * from DetalleVenta";
        try {
            con = conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DetalleVenta dv = new DetalleVenta();
                dv.setID(rs.getString(1));
                dv.setProducto(buscarProducto(rs.getString(2)));
                dv.setVenta(buscarVenta(rs.getString(3)));
                dv.setCantidad(rs.getInt(4));
                dv.setPrecio(rs.getFloat(5));
                lista.add(dv);
            }
            rs.close();
            System.out.println("Proceso ejecutado con exito");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public Cajero buscarCajero(String DNI) {
        String sql = "SELECT * FROM Cajero WHERE DNI=?";
        Cajero c = new Cajero();
        ResultSet resultado;
        PreparedStatement statement;
        Connection conexion;
        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);
            statement.setString(1, DNI);
            resultado = statement.executeQuery();
            while (resultado.next()) {

                c.setDNI(resultado.getString(1));
                c.setNombre(resultado.getString(2));
                c.setTurno(resultado.getString(3));
            }
            resultado.close();
            System.out.println("Proceso ejecutado con exito");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
    public Venta buscarVenta(String ID) {
        String sql = "SELECT * FROM Venta WHERE ID=?";
        Venta v = new Venta();
        ResultSet resultado;
        PreparedStatement statement;
        Connection conexion;
        try {
            conexion = conectar();
            statement = conexion.prepareStatement(sql);
            statement.setString(1, ID);
            resultado = statement.executeQuery();
            while (resultado.next()) {
                v.setID(resultado.getString(1));
                v.setTotal(resultado.getFloat(2));
                v.setFecha(new Fecha(resultado.getString(3)));
                v.setCajero(buscarCajero(resultado.getString(4)));
                v.setCliente(buscarCliente(resultado.getString(5)));
            }
            resultado.close();
            System.out.println("Proceso ejecutado con exito");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return v;
    }

}
