document.addEventListener("DOMContentLoaded", function () {
    // Add JavaScript to enable/disable radio buttons based on the state of the checkbox
    let suddenDeathCheckbox = document.getElementById("suddenDeath");
    let team1WinRadio = document.getElementById("team1WinRadio");
    let team2WinRadio = document.getElementById("team2WinRadio");

    suddenDeathCheckbox.addEventListener("change", function () {
        if (this.checked) {
            team1WinRadio.disabled = false;
            team2WinRadio.disabled = false;
        } else {
            team1WinRadio.disabled = true;
            team2WinRadio.disabled = true;
        }
    });
});