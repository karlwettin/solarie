package se.helsingborg.oppna.solarie.prevalence.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kalle
 * @since 2014-09-17 11:20
 */
public class Anvandare implements Identitfiable {

  private static final long serialVersionUID = 1l;

  private Long identity;

  private String signatur;
  private String namn;
  private String enhet;
  private String profilkod;
  private String epostadress;
  private boolean aktiv;

  private Long modifierad;

  private List<Arende> ägdaÄrenden = new ArrayList<>(100);
  private List<Arende> handlagdaÄrenden = new ArrayList<>(100);
  private List<Arende> registreradeÄrenden = new ArrayList<>(100);
  private List<Arende> modifieradeÄrenden = new ArrayList<>(100);

  private List<Atgard> ägdaÅtgärder = new ArrayList<>(100);


  // getters & setters


  public List<Arende> getModifieradeÄrenden() {
    return modifieradeÄrenden;
  }

  public void setModifieradeÄrenden(List<Arende> modifieradeÄrenden) {
    this.modifieradeÄrenden = modifieradeÄrenden;
  }

  public List<Arende> getRegistreradeÄrenden() {
    return registreradeÄrenden;
  }

  public void setRegistreradeÄrenden(List<Arende> registreradeÄrenden) {
    this.registreradeÄrenden = registreradeÄrenden;
  }

  public List<Arende> getÄgdaÄrenden() {
    return ägdaÄrenden;
  }

  public void setÄgdaÄrenden(List<Arende> ägdaÄrenden) {
    this.ägdaÄrenden = ägdaÄrenden;
  }

  public List<Arende> getHandlagdaÄrenden() {
    return handlagdaÄrenden;
  }

  public void setHandlagdaÄrenden(List<Arende> handlagdaÄrenden) {
    this.handlagdaÄrenden = handlagdaÄrenden;
  }

  public Long getModifierad() {
    return modifierad;
  }

  public void setModifierad(Long modifierad) {
    this.modifierad = modifierad;
  }

  @Override
  public Long getIdentity() {
    return identity;
  }

  @Override
  public void setIdentity(Long identity) {
    this.identity = identity;
  }

  public String getSignatur() {
    return signatur;
  }

  public void setSignatur(String signatur) {
    this.signatur = signatur;
  }

  public String getNamn() {
    return namn;
  }

  public void setNamn(String namn) {
    this.namn = namn;
  }

  public String getEnhet() {
    return enhet;
  }

  public void setEnhet(String enhet) {
    this.enhet = enhet;
  }

  public String getProfilkod() {
    return profilkod;
  }

  public void setProfilkod(String profilkod) {
    this.profilkod = profilkod;
  }

  public String getEpostadress() {
    return epostadress;
  }

  public void setEpostadress(String epostadress) {
    this.epostadress = epostadress;
  }

  public boolean isAktiv() {
    return aktiv;
  }

  public void setAktiv(boolean aktiv) {
    this.aktiv = aktiv;
  }

  public List<Atgard> getÄgdaÅtgärder() {
    return ägdaÅtgärder;
  }

  public void setÄgdaÅtgärder(List<Atgard> ägdaÅtgärder) {
    this.ägdaÅtgärder = ägdaÅtgärder;
  }
}
