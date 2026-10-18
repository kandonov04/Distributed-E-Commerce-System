package dto;

import java.math.BigDecimal;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "requestDTO")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestDTO {

    @XmlElement public String korisnickoIme;
    @XmlElement public String sifra;
    @XmlElement public String ime;
    @XmlElement public String prezime;
    @XmlElement public String adresa;
    @XmlElement public BigDecimal iznos;
    @XmlElement public Integer idKor;
    @XmlElement public Integer idG;
    @XmlElement public Integer idArt;
    @XmlElement public Integer idKat;
    @XmlElement public BigDecimal cena;
    @XmlElement public BigDecimal popust;
    @XmlElement public Integer kolicina;
    @XmlElement public String opis;

    public RequestDTO() {}
}