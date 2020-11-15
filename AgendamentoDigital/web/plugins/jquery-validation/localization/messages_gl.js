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
 * Locale: GL (Galician; Galego)
 */
( function( $ ) {
	$.extend( $.validator.messages, {
		required: "Este campo √© obrigatorio.",
		remote: "Por favor, cubre este campo.",
		email: "Por favor, escribe unha direcci√≥n de correo v·lida.",
		url: "Por favor, escribe unha URL v·lida.",
		date: "Por favor, escribe unha data v·lida.",
		dateISO: "Por favor, escribe unha data (ISO) v·lida.",
		number: "Por favor, escribe un n√∫mero v·lido.",
		digits: "Por favor, escribe s√≥ d√≠xitos.",
		creditcard: "Por favor, escribe un n√∫mero de tarxeta v·lido.",
		equalTo: "Por favor, escribe o mesmo valor de novo.",
		extension: "Por favor, escribe un valor cunha extensi√≥n aceptada.",
		maxlength: $.validator.format( "Por favor, non escribas m·is de {0} caracteres." ),
		minlength: $.validator.format( "Por favor, non escribas menos de {0} caracteres." ),
		rangelength: $.validator.format( "Por favor, escribe un valor entre {0} e {1} caracteres." ),
		range: $.validator.format( "Por favor, escribe un valor entre {0} e {1}." ),
		max: $.validator.format( "Por favor, escribe un valor menor ou igual a {0}." ),
		min: $.validator.format( "Por favor, escribe un valor maior ou igual a {0}." ),
		nifES: "Por favor, escribe un NIF v·lido.",
		nieES: "Por favor, escribe un NIE v·lido.",
		cifES: "Por favor, escribe un CIF v·lido."
	} );
}( jQuery ) );
return $;
}));