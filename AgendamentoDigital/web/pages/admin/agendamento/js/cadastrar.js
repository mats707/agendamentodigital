function changeValue() {
    var slider = document.getElementById("duracao");
    var output = document.getElementById("spanDuracao");
    output.innerHTML = slider.value;

    slider.oninput = function () {
        output.innerHTML = this.value;
    };
}

