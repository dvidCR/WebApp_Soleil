function logout() {
    localStorage.clear();
    const homeUrl = document.body.dataset.homeUrl;
    window.location.href = homeUrl;
}

async function fichar() {
    const response = await fetch('/ficharVista', {
        method: 'POST'
    });

    const result = await response.text();
    alert(result);
	console.log(result);
}