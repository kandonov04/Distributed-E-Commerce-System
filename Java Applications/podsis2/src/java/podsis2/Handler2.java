package podsis2;

import java.io.Serializable;
import java.math.BigDecimal;

public class Handler2 {

    public static Serializable handle(int op, String podaci) {

        try {
            switch (op) {

                // 6) KREIRAJ KATEGORIJU
                case 6: {
                    String[] niz = podaci.split(",");
                    
                    String ime = niz[0];
                    Integer idKatNad = niz.length > 1 && !niz[1].equals("null") ? Integer.parseInt(niz[1]) : null;
                    
                    return Upiti2.createKategorija(ime, idKatNad);
                }

                // 7) KREIRAJ ARTIKAL
                case 7: {
                    String[] niz = podaci.split(",");
                    
                    String ime = niz[0];
                    String opis = niz[1];
                    BigDecimal cena = new BigDecimal(niz[2]);
                    Integer popust = Integer.parseInt(niz[3]);
                    int idKor = Integer.parseInt(niz[4]);
                    int idKat = Integer.parseInt(niz[5]);
                    
                    return Upiti2.createArtikal(ime, opis, cena, popust, idKor, idKat);
                }

                // 8) PROMENI CENU
                case 8: {
                    String[] niz = podaci.split(",");
                    
                    int idArt = Integer.parseInt(niz[0]);
                    BigDecimal novaCena = new BigDecimal(niz[1]);
                    int idKor = Integer.parseInt(niz[2]);
                    
                    return Upiti2.updateCena(idArt, novaCena, idKor);
                }

                // 9) PROMENI POPUST
                case 9: {
                    String[] niz = podaci.split(",");
                    
                    int idArt = Integer.parseInt(niz[0]);
                    int popust = Integer.parseInt(niz[1]);
                    int idKor = Integer.parseInt(niz[2]);
                    
                    return Upiti2.updatePopust(idArt, popust, idKor);
                }

                // 10) DODAJ U KORPU
                case 10: {
                    String[] niz = podaci.split(",");
                    
                    int idKor = Integer.parseInt(niz[0]);
                    int idArt = Integer.parseInt(niz[1]);
                    int kolicina = Integer.parseInt(niz[2]);
                    
                    return Upiti2.dodajUKorpu(idKor, idArt, kolicina);
                }

                // 11) OBRISI IZ KORPE
                case 11: {
                    String[] niz = podaci.split(",");
                    
                    int idKor = Integer.parseInt(niz[0]);
                    int idArt = Integer.parseInt(niz[1]);
                    int kolicina = Integer.parseInt(niz[2]);
                    
                    return Upiti2.obrisiIzKorpe(idKor, idArt, kolicina);
                }

                // 12) DODAJ U LISTU ZELJA
                case 12: {
                    String[] niz = podaci.split(",");
                    
                    int idKor = Integer.parseInt(niz[0]);
                    int idArt = Integer.parseInt(niz[1]);
                    
                    return Upiti2.dodajUListuZelja(idKor, idArt);
                }

                // 13) OBRISI IZ LISTE ZELJA
                case 13: {
                    String[] niz = podaci.split(",");
                    
                    int idKor = Integer.parseInt(niz[0]);
                    int idArt = Integer.parseInt(niz[1]);
                    
                    return Upiti2.obrisiIzListeZelja(idKor, idArt);
                }

                // 17) SVE KATEGORIJE
                case 17: {
                    return (Serializable) Upiti2.getAllKategorije();
                }

                // 18) SVI ARTIKLI KORISNIKA
                case 18: {
                    int idKor = Integer.parseInt(podaci);
                    return (Serializable) Upiti2.getArtikliKorisnika(idKor);
                }

                // 19) DOHVATI SADRZAJ KORPE
                case 19: {
                    int idKor = Integer.parseInt(podaci);
                    return (Serializable) Upiti2.getKorpa(idKor);
                }

                // 20) GET LISTA ZELJA
                case 20: {
                    int idKor = Integer.parseInt(podaci);
                    return (Serializable) Upiti2.getListaZelja(idKor);
                }
                
                // 24) DOHVATI KORPU
                case 24: {
                    int idKor = Integer.parseInt(podaci);
                    return Upiti2.getKorpaObjekat(idKor);
                }
                
                // 25) DOHVATI ARTIKAL
                case 25: {
                    int idArt = Integer.parseInt(podaci);
                    return Upiti2.getArtikal(idArt);
                }
                
                // 28) RESETUJ KORPU - Korpa je prazna, a brisu se redovi iz tabele u_korpi
                case 28: {
                    int idKor = Integer.parseInt(podaci);
                    return Upiti2.resetujKorpu(idKor);
                }

                default:
                    return "ERROR Nepoznata operacija:" + op;
            }

        } catch (Exception e) {
            return "ERROR " + e.getMessage();
        }
    }
}
