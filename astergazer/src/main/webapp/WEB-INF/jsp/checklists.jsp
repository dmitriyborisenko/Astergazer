<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="title" scope="request">
	<spring:message code="checklists.title"/>
</c:set>
<jsp:include page="header.jsp"/>
<jsp:include page="checklistsVariables.jsp"/>
<script src="<c:url value="/js/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/js/jquery-ui.js" />"></script>
<script src="<c:url value="/js/spin.min.js" />"></script>
<script src="<c:url value="/js/messageDialog.js" />"></script>
<script src="<c:url value="/js/jstree.min.js" />"></script>
<script src="<c:url value="/js/jstreegrid.js" />"></script>
<script src="<c:url value="/js/checklists.js" />"></script>
<div class="div-sub-menu-wrapper">
	<div class="div-sub-menu">
		<ul id="ul-menu">
			<li id="li-mm-settings"><a href="<c:url value="/settings" />"><spring:message code="common.settings"/></a></li>
			<li id="li-mm-map"><a href="<c:url value="/mapping" />"><spring:message code="common.dialplanMap"/></a></li>
		</ul>
	</div>
	<div class="div-additional-info"></div>
</div>
<div class="div-middle-wrapper">
	<div class="div-panel-checklists">
		<div class="div-checklists-panel-caption">
			<label><spring:message code="checklists.lists"/></label>
		</div>
		<div class="div-checklists-panel-header">
			<button id="button-add-checklist" class="ui-button button-pic button-add-checklist"
							onclick="addChecklist()" title="<spring:message code="checklists.addList"/>"></button>
			<button id="button-add-entry" class="ui-button button-pic button-add-checklist-entry"
							onclick="addEntryToCurrentChecklist()" title="<spring:message code="checklists.addEntry"/>"></button>
			<button id="button-edit" class="ui-button button-pic button-edit"
							onclick="editCurrentTreeNode()" title="<spring:message code="checklists.edit"/>"></button>
			<button id="button-delete" class="ui-button button-pic button-delete"
							onclick="deleteCurrentTreeNode()" title="<spring:message code="checklists.delete"/>"></button>
		</div>
		<div class="div-checklists-tree-subwrapper">
			<div id="div-checklists-tree"></div>
		</div>
	</div>
</div>
<div id="dialog-checklist">
	<label><spring:message code="checklists.listName"/>:</label>
	<input id="input-checklist-name" type="text"/>
</div>
<div id="dialog-entry">
	<label><spring:message code="checklists.entryControlValue"/>:</label>
	<br/>
	<input id="input-entry-controlvalue" type="text"/>
	<br/>
	<label><spring:message code="checklists.entryReturnValue"/>:</label>
	<br/>
	<input id="input-entry-returnvalue" type="text"/>
</div>
<div id="dialog-message"></div>
<div id="dialog-confirmation"></div>
<jsp:include page="footer.jsp"/>

