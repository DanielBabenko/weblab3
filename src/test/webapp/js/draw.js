let R = 200;
let DEFAULT_R_ = 2;     // значения по умолчанию берутся в случае некорректности этих данных в форме
let DEFAULT_GROUP_NAME = "default";
let canvas = document.getElementById("canvas");
let context = canvas.getContext("2d");
context.font = "12px Verdana";

let centerX = canvas.width / 2;  //в этом мест x=0 с точки зрения математических координат
let centerY = canvas.width / 2;  //в этом мест y=0 с точки зрения математических координат

function drawPoint(x, y, delta = 2) {
    context.rect(x - delta / 2, y - delta / 2, delta, delta);
}

function get_r_() {
    let input_r = document.getElementsByClassName("input_r")[0];

    if (isNaN(input_r.value) || input_r.value < 1 || input_r.value > 4) {
        return DEFAULT_R_;
    }
    return input_r.value;
}

function drawPointForJSF(mathX, mathY, color = "red", delta = 6) {
    let r_ = get_r_();

    let x = centerX + mathX * canvas.width / 12;
    let y = centerY - mathY * canvas.height / 12;

    context.beginPath();
    drawPoint(x, y, delta);
    context.strokeStyle = color;
    context.fillStyle = color;
    context.fill();
    context.stroke();
}

function axesToCanvasCoordinates(xAxes, yAxes) {

    let canvasX = centerX + xAxes;
    let canvasY = centerY - yAxes;

    return { x: canvasX, y: canvasY };
}

function drawAxes() {

    context.beginPath();
    context.lineWidth = 2;
    context.moveTo(0, canvas.height / 2);
    context.lineTo(canvas.width, canvas.height / 2);
    context.moveTo(canvas.width / 2, 0);
    context.lineTo(canvas.width / 2, canvas.height);
    context.strokeStyle = "white";
    context.stroke();

    for (let i = -centerX; i < centerX; i += canvas.width / 24) {
        let scalePos = axesToCanvasCoordinates(i, 0);
        context.beginPath();
        context.moveTo(scalePos.x, centerY + 10);
        context.lineTo(scalePos.x, centerY - 10);
        context.strokeStyle = "white";
        context.stroke();
    }

    for (let j = -centerY; j < centerY; j += canvas.width / 24) {
        let scalePos = axesToCanvasCoordinates(0, j);
        context.beginPath();
        context.moveTo(centerX + 10, scalePos.y);
        context.lineTo(centerX - 10, scalePos.y);
        context.strokeStyle = "white"
        context.stroke();
    }
}

function drawCircle(radius) {
    //четверть окружности по радиусу
    context.moveTo(centerX, centerY);  //(0;0)

    context.arc(centerX, centerY, radius * canvas.width / 12,
        Math.PI, Math.PI * 3 / 2,
        false);
}

function drawRectangle(r) {
    //прямоугольник по сторонам
    //сторона отрицательная => направление влево или вниз по математическим осям
    //сторона положительная => направление вправо или вверх по математическим осям
    context.moveTo(centerX, centerY);  //(0;0)

    context.lineTo(centerX - r * canvas.width / 24, centerY); //(width;0)
    context.lineTo(centerX  - r * canvas.width / 24, centerY + r * canvas.height / 12); //(width;height)
    context.lineTo(centerX, centerY + r * canvas.height / 12); //(0;height)
}

function drawTriangle(r) {
    //прямоугольный треугольник по катетам
    //катеты могут быть отрицательными
    context.moveTo(centerX, centerY);  //(0;0)

    context.lineTo(centerX + r * canvas.width / 24, centerY); //(width;0)
    context.lineTo(centerX, centerY + r * canvas.width / 24);  //(0;height)
}

function drawPlot(r) {
    prepare();
    context.beginPath();

    drawCircle(r);
    drawRectangle(r);
    drawTriangle(r);

    context.closePath();
    context.strokeStyle = "rgb(65,105,225,0.75)";
    context.fillStyle = "rgb(65,105,225,0.75)";
    context.fill();
    context.stroke();
    context.strokeStyle = "white";
    context.fillStyle = "white";
    drawAxes();
}

function prepare(){
    context.clearRect(0, 0, canvas.width, canvas.height);
    context.fillStyle = "rgb(220,220,220,0.85)";
    context.fillRect(0, 0, canvas.width, canvas.height);
    context.strokeStyle = "rgb(220,20,60)";
    context.lineWidth = 5;
    context.strokeRect(0,0,canvas.width,canvas.height);
}

function updateCanvas(event) {
    context.clearRect(0, 0, canvas.width, canvas.height);  //очистка канваса
    const r = parseFloat(document.getElementsByClassName("input_r")[0].value);
    const resultDiv = document.getElementsByClassName("error-message")[0];

    drawPlot(r);

    for (let tr of document.querySelectorAll("#dataTable tr")) {
        let children = tr.children;
        // tagName возвращает название тега в верхнем регистре
        if (children[0].tagName.toLowerCase() === "td" && children[0].innerText !== "" && children[0].innerText !== "") {
            let x_ = children[0].innerText;
            let y_ = children[1].innerText;
            drawPointForJSF(x_, y_,
                (children[3].innerText === "Попал" ? "rgb(0, 250, 154,0.6)" : "rgb(199, 21, 133,0.75)")
            );
        }
    }
}

updateCanvas();

canvas.onclick = (event) => {
    let x = event.pageX - event.target.offsetLeft;
    let y = event.pageY - event.target.offsetTop;

    let mathX = x - centerX;
    let mathY = centerY - y;

    let r_ = get_r_();

    let x_ = mathX / (canvas.width / 12);
    let y_ = mathY / (canvas.width / 12);

    let x2 = Math.round(x_ * 10000) / 10000
    let y2 = Math.round(y_ * 10000) / 10000
    let r2 = Math.round(r_ * 10000) / 10000

    console.log(get_r_());

    if (x_ > 3) {
        document.getElementsByClassName("error-message")[0].textContent = "В этой области графика X за рамками валидного значения. (-5 <= X <= 3)";
    } else if (y_ <= -5 || y_ >= 3) {
        document.getElementsByClassName("error-message")[0].textContent = "В этой области графика Y за рамками валидного значения. (-5 < Y < 3)";
    } else {
        document.getElementsByClassName("error-message")[0].textContent = "";
        addAttempt(
            [
                {name: "x", value: x2.toString()},
                {name: "y", value: y2.toString()},
                {name: "r", value: r2.toString()},
            ]
        )
    }

}