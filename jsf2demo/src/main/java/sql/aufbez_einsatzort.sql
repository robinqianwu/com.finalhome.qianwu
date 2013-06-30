SELECT EinsatzortProjekt.EinsatzortName
FROM EinsatzortProjekt
WHERE EinsatzortProjekt.fk_Projekt = (SELECT Projekt.pk_Projekt FROM Projekt WHERE Projekt.fk_Auftrag = (SELECT Auftrag.pk_Auftrag FROM Auftrag WHERE Auftrag.auftragsbezeichnung = 001))