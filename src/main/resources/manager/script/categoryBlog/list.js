$(document).ready(function () {
    loadCategory(null, 1);
    setActiveMenu('#menu--category--blog');
    /* ENVENT KEY ENTER INPUT SEARCH START */
    $('#input-search-header').on('keydown', function (e) {
        if (e.keyCode == 13) {
            $('#btn_search_header').click();
        }
    });
    /* ENVENT KEY ENTER INPUT SEARCH END */

    $("body").on("change", ".btnChangeStatus", function (e) {
        e.preventDefault();
        var status = $(this).prop("checked") == true ? 1 : 0;
        var id = $(this).data("id-item");
        $.post({
            url: "/bounty-sneaker/admin/change-detail-category-blog",
            data: {
                status: status,
                id: id
            },
            dataType: "json",
            success: function (response) {
                if (response == true) {
                    showAlertMessage("Cập nhật trạng thái thành công!", true);
                } else {
                    showAlertMessage("Cập nhật trạng thái thất bại!", false);
                }
            }
        });
    });
});



function loadCategory(keySearch, currentPage) {
    var update_role = $("#update_role").val();
    var delete_role = $("#delete_role").val();
    $.ajax({
        url: "/bounty-sneaker/admin/all-category-blog",
        type: "get",
        contentType: "application/json", // kieu du lieu gui len server la json
        data: {
            currentPage: currentPage,
            keySearch: keySearch
        },
        dataType: "json", // kieu du lieu tra ve tu controller la json
        success: function (result) {
            var html = '';
            $.each(result.listEntity, function (index, value) {
                html += `<tr class="tr-shadow">
                            <td class="block-image">
                                <img src="/upload/${ value.avatar }" alt="Hình Ảnh Sản Phẩm" />
                            </td>
                            <td>
                                <span>${ value.name }</span>
                            </td>
                            <td>
                                <span>${ value.seo }</span>
                            </td>
                            <td>
                                <span>
                                    <!-- Rounded switch -->
                                    <label class="switch">
                                        <input type="checkbox" class="btnChangeStatus" data-id-item="${value.id}" ${value.status==true? "checked" : ""}>
                                        <span class="slider round"></span>
                                    </label>    
                                </span>
                            </td>
                            <td>
                                <div class="table-data-feature pr-4">
                                    <input type="hidden" id="view_${ value.id }" name="custId" value="${ value.seo }">
                                    <input type="hidden" id="edit_${ value.id}" name="custId" value="${ value.seo }">
                                    <input type="button" class="btn btn-outline-info mx-1" value="Xem" onclick="detail(${value.id})">
                                    <input type="button" class="btn btn-outline-success mx-1" value="Sửa" ${update_role == '    true'?"":"hide"}
                                        onclick="edit(${value.id})">
                                    <input type="button" class="btn btn-outline-danger mx-1" value="Xóa" ${update_role == 'true'?"":"hide"} 
                                        onclick="deleteCategory(${value.id})">
                                </div>
                            </td>
                        </tr>
                        <tr class="spacer"></tr>`;
            });



            var totalPage = result.totalPage;
            var currentPage = result.currentPage;
            var pagination_string = '';
            if (currentPage > 1) {
                var previousPage = currentPage - 1;
                pagination_string += `  <li class="page-item">
                                            <a href="" class="page-link" data-page="${previousPage}">
                                                <i class="fas fa-angle-double-left" style="font-size:18px"></i>
                                            </a>
                                        </li>`;
            }

            for (i = 1; i <= totalPage; i++) {
                if (i == currentPage) {
                    pagination_string += `  <li class="page-item active">
                                                <a href="" class="page-link" data-page="${i}">${currentPage}</a>
                                            </li>`;
                } else if (i >= currentPage - 3 && i <= currentPage + 4) {
                    pagination_string += `  <li class="page-item">
                                                <a href="" class="page-link" data-page="${i}">${i}</a>
                                            </li>`;
                }
            }

            if (currentPage > 0 && currentPage < totalPage) {
                var nextPage = currentPage + 1;
                pagination_string += `  <li class="page-item">
                                            <a href="" class="page-link"  data-page=${nextPage}>
                                                <i class="fas fa-angle-double-right" style="font-size:18px"></i>
                                            </a>
                                        </li>`;
            }
            $("#input-search-header").val(keySearch);
            $("#paged--list").html(pagination_string);
            $('#table_data').html(html);
        },
        error: function (jqXhr, textStatus, errorMessage) { // error callback 

        }
    });
}


$("body").on("click", ".pagination li a", function (event) {
    event.preventDefault();
    var txtSearch = $("#input-search-header").val();
    var currentPage = $(this).attr('data-page');
    //load event pagination
    loadCategory(txtSearch, currentPage);
});

/* SEARCH HEADER START */
$("#btn_search_header").click(function () {
    var txtSearch = $("#input-search-header").val();
    if (txtSearch != "") {
        loadCategory(txtSearch, 1);
    } else {
        loadCategory(null, 1);
    }
});
/* SEARCH HEADER END */

function detail(id) {
    window.location.href = '/bounty-sneaker/admin/category-blog-detail/' + $('#view_' + id).val();
}

function edit(id) {
    window.location.href = '/bounty-sneaker/admin/edit-category-blog/' + $('#edit_' + id).val();
}

function deleteCategory(idCategory) {
    $('#btnAgree').attr("onclick", "deleteConfirmed(" + idCategory + ")");
    showConfirm("Bạn có muốn xóa danh mục này không?", "Có", "Không", true);
};

function deleteConfirmed(idCategory) {
    $("#modalCustomerConfirm").modal("hide");
    $.post({
        url: '/bounty-sneaker/admin/delete-category-blog',
        data: {
            idCategory: idCategory
        },
        success: function (result) {
            if (result == true) {
                showAlertMessage("Xóa danh mục thành công!", true);
                loadCategory(null, 1);
            } else {
                showAlertMessage("Không thể xóa danh mục này!", false);
            }
        },
        error: function (jqXhr, textStatus, errorMessage) { // error callback 
            showAlertMessage("Không thể xóa danh mục này!", false);
        }
    });
}