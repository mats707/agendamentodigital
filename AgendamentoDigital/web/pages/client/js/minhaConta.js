var alturaFormulario = document.getElementById("panel").offsetHeight;
var nameproject = getContextPath();

document.getElementById("colImg").style.height = alturaFormulario + "px";

var btnCust = '';
//var btnCust = '<button type="button" class="btn btn-secondary" title="Add picture tags" ' +
//        'onclick="alert(\'Call your custom code here.\')">' +
//        '<i class="fas fa-tags"></i>' +
//        '</button>';
$("#avatar-2").fileinput({
    overwriteInitial: true,
    maxFileSize: 1500,
    showClose: false,
    showCaption: false,
    showBrowse: false,
    browseOnZoneClick: true,
    removeLabel: '',
    removeIcon: '<i class="fas fa-trash-alt"></i>',
    removeTitle: 'Cancel or reset changes',
    elErrorContainer: '#kv-avatar-errors-2',
    msgErrorClass: 'alert alert-block alert-danger',
    defaultPreviewContent: '<img src="' + nameproject + '/images/user_sample/profile-picture.jpg" alt="Your Avatar" style="width: 150px"><h6 class="text-muted">Click to select</h6>',
    layoutTemplates: {main2: '{preview} ' + btnCust + ' {remove} {browse}'},
    allowedFileExtensions: ["jpg", "png", "gif"]
});

function editarDados(element) {
    console.log(element);
    var itemId = element.id;
    console.log(itemId);

    switch (itemId) {
        case "itemNome":
            console.log("Libera Nome");
            document.getElementById("inputName").disabled = false;
            document.getElementById("groupNome").style.display = 'flex';
            break;
        case "itemDataNascimento":
            console.log("Libera Nome");
            document.getElementById("inputDataNasc").disabled = false;
            document.getElementById("groupDataNascimento").style.display = 'flex';
            break;
        case "itemCelular":
            console.log("Libera Nome");
            document.getElementById("inputCelular").disabled = false;
            document.getElementById("groupCelular").style.display = 'flex';
            break;
        default:
            console.log("Inválido");
    }
}