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
 * Locale: ES (Spanish; Español)
 * Region: AR (Argentina)
 */
$.extend( $.validator.messages, {
	required: "Este campo es obligatorio.",
	remote: "Por favor, complet� este campo.",
	email: "Por favor, escribí una dirección de correo v�lida.",
	url: "Por favor, escribí una URL v�lida.",
	date: "Por favor, escribí una fecha v�lida.",
	dateISO: "Por favor, escribí una fecha (ISO) v�lida.",
	number: "Por favor, escribí un número entero v�lido.",
	digits: "Por favor, escribí sólo dígitos.",
	creditcard: "Por favor, escribí un número de tarjeta v�lido.",
	equalTo: "Por favor, escribí el mismo valor de nuevo.",
	extension: "Por favor, escribí un valor con una extensión aceptada.",
	maxlength: $.validator.format( "Por favor, no escribas m�s de {0} caracteres." ),
	minlength: $.validator.format( "Por favor, no escribas menos de {0} caracteres." ),
	rangelength: $.validator.format( "Por favor, escribí un valor entre {0} y {1} caracteres." ),
	range: $.validator.format( "Por favor, escribí un valor entre {0} y {1}." ),
	max: $.validator.format( "Por favor, escribí un valor menor o igual a {0}." ),
	min: $.validator.format( "Por favor, escribí un valor mayor o igual a {0}." ),
	nifES: "Por favor, escribí un NIF v�lido.",
	nieES: "Por favor, escribí un NIE v�lido.",
	cifES: "Por favor, escribí un CIF v�lido."
} );
return $;
}));