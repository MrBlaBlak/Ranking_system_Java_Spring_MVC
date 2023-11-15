// Funkcja do generowania losowej liczby z zakresu 15-45
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

// Ustaw losową wartość w polu team2eliminacjeId po załadowaniu strony
window.addEventListener('load', function() {
    let team1eliminacjeIdInputs = document.getElementsByName('team1eliminacjeId');
    let team2eliminacjeIdInputs = document.getElementsByName('team2eliminacjeId');
    let team1flagiIdInputs = document.getElementsByName('team1flagiId');
    let team2flagiIdInputs = document.getElementsByName('team2flagiId');

    for (let i = 0; i < team1eliminacjeIdInputs.length; i++) {
        team1eliminacjeIdInputs[i].value = getRandomValue();
        team2eliminacjeIdInputs[i].value = getRandomValue();
        team1flagiIdInputs[i].value = getRandomFlagValue();
        team2flagiIdInputs[i].value = getRandomFlagValue();
    }

    let mapSelect = document.getElementsByName('map')[0]; // Dla jednego pola map
    let team1tytanSelects = document.getElementsByName('team1tytanId');
    let team2tytanSelects = document.getElementsByName('team2tytanId');

    let mapOptions = ['boomtown', 'exo', 'eden', 'drydock', 'angel', 'colony', 'glitch'];
    let titanOptions = ['ion', 'tone', 'monarch', 'northstar', 'ronin', 'legion', 'scorch'];

    mapSelect.value = getRandomOption(mapOptions);

    for (let i = 0; i < team1tytanSelects.length; i++) {
        team1tytanSelects[i].value = getRandomOption(titanOptions);
    }

    for (let i = 0; i < team2tytanSelects.length; i++) {
        team2tytanSelects[i].value = getRandomOption(titanOptions);
    }
});