<%@ page import="model.HeroJavaBeans"%>
<%@ include file="header.jsp"%>
<div class="container my-5">
	<% 
    HeroJavaBeans hero = (HeroJavaBeans) request.getAttribute("heroDetails");
    
    if (hero != null) {
    %>
	<h1>Hero Details</h1>

	<form action="Controller?action=update" method="post">

		<input type="hidden" name="heroId" value="<%= hero.getHeroId() %>">
		<input type="hidden" name="powerId" value="<%= hero.getPowerId() %>">

		<ul class="list-group">
			<li class="list-group-item"><strong>ID:</strong> <%= hero.getHeroId() %></li>

			<li class="list-group-item d-flex align-items-center"><strong>
					<label for="formHeroName" class="me-2 mb-0">Hero:</label>
			</strong> <input type="text" class="form-control flex-grow-1"
				name="formHeroName" id="formHeroName"
				value="<%= hero.getHeroName() %>"></li>

			<li class="list-group-item d-flex align-items-center"><strong>
					<label for="formHeroStatus" class="me-2 mb-0">Status:</label>
			</strong> <select class="form-select flex-grow-1" name="formHeroStatus"
				id="formHeroStatus">
					<option value="ATIVO"
						<%= hero.getHeroStatus().toString().equals("ATIVO") ? "selected" : "" %>>Ativo</option>
					<option value="APOSENTADO"
						<%= hero.getHeroStatus().toString().equals("APOSENTADO") ? "selected" : "" %>>Aposentado</option>
					<option value="FERIDO"
						<%= hero.getHeroStatus().toString().equals("FERIDO") ? "selected" : "" %>>Ferido</option>
					<option value="MORTO"
						<%= hero.getHeroStatus().toString().equals("MORTO") ? "selected" : "" %>>Morto</option>
			</select></li>

			<li class="list-group-item"><strong>Power ID:</strong> <%= hero.getPowerId() %></li>

			<li class="list-group-item d-flex align-items-center"><strong>
					<label for="formPowerName" class="me-2 mb-0">Power:</label>
			</strong> <input type="text" class="form-control flex-grow-1"
				name="formPowerName" id="formPowerName"
				value="<%= hero.getPowerName() %>"></li>
		</ul>

		<div class="mt-3">
			<a href="Controller" class="btn btn-secondary">Voltar</a>

		</div>
		<div class="d-grid gap-2 d-md-flex justify-content-md-end">
			<button type="submit" class="btn btn-success ms-2">Atualizar</button>
		</div>
	</form>
	<div class="d-grid gap-2 d-md-flex justify-content-md-end">
		<form action="Controller?action=delete" method="post"
			onsubmit="return confirm('Tem certeza que deseja excluir este herói?');">
			<input type="hidden" name="heroId" value="<%= hero.getHeroId() %>">
			<input type="hidden" name="powerId" value="<%= hero.getPowerId() %>">
			<div class="mt-3">
				<button type="submit" class="btn btn-danger">Excluir</button>
			</div>
		</form>
	</div>

	<%
    } else {
    %>
	<p class="alert alert-danger">Hero not found.</p>
	<%
    }
    %>
</div>
<%@ include file="footer.jsp"%>