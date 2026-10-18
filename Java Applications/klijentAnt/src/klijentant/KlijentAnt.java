package klijentant;

import dto.RequestDTO;
import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Response;
import java.util.Scanner;


public class KlijentAnt {

    static Scanner sc = new Scanner(System.in);
    static boolean loggedIn = false;
    static int loggedUserId = -1;
    
    public static void main(String[] args) throws Exception {

        RetrofitClient.init("", "");
        while (true) {
            System.out.println("\n========= MENI =========");
            System.out.println("1. Login");
            System.out.println("2. Kreiraj grada");
            System.out.println("3. Kreiraj korisnika");
            System.out.println("4. Dodaj novac korisniku");
            System.out.println("5. Promeni adresu i grad korisnika");
            System.out.println("6. Kreiraj kategoriju");
            System.out.println("7. Kreiraj artikal");
            System.out.println("8. Promeni cenu artikla");
            System.out.println("9. Postavi popust artikla");
            System.out.println("10. Dodavanje u korpu");
            System.out.println("11. Obrisi iz korpe");
            System.out.println("12. Dodaj u listu zelja");
            System.out.println("13. Obrisi iz liste zelja");
            System.out.println("14. PLATI");
            System.out.println("15. Svi gradovi");
            System.out.println("16. Svi korisnici");
            System.out.println("17. Sve kategorije");
            System.out.println("18. Artikli korisnika");
            System.out.println("19. Sadrzaj korpe");
            System.out.println("20. Sadrzaj liste zelja");
            System.out.println("21. Narudzbine korisnika");
            System.out.println("22. Sve narudzbine");
            System.out.println("23. Sve transakcije");
            System.out.println("0. Logout/Exit");

            int c = Integer.parseInt(sc.nextLine());
            
            if (c == 0) {
                logout();
                break;
            }

            switch (c) {
                case 1:
                    login();
                    break;
                case 2:
                    napraviGrad();
                    break;
                case 3:
                    napraviKorisnika();
                    break;
                case 4:
                    dodajNovac();
                    break;
                case 5:
                    promeniAdresu();
                    break;
                case 6:
                    napraviKategoriju();
                    break;
                case 7:
                    napraviArtikal();
                    break;
                case 8:
                    promeniCenu();
                    break;
                case 9:
                    postaviPopust();
                    break;
                case 10:
                    dodajUKorpu();
                    break;
                case 11:
                    obrisiIzKorpe();
                    break;
                case 12:
                    dodajUWishlist();
                    break;
                case 13:
                    obrisiIzWishlista();
                    break;
                case 14:
                    plati();
                    break;
                case 15:
                    sviGradovi();
                    break;
                case 16:
                    sviKorisnici();
                    break;
                case 17:
                    sveKategorije();
                    break;
                case 18:
                    artikliKorisnika();
                    break;
                case 19:
                    sadrzajKorpe();
                    break;
                case 20:
                    sadrzajWishlista();
                    break;
                case 21:
                    narudzbineKorisnika();
                    break;
                case 22:
                    sveNarudzbine();
                    break;
                case 23:
                    sveTransakcije();
                    break;
                default: 
                    System.out.println("Nepostojeca opcija");
            }
        }
    }

    static void login() throws Exception {
        if (loggedIn) {
            System.out.println("Vec ste ulogovani!");
            return;
        }
        System.out.println("Korisnicko ime:");
        String u = sc.nextLine();
        System.out.println("Sifra:");
        String p = sc.nextLine();

        RetrofitClient.init(u, p);

        RequestDTO req = new RequestDTO();
        req.korisnickoIme = u;
        req.sifra = p;
        Response<ResponseBody> res = RetrofitClient.getInstance().korisnik().login(req).execute();
        if (res.code() == 401) {
            System.out.println("Pogresni kredencijali!");
            RetrofitClient.init("", "");
            return;
        }
        String xml = res.body().string();
        loggedUserId = Integer.parseInt(xml.replaceAll("(?s).*<idKor>(\\d+)</idKor>.*", "$1"));
        loggedIn = true;
        System.out.println("Uspesno ulogovani kao: " + u + " (ID: " + loggedUserId + ")");
    }

    static void napraviGrad() throws Exception {
        RequestDTO req = new RequestDTO();
        System.out.println("Ime grada:");
        req.ime = sc.nextLine();
        print(RetrofitClient.getInstance().grad().kreirajGrad(req).execute());
    }

    static void napraviKorisnika() throws Exception {
        RequestDTO req = new RequestDTO();
        System.out.println("Korisnicko ime:");
        req.korisnickoIme = sc.nextLine();
        System.out.println("Sifra:");
        req.sifra = sc.nextLine();
        System.out.println("Ime:");
        req.ime = sc.nextLine();
        System.out.println("Prezime:");
        req.prezime = sc.nextLine();
        System.out.println("Adresa:");
        req.adresa = sc.nextLine();
        System.out.println("ID grada:");
        req.idG = Integer.parseInt(sc.nextLine());
        print(RetrofitClient.getInstance().korisnik().kreirajKorisnika(req).execute());
    }

    static void dodajNovac() throws Exception {
        RequestDTO req = new RequestDTO();
        System.out.println("Korisnicko ime:");
        req.korisnickoIme = sc.nextLine();
        System.out.println("Iznos:");
        req.iznos = new java.math.BigDecimal(sc.nextLine());
        print(RetrofitClient.getInstance().korisnik().dodajNovac(req).execute());
    }

    static void promeniAdresu() throws Exception {
        RequestDTO req = new RequestDTO();
        System.out.println("Korisnicko ime:");
        req.korisnickoIme = sc.nextLine();
        System.out.println("Nova adresa:");
        req.adresa = sc.nextLine();
        System.out.println("ID novog grada:");
        req.idG = Integer.parseInt(sc.nextLine());
        print(RetrofitClient.getInstance().korisnik().promeniAdresu(req).execute());
    }

    static void napraviKategoriju() throws Exception {
        RequestDTO req = new RequestDTO();
        System.out.println("Ime kategorije:");
        req.ime = sc.nextLine();
        System.out.println("ID nadkategorije (Ako nema postaviti vrednost 0):");
        int idKatNad = Integer.parseInt(sc.nextLine());
        if (idKatNad != 0) {
            req.idKat = idKatNad;
        }
        print(RetrofitClient.getInstance().kategorija().kreirajKategoriju(req).execute());
    }

    static void napraviArtikal() throws Exception {
        RequestDTO req = new RequestDTO();
        System.out.println("Ime artikla:");
        req.ime = sc.nextLine();
        System.out.println("Opis:");
        req.opis = sc.nextLine();
        System.out.println("Cena:");
        req.cena = new java.math.BigDecimal(sc.nextLine());
        System.out.println("Popust (%):");
        req.popust = new java.math.BigDecimal(sc.nextLine());
        System.out.println("ID kategorije:");
        req.idKat = Integer.parseInt(sc.nextLine());
        req.idKor = loggedUserId;
        print(RetrofitClient.getInstance().artikal().kreirajArtikal(req).execute());
    }

    static void promeniCenu() throws Exception {
        RequestDTO req = new RequestDTO();
        System.out.println("ID artikla:");
        req.idArt = Integer.parseInt(sc.nextLine());
        System.out.println("Nova cena:");
        req.cena = new java.math.BigDecimal(sc.nextLine());
        req.idKor = loggedUserId;
        print(RetrofitClient.getInstance().artikal().updateCena(req).execute());
    }

    static void postaviPopust() throws Exception {
        RequestDTO req = new RequestDTO();
        System.out.println("ID artikla:");
        req.idArt = Integer.parseInt(sc.nextLine());
        System.out.println("Popust (%):");
        req.popust = new java.math.BigDecimal(sc.nextLine());
        req.idKor = loggedUserId;
        print(RetrofitClient.getInstance().artikal().updatePopust(req).execute());
    }

    static void dodajUKorpu() throws Exception {
        RequestDTO req = new RequestDTO();
        System.out.println("ID artikla:");
        req.idArt = Integer.parseInt(sc.nextLine());
        System.out.println("Kolicina:");
        req.kolicina = Integer.parseInt(sc.nextLine());
        req.idKor = loggedUserId;
        print(RetrofitClient.getInstance().korpa().dodajUKorpu(req).execute());
    }

    static void obrisiIzKorpe() throws Exception {
        RequestDTO req = new RequestDTO();
        System.out.println("ID artikla:");
        req.idArt = Integer.parseInt(sc.nextLine());
        System.out.println("Kolicina:");
        req.kolicina = Integer.parseInt(sc.nextLine());
        req.idKor = loggedUserId;
        print(RetrofitClient.getInstance().korpa().obrisiIzKorpe(req).execute());
    }

    static void dodajUWishlist() throws Exception {
        RequestDTO req = new RequestDTO();
        System.out.println("ID artikla:");
        req.idArt = Integer.parseInt(sc.nextLine());
        req.idKor = loggedUserId;
        print(RetrofitClient.getInstance().wishlist().dodajUListuZelja(req).execute());
    }

    static void obrisiIzWishlista() throws Exception {
        RequestDTO req = new RequestDTO();
        System.out.println("ID artikla:");
        req.idArt = Integer.parseInt(sc.nextLine());
        req.idKor = loggedUserId;
        print(RetrofitClient.getInstance().wishlist().obrisiIzListeZelja(req).execute());
    }

    static void plati() throws Exception {
        RequestDTO req = new RequestDTO();
        System.out.println("Adresa dostave:");
        req.adresa = sc.nextLine();
        System.out.println("ID grada dostave:");
        req.idG = Integer.parseInt(sc.nextLine());
        req.idKor = loggedUserId;
        print(RetrofitClient.getInstance().narudzbine().plati(req).execute());
    }

    static void sviGradovi() throws Exception {
        print(RetrofitClient.getInstance().grad().sviGradovi().execute());
    }

    static void sviKorisnici() throws Exception {
        print(RetrofitClient.getInstance().korisnik().sviKorisnici().execute());
    }

    static void sveKategorije() throws Exception {
        print(RetrofitClient.getInstance().kategorija().sveKategorije().execute());
    }

    static void artikliKorisnika() throws Exception {
        RequestDTO req = new RequestDTO();
        req.idKor = loggedUserId;
        print(RetrofitClient.getInstance().artikal().mojiArtikli(req).execute());
    }

    static void sadrzajKorpe() throws Exception {
        RequestDTO req = new RequestDTO();
        req.idKor = loggedUserId;
        print(RetrofitClient.getInstance().korpa().sadrzajKorpe(req).execute());
    }

    static void sadrzajWishlista() throws Exception {
        RequestDTO req = new RequestDTO();
        req.idKor = loggedUserId;
        print(RetrofitClient.getInstance().wishlist().sadrzajListeZelja(req).execute());
    }

    static void narudzbineKorisnika() throws Exception {
        RequestDTO req = new RequestDTO();
        req.idKor = loggedUserId;
        print(RetrofitClient.getInstance().narudzbine().mojeNarudzbine(req).execute());
    }

    static void sveNarudzbine() throws Exception {
        print(RetrofitClient.getInstance().narudzbine().sveNarudzbine().execute());
    }

    static void sveTransakcije() throws Exception {
        print(RetrofitClient.getInstance().narudzbine().sveTransakcije().execute());
    }

    static void logout() {
        RetrofitClient.init("", "");
        loggedIn = false;
        loggedUserId = -1;
        System.out.println("Uspesno odjavljeni!");
    }
    
    static void print(Response<ResponseBody> res) throws Exception {
        if (res.isSuccessful() && res.body() != null) {
            String xml = res.body().string();
            //System.out.println(xml);
            printXml(xml);
        } else {
            String err = res.errorBody() != null ? res.errorBody().string() : "nepoznata greska";
            System.out.println("HTTP greska " + res.code() + ": " + err);
        }
    }

    static void printXml(String xml) {
        
        if (!xml.trim().startsWith("<")) {
            System.out.println(xml);
            return;
        }
            
        xml = xml.replaceAll("<\\?xml.*?\\?>", "").trim();
        xml = xml.replaceAll("^<\\w+>", "").replaceAll("</\\w+>$", "").trim();

        java.util.regex.Pattern objPattern = java.util.regex.Pattern.compile("<(\\w+)>(.*?)</\\1>", java.util.regex.Pattern.DOTALL);
        java.util.regex.Pattern fieldPattern = java.util.regex.Pattern.compile("<(\\w+)>([^<]*)</\\1>");

        java.util.regex.Matcher objMatcher = objPattern.matcher(xml);

        List<List<String>> allHeaders = new ArrayList<>();
        List<List<String>> rows = new ArrayList<>();

        while (objMatcher.find()) {
            String content = objMatcher.group(2);
            java.util.LinkedHashMap<String, String> fieldMap = new java.util.LinkedHashMap<>();
            java.util.regex.Matcher fieldMatcher = fieldPattern.matcher(content);
            while (fieldMatcher.find()) {
                fieldMap.put(fieldMatcher.group(1), fieldMatcher.group(2));
            }
            if (!fieldMap.isEmpty()) {
                allHeaders.add(new ArrayList<>(fieldMap.keySet()));
                rows.add(new ArrayList<>(fieldMap.values()));
            }
        }

        
        List<String> headers = null;
        for (List<String> h : allHeaders) {
            if (headers == null || h.size() > headers.size())
                headers = h;
        }

        if (headers == null || allHeaders.isEmpty()) {
            
            List<String> row = new ArrayList<>();
            headers = new ArrayList<>();
            java.util.regex.Matcher fieldMatcher = fieldPattern.matcher(xml);
            while (fieldMatcher.find()) {
                headers.add(fieldMatcher.group(1));
                row.add(fieldMatcher.group(2));
            }
            if (!row.isEmpty()) rows.add(row);
        } else {
            
            for (int r = 0; r < rows.size(); r++) {
                List<String> rowHeaders = allHeaders.get(r);
                List<String> row = rows.get(r);
                List<String> newRow = new ArrayList<>();
                for (String h : headers) {
                    int idx = rowHeaders.indexOf(h);
                    newRow.add(idx >= 0 ? row.get(idx) : "-");
                }
                rows.set(r, newRow);
            }
        }

        if (headers == null || headers.isEmpty()) return;

        int numCols = headers.size();
        int[] widths = new int[numCols];
        for (int i = 0; i < numCols; i++)
            widths[i] = headers.get(i).length();
        for (List<String> row : rows)
            for (int i = 0; i < numCols; i++)
                widths[i] = Math.max(widths[i], row.get(i).length());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numCols; i++) {
            sb.append(String.format("%-" + widths[i] + "s", headers.get(i)));
            if (i < numCols - 1) sb.append(" | ");
        }
        System.out.println(sb);

        sb = new StringBuilder();
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < widths[i]; j++) sb.append("-");
            if (i < numCols - 1) sb.append("-+-");
        }
        System.out.println(sb);

        for (List<String> row : rows) {
            sb = new StringBuilder();
            for (int i = 0; i < numCols; i++) {
                sb.append(String.format("%-" + widths[i] + "s", row.get(i)));
                if (i < numCols - 1) sb.append(" | ");
            }
            System.out.println(sb);
        }
    }
}
