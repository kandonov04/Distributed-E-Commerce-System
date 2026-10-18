package podsis3;

import java.io.Serializable;
import java.math.BigDecimal;

public class Handler3 {

    public static Serializable handle(int op, String podaci) {

        try {
            switch (op) {
                
                // 14) KREIRAJ NARUDZBINU
                case 14: {
                    String[] niz = podaci.split(",");
                    
                    int idKor = Integer.parseInt(niz[0]);
                    BigDecimal ukupnaCena = new BigDecimal(niz[1]);
                    String adresa = niz[2];
                    int idG = Integer.parseInt(niz[3]);
                    
                    return Upiti3.kreirajNarudzbinu(idKor, ukupnaCena, adresa, idG);
                }

                // 21) SVE NARUDZBINE KORISNIKA
                case 21: {
                    int idKor = Integer.parseInt(podaci);
                    return (Serializable) Upiti3.getNarudzbineKorisnika(idKor);
                }

                // 22) SVE NARUDZBINE
                case 22: {
                    return (Serializable) Upiti3.getAllNarudzbine();
                }

                // 23) SVE TRANSAKCIJE
                case 23: {
                    return (Serializable) Upiti3.getAllTransakcije();
                }
                
                // 26) KREIRAJ STAVKU NARUDZBINE
                case 26: {
                    String[] niz = podaci.split(",", 2);
                    
                    int idN = Integer.parseInt(niz[0]);
                    String stavke = niz[1];
                    
                    return Upiti3.kreirajStavke(idN, stavke);
                }
                
                // 27) KREIRAJ TRANSAKCIJU
                case 27: {
                    String[] niz = podaci.split(",");
                    
                    int idN = Integer.parseInt(niz[0]);
                    BigDecimal placeno = new BigDecimal(niz[1]);
                    
                    return Upiti3.kreirajTransakciju(idN, placeno);
                }

                default:
                    return "ERROR Nepoznata operacija:" + op;
            }

        } catch (Exception e) {
            return "ERROR " + e.getMessage();
        }
    }
}