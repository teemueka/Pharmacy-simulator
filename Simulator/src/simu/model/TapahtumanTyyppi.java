package simu.model;

import simu.framework.ITapahtumanTyyppi;

/**
 * Enumerated type for the different types of events in the simulation
 * S= Arrival, P = Departure
 */
public enum TapahtumanTyyppi implements ITapahtumanTyyppi{

	//S = Saapuminen, P = Poistuminen
	AULA_S, AULA_P, ASPA_P, KAUPPA_P, RESEPTI_P, KASSA_P;


}
