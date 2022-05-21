<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- SPRING FORM -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<!DOCTYPE html>
<html lang="en">

<head>
    <!-- Required meta tags-->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="au theme template">
    <meta name="author" content="Hau Nguyen">
    <meta name="keywords" content="au theme template">

    <!-- Title Page-->
    <title>Blog | ${tileWebsite}</title>
    <link rel="icon" href="${base}/manager/images/logo-asp.net.png">
    <jsp:include page="/WEB-INF/views/common/variable.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/views/manager/layout/style.jsp"></jsp:include>

</head>

<body class="">
    <div class="page-wrapper">
        <!-- HEADER MOBILE-->
        <jsp:include page="/WEB-INF/views/manager/layout/header_mobile.jsp"></jsp:include>
        <!-- END HEADER MOBILE-->

        <!-- MENU SIDEBAR-->
        <jsp:include page="/WEB-INF/views/manager/layout/left_sidebar.jsp"></jsp:include>
        <!-- END MENU SIDEBAR-->

        <!-- PAGE CONTAINER-->
        <div class="page-container">
            <!-- HEADER DESKTOP-->
            <jsp:include page="/WEB-INF/views/manager/layout/header.jsp"></jsp:include>
            <!-- HEADER DESKTOP-->

            <!-- MAIN CONTENT-->
            <div class="main-content">
                <div class="section__content section__content--p30">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- DATA TABLE -->
                                <div class="d-flex ">
                                    <a href="${base}/bounty-sneaker/admin/blog.html" class="btn_back_list"><i
                                            class="fa fa-arrow-left"></i></a> &nbsp
                                    <h3 class="title-5" id="title-page-update-add">Thêm blog </h3>
                                </div>
                                <div class="">
                                    <div class="bg-light p-4">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <form enctype="multipart/form-data" id="form--upload" type="post">

                                                    <input id="id" name="id" value="${id_blog}" hidden="true" />
                                                    <div class="form-row">
                                                        <div class="form-group col-6">
                                                            <label for="category">Danh mục blog<span
                                                                    class="required_field">*</span></label>
                                                            <select class="form-control " id="select-category"
                                                                name="id_category_blog">

                                                            </select>
                                                        </div>

                                                        <div class="form-group col-6">
                                                            <label for="name">Tên blog <span
                                                                    class="required_field">*</span></label>
                                                            <input name="name" autocomplete="off" type="text"
                                                                class="form-control" id="name"
                                                                placeholder="Tên danh mục" required="required" />
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="fileProductAvatar">Avatar <span
                                                                class="required_field">*</span></label>
                                                        <div class="row d-flex justify-content-center mt-4">
                                                            <div class="col-sm-4 imgUp">
                                                                <div class="imagePreview img--avatar"></div>
                                                                <label class="btn btn-primary btn--upload-image">Upload
                                                                    <input type="file"
                                                                        class="uploadFile img uploadAvatar"
                                                                        name="avatar"
                                                                        accept="image/png, image/jpeg, image/jpg"
                                                                        style="width: 0px;height: 0px;overflow: hidden;">
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label for="description">Mô tả <span
                                                                class="required_field">*</span></label>
                                                        <input autocomplete="off" name="description"
                                                            class="form-control" placeholder="Mô tả" id="description"
                                                            required="required" />
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="detail">Chi tiết <span
                                                                class="required_field">*</span></label>
                                                        <textarea row=5 autocomplete="off"
                                                            class="form-control summernote" id="detail" name="detail"
                                                            required="required">
                                                        </textarea>
                                                        <label class="text-danger message_error"
                                                            style="display:none;width:100%" id="detailMsgErr">
                                                            <span class="col-md-9 message-content"></span>
                                                        </label>
                                                    </div>
                                                    <div class="form-group">
                                                        <a class="btn btn-secondary"
                                                            href="${base}/bounty-sneaker/admin/blog.html">Hủy</a>
                                                        <button type="submit" class="btn btn-primary">Lưu</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <jsp:include page="/WEB-INF/views/manager/layout/footer.jsp"></jsp:include>
                    </div>
                </div>
            </div>
        </div>
        <!-- END PAGE CONTAINER-->

    </div>

    <!-- START NOTIFY MODAL -->
    <jsp:include page="/WEB-INF/views/manager/layout/notify.jsp"></jsp:include>
    <!-- START NOTIFI MODAL -->

    <!-- START MESSAGE TO USER -->
    <jsp:include page="/WEB-INF/views/manager/layout/message-to-user.jsp"></jsp:include>
    <!-- START MESSAGE TO USER -->

    <!-- JS-->
    <jsp:include page="/WEB-INF/views/manager/layout/script.jsp"></jsp:include>
    <script src="${base }/manager/script/blog/save-update.js"></script>
</body>

</html>
<!-- end document-->