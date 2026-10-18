package podsis2;

import entities.*;
import idClass.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

public class Upiti2 {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsis2PU");

    // 6) KREIRAJ KATEGORIJU
    public static Kategorija createKategorija(String ime, Integer idKatNad) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Kategorija k = new Kategorija();
            k.setIme(ime);

            if (idKatNad != null) {
                Kategorija nadredjena = em.find(Kategorija.class, idKatNad);
                if (nadredjena == null) {
                    tx.rollback();
                    return null;
                }
                k.setIdKatNad(nadredjena);
            }

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

    // 7) KREIRAJ ARTIKAL
    public static Artikal createArtikal(String ime, String opis, BigDecimal cena, Integer popust, int idKor, int idKat) {
        
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();

            Kategorija k = em.find(Kategorija.class, idKat);
            if (k == null) {
                tx.rollback();
                return null;
            }

            Artikal a = new Artikal();
            a.setIme(ime);
            a.setOpis(opis);
            a.setCena(cena);
            a.setPopust(popust);
            a.setIdKor(idKor);
            a.setKategorijaList(new java.util.ArrayList<>());
            a.getKategorijaList().add(k);
            em.persist(a);

            tx.commit();
            return a;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    // 8) PROMENI CENU
    public static Artikal updateCena(int idArt, BigDecimal novaCena, int idKor) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Artikal a = em.find(Artikal.class, idArt);
            if (a == null || a.getIdKor() != idKor) {
                tx.rollback();
                return null;
            }

            a.setCena(novaCena);
            tx.commit();
            return a;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    // 9) PROMENI POPUST
    public static Artikal updatePopust(int idArt, int popust, int idKor) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Artikal a = em.find(Artikal.class, idArt);
            if (a == null || a.getIdKor() != idKor) {
                tx.rollback();
                return null;
            }

            a.setPopust(popust);
            tx.commit();
            return a;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    // 10) DODAJ U KORPU
    public static Korpa dodajUKorpu(int idKor, int idArt, int kolicina) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Korpa korpa = null;
            List<Korpa> korpe = em.createNamedQuery("Korpa.findByIdKor", Korpa.class)
                    .setParameter("idKor", idKor)
                    .getResultList();

            if (korpe.isEmpty()) {
                korpa = new Korpa();
                korpa.setIdKor(idKor);
                korpa.setUkupnaCena(BigDecimal.ZERO);
                em.persist(korpa);
                em.flush();
            } else {
                korpa = korpe.get(0);
            }

            Artikal a = em.find(Artikal.class, idArt);
            if (a == null) {
                tx.rollback();
                return null;
            }
            
            if (a.getIdKor() == idKor) {
                tx.rollback();
                return null;
            }

            UKorpiPK pk = new UKorpiPK(korpa.getIdK(), idArt);
            UKorpi postojeci = em.find(UKorpi.class, pk);

            if (postojeci != null) {
                postojeci.setKolicina(postojeci.getKolicina() + kolicina);
            } else {
                UKorpi uk = new UKorpi(pk, kolicina);
                em.persist(uk);
            }

            BigDecimal cenaSaPopustom = a.getCena();
            if (a.getPopust() > 0) {
                cenaSaPopustom = a.getCena().multiply(BigDecimal.valueOf(1 - a.getPopust() / 100.0));
            }
            korpa.setUkupnaCena(korpa.getUkupnaCena().add(cenaSaPopustom.multiply(BigDecimal.valueOf(kolicina))));

            tx.commit();
            return korpa;

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return null;
        } finally {
            em.close();
        }
    }


    // 11) OBRISI IZ KORPE
    public static Korpa obrisiIzKorpe(int idKor, int idArt, int kolicina) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            List<Korpa> korpe = em.createNamedQuery("Korpa.findByIdKor", Korpa.class)
                    .setParameter("idKor", idKor)
                    .getResultList();

            if (korpe.isEmpty()) {
                tx.rollback();
                return null;
            }

            Korpa korpa = korpe.get(0);
            UKorpiPK pk = new UKorpiPK(korpa.getIdK(), idArt);
            UKorpi uk = em.find(UKorpi.class, pk);

            if (uk == null) {
                tx.rollback();
                return null;
            }

            Artikal a = em.find(Artikal.class, idArt);
            BigDecimal cenaSaPopustom = a.getCena();
            if (a.getPopust() > 0) {
                cenaSaPopustom = a.getCena().multiply(BigDecimal.valueOf(1 - a.getPopust() / 100.0));
            }

            if (uk.getKolicina() <= kolicina) {
                korpa.setUkupnaCena(korpa.getUkupnaCena().subtract(cenaSaPopustom.multiply(BigDecimal.valueOf(uk.getKolicina()))));
                em.remove(uk);
            } else {
                uk.setKolicina(uk.getKolicina() - kolicina);
                korpa.setUkupnaCena(korpa.getUkupnaCena().subtract(cenaSaPopustom.multiply(BigDecimal.valueOf(kolicina))));
            }

            tx.commit();
            return korpa;

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return null;
        } finally {
            em.close();
        }
    }

    // 12) DODAJ U LISTU ZELJA
    public static ListaZelja dodajUListuZelja(int idKor, int idArt) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            ListaZelja lista = null;
            List<ListaZelja> liste = em.createNamedQuery("ListaZelja.findByIdKor", ListaZelja.class)
                    .setParameter("idKor", idKor)
                    .getResultList();

            if (liste.isEmpty()) {
                lista = new ListaZelja();
                lista.setIdKor(idKor);
                em.persist(lista);
                em.flush();
            } else {
                lista = liste.get(0);
            }

            Artikal a = em.find(Artikal.class, idArt);
            if (a == null) {
                tx.rollback();
                return null;
            }
            
            if (a.getIdKor() == idKor) {
                tx.rollback();
                return null;
            }

            UListiZeljaPK pk = new UListiZeljaPK(lista.getIdList(), idArt);
            UListiZelja postojeci = em.find(UListiZelja.class, pk);

            if (postojeci == null) {
                UListiZelja ulz = new UListiZelja(pk);
                em.persist(ulz);
            }

            tx.commit();
            return lista;

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return null;
        } finally {
            em.close();
        }
    }

    // 13) OBRISI IZ LISTE ZELJA
    public static ListaZelja obrisiIzListeZelja(int idKor, int idArt) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            List<ListaZelja> liste = em.createNamedQuery("ListaZelja.findByIdKor", ListaZelja.class)
                    .setParameter("idKor", idKor)
                    .getResultList();

            if (liste.isEmpty()) {
                tx.rollback();
                return null;
            }

            ListaZelja lista = liste.get(0);
            UListiZeljaPK pk = new UListiZeljaPK(lista.getIdList(), idArt);
            UListiZelja ulz = em.find(UListiZelja.class, pk);

            if (ulz == null) {
                tx.rollback();
                return null;
            }

            em.remove(ulz);
            tx.commit();
            return lista;

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            return null;
        } finally {
            em.close();
        }
    }

    // 17) SVE KATEGORIJE
    public static List<Kategorija> getAllKategorije() {

        EntityManager em = emf.createEntityManager();

        try {
            List<Kategorija> list = em.createNamedQuery("Kategorija.findAll", Kategorija.class).getResultList();
            for (Kategorija k : list) {
                k.setArtikalList(null);
                k.setKategorijaList(null);
            }
            return list;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    // 18) SVI ARTIKLI KORISNIKA
    public static List<Artikal> getArtikliKorisnika(int idKor) {

        EntityManager em = emf.createEntityManager();

        try {
            List<Artikal> list = em.createNamedQuery("Artikal.findByIdKor", Artikal.class)
                    .setParameter("idKor", idKor)
                    .getResultList();
            for (Artikal a : list) {
                a.setUKorpiList(null);
                a.setUListiZeljaList(null);
                a.setKategorijaList(null);
            }
            return list;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    // 19) DOHVATI SADRZAJ KORPE
    public static List<UKorpi> getKorpa(int idKor) {
        EntityManager em = emf.createEntityManager();
        try {
            Korpa korpa = em.createNamedQuery("Korpa.findByIdKor", Korpa.class)
                    .setParameter("idKor", idKor)
                    .getSingleResult();

            return em.createNamedQuery("UKorpi.findByIdK", UKorpi.class)
                    .setParameter("idK", korpa.getIdK())
                    .getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    // 20) GET LISTA ZELJA
    public static List<UListiZelja> getListaZelja(int idKor) {
        EntityManager em = emf.createEntityManager();
        try {
            ListaZelja lista = em.createNamedQuery("ListaZelja.findByIdKor", ListaZelja.class)
                    .setParameter("idKor", idKor)
                    .getSingleResult();

            return em.createNamedQuery("UListiZelja.findByIdList", UListiZelja.class)
                    .setParameter("idList", lista.getIdList())
                    .getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    
    // 24) DOHVATI KORPU
    public static Korpa getKorpaObjekat(int idKor) {

        EntityManager em = emf.createEntityManager();

        try {
            List<Korpa> korpe = em.createNamedQuery("Korpa.findByIdKor", Korpa.class)
                    .setParameter("idKor", idKor)
                    .getResultList();

            if (korpe.isEmpty()) return null;

            Korpa k = korpe.get(0);
            if (k.getUKorpiList() != null) {
                for (UKorpi uk : k.getUKorpiList()) {
                    uk.getKolicina();
                    if (uk.getArtikal() != null) {
                        uk.getArtikal().setUKorpiList(null);
                        uk.getArtikal().setUListiZeljaList(null);
                        uk.getArtikal().setKategorijaList(null);
                    }
                }
            }
            return k;

        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    // 25) DOHVATI ARTIKAL
    public static Artikal getArtikal(int idArt) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Artikal.class, idArt);
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    // 28) RESETUJ KORPU - Ukupna cena korpe je 0, a brisu se redovi iz tabele u_korpi
    public static Korpa resetujKorpu(int idKor) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Korpa korpa = em.createNamedQuery("Korpa.findByIdKor", Korpa.class)
                    .setParameter("idKor", idKor)
                    .getSingleResult();

            // Brisi u_korpi
            List<UKorpi> stavke = em.createNamedQuery("UKorpi.findByIdK", UKorpi.class)
                    .setParameter("idK", korpa.getIdK())
                    .getResultList();
            for (UKorpi uk : stavke) {
                em.remove(uk);
            }

            // Ukupna cena 0
            korpa.setUkupnaCena(BigDecimal.ZERO);

            tx.commit();
            return korpa;

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