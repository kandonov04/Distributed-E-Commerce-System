package podsis1;

import java.io.Serializable;
import java.math.BigDecimal;

public class Handler1 {
    
     public static Serializable handle(int op, String podaci) {

        try {
            switch (op) {

                // 1) CHECK CREDENTIALS / LOGIN
                case 1: {
                    
                    String[] niz = podaci.split(",");
                    
                    String korisnickoIme = niz[0];
                    String sifra = niz[1];
                    
                    return Upiti1.checkCredentials(korisnickoIme, sifra);
                }

                // 2) KREIRAJ GRAD
                case 2: {

                    String ime = podaci;

                    return Upiti1.createGrad(ime);
                }

                // 3) KREIRAJ KORISNIKA
                case 3: {
                    
                    String[] niz = podaci.split(",");

                    String korisnickoIme = niz[0];
                    String sifra = niz[1];
                    String ime = niz[2];
                    String prezime = niz[3];
                    String adresa = niz[4];
                    int idG = Integer.parseInt(niz[5]);

                    return Upiti1.createKorisnik(korisnickoIme, sifra, ime, prezime, adresa, idG);
                }

                // 4) DODAJ NOVAC
                case 4: {
                    
                    String[] niz = podaci.split(",");
                    
                    String korisnickoIme = niz[0];
                    double iznos = Double.parseDouble(niz[1]);

                    return Upiti1.addMoney(korisnickoIme, iznos);
                }

                // 5) PROMENI ADRESU I GRAD
                case 5: {

                    String[] niz = podaci.split(",");

                    String korisnickoIme = niz[0];
                    String adresa = niz[1];
                    int idG = Integer.parseInt(niz[2]);

                    return Upiti1.updateAddressAndGrad(korisnickoIme, adresa, idG);
                }

                // 15) SVI GRADOVI
                case 15: {
                    return (Serializable) Upiti1.getAllGradovi();
                }

                // 16) SVI KORISNICI
                case 16: {
                    return (Serializable) Upiti1.getAllKorisnici();
                }
                
                // 29) SVE ULOGE
                case 29: {
                    return (Serializable) Upiti1.getUloge(podaci);
                }
                
                // 30) SMANJI NOVAC
                case 30: {
                    String[] niz = podaci.split(",");
                    
                    int idKor = Integer.parseInt(niz[0]);
                    BigDecimal iznos = new BigDecimal(niz[1]);
                    
                    return Upiti1.subtractMoney(idKor, iznos);
                }
                
                // 31) DODAJ NOVAC PO ID-U KORISNIKA
                case 31: {
                    String[] niz = podaci.split(",");
                    
                    int idKor = Integer.parseInt(niz[0]);
                    BigDecimal iznos = new BigDecimal(niz[1]);
                    
                    return Upiti1.addMoneyById(idKor, iznos);
                }

                default:
                    return "ERROR Nepoznata operacija:" + op;
            }

        } catch (Exception e) {
            return "ERROR" + e.getMessage();
        }
     }
    
}
