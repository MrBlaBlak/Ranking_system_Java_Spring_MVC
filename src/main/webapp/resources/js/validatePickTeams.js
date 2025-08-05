function validateForm() {
    let gamers = document.getElementsByName("gamersList");
    let server = document.getElementsByName("server")[0];

    for (let i = 0; i < gamers.length; i++) {
        if (gamers[i].value === "empty") {
            alert("Please select all players.");
            return false;
        }
    }

    if (server.value === "empty") {
        alert("Please select a server.");
        return false;
    }

    return true;
}
