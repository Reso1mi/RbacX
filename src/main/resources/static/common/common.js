// 展示提示信息
function showMessage(title, msg, isSuccess) {
    if (!isSuccess) {
        msg = msg || '';
    } else {
        msg = msg || '操作成功'
    }
    $.gritter.add({
        title: title,
        text: msg != '' ? msg : "服务器处理异常, 建议刷新页面来保证数据是最新的",
        time: '',
        class_name: (isSuccess ? 'gritter-success' : 'gritter-warning') + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
    });
}

function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};
    $.map(unindexed_array, function (n, i) {
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}
