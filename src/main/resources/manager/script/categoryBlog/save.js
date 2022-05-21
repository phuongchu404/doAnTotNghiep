$(document).ready(function () {
    var id_category = $('#id').val();
    showNotifyHeader();

    if (id_category != null && id_category != "") {
        loadDetailForEdit(id_category);
        $("#title-page-update-add").text("Cập nhật danh mục blog");
    }
    setActiveMenu('#menu--category--blog');

    $("#form--upload").validate({
        rules: {
            name: "required",
            avatar: {
                required: function () {
                    if ($('#id').val() != null && $('#id').val() != "")
                        return false;
                    return true;
                }
            },
        },

        messages: {
            name: "Vui lòng nhập tên danh mục blog",
            avatar: "Vui lòng chọn ảnh đại diện blog",
        },

        submitHandler: function (form1) {
            var form = $('#form--upload')[0];
            var data = new FormData(form);

            $.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                url: "/bounty-sneaker/admin/add-update-category-blog",
                data: data,
                processData: false, //prevent jQuery from automatically transforming the data into a query string
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    showAlertMessage("Thành công!", true);
                    $(location).attr('href', "/bounty-sneaker/admin/category-blog.html");
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            });
        }
    });
});


function loadDetailForEdit(idCategory) {
    $.ajax({
        url: "/bounty-sneaker/admin/detail-category-blog",
        type: "get",
        contentType: "application/json", //set data send to server is json
        data: {
            idCategory: idCategory
        },
        dataType: "json", //set data return is json
        success: function (result) {
            $('#id').val(result.id);
            $('#name').val(result.name);
            $('.img--avatar').css("background-image", "url(/upload/" + result.avatar + ")");
        },
        error: function (jqXhr, textStatus, errorMessage) {
            //show error
        }
    });
}