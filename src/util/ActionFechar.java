package util;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class ActionFechar extends AbstractAction {

    JFrame jf;
    JDialog jd;

    public ActionFechar(JFrame j) {
        this.jf = j;
    }

    public ActionFechar(JDialog j) {
        this.jd = j;
    }

    public void actionPerformed(ActionEvent e) {
        if (jf != null) {
            jf.dispose();
        } else {
            jd.dispose();
        }
    }
}
