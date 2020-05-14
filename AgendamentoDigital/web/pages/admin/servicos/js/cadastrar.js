$(document).ready(function () {

    var textarea = document.querySelector('textarea');

    textarea.addEventListener('keydown', autosize);

    function autosize() {
        var el = this;
        setTimeout(function () {
            el.style.cssText = 'height:auto; padding:.375rem .75rem';
            // for box-sizing other than "content-box" use:
            // el.style.cssText = '-moz-box-sizing:content-box';
            el.style.cssText = 'height:' + el.scrollHeight + 'px';
        }, 0);
    }
});

function changeValue() {
    var slider = document.getElementById("duracao");
    var output = document.getElementById("spanDuracao");
    output.innerHTML = slider.value;

    slider.oninput = function () {
        output.innerHTML = this.value;
    };
}

