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
                                <div class="d-flex">
                                    <a href="${base}/bounty-sneaker/admin/blog.html" class="btn_back_list"><i
                                            class="fa fa-arrow-left"></i></a> &nbsp
                                    <h3 class="title-5">Chi ti???t blog</h3>
                                </div>
                                <div class="">
                                    <div class="bg-light p-4">
                                        <table class="table table-bordered">
                                            <tr>
                                                <td>T??n blog</td>
                                                <td>${blog.name}</td>
                                            </tr>
                                            <tr>
                                                <td>???nh ?????i di???n blog</td>
                                                <td> <img src="/upload/${blog.avatar}" width="300" /></td>
                                            </tr>
                                            <tr>
                                                <td>M?? t???</td>
                                                <td>
                                                    ${blog.description}
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Ng??y t???o</td>
                                                <td>
                                                    <fmt:formatDate pattern="dd/MM/yyyy" value="${blog.createdDate}" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Ng?????i t???o</td>
                                                <td>${createdBy.fullname} (${createdBy.username})</td>
                                            </tr>
                                            <tr>
                                                <td>Ng??y s???a</td>
                                                <td>
                                                    <fmt:formatDate pattern="dd/MM/yyyy" value="${blog.updatedDate}" />
                                                </td>
                                            </tr>
                                            <c:if test="${updatedBy!=null }">
                                                <tr>
                                                    <td>Ng?????i s???a</td>
                                                    <td>${updatedBy.fullname} (${updatedBy.username })</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${updatedBy==null }">
                                                <tr>
                                                    <td>Ng?????i s???a</td>
                                                    <td>Danh m???c ch??a th???c hi???n c???p nh???t.</td>
                                                </tr>
                                            </c:if>
                                            <tr>
                                                <td>Seo</td>
                                                <td> ${blog.seo}</td>
                                            </tr>
                                            <tr>
                                                <td>Hot</td>
                                                <td>
                                                    ${blog.isHot==true?"Hot":"B??nh th?????ng"}
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Tr???ng th??i</td>
                                                <td>
                                                    ${blog.status==true?"Hi???n th???":"???n"}
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Chi ti???t danh m???c</td>
                                                <td>${blog.detail}</td>
                                            </tr>
                                        </table>
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
    <script src="${base }/manager/script/blog/detail.js"></script>
</body>

</html>
<!-- end document-->