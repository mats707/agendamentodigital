function sweet(title, type, timer) {
    if (title == "") {
        title = "Carregando...";
    }
    if (type == "") {
        type = "info";
    }
    if (timer == null) {
        timer = 1500;
    }
    const Toast = swal.mixin({
        toast: true,
        position: 'center',
        showConfirmButton: false,
        timer: timer
    });
    Toast.fire({
        type: type,
        title: '  ' + title
    });
}