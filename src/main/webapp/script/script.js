/**
 * 
 */

function validation() {
	let heroName = formNewHero.formHeroName.value; 
	let heroStatus = formNewHero.formHeroStatus.value;
	let heroPowerName =  formNewHero.formPowerName.value;
	if(heroName === "" && heroPowerName === "") {
		alert("Campos nome do herói e do poder são obrigatórios!");
		formNewHero.formHeroName.focus();
		return false;
	} else if (heroName === "") {
		alert("Campos nome do herói é obrigatório!");
		formNewHero.formHeroName.focus();
		return false;
	} else if (heroPowerName === "") {
		alert("Campos nome do poder do herói é obrigatório!");
		formNewHero.formHeroName.focus();
		return false;
	} else {
		try {
			document.forms["formNewHero"].submit();		
		} catch(e) {
			console.log(e);
		}
	}
	
}