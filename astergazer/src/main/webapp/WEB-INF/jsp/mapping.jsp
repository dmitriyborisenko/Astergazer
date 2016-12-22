<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="title" scope="request">
	<spring:message code="mapping.title" />
</c:set>
<jsp:include page="header.jsp" />
<jsp:include page="mappingVariables.jsp" />
<script src="<c:url value="/js/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/js/jquery-ui.js" />"></script>
<script src="<c:url value="/js/spin.min.js" />"></script>
<script src="<c:url value="/js/messageDialog.js" />"></script>
<script src="<c:url value="/js/jstree.min.js" />"></script>
<script src="<c:url value="/js/jstreegrid.js" />"></script>
<script src="<c:url value="/js/autoCompleteComboBox.js" />"></script>
<script src="<c:url value="/js/mappingScript.js" />"></script>
<script src="<c:url value="/js/mappingDialplan.js" />"></script>
<script src="<c:url value="/js/mapping.js" />"></script>
<div class="div-sub-menu-wrapper">
	<div class="div-sub-menu">
		<ul id="ul-menu">
			<li id="li-mm-settings"><a href="<c:url value="/settings" />"><spring:message code="common.settings" /></a></li>
            <li id="li-mm-checklists"><a href="<c:url value="/checklists" />"><spring:message code="common.checklists" /></a></li>
		</ul>
	</div>
    <div class="div-actions-menu">
        <ul id="ul-actions-menu">
            <li id="li-am-map"><a href="<c:url value="/translator" />"  target="_blank"><spring:message code="mapping.translation" /></a></li>
        </ul>
    </div>
	<div class="div-additional-info"></div>
</div>
<div class="div-middle-wrapper">
	<div class="div-panel-dialplan">
	    <div class="div-panel-caption">
	        <label><spring:message code="mapping.dialplanTree" /></label>
	    </div>
        <div class="div-dialplan-panel-header">
			<button id="button-add-context" class="ui-button"
				onclick="addContext()"><spring:message code="mapping.addCOntext" /></button>
			<button id="button-add-exten" class="ui-button" onclick="addExtensionToCurrentContext()"><spring:message code="mapping.addExtension" /></button>
			<button id="button-edit" class="ui-button" onclick="editCurrentDialplanTreeNode()"><spring:message code="mapping.edit" /></button>
			<button id="button-delete" class="ui-button" onclick="deleteCurrentDialplanTreeNode()"><spring:message code="mapping.delete" /></button>
		</div>
		<div class="div-dialplan-tree-subwrapper">
            <div id="div-dialplan-tree"></div>
        </div>
    </div>
	<div class="div-panel-script-list">
	    <div class="div-panel-caption">
            <label><spring:message code="mapping.scriptList" /></label>
        </div>
		<div class="div-script-panel-header">
			<button id="button-add-script" class="ui-button"
				onclick="addScript()"><spring:message code="mapping.add" /></button>
			<button id="button-edit-script" class="ui-button"
				onclick="editCurrentScript()"><spring:message code="mapping.edit" /></button>
			<button id="button-construct-script" class="ui-button"
                onclick="constructCurrentScript()"><spring:message code="mapping.constructor" /></button>
			<button id="button-delete-script" class="ui-button"
				onclick="deleteCurrentScript()"><spring:message code="mapping.delete" /></button>
		</div>
		<div id="div-script-tree" class="div-script-tree"></div>
	</div>
</div>
<div id="dialog-script">
    <label><spring:message code="mapping.scriptName" />:</label>
    <input id="input-script-name" type="text" />
</div>
<div id="dialog-context">
    <label><spring:message code="mapping.contextName" />:</label>
    <input id="input-context-name" type="text" />
</div>
<div id="dialog-extension">
    <label class="label-dialog"><spring:message code="mapping.extensionName" />:</label>
    <br/>
    <input id="input-extension-name" type="text" class="input-dialog"/>
    <br/>
    <label class="label-dialog"><spring:message code="mapping.script" />:</label>
    <br/>
    <select id="select-script"></select>
    <br/>
    <button class="ui-button button-clear" onclick="clearSelectScript()"><img src="<c:url value="/images/clear.svg" />" alt="<spring:message code="mapping.clear" />"></button>
</div>
<div id="dialog-message"></div>
<div id="dialog-confirmation"></div>
<jsp:include page="footer.jsp" />

