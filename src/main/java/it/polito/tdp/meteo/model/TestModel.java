package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		System.out.println(m.getUmiditaMedia(12));
		List<Rilevamento> T= new ArrayList<Rilevamento>( m.getM().getRilevamentiMese(5));
		for(int i=0;i<T.size();i++) {
			System.out.println(T.get(i).getUmidita()+" "+T.get(i).getData()+" "+T.get(i).getLocalita());
		}
		m.trovaSequenza(3);
		System.out.println(m.getRis());
		//genova 0+multipli di 3
		//milano 1+multipli di 3
		// torino 2 piu multipli 3 

	}

}
