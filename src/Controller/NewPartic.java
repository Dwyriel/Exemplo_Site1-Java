package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import DAL.UsuarioDAL;
import Model.Usuario;

@WebServlet("/NewPartic")
public class NewPartic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NewPartic() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/ListEventos.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Usuario us = new Usuario();
		UsuarioDAL usDal = new UsuarioDAL();
		boolean error;
		us.setNome(request.getParameter("nome"));
		us.setCpf(request.getParameter("cpf"));
		us.setEmail(request.getParameter("email"));
		us.setEvento_id(Integer.parseInt(request.getParameter("eventid").toString()));
		Usuario us2 = us;
		us = usDal.insert(us);
		HttpSession session = request.getSession();
		if (us != null) {
			error = false;
			session.setAttribute("error", error);
			session.setAttribute("msg", "Cadastro realizado!");
			response.sendRedirect(request.getContextPath() + "/ListEventos.jsp");
		} else {// only reusing the checks here to send a message back to the page
			if (usDal.checkCpf(us2)) {
				if (usDal.checkEmail(us2)) {
					if (usDal.checkTotal(us2.getEvento_id())) {
						if(usDal.checkDate(us2.getEvento_id()) == false ) {
							error = true;
							session.setAttribute("error", error);
							session.setAttribute("msg", "Não é mais possivel se cadastrar nesse evento!");
							response.sendRedirect(request.getContextPath() + "/Events?id=" + us2.getEvento_id());
						}
					}else {
						error = true;
						session.setAttribute("error", error);
						session.setAttribute("msg", "Limite de participantes atingido!");
						response.sendRedirect(request.getContextPath() + "/Events?id=" + us2.getEvento_id());
					
					}
				} else {
					error = true;
					session.setAttribute("error", error);
					session.setAttribute("msg", "Email ja esta em uso!");
					response.sendRedirect(request.getContextPath() + "/Events?id=" + us2.getEvento_id());
				}
			} else {
				error = true;
				session.setAttribute("error", error);
				session.setAttribute("msg", "CPF ja esta em uso!");
				response.sendRedirect(request.getContextPath() + "/Events?id=" + us2.getEvento_id());
			}
		}
	}
}
