<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="title" scope="request">
	<spring:message code="configuration.title"/>
</c:set>
<jsp:include page="header.jsp"/>
<jsp:include page="configurationVariables.jsp"/>
<script src="<c:url value="/js/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/js/jquery-ui.js" />"></script>
<script src="<c:url value="/js/spin.min.js" />"></script>
<script src="<c:url value="/js/messageDialog.js" />"></script>
<script src="<c:url value="/js/configuration.js" />"></script>
<div class="div-sub-menu-wrapper">
	<div class="div-sub-menu">
		<ul id="ul-menu">
			<li id="li-mm-map"><a href="<c:url value="/mapping" />"><spring:message code="common.dialplanMap"/></a></li>
			<li id="li-mm-checklists"><a href="<c:url value="/checklists" />"><spring:message code="common.checklists"/></a>
			</li>
		</ul>
	</div>
	<div class="div-actions-menu">
		<ul id="ul-actions-menu">
			<li id="li-am-save"><span onclick="saveAll()"><spring:message code="configuration.save"/></span></li>
		</ul>
	</div>
	<div class="div-additional-info"></div>
</div>
<div class="div-middle-wrapper">
	<label class="label-conf-parameter-caption"><spring:message code="configuration.fastAgiHost"/></label>
	<input class="input-conf input-fastagihost input-dialog" id="input-fastagihost" name="fastAgiHost" type="text"/>
</div>
<div id="dialog-message"></div>
<div id="dialog-confirmation"></div>
<jsp:include page="footer.jsp"/>

