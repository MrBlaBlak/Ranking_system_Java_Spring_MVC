document.addEventListener("DOMContentLoaded", function () {
    // Add JavaScript to enable/disable radio buttons based on the state of the checkbox
    var suddenDeathCheckbox = document.getElementById("suddenDeath");
    var team1WinRadio = document.getElementById("team1WinRadio");
    var team2WinRadio = document.getElementById("team2WinRadio");

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