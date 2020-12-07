/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventaException;

/**
 *
 * @author Andrea
 */
public class VentaException extends Exception {

    public VentaException() {
    }

    public VentaException(String msj) {
        super(msj);
    }

    public VentaException(Throwable arrojable) {
        super(arrojable);
    }

    public VentaException(String msj, Throwable arrojable) {
        super(msj, arrojable);
    }

}
