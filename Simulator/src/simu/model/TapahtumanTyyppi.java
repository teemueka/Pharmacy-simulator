package simu.model;

import simu.framework.ITapahtumanTyyppi;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi implements ITapahtumanTyyppi{

	//S = Saapuminen, P = Poistuminen
	AULA_S, AULA_P, ASPA_P, KAUPPA_P, RESEPTI_P, KASSA_P;


}
