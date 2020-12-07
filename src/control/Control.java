/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.SQLException;
import java.util.ArrayList;
import objetoNegocio.Cajero;
import objetoNegocio.Categoria;
import objetoNegocio.Cliente;
import objetoNegocio.DetalleVenta;
import objetoNegocio.Producto;
import objetoNegocio.Venta;
import persistencia.PersistenciaBD;
import proyectofinal.DlgLogIn;
import proyectofinal.DlgSignIn;
import ventaException.VentaException;

/**
 *
 * @author f_aco
 */
public class Control {

    private PersistenciaBD persistencia = new PersistenciaBD();

    public void validar(String nombre, String password, DlgLogIn frame) throws SQLException {
        persistencia.validar(nombre, password, frame);
    }

    public void actualizarCajero(Cajero cajero) {
        persistencia.actualizarCajero(cajero);
    }

    public void eliminarCajero(String DNI) {
        persistencia.eliminarCajero(DNI);
    }

    public ArrayList<Cajero> listarCajero() {
        return persistencia.listarCajero();
    }

    public ArrayList<Cliente> listarCliente() {
        return persistencia.listarCliente();
    }

    public ArrayList<Categoria> listarCategoria() {
        return persistencia.listarCategoria();
    }

    public ArrayList<Producto> listarProducto() {
        return persistencia.listarProducto();
    }

    public ArrayList<Venta> listarVentas() {
        return persistencia.listarVentas();
    }
    public ArrayList<DetalleVenta> listarDetalleVentas() {
        return persistencia.listarDetalleVentas();
    }

    public void crearCajero(String nombre, String tunno, String password, DlgSignIn frame) throws SQLException {
        persistencia.crearCajero(nombre, tunno, password, frame);
    }

    public void agregarCategoria(Categoria categoria) throws VentaException {
        Categoria cate = buscarCategoria(categoria.getID());
        if (cate.getID() == null) {
            persistencia.agregarCategoria(categoria);
        } else {
            throw new VentaException("Error: el usuario ya esta registrado");
        }

    }

    public void actualizarCategoria(Categoria categoria) throws VentaException {
        Categoria cate = buscarCategoria(categoria.getID());

        if (cate.getID() != null) {
            persistencia.actualizarCategoria(categoria);
        } else {
            throw new VentaException("Error: el usuario no existe");
        }
    }

    public void eliminarCategoria(String ID) throws VentaException {

        Categoria cate = buscarCategoria(ID);

        if (cate.getID() != null) {
            persistencia.eliminarCategoria(ID);
        } else {
            throw new VentaException("Error: el usuario no existe");
        }
    }

    public void agregarCliente(Cliente cliente) throws VentaException {

        Cliente clien = buscarCliente(cliente.getDNI());
        if (clien.getDNI() == null) {
            persistencia.agregarCliente(cliente);
        } else {
            throw new VentaException("Error: El cliente ya esta registrado");
        }
    }

    public void actualizarCliente(Cliente cliente) throws VentaException {

        Cliente clien = buscarCliente(cliente.getDNI());
        if (clien.getDNI() != null) {
            persistencia.actualizarCliente(cliente);
        } else {
            throw new VentaException("Error: El cliente no existe");
        }
    }

    public void eliminarCliente(String DNI) throws VentaException {

        Cliente clien = buscarCliente(DNI);
        if (clien.getDNI() != null) {
            persistencia.eliminarCliente(DNI);
        } else {
            throw new VentaException("Error: El cliente no existe");
        }
    }

    public ArrayList<Categoria> llenarComboboxCategoria() {
        return persistencia.llenarComboboxCategoria();
    }

    public void eliminarProducto(String ID) throws VentaException {

        Producto produc = buscarProducto(ID);
        if (produc.getID() != null) {
            persistencia.eliminarProducto(ID);
        } else {
            throw new VentaException("Error: El producto no existe");
        }
    }

    public void actualizarProducto(Producto producto) throws VentaException {
        Producto produc = buscarProducto(producto.getID());
        if (produc.getID() != null) {
            persistencia.actualizarProducto(producto);
        } else {
            throw new VentaException("Error: El producto no existe");
        }

    }

    public void agregarProducto(Producto producto) throws VentaException {
        Producto produc = buscarProducto(producto.getID());
        if (produc.getID() == null) {
            persistencia.agregarProducto(producto);
        } else {
            throw new VentaException("Error: El producto ya esta registrado");
        }

    }

    public Cliente buscarCliente(String DNI) {
        return persistencia.buscarCliente(DNI);
    }

    public Producto buscarProducto(String ID) {
        return persistencia.buscarProducto(ID);
    }

    public Categoria buscarCategoria(String ID) {
        return persistencia.buscarCategoria(ID);
    }

    public String maximaVenta() {
        return persistencia.maximaVenta();
    }

    public void GuardarDetalleVenta(DetalleVenta detalleVenta) {
        persistencia.GuardarDetalleVenta(detalleVenta);
    }

    public void GuardarVenta(Venta venta) {
        persistencia.GuardarVenta(venta);
    }

    public void inventariar(int cantidad, String ID) {
        persistencia.inventariar(cantidad, ID);
    }

}
