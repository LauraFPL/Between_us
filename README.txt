

	BETWEEEEEEEEN UUUUUUS ---> GRUP B4


-------------------------------------------------------------------------------

Per compilar el nostre projecte, cal que us instaleu la font
'Aclonica-Regular', la qual es troba a /files/Assets

IMPORTANT, si compileu en el vostre ordinador, la resolució 
ha de ser 1920x1080 a escala 100%.

Hi ha usuaris "vip" que es generen a la Base de dades i tenen 
la contrassenya 123. Només aquests poden tenir la contrassenya
que incompleixi els 8 caràcter, majuscula i número.

COMPILACIÓ---------------------------------------------------------------------

Considerem les nostres vistes prou instintives per saber que fan
cada un dels botons. 

	Però, si busqueu per eliminar la conta, s'ha d'entrar
	a la conta que vols borrar, anar a configuració, i 
	allà trobareu per borrar. 

	Durant la partida, la hi ha un botó com de rellotge, 
	aquest només serà pressionable quan estiguis a la 
	sala corresponent del mapa per veure la TAULA LOGS

	Durant la partida, la hi ha un botó com de carpeta amb 
	un tik,	aquest només serà pressionable quan estiguis a la 
	sala corresponent del mapa per fer el check dels resultats
	i les teves sospites. 

	Per la partida, es pot anar canviant les sospites amb les 
	fletxes a dins de la mateixa taula. També a dins la taula 
	Logs

	Es pot moure el jugador amb les fletxes de sota el mapa

	Es pot veure els NPC si pressiones el ull tancat. Si no, 
	només veuràs els NPC que estiguin a la teva pròpia sala.


En general, compleix les especificacions que se'ns demana l'enunciat,
per tant, si esteu a la sala de fer el check i no encerteu, caldrà
esperar el minut. Per aquest motiu, si pressioneu en menys d'un minut
i no va, no penseu que és perquè no funciona, sino perquè no ha 
passat el minut. 

A vegades, tarda una mica en carregar les coses de la base i els botons
sembla que es quedin congelats, però només és per la càrrega.

BASE DADES---------------------------------------------------------------------


INSTRUCCIONS D'US DE LA BASE DE DADES

Pas 1: Obrir un editor de base de dades (preferiblement MySQL Workbench)
Pas 2: Creeu una nova conexió  amb els seguents requisits:
        - nom de la conexio: el que desitjeu
        - Hostname: 127.0.0.1 (per defecte)
        - Port: 3306 (per defecte)
        - Usuari: Ha de ser el vostre usuari admin (normalment 'root')
        - Contrasenya: La del vostre usuari admin
Pas 3: Anar a l'apartat de Administració > Usuaris i privilegis i afegir una conta:
        - Nom usuari: 'Amogus'
        - Contrasenya: 'DaBestAmogus1234'
        - Ha de tenir tots els permisos
        - Assegureu-vos que tingui un limit de queries sufficientment important com per fer que funcioni el joc
         (Aixo pot ser causa de errors en cas de no tenir un numero sufficientment gran)
Pas 4: Obrir el fitxer 'BBDD_BetweenUs.sql' localitzat en la carpeta 'BaseDeDades' i executeu-lo apretant la icona del 'raig'
	