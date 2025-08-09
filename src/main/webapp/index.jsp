<%@ page import="java.util.ArrayList"%>
<%@ page import="model.HeroJavaBeans"%>
<%@ include file="pages/header.jsp"%>
<div class="container my-5">
	<table class="table">
		<thead>
			<tr>
				<th scope="col">Hero</th>
				<th scope="col">Status</th>
				<th scope="col">Power</th>
				<th scope="col"></th>
			</tr>
		</thead>
		<tbody>
			<% 
    ArrayList<HeroJavaBeans> heroes = (ArrayList<HeroJavaBeans>) request.getAttribute("heroesList");
    
    if(heroes != null) {
    	for(HeroJavaBeans hero : heroes) {
    %>
			<tr>
				<td><%= hero.getHeroName() %></td>
				<td><%= hero.getHeroStatus() %></td>
				<td><%= hero.getPowerName() %></td>
				<td><a class="btn btn-primary"
					href="Controller?action=details&heroId=<%= hero.getHeroId() %>"
					role="button">Detalhes</a>
			</tr>
			<%
    	}
    }
    %>
		</tbody>
	</table>
	<div class="d-grid gap-2 d-md-flex justify-content-md-end">
		<a class="btn btn-primary btn-lg btn-lg-custom"
			href="Controller?action=insert" role="button">Inserir</a>
	</div>
</div>
<%@ include file="pages/footer.jsp"%>