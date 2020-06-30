package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import Model.*;

public class UsuarioDAL extends Conn {
	public Usuario insert(Usuario us) {
		try {
			UsuarioDAL usDal = new UsuarioDAL();
			if (usDal.checkCpf(us) && usDal.checkEmail(us) && usDal.checkTotal(us.getEvento_id()) && checkDate(us.getEvento_id())) {
				// double checking here to make sure
				super.abrir();
				PreparedStatement stmt = getCnn().prepareStatement("INSERT INTO participante VALUES (null, ?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, us.getNome());
				stmt.setString(2, us.getCpf());
				stmt.setString(3, us.getEmail());
				stmt.setInt(4, us.getEvento_id());
				stmt.executeUpdate();
				ResultSet id = stmt.getGeneratedKeys();
				if (id.next())
					us.setId(id.getInt(1));
				return us;
			} else
				return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			super.fechar();
		}
	}

	public boolean update(Usuario us) {
		try {
			super.abrir();
			PreparedStatement stmt = getCnn().prepareStatement(
					"UPDATE participante SET nome = ?, cpf = ?, email = ? WHERE id = ? AND event_id = ?");
			stmt.setString(1, us.getNome());
			stmt.setString(2, us.getCpf());
			stmt.setString(3, us.getEmail());
			stmt.setInt(4, us.getId());
			stmt.setInt(5, us.getEvento_id());
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			super.fechar();
		}
	}

	public Usuario get(int id) {
		try {
			super.abrir();
			Usuario us = new Usuario();
			PreparedStatement stmt = getCnn().prepareStatement("SELECT * FROM participante WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				us.setNome(rs.getString("nome"));
				us.setCpf(rs.getString("cpf"));
				us.setEmail(rs.getString("email"));
				us.setEvento_id(rs.getInt("event_id"));
				us.setId(rs.getInt("id"));
				return us;
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

	public ArrayList<Usuario> getAll() {
		try {
			super.abrir();
			ArrayList<Usuario> usL = new ArrayList<Usuario>();
			Usuario us;
			PreparedStatement stmt = getCnn().prepareStatement("SELECT * FROM participante");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				us = new Usuario();
				us.setNome(rs.getString("nome"));
				us.setCpf(rs.getString("cpf"));
				us.setEmail(rs.getString("email"));
				us.setEvento_id(rs.getInt("event_id"));
				us.setId(rs.getInt("id"));
				usL.add(us);
			}
			return usL;
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
			PreparedStatement stmt = getCnn().prepareStatement("DELETE FROM participante WHERE id = ?");
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

	// return true if ok to proceed
	public boolean checkCpf(Usuario us) {
		try {
			super.abrir();
			PreparedStatement stmt = getCnn()
					.prepareStatement("SELECT * FROM participante WHERE event_id = ? and cpf = ?");
			stmt.setInt(1, us.getEvento_id());
			stmt.setString(2, us.getCpf());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return false;
			} else
				return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			super.fechar();
		}
	}

	// return true if ok to proceed
	public boolean checkEmail(Usuario us) {
		try {
			super.abrir();
			PreparedStatement stmt = getCnn()
					.prepareStatement("SELECT * FROM participante WHERE event_id = ? and email = ?");
			stmt.setInt(1, us.getEvento_id());
			stmt.setString(2, us.getEmail());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return false;
			} else
				return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			super.fechar();
		}
	}

	// return true if ok to proceed
	public boolean checkTotal(int id) {
		try {
			super.abrir();
			int totalNow, max;
			PreparedStatement stmt = getCnn()
					.prepareStatement("SELECT COUNT(id) as total FROM participante WHERE event_id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) { // only using if to prevent exception in case it doesn't exist
				totalNow = rs.getInt("total");
				PreparedStatement stmt2 = getCnn().prepareStatement("SELECT part_max as max FROM evento WHERE id = ?");
				stmt2.setInt(1, id);
				ResultSet rs2 = stmt2.executeQuery();
				if (rs2.next()) {// only using if to prevent exception in case it doesn't exist
					max = rs2.getInt("max");
					if (totalNow < max)
						return true;
					else
						return false;
				} else
					return false;
			} else
				return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			super.fechar();
		}
	}

	// return true if ok to proceed
	public boolean checkDate(int id) {
		try {
			super.abrir();
			LocalDate ldate, ldatetoday;
			int checker;
			ldatetoday = LocalDate.now();
			PreparedStatement stmt = getCnn().prepareStatement("SELECT event_date FROM evento WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ldate = rs.getObject("event_date", LocalDate.class);
				checker = ldate.compareTo(ldatetoday);
				if (checker > 0)
					return true;
				else
					return false;
			} else
				return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			super.fechar();
		}
	}
}
