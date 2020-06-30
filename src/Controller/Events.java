package Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import DAL.*;
import Model.*;

@WebServlet("/Events")
public class Events extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Events() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id").toString());
		request.setAttribute("id", id);
		// don't think there was a need to do all this, could just redirect directly
		// forwarding request and using getParameter() there rather than getAttribute()
		// TODO Remove and test if time allows
		request.getRequestDispatcher("/Cadastro.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean log;
		Evento ev = new Evento();
		EventoDAL evDal = new EventoDAL();
		ev.setNome(request.getParameter("nome"));
		String dataInput = request.getParameter("data").toString();
		DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		ev.setData(LocalDate.parse(dataInput, f));
		ev.setQuantiaMax(Integer.parseInt(request.getParameter("qMax")));
		HttpSession session = request.getSession();
		if (evDal.checkNegative(ev.getQuantiaMax())) {
			session.setAttribute("logged", false);
			session.setAttribute("msg", "Evento deve ter pelomenos 1 participante.");
			response.sendRedirect(request.getContextPath() + "/NewEvent.jsp");
		} else {
		ev = evDal.insert(ev);
		if (ev != null) {
			log = true;
			session.setAttribute("logged", log);
			response.sendRedirect(request.getContextPath() + "/ListEventos.jsp");
		} else {
			log = false;
		}
		if (log != true) {
			session.setAttribute("logged", log);
			session.setAttribute("msg", "Não foi possivel criar o evento.");
			response.sendRedirect(request.getContextPath() + "/NewEvent.jsp");
		}}
	}

}
