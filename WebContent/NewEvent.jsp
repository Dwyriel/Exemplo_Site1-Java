<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.http.*,Model.*, DAL.*"%>
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

<title>Novo Evento</title>
</head>
</head>
<body class="text-center">
	<%
		HttpSession s = request.getSession();
	boolean bool;
	if (s.getAttribute("logged") != null) {
		if ((bool = (boolean) s.getAttribute("logged")) == false) {
			String msg = (String) s.getAttribute("msg");
	%>
	<script>
		alert("<%=msg%>")
	</script>
	<%
		s.setAttribute("logged", null);
		s.setAttribute("msg", null);
	}
	}
	%>
	<div class="container">
		<form method="post" action="<%=request.getContextPath()%>/Events">
			<div class="my-3">
				<h1 class="h3 mb-3 font-weight-normal">Adicionar um Evento</h1>
			</div>
			<div class="my-3 text-left">
				<div class="my-2">
					<label for="nome">Nome do Evento: </label> <input type="text"
						id="titulo" class="form-control" placeholder="Nome" name="nome"
						required>
				</div>
				<div class="my-2">
					<label for="data">Data do Evento: </label> <input type="text"
						id="data" name="data" class="form-control"
						placeholder="dd/MM/yyyy" required pattern="[0-3]{1}[0-9]{1}/[0-1]{1}[0-9]{1}/[0-2]{1}[0-1]{1}[0-9]{2}">
				</div>
				<div class="my-2">
					<label for="qMax">Numero maximo de participantes: </label> <input
						type="number" name="qMax" class="form-control"
						placeholder="Numero maximo de participantes" step="1" required>
				</div>
			</div>
			<div class="my-3">
				<button class="btn btn-lg btn-primary btn-block" type="submit">Adicionar</button>
			</div>
			<p class="mt-5 mb-3 text-muted">&copy; 2017-2020</p>
		</form>
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