SELECT Personen.kurzName
FROM Personen
WHERE pk_Person IN(SELECT Mitarbeiter.fk_Personen
FROM Mitarbeiter
WHERE Mitarbeiter.pk_Mitarbeiter IN (SELECT Ansprechpartner.fk_Mitarbeiter
FROM Ansprechpartner
WHERE Ansprechpartner.fk_Projekt IN (SELECT Projekt.pk_Projekt
FROM Projekt
WHERE Projekt.fk_Auftrag IN (SELECT Auftrag.pk_Auftrag
FROM Auftrag
WHERE Auftrag.auftragsbezeichnung = '002'))))