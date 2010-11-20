package util;

import java.util.Vector;

public class PesquisarVector extends Vector {

    Vector vetor;

    public PesquisarVector(Vector vetor) {
        this.vetor = vetor;
    }

    public PesquisarVector() {
    }

    public Vector pesquisar(String s) {
        Vector p = new Vector();
        for (int i = 0; i < vetor.size(); i++) {
            Object o = vetor.get(i);
            if (o.toString().length() >= s.length()) {
                String sub = o.toString().substring(0, s.length());
                if (sub.toLowerCase().equals(s.toLowerCase())) {
                    p.add(o);
                }
            }
        }
        return p;
    }
}
