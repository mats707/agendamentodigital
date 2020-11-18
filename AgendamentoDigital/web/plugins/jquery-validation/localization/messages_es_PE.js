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
 * Locale: ES (Spanish; EspaÃ±ol)
 * Region: PE (PerÃº)
 */
$.extend( $.validator.messages, {
	required: "Este campo es obligatorio.",
	remote: "Por favor, llene este campo.",
	email: "Por favor, escriba un correo electrÃ³nico válido.",
	url: "Por favor, escriba una URL válida.",
	date: "Por favor, escriba una fecha válida.",
	dateISO: "Por favor, escriba una fecha (ISO) válida.",
	number: "Por favor, escriba un nÃºmero válido.",
	digits: "Por favor, escriba sÃ³lo dÃ­gitos.",
	creditcard: "Por favor, escriba un nÃºmero de tarjeta válido.",
	equalTo: "Por favor, escriba el mismo valor de nuevo.",
	extension: "Por favor, escriba un valor con una extensiÃ³n permitida.",
	maxlength: $.validator.format( "Por favor, no escriba más de {0} caracteres." ),
	minlength: $.validator.format( "Por favor, no escriba menos de {0} caracteres." ),
	rangelength: $.validator.format( "Por favor, escriba un valor entre {0} y {1} caracteres." ),
	range: $.validator.format( "Por favor, escriba un valor entre {0} y {1}." ),
	max: $.validator.format( "Por favor, escriba un valor menor o igual a {0}." ),
	min: $.validator.format( "Por favor, escriba un valor mayor o igual a {0}." ),
	nifES: "Por favor, escriba un NIF válido.",
	nieES: "Por favor, escriba un NIE válido.",
	cifES: "Por favor, escriba un CIF válido."
} );
return $;
}));