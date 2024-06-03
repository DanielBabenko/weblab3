function handleSubmit(event) {
    let status = event.status; // Can be 'begin', 'complete' and 'success'.
    if (status === 'success') {  //чтобы при отправке формы отрисовка была только после ajax
        updateCanvas();
    }
    else if (status === 'begin') {
        handlerY();
        if (!validateY()) return;
        handlerR();
        return validateR();
    }
}

function handleClear(event) {
    let status = event.status; // Can be 'begin', 'complete' and 'success'.
    if (status === 'success') {  //чтобы при отправке формы отрисовка была только после ajax
        updateCanvas();
    }
}