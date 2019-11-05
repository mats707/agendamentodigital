$(document).ready(function () {

    var nameproject = "/AgendamentoDigital";

    $.fn.setCursorPosition = function (pos) {
        this.each(function (index, elem) {
            if (elem.setSelectionRange) {
                elem.setSelectionRange(pos, pos);
            } else if (elem.createTextRange) {
                var range = elem.createTextRange();
                range.collapse(true);
                range.moveEnd('character', pos);
                range.moveStart('character', pos);
                range.select();
            }
        });
        return this;
    };

    $("input#celular").attr("pattern", "(^\([1-9]{2}\) 9[0-9]{4}\-[0-9]{4}$)");
    $("input#celular").attr("maxlength","15");
    $("input#celular").mask('(99) 99999-9999');


});