function randomlySelectPlayersAndServer() {
    randomlySelectPlayers();
    randomlySelectServer();
}
function randomlySelectPlayers() {
    let selects = document.querySelectorAll('select[name^="gamers"]');
    let gamersIds = [];
    let options = selects[0].querySelectorAll('option:not([disabled]):not([value="empty"])');
    options.forEach(function (option) {
        gamersIds.push(option.value);
    });

    // Randomly select 10 unique players
    let selectedPlayers = [];
    for (let i = 0; i < 10; i++) {
        let randomIndex = Math.floor(Math.random() * gamersIds.length);
        selectedPlayers.push(gamersIds.splice(randomIndex, 1)[0]);
    }

    // Set selected players in the selects
    selects.forEach(function (select, index) {
        let options = select.querySelectorAll('option:not([disabled]):not([value="empty"])');
        options.forEach(function (option) {
            if (selectedPlayers[index] === option.value) {
                option.selected = true;
            } else {
                option.selected = false;
            }
        });
    });
}
function randomlySelectServer() {
    let serverSelect = document.querySelector('select[name="server"]');
    let serverOptions = serverSelect.querySelectorAll('option:not([disabled]):not([value="empty"])');

    // Randomly select a server
    let randomServerIndex = Math.floor(Math.random() * serverOptions.length);
    let selectedServer = serverOptions[randomServerIndex].value;

    // Set selected server in the select
    serverOptions.forEach(function (option) {
        if (option.value === selectedServer) {
            option.selected = true;
        } else {
            option.selected = false;
        }
    });
}