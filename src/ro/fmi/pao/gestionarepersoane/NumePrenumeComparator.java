package ro.fmi.pao.gestionarepersoane;

import ro.fmi.pao.model.Persoana;

import java.util.Comparator;

public class NumePrenumeComparator implements Comparator<Persoana> {

    @Override
    public int compare(Persoana persoana, Persoana t1) {
        return persoana.getNumePrenume().compareTo(t1.getNumePrenume());
    }
}
