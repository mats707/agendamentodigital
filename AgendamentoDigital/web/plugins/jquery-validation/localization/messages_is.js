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
 * Locale: IS (Icelandic; íslenska)
 */
$.extend( $.validator.messages, {
	required: "Þessi reitur er nauðsynlegur.",
	remote: "Lagaðu þennan reit.",
	maxlength: $.validator.format( "Sl�ðu inn mest {0} stafi." ),
	minlength: $.validator.format( "Sl�ðu inn minnst {0} stafi." ),
	rangelength: $.validator.format( "Sl�ðu inn minnst {0} og mest {1} stafi." ),
	email: "Sl�ðu inn gilt netfang.",
	url: "Sl�ðu inn gilda vefslóð.",
	date: "Sl�ðu inn gilda dagsetningu.",
	number: "Sl�ðu inn tölu.",
	digits: "Sl�ðu inn tölustafi eingöngu.",
	equalTo: "Sl�ðu sama gildi inn aftur.",
	range: $.validator.format( "Sl�ðu inn gildi milli {0} og {1}." ),
	max: $.validator.format( "Sl�ðu inn gildi sem er minna en eða jafnt og {0}." ),
	min: $.validator.format( "Sl�ðu inn gildi sem er stærra en eða jafnt og {0}." ),
	creditcard: "Sl�ðu inn gilt greiðslukortanúmer."
} );
return $;
}));