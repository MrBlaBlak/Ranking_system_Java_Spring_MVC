
function getRandomData() {
    let team1Elims = document.getElementsByName('team1elims');
    let team2Elims = document.getElementsByName('team2elims');
    let team1Flags = document.getElementsByName('team1flags');
    let team2Flags = document.getElementsByName('team2flags');

    for (let i = 0; i < team1Elims.length; i++) {
        team1Elims[i].value = getRandomValue();
        team2Elims[i].value = getRandomValue();
        team1Flags[i].value = getRandomFlagValue();
        team2Flags[i].value = getRandomFlagValue();
    }

    let mapSelect = document.getElementById("map"); // Dla jednego pola map
    let team1Titans = document.getElementsByName('team1titans');
    let team2Titans = document.getElementsByName('team2titans');

    let mapOptions = ['boomtown', 'exo', 'eden', 'drydock', 'angel', 'colony', 'glitch'];
    let titanOptions = ['ion', 'tone', 'monarch', 'northstar', 'ronin', 'legion', 'scorch'];

    mapSelect.value = getRandomOption(mapOptions);

    for (let i = 0; i < team1Titans.length; i++) {
        team1Titans[i].value = getRandomOption(titanOptions);
    }

    for (let i = 0; i < team2Titans.length; i++) {
        team2Titans[i].value = getRandomOption(titanOptions);
    }
}
function getRandomOption(options) {
    let randomIndex = Math.floor(Math.random() * options.length);
    return options[randomIndex];
}
function getRandomValue() {
    return Math.floor(Math.random() * (45 - 15 + 1)) + 15;
}
function getRandomFlagValue() {
    return Math.floor(Math.random() * 3);
}

