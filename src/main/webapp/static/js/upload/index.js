$(function(){
    //上传事件
    $('.upload').off('change');
    $('.upload').on('change', function (event) {
        event.stopPropagation();
        debugger;
        console.log($(this).data("id"));
        importFile($(this),$(this).data("id"));
    });
    function importFile(f, proId) {
        var fileVal = f.val();
        if(fileVal=="")return;
        var tmp = fileVal.split('.');
        var suffix = tmp[tmp.length - 1];
        var fileName = (tmp[0].split("\\"))[tmp[0].split("\\").length - 1];
        suffix = suffix.toLowerCase();


        $.ajaxFileUpload({
            fileElementId: f[0].id,
            url: "/land/fileService/zip/upload",
            type: 'post',
            dataType: 'json',
            success: function(d){
                debugger;
            }
        });

    }


})