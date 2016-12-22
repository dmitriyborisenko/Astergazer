<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="title" scope="request">
	<spring:message code="error.error" />
</c:set>
<jsp:include page="errorHeader.jsp"/>
<div class="div-sub-menu_wrapper"></div>
<div class="div-middle-wrapper">
    <h1 class="error-header"><spring:message code="error.error" />: 500</h1>
	<h2 class="error-header"><spring:message code="error.internalServerError" /></h2>
	<hr/>
	<br/>
	<label class="label-error-text"><spring:message code="${errorText}" /></label>
</div>
<jsp:include page="footer.jsp"/>

