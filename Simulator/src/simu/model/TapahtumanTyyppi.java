package simu.model;

import simu.framework.ITapahtumanTyyppi;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi implements ITapahtumanTyyppi{

	//S = Saapuminen, P = Poistuminen
	AULA_S, AULA_P, INFO_S, INFO_P, KAUPPA_S, KAUPPA_P, RESEPTI_S, RESEPTI_P, KASSA_S, KASSA_P;

}
