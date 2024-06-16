document.addEventListener('DOMContentLoaded', () => {
    const selects = document.querySelectorAll('select[name^="gamers"]');

    selects.forEach(select => {
        select.addEventListener('change', disableSelectedOptions);
    });
});

function randomlySelectPlayersAndServer() {
    randomlySelectPlayers();
    randomlySelectServer();
}

function randomlySelectPlayers() {
    const selects = document.querySelectorAll('select[name^="gamers"]');
    const availableOptions = Array.from(selects[0].querySelectorAll('option:not([disabled]):not([value="empty"])'));
    const gamersIds = availableOptions.map(option => option.value);

    // Randomly select 10 unique players
    const selectedPlayers = getRandomElements(gamersIds, 10);

    // Set selected players in the selects
    selects.forEach((select, index) => {
        updateSelectWithSelectedValue(select, selectedPlayers[index]);
    });

    // Manually call the disabling logic
    disableSelectedOptions();
}

function randomlySelectServer() {
    const serverSelect = document.querySelector('select[name="server"]');
    const serverOptions = Array.from(serverSelect.querySelectorAll('option:not([disabled]):not([value="empty"])'));

    // Randomly select a server
    const selectedServer = getRandomElement(serverOptions).value;

    // Set selected server in the select
    updateSelectWithSelectedValue(serverSelect, selectedServer);
}

function disableSelectedOptions() {
    const selects = document.querySelectorAll('select[name^="gamers"]');
    const selectedValues = Array.from(selects).map(select => select.value);

    selects.forEach(select => {
        Array.from(select.options).forEach(option => {
            if (selectedValues.includes(option.value) && option.value !== select.value) {
                option.disabled = true;
            } else {
                option.disabled = false;
            }
        });
    });
}

function getRandomElements(arr, count) {
    const result = [];
    const _arr = [...arr];
    for (let i = 0; i < count; i++) {
        const randomIndex = Math.floor(Math.random() * _arr.length);
        result.push(_arr.splice(randomIndex, 1)[0]);
    }
    return result;
}

function getRandomElement(arr) {
    const randomIndex = Math.floor(Math.random() * arr.length);
    return arr[randomIndex];
}

function updateSelectWithSelectedValue(select, value) {
    Array.from(select.options).forEach(option => {
        option.selected = option.value === value;
    });
}