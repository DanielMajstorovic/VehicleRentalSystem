
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ include file="includes/head.jsp"%>   
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/feed.css"/>

<body>
    <%@ include file="header.jsp"%>      

    <div class="container mt-4">
        <div class="d-flex flex-wrap justify-content-between align-items-center mb-3">
            <input id="search"
                   class="form-control flex-grow-1 me-3"
                   style="max-width:420px"
                   placeholder="Search…"/>

            <div class="ms-auto">
                <button class="btn btn-success me-2" data-bs-toggle="modal" data-bs-target="#promoModal">
                    <i class="bi bi-megaphone-fill"></i> New promotion
                </button>
                <button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#postModal">
                    <i class="bi bi-newspaper"></i> New post
                </button>
            </div>
        </div>

        <div class="d-flex justify-content-between align-items-center mb-2">
            <div class="btn-group" role="group" id="filterGroup">
                <button type="button" class="btn btn-outline-primary filter-btn active" data-filter="all">All</button>
                <button type="button" class="btn btn-outline-primary filter-btn" data-filter="post">Posts</button>
                <button type="button" class="btn btn-outline-primary filter-btn" data-filter="promotion">Promotions</button>
                <button type="button" class="btn btn-outline-primary filter-btn" data-filter="active-promo">Active Promotions</button>
            </div>

            <span id="countLabel" class="badge bg-secondary"></span>
        </div>

        <div id="cards" class="row g-3"></div>
    </div>

    <%@ include file="includes/feed-modals.jsp"%>

    <script src="${pageContext.request.contextPath}/js/feed.js"></script>
</body>

<%@ include file="footer.jsp"%>