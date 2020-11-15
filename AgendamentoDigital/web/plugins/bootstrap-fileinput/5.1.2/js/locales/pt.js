/*!
 * FileInput Portuguese Translations
 *
 * This file must be loaded after 'fileinput.js'. Patterns in braces '{}', or
 * any HTML markup tags in the messages must not be converted or translated.
 *
 * @see http://github.com/kartik-v/bootstrap-fileinput
 *
 * NOTE: this file must be saved in UTF-8 encoding.
 */
(function ($) {
    "use strict";

    $.fn.fileinputLocales['pt'] = {
        fileSingle: 'ficheiro',
        filePlural: 'ficheiros',
        browseLabel: 'Procurar &hellip;',
        removeLabel: 'Remover',
        removeTitle: 'Remover ficheiros selecionados',
        cancelLabel: 'Cancelar',
        cancelTitle: 'Abortar envio',
        pauseLabel: 'Parar',
        pauseTitle: 'Parar envio em curso',
        uploadLabel: 'Enviar',
        uploadTitle: 'Enviar ficheiros selecionados',
        msgNo: 'N√£o',
        msgNoFilesSelected: 'Nenhum ficheiro selecionado',
        msgPaused: 'Parado',
        msgCancelled: 'Cancelado',
        msgPlaceholder: 'Selecionar {files} ...',
        msgZoomModalHeading: 'Pr√©-visualizaÁ„o detalhada',
        msgFileRequired: '√â necess·rio selecionar um ficheiro a enviar.',
        msgSizeTooSmall: 'Ficheiro "{name}" (<b>{size} KB</b>) √© demasiado pequeno, tem ser ser maior que <b>{minSize} KB</b>.',
        msgSizeTooLarge: 'Ficheiro "{name}" (<b>{size} KB</b>) excede o tamanho m·ximo permido de <b>{maxSize} KB</b>.',
        msgFilesTooLess: 'Deve selecionar pelo menos <b>{n}</b> {files} para enviar.',
        msgFilesTooMany: 'N√∫mero m·ximo de ficheiros selecionados <b>({n})</b> excede o limite m·ximo de <b>{m}</b>.',
        msgTotalFilesTooMany: 'Pode enviar no m·ximo <b>{m}</b> ficheiros (<b>{n}</b> ficheiros detetados).',
        msgFileNotFound: 'Ficheiro "{name}" n√£o encontrado.',
        msgFileSecured: 'Restri√ß√µes de seguran√ßa impedem a leitura do ficheiro "{name}".',
        msgFileNotReadable: 'Ficheiro "{name}" n√£o pode ser lido.',
        msgFilePreviewAborted: 'Pr√©-visualizaÁ„o abortado para o ficheiro "{name}".',
        msgFilePreviewError: 'Ocorreu um erro ao ler o ficheiro "{name}".',
        msgInvalidFileName: 'Caracteres inv·lidos ou n√£o suportados no nome de ficheiro "{name}".',
        msgInvalidFileType: 'Tipo inv·lido para o ficheiro "{name}". Apenas ficheiros "{types}" s√£o suportados.',
        msgInvalidFileExtension: 'Extens√£o inv·lida para o ficheiro "{name}". Apenas ficheiros "{extensions}" s√£o suportados.',
        msgFileTypes: {
            'image': 'imagem',
            'html': 'HTML',
            'text': 'texto',
            'video': 'v√≠deo',
            'audio': 'audio',
            'flash': 'flash',
            'pdf': 'PDF',
            'object': 'objeto'
        },
        msgUploadAborted: 'O envio do ficheiro foi abortado',
        msgUploadThreshold: 'A processar &hellip;',
        msgUploadBegin: 'A inicializar &hellip;',
        msgUploadEnd: 'Conclu√≠do',
        msgUploadResume: 'A retomar o envio &hellip;',
        msgUploadEmpty: 'N√£o existem dados v·lidos dispon√≠veis para o envio.',
        msgUploadError: 'Erro de Envio',
        msgDeleteError: 'Erro de EliminaÁ„o',
        msgProgressError: 'Erro',
        msgValidationError: 'Erro de ValidaÁ„o',
        msgLoading: 'A enviar ficheiro {index} de {files} &hellip;',
        msgProgress: 'A enviar ficheiro {index} de {files} - {name} - {percent}% completo.',
        msgSelected: '{n} {files} selecionados',
        msgFoldersNotAllowed: 'Arrastar e largar ficheiros apenas. {n} pasta(s) ignoradas.',
        msgImageWidthSmall: 'Largura da imagem "{name}" deve ser pelo menos {size} px.',
        msgImageHeightSmall: 'Altura da imagem "{name}" deve ser pelo menos {size} px.',
        msgImageWidthLarge: 'Largura da imagem "{name}" n√£o pode exceder {size} px.',
        msgImageHeightLarge: 'Altura da imagem "{name}" n√£o pode exceder {size} px.',
        msgImageResizeError: 'N√£ofoi poss√≠vel obter as dimens√µes da imagem para redimensionar.',
        msgImageResizeException: 'Erro ao redimensionar a imagem.<pre>{errors}</pre>',
        msgAjaxError: 'Ocorreu um erro durante a operaÁ„o {operation}. Por favor tente de novo mais tarde.',
        msgAjaxProgressError: '{operation} falhou',
        msgDuplicateFile: 'O ficheiro "{name}" com o mesmo tamanho "{size} KB" j· foi anteriormente selecionado. O ficheiro duplicado foi ignorado.',
        msgResumableUploadRetriesExceeded: 'O envio foi abortado ap√≥s <b>{max}</b> tentativas para o ficheiro <b>{file}</b>. Detalhes do erro: <pre>{error}</pre>',
        msgPendingTime: '{time} restante',
        msgCalculatingTime: 'a calcular o tempo restante',
        ajaxOperations: {
            deleteThumb: 'eliminar ficheiro',
            uploadThumb: 'enviar ficheiro',
            uploadBatch: 'envio de ficheiros em lote',
            uploadExtra: 'envio de ficheiro em formul·rio'
        },
        dropZoneTitle: 'Arrastar e largar ficheiros aqui &hellip;',
        dropZoneClickTitle: '<br>(ou clique para selecionar {files})',
        fileActionSettings: {
            removeTitle: 'Remover ficheiro',
            uploadTitle: 'Enviar ficheiro',
            uploadRetryTitle: 'Voltar a tentar o envio',
            downloadTitle: 'Transferir ficheiro',
            zoomTitle: 'Ver detalhes',
            dragTitle: 'Mover / Reorganizar',
            indicatorNewTitle: 'Ainda N√£o Enviado',
            indicatorSuccessTitle: 'Enviado',
            indicatorErrorTitle: 'Erro de Envio',
            indicatorPausedTitle: 'Envio Parado',
            indicatorLoadingTitle:  'A enviar &hellip;'
        },
        previewZoomButtonTitles: {
            prev: 'Ver ficheiro anterior',
            next: 'Ver pr√≥ximo ficheiro',
            toggleheader: 'Mostrar/esconder cabe√ßalho',
            fullscreen: 'Alternar entre ecr√£ completo',
            borderless: 'Alternar entre modo sem bordas',
            close: 'Fechar pr√©-visualizaÁ„o detalhada'
        }
    };
})(window.jQuery);
