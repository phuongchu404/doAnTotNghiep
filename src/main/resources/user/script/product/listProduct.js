$(document).ready(function () {

    setMenuBanner();
    $("#orderBy").val("0");
    $("#filterBy").val("0");

    $("body").on("click", ".close-btn-alert", function () {
        $('.alert').removeClass("show");
        $('.alert').addClass("hide");
    });

    $("#orderBy").css({
        "display": "block"
    });

    $("#searchStr").val("");
    $("#filterBy").css({
        "display": "block"
    });
    //load init

    var idCategoryLoad = $("#idCategoryLoad").val();
    if (idCategoryLoad != 0) {
        loadData(null, 1, idCategoryLoad, null, null);
        $("#table-category #" + idCategoryLoad).addClass('chosed');
        $("#table-category #" + idCategoryLoad).addClass('selected-category');
        var title = $("#table-category #" + idCategoryLoad).children('td:nth-child(2)').text();
        $('#title-banner').find('h2').html("Danh mục sản phẩm");
        $('#title-banner').find('p').html("Trang chủ > " + title);

    } else {
        loadData(null, 1, null, null, null);

    }

    $("body").on("change", "#orderBy", function () {
        var txtSearch = $("#searchStr").val();
        var orderType = $("#orderBy").val();
        var filterType = $("#filterBy").val();
        var id_category = $('.table-categoty').find('.chosed').attr('id');
        if (orderType != 0) {
            loadData(txtSearch, 1, id_category, orderType, filterType);

        } else {
            loadData(txtSearch, 1, id_category, null, filterType);

        }
    });

    $("body").on("change", "#filterBy", function (e) {
        var txtSearch = $("#searchStr").val();
        var orderType = $("#orderBy").val();
        var filterType = $("#filterBy").val();
        var id_category = $('.table-categoty').find('.chosed').attr('id');
        if (filterType != 0) {
            loadData(txtSearch, 1, id_category, orderType, filterType);

        } else {
            loadData(txtSearch, 1, id_category, orderType, null);

        }
    });

    $("body").on("click", ".row-left-sidebar", function () {
        $("#orderBy").val("0");
        $("#filterBy").val("0");
        $('#searchStr').val("");

        $('.table-categoty').find('.selected-category').removeClass('selected-category');
        $('.table-categoty').find('.chosed').removeClass('chosed');

        var id_category = $(this).attr('id');
        loadData(null, 1, id_category, null, null);

        $(this).addClass('chosed');
        $(this).addClass('selected-category');
        var title = $(this).children('td:nth-child(2)').text();
        $('#title-banner').find('h2').html("Danh mục sản phẩm");
        $('#title-banner').find('p').html("Trang chủ > " + title);
    });

    $("body").on("click", ".pagination li a", function (event) {
        event.preventDefault();
        var page = $(this).attr('data-page');
        var id_category = $('.table-categoty').find('.chosed').attr('id');

        var txtSearch = $("#searchStr").val();
        if (txtSearch != "") {
            loadData(txtSearch, page, id_category, $("#orderBy").val(), $("#filterBy").val());

        } else {
            loadData(null, page, id_category, $("#orderBy").val(), $("#filterBy").val());

        }

    });

    $("body").on("click", "#search", function (e) {
        $("#orderBy").val("0");
        $("#filterBy").val("0");
        $('.table-categoty').find('.selected-category').removeClass('selected-category');
        var txtSearch = $("#searchStr").val();
        if (txtSearch != "") {
            loadData(txtSearch, 1, null, null, null);

        } else {
            loadData(null, 1, null, null, null);

        }
    });

    $("body").on("keyup", "#searchStr", function (e) {
        $("#orderBy").val("0");
        $("#filterBy").val("0");
        $('#search').click();
    });



    function loadData(searchStr, page, id_category, typeOrder, filterType) {
        $.ajax({
            url: "/bounty-sneaker/all-product",
            type: "GET",
            data: {
                searchStr: searchStr,
                page: page,
                id_category: id_category,
                typeOrder: typeOrder,
                filterType: filterType
            },
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: function (result) {
                var html = '';
                if (result == null) {
                    $("#product-container").html(`<h4 class="text-primary">Lỗi kết nối cơ sở dữ liệu</h4>`);
                    return;
                }
                if ((result.products.length == 0 || result.products == null) && searchStr != null &&
                    searchStr != "") {
                    html += `<div style="margin:auto">
                            <img src="/user/img/notFoundProduct.png" width="300" style="margin:auto"/>
                            <br/>
                            <p style="text-align:center; margin: 25px 0px 30px;">Không tìm thấy sản phẩm phù hợp!</p>
                        </div>`;
                    $("#load-pagination").html("");
                } else if ((result.products.length == 0 || result.products == null) && id_category != null &&
                    id_category != "") {
                    html += `<div style="margin:auto">
                            <img src="/user/img/notFoundProduct.png" width="300" style="margin:auto"/>
                            <br/>
                            <p style="text-align:center; margin: 25px 0px 30px;">Không có sản phẩm nào trong danh mục!</p>
                        </div>`;
                    $("#load-pagination").html("");
                } else {
                    $.each(result.products, function (index, item) {
                        html += `<div class="col-lg-4 col-sm-6 box-single-product" onclick = "detail(${item.id})">
                                <input type="hidden" id="view_${item.id}" name="custId" value="${item.seo}">
                                <div class="single_product_item">
                                    <img class="img-product" src="/upload/${item.avatar}"  alt="">
                                    <div class="single_product_text">
                                        <h4>${item.title}</h4>

                                        <h5>${item.minPrice.toLocaleString('it-IT', {style: 'currency',currency: 'VND'})} - 
                                        ${item.maxPrice.toLocaleString('it-IT', {style: 'currency',currency: 'VND'})}</h5>
                                        
                                        <div class="star-rating" title="${item.starReviews==0?100:item.starReviews/5*100}%">
                                            <div class="back-stars">
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>
                                                <i class="fa fa-star" aria-hidden="true"></i>

                                                <div class="front-stars" style="width: ${item.starReviews==0?100:item.starReviews/5*100}" id="${item.id}">
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                    <i class="fa fa-star" aria-hidden="true"></i>
                                                </div>
                                            </div>
                                        </div>

                                   

                                        <div class="my-cart border rounded-circle" title="Thêm sản phẩm vào giỏ" 
                                            onclick="event.stopPropagation();addProductToCart(${item.id})">
                                        <i class="fas fa-shopping-cart"></i>
                                    </div>
                                </div>
                            </div>
                        </div>`;
                    });
                    //create pagination
                    var pagination_string = "";
                    var currentPage = result.currentPage;
                    var totalPage = result.totalPage;


                    if (currentPage > 1) {
                        var previousPage = currentPage - 1;
                        pagination_string += `<li class="page-item">
                                            <a href="" class="page-link" data-page="${previousPage}">
                                                <i class="fas fa-angle-double-left" style="font-size:18px"></i>
                                            </a>
                                        </li>`;
                    }

                    for (i = 1; i <= totalPage; i++) {
                        if (i == currentPage) {
                            pagination_string += `<li class="page-item active">
                                                <a href="" class="page-link" data-page=${i}>${currentPage}</a>
                                            </li>`;
                        } else if (i >= currentPage - 3 && i <= currentPage + 4) {
                            pagination_string += `<li class="page-item">
                                                <a href="" class="page-link" data-page=${i}>${i}</a>
                                            </li>`;
                        }
                    }

                    if (currentPage > 0 && currentPage < totalPage) {
                        var nextPage = currentPage + 1;
                        pagination_string += `<li class="page-item">
                                            <a href="" class="page-link"  data-page=${nextPage}>
                                                <i class="fas fa-angle-double-right" style="font-size:18px"></i>
                                            </a>
                                        </li>`;
                    }
                    $("#load-pagination").html(pagination_string);
                }
                $("#product-container").html(html);
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log(errorMessage);
            }

        });
        $(".clear-rating").remove();
        $(".rating-container").find(".caption").remove();

    }

});

function setMenuBanner() {
    var titlebanner = '';
    $("#img-banner").html('<img src="/user/img/my-image/banner/product1.png" alt="" width="350">');
    titlebanner += '<h2>Sản phẩm</h2>';
    titlebanner += '<p> Trang chủ <span>></span> Sản phẩm </p>';
    $("#title-banner").html(titlebanner);

    $("#mainNav li").each(function (index) {
        $(this).removeClass("my-menu-active");
    });

    $("#menu-product").addClass("my-menu-active");
}


function detail(id_product) {
    window.location.href = '/bounty-sneaker/detail-product/' + $('#view_' + id_product).val();
};

function loadNewProduct() {
    $.ajax({
        url: "/bounty-sneaker/new-product",
        type: "GET",
        data: {},
        dataType: "json",
        contentType: "application/json",
        success: function (jsonResult) {
            var html = '';
            $.each(jsonResult, function (index, value) {
                html += '<div class="single_product_item" >';
                html += '     <img class="img-product" src="/upload/' + value.avatar + '" alt="' +
                    value.title + '">';
                html += '     <div class="single_product_text">';
                html += '         <h4>' + value.title + '</h4>';
                html += '         <h3>' + value.price.toLocaleString('it-IT', {
                    style: 'currency',
                    currency: 'VND'
                }) + '</h3>';
                html +=
                    '         <div class="my-cart border rounded-circle" title="Thêm sản phẩm vào giỏ">';
                html += '             <i class="fas fa-shopping-cart"></i>';
                html += '         </div>';
                html += '     </div>';
                html += ' </div>';
            });
            $('#list-new-product').html(html);
        },
        error: function (jqXhr, textStatus, errorMessage) {
            console.log(errorMessage);
        }
    });
}