<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="javax.servlet.http.*,Model.*, DAL.*, java.util.ArrayList, java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">

<title>Eventos</title>
</head>
</head>
<body class="text-center">
	<%
		HttpSession s = request.getSession();
	boolean bool;
	if (s.getAttribute("logged") != null) {
		if ((bool = (boolean) s.getAttribute("logged")) == true) {
	%>
	<script>
		alert("Evento Criado!")
	</script>
	<%
		s.setAttribute("logged", null);
	}
	}
	if (s.getAttribute("error") != null) {
		if ((boolean) s.getAttribute("error") == false) {
			String msg = (String) s.getAttribute("msg");
	%>
	<script>
			alert("<%=msg%>")
	</script>
	<%
		s.setAttribute("error", null);
	s.setAttribute("msg", null);
	}
	}
	%>
	<div class="container mt-5 pt-5">
		<div
			class="btn-light border border-bottom-0 border-primary rounded-pill position-relative pb-1 mb-1">
			<div class="mt-2">
				<h2>Novo Evento</h2>
			</div>
			<a href="NewEvent.jsp" class="stretched-link"></a>
		</div>
		<%
			EventoDAL evDal = new EventoDAL();
		ArrayList<Evento> evL = evDal.getAll();
		for (Evento ev : evL) {
			int participantes = evDal.totalNow(ev.getId());
			String data = ev.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		%>
		<div
			class="btn-light border border-bottom-0 border-danger rounded-pill position-relative pb-1 mb-1">
			<div class="mt-2">
				<h2><%=ev.getNome()%></h2>
				<span>Vagas: <%=participantes %>/<%=ev.getQuantiaMax()%></span>
			</div>
			<p>
				Data:
				<%=data%></p>
			<a href="Events?id=<%=ev.getId()%>" class="stretched-link"></a>
		</div>
		<%
			}
		%>
	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
		integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
		crossorigin="anonymous"></script>

</body>
</html>