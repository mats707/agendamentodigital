$(document).ready(function () {

    $('.mask-email').mask("A", {
        translation: {
            "A": {pattern: /[\w@\-.+]/, recursive: true}
        }
    });

});

$(document).on('focusin', '.mask', function () {
    var $this = $(this);
    var mask = $this.data('mask');
    makeMask(mask, $this);
});

var makeMask = function (mask, $this) {
    if (mask === 'telefone') {
        $this.inputmask({"mask": "(99) [9] 9999-9999"});
    }
    if (mask === 'email') {
        $this.inputmask({"alias": "email"});
    }
}

$.fn.selectRange = function (start, end) {
    return this.each(function () {
        if (this.setSelectionRange) {
            this.focus();
            this.setSelectionRange(start, end);
        } else if (this.createTextRange) {
            var range = this.createTextRange();
            range.collapse(true);
            range.moveEnd('character', end);
            range.moveStart('character', start);
            range.select();
        }
    });
};