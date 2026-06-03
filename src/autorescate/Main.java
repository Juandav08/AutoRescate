package autorescate;

import javax.swing.SwingUtilities;

import autorescate.controller.AutoRescateController;
import autorescate.view.AutoRescateFrame;

public class Main {

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
