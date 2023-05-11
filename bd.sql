DROP TABLE public.persoana;

CREATE TABLE IF NOT EXISTS public.persoana
(
    id_persoana SERIAL PRIMARY KEY,
    nume VARCHAR(100) NOT NULL,
    prenume VARCHAR(100) NOT NULL
);

DROP TABLE public.medic;

CREATE TABLE IF NOT EXISTS public.medic
(
    id_persoana INTEGER PRIMARY KEY,
    cod_parafa VARCHAR(100) NOT NULL
);

ALTER TABLE public.medic
    ADD CONSTRAINT fk_medic FOREIGN KEY (id_persoana) REFERENCES persoana (id_persoana);

DROP TABLE public.client;

CREATE TABLE IF NOT EXISTS public.client
(
    id_persoana integer NOT NULL,
    cnp VARCHAR(100) NOT NULL,
    CONSTRAINT client_pkey PRIMARY KEY (id_persoana)
);

ALTER TABLE public.client
    ADD CONSTRAINT fk_client FOREIGN KEY (id_persoana) REFERENCES persoana (id_persoana);

DROP TABLE public.programare;

CREATE TABLE IF NOT EXISTS public.programare
(
    id_programare serial NOT NULL,
    id_client integer NOT NULL,
    id_medic integer NOT NULL,
    cod_programare VARCHAR(100) NOT NULL,
    data_programare timestamp NOT NULL,
    CONSTRAINT programare_pkey PRIMARY KEY (id_programare)
);

ALTER TABLE public.programare
    ADD CONSTRAINT fk_client_programare FOREIGN KEY (id_client) REFERENCES persoana (id_persoana);

ALTER TABLE public.programare
    ADD CONSTRAINT fk_medic_programare FOREIGN KEY (id_medic) REFERENCES persoana (id_persoana);

DROP TABLE public.specializare;

CREATE TABLE IF NOT EXISTS public.specializare
(
    id_specializare serial NOT NULL,
    cod_specializare VARCHAR(100) NOT NULL,
    denumire VARCHAR(100) NOT NULL,
    CONSTRAINT specializare_pkey PRIMARY KEY (id_specializare)
);

DROP TABLE public.medic_specializare;

CREATE TABLE IF NOT EXISTS public.medic_specializare
(
    id_specializare integer NOT NULL,
    id_medic integer NOT NULL,
    CONSTRAINT medic_specializare_pkey PRIMARY KEY (id_specializare, id_medic)
);

ALTER TABLE public.medic_specializare
    ADD CONSTRAINT fk_medic_medic_specializare FOREIGN KEY (id_medic) REFERENCES medic (id_persoana);

ALTER TABLE public.medic_specializare
    ADD CONSTRAINT fk_specializare_medic_specializare FOREIGN KEY (id_specializare) REFERENCES specializare (id_specializare);




