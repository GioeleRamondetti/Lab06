package it.polito.tdp.meteo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private MeteoDAO m;
	private int min;
	private String ris="";
	public int totale_esterno=99;
	public int tot=0;
	private List<String> soluzione;
	public Model() {
		this.m=new MeteoDAO();
	}

	public MeteoDAO getM() {
		return m;
	}

	// of course you can change the String output with what you think works best
	public double getUmiditaMedia(int mese) {
		double s=this.m.getAvgRilevamentiMese(mese);
		return s;
	}
	
	
public String getRis() {
		return ris;
	}



public String trovaSequenza(int mese) {
		
		List<String> parziale = new LinkedList<String>();
		List<Rilevamento> rilevamentiMilano = m.getAllRilevamentiLocalitaMese(mese, "Milano");
		List<Rilevamento> rilevamentiTorino = m.getAllRilevamentiLocalitaMese(mese, "Torino");
		List<Rilevamento> rilevamentiGenova = m.getAllRilevamentiLocalitaMese(mese, "Genova");
		min = 999999;
		int tot=0;
		
		trovaSequenzaRicorsivo(parziale, 0, mese, rilevamentiMilano, rilevamentiTorino, rilevamentiGenova, tot);
		
		String ris = ""+totale_esterno+"  "+soluzione.size()+"\n"; //fino a qua arriva ma soluzione Ã¨ vuoot
		
		for(String si : soluzione) {
			this.ris=this.ris+si+" ";
		}
		
	
		
		return ris;
	}
	
	public void trovaSequenzaRicorsivo(List<String> parziale, int livello, int mese, List<Rilevamento> rilevamentiMilano, List<Rilevamento> rilevamentiTorino, List<Rilevamento> rilevamentiGenova, int tot){
		
		//controllogiorniMinCitta(parziale)
		/*se metti controllogiorni non funziona provare senza si perchÃ¨ 
		 * ovviamente non conviene cambiare tante volte cittÃ  controllare quel metodo
		 */
		
		if(livello==15) {
			
			if(tot<min && controllogiorniMinCitta(parziale)) {
				min=tot;

				soluzione = new LinkedList<String>(parziale);
				totale_esterno=tot;
			}
			
		} else {
			
			for(int i=0;i<3;i++) {
				
				if(i==0) {
					parziale.add("Milano");
					tot=tot+rilevamentiMilano.get(livello).getUmidita();
				}
				if(i==1) {
					parziale.add("Torino");
					tot=tot+rilevamentiTorino.get(livello).getUmidita();
				}
				if(i==2) {
					parziale.add("Genova");
					tot=tot+rilevamentiGenova.get(livello).getUmidita();
				}
				
				if(livello>=1) {
					if(parziale.get(livello-1).compareTo(parziale.get(livello))!=0) {
						tot=tot+COST;
					}
				}
								
				if(controlloGiorniMaxCitta(parziale)) {
					
					trovaSequenzaRicorsivo(parziale, livello+1, mese, rilevamentiMilano, rilevamentiTorino, rilevamentiGenova, tot);
					
					
					if(parziale.get(parziale.size()-1).compareTo("Milano")==0) {
						tot=tot-rilevamentiMilano.get(parziale.size()-1).getUmidita();
					}
					if(parziale.get(parziale.size()-1).compareTo("Torino")==0) {
						tot=tot-rilevamentiTorino.get(parziale.size()-1).getUmidita();
					}
					if(parziale.get(parziale.size()-1).compareTo("Genova")==0) {
						tot=tot-rilevamentiGenova.get(parziale.size()-1).getUmidita();
					}
					
					if(livello>=1) {
						if(parziale.get(livello-1).compareTo(parziale.get(livello))!=0) {
							tot=tot-COST;
						}
					}
					
					parziale.remove(parziale.size()-1);
					
				} else {
					
					if(parziale.get(parziale.size()-1).compareTo("Milano")==0) {
						tot=tot-rilevamentiMilano.get(parziale.size()-1).getUmidita();
					}
					if(parziale.get(parziale.size()-1).compareTo("Torino")==0) {
						tot=tot-rilevamentiTorino.get(parziale.size()-1).getUmidita();
					}
					if(parziale.get(parziale.size()-1).compareTo("Genova")==0) {
						tot=tot-rilevamentiGenova.get(parziale.size()-1).getUmidita();
					}
					
					if(livello>=1) {
						if(parziale.get(livello-1).compareTo(parziale.get(livello))!=0) {
							tot=tot-COST;
						}
					}
					
					parziale.remove(parziale.size()-1);
				}
				
			} 
			
			
		}
		
 }

	private boolean controlloGiorniMaxCitta(List<String> parziale) {
		
		int cntMilano=0;
		int cntTorino=0;
		int cntGenova=0;
		
		boolean ok=true;
		
		for(int i=0;i<parziale.size();i++) {
			if(parziale.get(i).compareTo("Milano")==0) {
				cntMilano++;
			}
			if(parziale.get(i).compareTo("Torino")==0) {
				cntTorino++;
			}
			if(parziale.get(i).compareTo("Genova")==0) {
				cntGenova++;
				
			}
		}
			
			
		if(cntMilano<=NUMERO_GIORNI_CITTA_MAX && cntTorino<=NUMERO_GIORNI_CITTA_MAX && cntGenova<=NUMERO_GIORNI_CITTA_MAX) {
			ok = true;
		} else {
			ok = false;
		}

		
		return ok;
	}

	private boolean controllogiorniMinCitta(List<String> parziale) {
		
		boolean ok = true;
		
		if(parziale.get(0).compareTo(parziale.get(1))==0 && parziale.get(1).compareTo(parziale.get(2))==0){
			
		} else {
			ok=false;
		}
		
		for(int i=0;i<parziale.size()-3;i++) {
			if(i>=1) {
				if(parziale.get(i-1).compareTo(parziale.get(i))!=0) {
					for(int j=i;j<i+3;j++) {
						if(parziale.get(j).compareTo(parziale.get(i))!=0) {
							ok=false;
						}
					}
			}
			}	
		
		}
		
		if(parziale.get(14).compareTo(parziale.get(13))==0 && parziale.get(13).compareTo(parziale.get(12))==0){
			
		}	else {
			ok=false;
		}
		
		return ok;
	}

	private String cercaminimo_giorno(int livello,int mese,int conta,String localita) {
		
		List<Rilevamento> rilevazioniMilano=new ArrayList<Rilevamento>(this.m.get15ggRilevamentiLocalitaMese(mese, "Milano")); 
		List<Rilevamento> rilevazioniTorino=new ArrayList<Rilevamento>(this.m.get15ggRilevamentiLocalitaMese(mese, "Torino")); 
		List<Rilevamento> rilevazioniGenova=new ArrayList<Rilevamento>(this.m.get15ggRilevamentiLocalitaMese(mese, "Genova")); 
		String ris="";
		int surplusM=0;
		int surplusG=0;
		int surplusT=0;
		if(localita.compareTo("Milano")==0){
			surplusM=100;
		}
		if(localita.compareTo("Torino")==0){
			surplusT=100;
		}
		if(localita.compareTo("Genova")==0){
			surplusG=100;
		}
		if(rilevazioniMilano.get(livello).getUmidita()+surplusM>rilevazioniGenova.get(livello).getUmidita()+surplusG) {
			if(rilevazioniGenova.get(livello).getUmidita()+surplusG>rilevazioniTorino.get(livello).getUmidita()+surplusT) {
				if(conta==6 && localita.compareTo("Torino")==0) {
					ris="Genova";
				}else {
					ris="Torino";
				}
			}else {
				if(conta==6 && localita.compareTo("Genova")==0) {
					ris="Torino";
				}else {
					ris="Genova";
				}
			}
		}else {
			if(rilevazioniMilano.get(livello).getUmidita()+surplusM>rilevazioniTorino.get(livello).getUmidita()+surplusT) {
				if(conta==6 && localita.compareTo("Torino")==0) {
					ris="Milano";
				}else {
					ris="Torino";
				}
			}else {
				if(conta==6 && localita.compareTo("Milano")==0) {
					ris="Torino";
				}else {
					ris="Milano";
				}
			}
		}
		return ris;
	}
	
	}
