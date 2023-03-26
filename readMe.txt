Programare avansata pe obiecte - Programare cabinet medical

Acțiuni/Interogari

- Adaugare persoana (Client sau Medic): GestionarePersoane.adaugaPersoana
- Selectare toti medicii ordonati dupa nume si prenume: GestionarePersoane.getTotiMedicii
- Selectare toti clientii ordonati dupa nume si prenume: GestionarePersoane.getTotiClientii
- Selectare un medic dupa cod parafa: GestionarePersoane.getMedicDupaCodParafa
- Selectare un client dupa cnp: GestionarePersoane.getClientDupaCnp
- Adaugare programare: GestionareProgramari.adaugaProgramare
- Anulare programare: GestionareProgramari.anuleazaProgramare
- Selectarea programarilor unui medic dintr-o zi ordonata crescator dupa ora: GestionareProgramari.getProgramariPentruMedicDinZiua
- Selectarea tuturor programarilor unui client: GestionareProgramari.getToateProgramarileClient
- Selectarea tuturor medicilor dupa o specializare ordonati dupa nume si prenume: GestionarePersoane.getMediciDupaSpecializare

Clase/Interfete

- Persoana
    - Client
    - Medic
- Adresa
- CabinetMedical
- Programare
- SpecializareMedic
- Main
- GestionarePersoane (interfata)
    - GestionarePersoaneInMemorie
- GestionareProgramari (interfata)
    - GestionareProgramariInMemorie
- DataOraProgramariiComparator
- ConstanteInitializare