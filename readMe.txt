Programare avansata pe obiecte - Programare cabinet medical

Acțiuni/Interogari
- Adaugare specializare: GestionarePersoane.adaugaSpecializare
- Adaugare persoana (Client sau Medic): GestionarePersoane.adaugaPersoana
- Adaugare programare: GestionareProgramari.adaugaProgramare
- Selectare toti medicii ordonati dupa nume si prenume: GestionarePersoane.getTotiMedicii
- Selectare toti clientii ordonati dupa nume si prenume: GestionarePersoane.getTotiClientii
- Selectare un medic dupa cod parafa: GestionarePersoane.getMedicDupaCodParafa
- Selectare un client dupa cnp: GestionarePersoane.getClientDupaCnp
- Anulare programare: GestionareProgramari.anuleazaProgramare
- Selectarea programarilor unui medic dintr-o zi ordonata crescator dupa ora: GestionareProgramari.getProgramariPentruMedicDinZiua
- Selectarea tuturor programarilor unui client: GestionareProgramari.getToateProgramarileClient
- Selectarea tuturor medicilor dupa o specializare ordonati dupa nume si prenume: GestionarePersoane.getMediciDupaSpecializare
- Start operatii aplicatie: GestionareCabinetMedical.startExecutareaOperatilorAplicatiei

Clase/Interfete

- Persoana
    - Client
    - Medic
- Programare
- SpecializareMedic
- Main
- GestionarePersoane (interfata)
    - GestionarePersoaneInMemorie
- GestionareProgramari (interfata)
    - GestionareProgramariInMemorie
- DataOraProgramariiComparator
- Constante
- NumePrenumeComparator
- GestionareCabinetMedical (interfata)
    - GestionareCabinetMedicalInMemorie