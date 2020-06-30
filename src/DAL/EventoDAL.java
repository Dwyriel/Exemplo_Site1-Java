package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import Model.Evento;

public class EventoDAL extends Conn {
	public Evento insert(Evento ev) {
		try {
			super.abrir();
			EventoDAL evDal = new EventoDAL();
			if (evDal.checkNegative(ev.getQuantiaMax()) == false) { //repeating here for the test & safety
				PreparedStatement stmt = getCnn().prepareStatement("INSERT INTO evento VALUES (null, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, ev.getNome());
				stmt.setObject(2, ev.getData());
				stmt.setInt(3, ev.getQuantiaMax());
				stmt.executeUpdate();
				ResultSet id = stmt.getGeneratedKeys();
				if (id.next())
					ev.setId(id.getInt(1));
				return ev;
			} else
				return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			super.fechar();
		}
	}

	public boolean update(Evento ev) {
		try {
			super.abrir();
			PreparedStatement stmt = getCnn()
					.prepareStatement("UPDATE evento SET nome = ?, event_date = ?, part_max = ? WHERE id = ?");
			stmt.setString(1, ev.getNome());
			stmt.setObject(2, ev.getData());
			stmt.setInt(3, ev.getQuantiaMax());
			stmt.setInt(4, ev.getId());
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			super.fechar();
		}
	}

	public Evento get(int id) {
		try {
			super.abrir();
			Evento ev = new Evento();
			PreparedStatement stmt = getCnn().prepareStatement("SELECT * FROM evento WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ev.setNome(rs.getString("nome"));
				ev.setData(rs.getObject("event_date", LocalDate.class));
				ev.setQuantiaMax(rs.getInt("part_max"));
				ev.setId(rs.getInt("id"));
				return ev;
			} else {
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		} finally {
			super.fechar();
		}
	}

	public ArrayList<Evento> getAll() {
		try {
			super.abrir();
			ArrayList<Evento> evL = new ArrayList<Evento>();
			Evento ev;
			PreparedStatement stmt = getCnn().prepareStatement("SELECT * FROM evento");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ev = new Evento();
				ev.setNome(rs.getString("nome"));
				ev.setData(rs.getObject("event_date", LocalDate.class));
				ev.setQuantiaMax(rs.getInt("part_max"));
				ev.setId(rs.getInt("id"));
				evL.add(ev);
			}
			return evL;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		} finally {
			super.fechar();
		}
	}

	public boolean delete(int id) {
		try {
			super.abrir();
			PreparedStatement stmt = getCnn().prepareStatement("DELETE FROM evento WHERE id = ?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			super.fechar();
		}
	}

	public int totalNow(int id) {
		try {
			super.abrir();
			int total;
			PreparedStatement stmt = getCnn()
					.prepareStatement("SELECT COUNT(id) as partstotal FROM participante WHERE event_id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				total = rs.getInt("partstotal");
				return total;
			} else {
				return 0;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return 0;
		} finally {
			super.fechar();
		}
	}

	public boolean checkNegative(int nMax) {
		if (nMax < 0)
			return true;
		else
			return false;
	}
}
