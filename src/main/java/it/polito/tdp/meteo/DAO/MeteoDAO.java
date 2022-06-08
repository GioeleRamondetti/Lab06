package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE Localita=? ORDER BY data ASC ";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();
		List<Rilevamento> rilevamentimese = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, localita);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}
			for(int i=0;i<rilevamenti.size();i++) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(rilevamenti.get(i).getData());
				int month = cal.get(Calendar.MONTH);
				month=month+1;
				if(month==mese) {
					rilevamentimese.add(rilevamenti.get(i));
				}
			}

			conn.close();
			return rilevamentimese;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public double getAvgRilevamentiLocalitaMese(int k, String string) {
		// TODO Auto-generated method stub
		double avg= 0;
		List<Rilevamento> ril= new ArrayList<Rilevamento>(getAllRilevamentiLocalitaMese(k, string));
		
		for(int i=0;i<ril.size();i++) {
			avg=avg+ril.get(i).getUmidita();
		}
		avg=avg/ril.size();
		return avg;
	}

	public double getAvgRilevamentiMese(int mese) {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();
		List<Rilevamento> rilevamentimese = new ArrayList<Rilevamento>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			double avg=0;
			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}
			for(int i=0;i<rilevamenti.size();i++) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(rilevamenti.get(i).getData());
				int month = cal.get(Calendar.MONTH);
				month=month+1;
				if(month==mese) {
					rilevamentimese.add(rilevamenti.get(i));
				}
			}
			for(int i=0;i<rilevamentimese.size();i++) {
				avg=avg+rilevamentimese.get(i).getUmidita();
			}
			conn.close();
			avg=avg/rilevamentimese.size();
			return avg;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	public List<String> getlocalita(){
		final String sql = "SELECT distinct Localita  FROM situazione ";

		List<String> localita = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String r =rs.getString("Localita");
				localita.add(r);
			}

			conn.close();
			return localita;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public List<Rilevamento> getRilevamentiMese(int mese) {
		String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();
		List<Rilevamento> rilevamentimese = new ArrayList<Rilevamento>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}
			for(int i=0;i<rilevamenti.size();i++) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(rilevamenti.get(i).getData());
				int month = cal.get(Calendar.MONTH);
				month=month+1;
				Calendar cal1 = Calendar.getInstance();
				cal.setTime(rilevamenti.get(i).getData());
				int day = cal.get(Calendar.DAY_OF_MONTH);
				if(month==mese && day<=15) {
					rilevamentimese.add(rilevamenti.get(i));
				}
			}

			conn.close();
			return rilevamentimese;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	public List<Rilevamento> get15ggRilevamentiLocalitaMese(int mese, String localita) {
		final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE Localita=? ORDER BY data ASC ";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();
		List<Rilevamento> rilevamentimese = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, localita);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}
			for(int i=0;i<rilevamenti.size();i++) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(rilevamenti.get(i).getData());
				int month = cal.get(Calendar.MONTH);
				month=month+1;
				Calendar cal1 = Calendar.getInstance();
				cal.setTime(rilevamenti.get(i).getData());
				int day = cal.get(Calendar.DAY_OF_MONTH);
				if(month==mese && day<=15) {
					rilevamentimese.add(rilevamenti.get(i));
				}
			}

			conn.close();
			return rilevamentimese;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
