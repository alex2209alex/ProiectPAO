package ro.fmi.pao.gestionareprogramari;

import ro.fmi.pao.model.Programare;

import java.time.LocalDateTime;
import java.util.Comparator;

public class DataOraProgramariiComparator implements Comparator<Programare> {

    @Override
    public int compare(Programare programare_1, Programare programare_2) {
        return programare_1.getDataOraProgramarii().compareTo(programare_2.getDataOraProgramarii());
    }
}
