function validateY() {
    return getErrorY() === "";
}

function validateR() {
    return getErrorR() === "";
}

function handlerY() {
    document.getElementsByClassName("error-message")[0].textContent = getErrorY();
}

function handlerR() {
    document.getElementsByClassName("error-message")[0].textContent = getErrorR();
}

function getErrorY() {
    //возвращает строковую ошибку или пустую строку при отсутствии ошибок
    let input_y = document.getElementsByClassName("input_y")[0];
    let y = input_y.value;

    if (y === "") {
        return "Y не должен быть пустым";
    }
    if (isNaN(y)) {
        return "Y должен быть числом";
    }
    if (String(y).length > 6) {
        return "Y должен иметь максимум 4 знака после запятой";
    }
    if (-5 >= y || y >= 3) {
        return "Y на промежутке (-5;3)";
    }
    return "";
}

function getErrorR() {
    //возвращает строковую ошибку или пустую строку при отсутствии ошибок
    let input_r = document.getElementsByClassName("input_r")[0];
    let r = input_r.value;

    if (r === "") {
        return "R не должен быть пустым";
    }
    if (isNaN(r)) {
        return "R должен быть числом";
    }
    if (String(r).length > 6) {
        return "R должен иметь максимум 4 знака после запятой";
    }
    if (1 > r || r >= 4) {
        return "R на промежутке [1;4)";
    }
    return "";
}