package podsis3;

import entities.*;
import idClass.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

public class Upiti3 {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsis3PU");
    
    // 14) KREIRAJ NARUDZBINU
    public static Narudzbina kreirajNarudzbinu(int idKor, BigDecimal ukupnaCena, String adresa, int idG) {
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();

            Narudzbina n = new Narudzbina();
            n.setIdKor(idKor);
            n.setUkupnaCena(ukupnaCena);
            n.setAdresa(adresa);
            n.setIdG(idG);
            em.persist(n);

            tx.commit();
            return n;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    // 21) SVE NARUDZBINE KORISNIKA
    public static List<Narudzbina> getNarudzbineKorisnika(int idKor) {

        EntityManager em = emf.createEntityManager();

        try {
            List<Narudzbina> list = em.createNamedQuery("Narudzbina.findByIdKor", Narudzbina.class)
                    .setParameter("idKor", idKor)
                    .getResultList();
            for (Narudzbina n : list) {
                n.setTransakcija(null);
                n.setUNarudzbiniList(null);
            }
            return list;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    // 22) SVE NARUDZBINE
    public static List<Narudzbina> getAllNarudzbine() {

        EntityManager em = emf.createEntityManager();

        try {
            List<Narudzbina> list = em.createNamedQuery("Narudzbina.findAll", Narudzbina.class).getResultList();
            for (Narudzbina n : list) {
                n.setTransakcija(null);
                n.setUNarudzbiniList(null);
            }
            return list;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    // 23) SVE TRANSAKCIJE
    public static List<Transakcija> getAllTransakcije() {

        EntityManager em = emf.createEntityManager();

        try {
            List<Transakcija> list = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
            for (Transakcija t : list) {
                if (t.getIdN() != null) {
                    t.getIdN().setTransakcija(null);
                    t.getIdN().setUNarudzbiniList(null);
                }
            }
            return list;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    // 26) KREIRAJ STAVKU NARUDZBINE
    public static Narudzbina kreirajStavke(int idN, String stavke) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Narudzbina n = em.find(Narudzbina.class, idN);
            if (n == null) {
                tx.rollback();
                return null;
            }

            for (String stavka : stavke.split("\\|")) {
                String[] delovi = stavka.split(":");
                int idArt = Integer.parseInt(delovi[0]);
                int kolicina = Integer.parseInt(delovi[1]);
                BigDecimal jedinicnaCena = new BigDecimal(delovi[2]);

                UNarudzbiniPK pk = new UNarudzbiniPK(idN, idArt);
                UNarudzbini un = new UNarudzbini(pk, kolicina, jedinicnaCena);
                em.persist(un);
            }

            tx.commit();
            return n;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }
    
    // 27) KREIRAJ TRANSAKCIJU
    public static Transakcija kreirajTransakciju(int idN, BigDecimal placeno) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Narudzbina n = em.find(Narudzbina.class, idN);
            if (n == null) {
                tx.rollback();
                return null;
            }

            Transakcija t = new Transakcija();
            t.setPlaceno(placeno);
            t.setIdN(n);
            em.persist(t);

            tx.commit();
            return t;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }
}
