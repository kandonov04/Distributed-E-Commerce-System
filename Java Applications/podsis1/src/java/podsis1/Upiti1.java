package podsis1;

import entities.Grad;
import entities.Korisnik;
import entities.Uloga;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

public class Upiti1 {
    
   private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsis1PU");

    // 1) CHECK CREDENTIALS / LOGIN
    public static Korisnik checkCredentials(String korisnickoIme, String sifra) {

        EntityManager em = emf.createEntityManager();

        try {
            Korisnik k = em.createQuery("SELECT k FROM Korisnik k WHERE k.korisnickoIme = :u AND k.sifra = :p", Korisnik.class)
            .setParameter("u", korisnickoIme).setParameter("p", sifra)
            .getSingleResult();

            return k;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    // 2) KREIRAJ GRAD
    public static Grad createGrad(String ime) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Grad g = new Grad();
            g.setIme(ime);
            em.persist(g);

            tx.commit();
            return g;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    // 3) KREIRAJ KORISNIKA
    public static Korisnik createKorisnik(String korisnickoIme, String sifra, String ime, String prezime, String adresa, int idG) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Grad grad = em.find(Grad.class, idG);
            if (grad == null) {
                tx.rollback();
                return null;
            }

            Korisnik k = new Korisnik();
            k.setKorisnickoIme(korisnickoIme);
            k.setSifra(sifra);
            k.setIme(ime);
            k.setPrezime(prezime);
            k.setAdresa(adresa);
            k.setIdG(grad);
            k.setStanje(BigDecimal.ZERO);

            em.persist(k);
            tx.commit();

            return k;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    // 4) DODAJ NOVAC
    public static Korisnik addMoney(String korisnickoIme, double iznos) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Korisnik k = em.createQuery("SELECT k FROM Korisnik k WHERE k.korisnickoIme = :u", Korisnik.class)
            .setParameter("u", korisnickoIme)
            .getSingleResult();

            k.setStanje(k.getStanje().add(BigDecimal.valueOf(iznos)));

            tx.commit();
            return k;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    // 5) PROMENI ADRESU I GRAD
    public static Korisnik updateAddressAndGrad(String korisnickoIme, String adresa, int idG) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Korisnik k = em.createQuery("SELECT k FROM Korisnik k WHERE k.korisnickoIme = :u", Korisnik.class)
            .setParameter("u", korisnickoIme)
            .getSingleResult();

            Grad grad = em.find(Grad.class, idG);
            if (grad == null) {
                tx.rollback();
                return null;
            }

            k.setAdresa(adresa);
            k.setIdG(grad);

            tx.commit();
            return k;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    // 15) SVI GRADOVI
    public static List<Grad> getAllGradovi() {

        EntityManager em = emf.createEntityManager();

        try {
            List<Grad> list = em.createQuery("SELECT g FROM Grad g", Grad.class)
            .getResultList();
            for (Grad g : list) {
                g.setKorisnikList(null);
            }
            return list;

        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    // 16) SVI KORISNICI
    public static List<Korisnik> getAllKorisnici() {

        EntityManager em = emf.createEntityManager();

        try {
            List<Korisnik> list = em.createQuery("SELECT k FROM Korisnik k", Korisnik.class)
            .getResultList();
            for (Korisnik k : list) {
                k.setUlogaList(null);
            }
            return list;

        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    // 29) SVE ULOGE
    public static List<Uloga> getUloge(String korisnickoIme) {
        
        EntityManager em = emf.createEntityManager();
        
        try {
            Korisnik k = em.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class)
                    .setParameter("korisnickoIme", korisnickoIme)
                    .getSingleResult();

            return em.createQuery("SELECT u FROM Uloga u JOIN u.korisnikList k WHERE k.idKor = :idKor", Uloga.class)
                    .setParameter("idKor", k.getIdKor())
                    .getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    // 30) SMANJI NOVAC
    public static Korisnik subtractMoney(int idKor, BigDecimal iznos) {
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            Korisnik k = em.find(Korisnik.class, idKor);
            if (k == null) {
                tx.rollback();
                return null;
            }
            if (k.getStanje().compareTo(iznos) < 0) {
                tx.rollback();
                return null;
            }
            k.setStanje(k.getStanje().subtract(iznos));
            tx.commit();
            return k;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return null;
        } finally {
            em.close();
        }
    }
    
    // 31) DODAJ NOVAC PO ID-U KORISNIKA
    public static Korisnik addMoneyById(int idKor, BigDecimal iznos) {
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            Korisnik k = em.find(Korisnik.class, idKor);
            if (k == null) {
                tx.rollback();
                return null;
            }
            k.setStanje(k.getStanje().add(iznos));
            tx.commit();
            return k;
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
