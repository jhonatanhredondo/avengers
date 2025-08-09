<%@ include file="header.jsp"%>
<div class="container my-5">
	<form name="formNewHero" method="post" action="${pageContext.request.contextPath}/Controller?action=add"
		class="insert-form" onsubmit="return validation()">
		<div class="row align-items-center">
			<div class="col-md-2">
				<label for="formHeroName" class="form-label fw-bold">Hero
					Name</label>
			</div>
			<div class="col-md-10">
				<input type="text" class="form-control" name="formHeroName"
					id="formHeroName" placeholder="Insert the hero name">
			</div>
		</div>
		<div class="row align-items-center mt-2">
			<div class="col-md-2">
				<label for="formHeroStatus" class="form-label fw-bold">Hero
					Status</label>
			</div>
			<div class="col-md-10">
				<select class="form-select" name="formHeroStatus"
					id="formHeroStatus">
					<option value="ATIVO" selected>Ativo</option>
					<option value="APOSENTADO">Aposentado</option>
					<option value="FERIDO">Ferido</option>
					<option value="MORTO">Morto</option>
				</select>
			</div>
		</div>
		<div class="row align-items-center mt-2">
			<div class="col-md-2">
				<label for="formPowerName" class="form-label fw-bold">Power
					Name</label>
			</div>
			<div class="col-md-10">
				<input type="text" class="form-control" name="formPowerName"
					id="formPowerName" placeholder="Insert the hero power name">
			</div>
		</div>
		<div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
			<button type="submit" class="btn btn-primary btn-lg btn-lg-custom"
				role="button">Inserir</button>
			<a class="btn btn-secondary btn-lg btn-lg-custom" href="${pageContext.request.contextPath}/Controller"
				role="button">Voltar</a>
		</div>
	</form>
</div>
<%@ include file="footer.jsp"%>