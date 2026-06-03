package autorescate;

import javax.swing.SwingUtilities;

import autorescate.controller.AutoRescateController;
import autorescate.view.AutoRescateFrame;

/**
 * Punto de entrada de la aplicacion AutoRescate 24/7.
 */
public class Main {

    /**
     * Inicia la interfaz grafica.
     *
     * @param args Argumentos de consola no usados.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AutoRescateController controller = new AutoRescateController();
                AutoRescateFrame frame = new AutoRescateFrame(controller);
                frame.setVisible(true);
            }
        });
    }
}
