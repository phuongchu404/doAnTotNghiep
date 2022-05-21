$(document).ready(function () {
	loadCategory(null, 1);
	setActiveMenu();
	showNotifyHeader();

	if ($("#update_role").val() == 'false') {
		$(".update_category").each(function () {
			$(this).hide();
		});
	}
	if ($("#delete_role").val() == 'false') {
		$(".delete_category").hide();
	}

	$("body").on("change", ".btnChangeStatus", function (e) {
		e.preventDefault();
		var status = $(this).prop("checked") == true ? 1 : 0;
		var id = $(this).data("id-item");
		$.post({
			url: "/bounty-sneaker/admin/change-status-category",
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

	/* ENVENT KEY ENTER INPUT SEARCH START */
	$('#input-search-header').on('keydown', function (e) {
		if (e.keyCode == 13) {
			$('#btn_search_header').click();
		}
	});

	$("#btn_search_header").click(function () {
		var txtSearch = $("#input-search-header").val();
		if (txtSearch != "") {
			loadCategory(txtSearch, 1);
		} else {
			loadCategory(null, 1);
		}
	});


	$("body").on("click", ".pagination li a", function (event) {
		event.preventDefault();
		var currentPage = $(this).attr('data-page');
		var txtSearch = $("#input-search-header").val();
		loadCategory(txtSearch, currentPage);
	});

});

function setActiveMenu() {
	console.log("call");
	$(".navbar__list li").each(function () {
		$(this).removeClass("active");
	});
	$(".list-unstyled li").each(function () {
		$(this).removeClass("active");
	});
	$('.list-unstyled #menu--category').addClass("active");
	$('.navbar__list #menu--category').addClass("active");
}


function detail(id) {
	window.location.href = '/bounty-sneaker/admin/category-detail/' + $('#view_' + id).val();
}

function edit(id) {
	window.location.href = '/bounty-sneaker/admin/edit-category/' + $('#edit_' + id).val();
}

function deleteCategory(idCategory) {
	$('#btnAgree').attr("onclick", "deleteConfirmed(" + idCategory + ")");
	showConfirm("Bạn có thực sự muốn xóa danh mục này?", "Có", "Không", true);
};

function deleteConfirmed(idCategory) {
	$('#modalCustomerConfirm').modal('hide');
	$.ajax({
		url: '/bounty-sneaker/admin/delete-category/'+idCategory,
		dataType: "json",
		type: "DELETE",
		contentType: "application/json",
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

function loadCategory(keySearch, currentPage) {
	var update_role = $("#update_role").val();
	var delete_role = $("#delete_role").val();
	$.get({
		url: "/bounty-sneaker/admin/all-category",
		contentType: "application/json", // kieu du lieu gui len server la json
		data: {
			currentPage: currentPage,
			keySearch: keySearch
		},
		success: function (result) {
			var html = '';
			$.each(result.categories, function (index, value) {
				html += `<tr class="tr-shadow">
						   <td class="block-image">
							   <img src="/upload/${value.avatar}" alt="Hình Ảnh Sản Phẩm" />
						   </td>
						<td>
							<span class="block-name-product">${value.name}</span>
						</td>
						<td>
							<span class="block-name-product">${value.description}</span>
						</td>
						<td>
							<span class="block-name-product">${value.seo}</span>
						</td>
						
						<td>
							<span>
							<!-- Rounded switch -->
								<label class="switch"  ${update_role=='false'?'data-toggle="tooltip" data-placement="top" title="Bạn không có quyền truy cập chức năng này"':''}>
									<input type="checkbox" data-id-item="${value.id}" class="btnChangeStatus" 
									${update_role=='true'?"":"disabled"}  ${value.status==true? "checked" : ""}>
									<span class="slider round"></span>
								</label>    
							</span>
						</td>
			  
						<td>
							<div class="table-data-feature pr-4">
								<input type="hidden" id="view_${value.id}" name="custId" value="${value.seo}">
								<input type="hidden" id="edit_${value.id}" name="custId" value="${value.seo}">
								<input type="button" class="btn btn-outline-info mx-1" value="Xem" onclick="detail(${value.id})">
								<input type="button" class="btn btn-outline-success mx-1" value="Sửa" onclick="edit(${value.id})"
									${update_role=='true'?"":"disabled"} 
									${update_role=='false'?'data-toggle="tooltip" data-placement="top" title="Bạn không có quyền truy cập chức năng này"':''}>
								<input type="button" class="btn btn-outline-danger mx-1" value="Xóa" onclick="deleteCategory(${value.id})"
									${delete_role=='true'?"":"disabled"} 
									${delete_role=='false'?'data-toggle="tooltip" data-placement="top" title="Bạn không có quyền truy cập chức năng này"':''}>
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
				pagination_string += `	<li class="page-item">
										<a href="" class="page-link" data-page=${previousPage }>
											<i class="fas fa-angle-double-left" style="font-size:18px"></i>
										</a>
									</li>`;
			}

			for (i = 1; i <= totalPage; i++) {
				if (i == currentPage) {
					pagination_string += `	<li class="page-item active">
											<a href="" class="page-link" data-page=${i}>${currentPage}</a>
										</li>`;
				} else if (i >= currentPage - 3 && i <= currentPage + 4) {
					pagination_string += `	<li class="page-item">
											<a href="" class="page-link" data-page=${i}>${i}</a>
										</li>`;
				}
			}

			if (currentPage > 0 && currentPage < totalPage) {
				var nextPage = currentPage + 1;
				pagination_string += `	<li class="page-item">
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