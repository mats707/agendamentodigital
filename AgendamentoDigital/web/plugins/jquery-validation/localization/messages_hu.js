(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["jquery", "../jquery.validate"], factory );
	} else if (typeof module === "object" && module.exports) {
		module.exports = factory( require( "jquery" ) );
	} else {
		factory( jQuery );
	}
}(function( $ ) {

/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: HU (Hungarian; Magyar)
 */
$.extend( $.validator.messages, {
	required: "Kötelező megadni.",
	maxlength: $.validator.format( "Legfeljebb {0} karakter hosszú legyen." ),
	minlength: $.validator.format( "Legal�bb {0} karakter hosszú legyen." ),
	rangelength: $.validator.format( "Legal�bb {0} és legfeljebb {1} karakter hosszú legyen." ),
	email: "Érvényes e-mail címnek kell lennie.",
	url: "Érvényes URL-nek kell lennie.",
	date: "D�tumnak kell lennie.",
	number: "Sz�mnak kell lennie.",
	digits: "Csak sz�mjegyek lehetnek.",
	equalTo: "Meg kell egyeznie a két értéknek.",
	range: $.validator.format( "{0} és {1} közé kell esnie." ),
	max: $.validator.format( "Nem lehet nagyobb, mint {0}." ),
	min: $.validator.format( "Nem lehet kisebb, mint {0}." ),
	creditcard: "Érvényes hitelk�rtyasz�mnak kell lennie.",
	remote: "Kérem javítsa ki ezt a mezőt.",
	dateISO: "Kérem írjon be egy érvényes d�tumot (ISO).",
	step: $.validator.format( "A {0} egyik többszörösét adja meg." )
} );
return $;
}));