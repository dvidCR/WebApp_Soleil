function logout() {
    localStorage.clear();
    const homeUrl = document.body.dataset.homeUrl;
    window.location.href = homeUrl;
}