<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- MENU SIDEBAR-->
<aside class="menu-sidebar d-none d-lg-block">
	<div class="logo">
		<a href="${base}/manager/dashboard"> <img src="${base}/manager/images/logo_admin.png" alt="Admin" />
		</a>
	</div>
	<div class="menu-sidebar__content js-scrollbar1">
		<nav class="navbar-sidebar">
			<ul class="list-unstyled navbar__list">
				<c:if test="${statiscalRole.view==true}">
					<li id="menu--dashboard" class="">
						<a href="${base}/bounty-sneaker/admin/dashboard.html">
							<i class="fas fa-tachometer-alt"></i>Thống kê
						</a>
					</li>
				</c:if>

				<c:if test="${categoryRole.view==true}">
					<li id="menu--category" class="">
						<a href="${base}/bounty-sneaker/admin/category.html">
							<i class="fas fa-th-large"></i> Quản lý Danh mục sản phẩm
						</a>
					</li>
				</c:if>

				<c:if test="${productRole.view==true}">
					<li class="has-sub">
						<a class="js-arrow" href="#">
							<i class="fas fa-wine-bottle"></i>Quản lý Sản phẩm</a>
						<ul class="navbar-mobile-sub__list list-unstyled js-sub-list">
							<li id="menu--product" class=""><a href="${base}/bounty-sneaker/admin/product.html">
									<i class="fas fa-clipboard-list"></i></i>Danh sách sản phẩm
								</a></li>
							<li id="menu--review-product" class=""><a
									href="${base}/bounty-sneaker/admin/review-product.html">
									<i class="fas fa-tasks"></i></i>Đánh giá
								</a></li>
						</ul>
					</li>
				</c:if>

				<c:if test="${orderRole.view==true}">
					<li class="has-sub">
						<a class="js-arrow" href="#">
							<i class="fas fa-money-check"></i>Quản lý Đơn hàng</a>
						<ul class="navbar-mobile-sub__list list-unstyled js-sub-list">
							<li id="menu--order" class=""><a href="${base}/bounty-sneaker/admin/order.html">
									<i class="far fa-list-alt"></i>Danh sách đơn hàng
								</a></li>
							<li id="menu--cancel-order" class=""><a
									href="${base}/bounty-sneaker/admin/request-cancel-order.html">
									<i class="fas fa-ban"></i>Yêu cầu hủy đơn hàng
								</a></li>
						</ul>
					</li>
				</c:if>


				<li class="has-sub">
					<a class="js-arrow" href="#">
						<i class="fas fa-blog"></i>Blog</a>
					<ul class="navbar-mobile-sub__list list-unstyled js-sub-list">
						<c:if test="${categoryBlogRole.view==true}">
							<li id="menu--category--blog" class=""><a
									href="${base}/bounty-sneaker/admin/category-blog.html">
									<i class="fas fa-th"></i>Quản lý Danh mục blog
								</a></li>
						</c:if>
						<c:if test="${blogRole.view==true}">
							<li id="menu--blog"><a href="${base}/bounty-sneaker/admin/blog.html">
									<i class="fab fa-blogger"></i>Blog
								</a></li>
						</c:if>
					</ul>
				</li>



				<li class="has-sub">
					<a class="js-arrow" href="#">
						<i class="fas fa-users-cog"></i> Quản lý Tài khoản</a>
					<ul class="navbar-mobile-sub__list list-unstyled js-sub-list">
						<c:if test="${accountRole.view==true}">
							<li id="menu--account" class=""><a href="${base}/bounty-sneaker/admin/account.html">
									<i class="fas fa-user-cog"></i>Danh sách tài khoản
								</a></li>
						</c:if>
						<li id="menu--my-account" class=""> <a href="${base}/bounty-sneaker/admin/my-account.html">
								<i class="fas fa-id-card"></i>Tài khoản của tôi
							</a></li>
					</ul>
				</li>

				<c:if test="${contactRole.view==true}">
					<li id="menu--contact" class=""><a href="${base}/bounty-sneaker/admin/contact.html">
							<i class="fas fa-id-card-alt"></i>Quản lý Liên hệ
						</a></li>
				</c:if>

				<c:if test="${introduceRole.view==true}">
					<li id="menu--introduce" class=""><a href="${base}/bounty-sneaker/admin/introduce.html">
							<i class="fas fa-info"></i>Giới thiệu
						</a></li>
				</c:if>

			</ul>
		</nav>
	</div>
</aside>
<!-- END MENU SIDEBAR-->