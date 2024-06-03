const xCheckboxes = document.querySelectorAll("p.p_input_x input");

function handleClick() {
    xCheckboxes.forEach(checkbox => {
        checkbox.addEventListener("change", function () {
            if (this.checked) {
                // Если чекбокс выбран, снимаем выбор с других
                xCheckboxes.forEach(otherCheckbox => {
                    if (otherCheckbox !== this) {
                        otherCheckbox.checked = false;
                    }
                });
            }
        });
    });
}