document.addEventListener('DOMContentLoaded', function () {
    let selects = document.querySelectorAll('select[name^="gamers"]');

    selects.forEach(function (select) {
        select.addEventListener('change', function () {
            let selectedValue = select.value;

            selects.forEach(function (otherSelect) {
                if (otherSelect !== select) {
                    let optionToDisable = otherSelect.querySelector('option[value="' + selectedValue + '"]');

                    if (selectedValue !== "") {
                        optionToDisable.disabled = true;
                    } else {
                        let options = otherSelect.querySelectorAll('option');
                        options.forEach(function (option) {
                            option.disabled = false;
                        });
                    }
                }
            });
        });
    });
});
