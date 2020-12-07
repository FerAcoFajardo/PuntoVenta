/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.Control;
import objetoNegocio.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author f_aco
 */
public class Utilidades {

    Control control = new Control();

    public void listarClientes(DefaultTableModel modelo, javax.swing.JTable Tabla) {
        ArrayList<Cliente> lista = control.listarCliente();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }

        modelo = (DefaultTableModel) Tabla.getModel();
        Object[] ob = new Object[3];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getDNI();
            ob[1] = lista.get(i).getNombre();
            ob[2] = lista.get(i).getDireccion();
            modelo.addRow(ob);
        }
        Tabla.setModel(modelo);
    }

    public void listarCajeros(DefaultTableModel modelo, javax.swing.JTable Tabla) {
        ArrayList<Cajero> lista = control.listarCajero();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }

        modelo = (DefaultTableModel) Tabla.getModel();
        Object[] ob = new Object[3];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getDNI();
            ob[1] = lista.get(i).getNombre();
            ob[2] = lista.get(i).getTurno();
            modelo.addRow(ob);
        }
        Tabla.setModel(modelo);
    }

    public void listarCategorias(DefaultTableModel modelo, javax.swing.JTable Tabla) {
        ArrayList<Categoria> lista = control.listarCategoria();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }

        modelo = (DefaultTableModel) Tabla.getModel();
        Object[] ob = new Object[2];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getID();
            ob[1] = lista.get(i).getNombre();
            modelo.addRow(ob);
        }
        Tabla.setModel(modelo);
    }

    public void listarProductos(DefaultTableModel modelo, javax.swing.JTable Tabla) {
        ArrayList<Producto> lista = control.listarProducto();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }

        modelo = (DefaultTableModel) Tabla.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getID();
            ob[1] = lista.get(i).getNombre();
            ob[2] = lista.get(i).getStock();
            ob[3] = lista.get(i).getPrecio();
            ob[4] = lista.get(i).getCategoria().getNombre();
            modelo.addRow(ob);
        }
        Tabla.setModel(modelo);
    }

    public void listarVentas(DefaultTableModel modelo, javax.swing.JTable Tabla) {
        ArrayList<Venta> lista = control.listarVentas();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }

        modelo = (DefaultTableModel) Tabla.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getID();
            ob[1] = lista.get(i).getTotal();
            ob[2] = lista.get(i).getFecha();
            ob[3] = lista.get(i).getCajero().getNombre();
            ob[4] = lista.get(i).getCliente().getNombre();
            modelo.addRow(ob);
        }
        Tabla.setModel(modelo);
    }
    public void listarDetalleVentas(DefaultTableModel modelo, javax.swing.JTable Tabla) {
        ArrayList<DetalleVenta> lista = control.listarDetalleVentas();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }

        modelo = (DefaultTableModel) Tabla.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getID();
            ob[1] = lista.get(i).getProducto().getNombre();
            ob[2] = lista.get(i).getVenta().getID();
            ob[3] = lista.get(i).getCantidad();
            ob[4] = lista.get(i).getPrecio();
            modelo.addRow(ob);
        }
        Tabla.setModel(modelo);
    }

}
