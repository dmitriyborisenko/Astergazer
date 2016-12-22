<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script>
    treeControllerUrl = "<c:url value="/mapping/tree" />"
    restControllerUrl = "<c:url value="/mapping/rest" />";
    constructorControllerUrl = "<c:url value="/constructor" />";
    imageUrl = "<c:url value="/images" />"
    
    unknownNodeTypeErrorText = "<spring:message code="common.unknownNodeTypeError" />";
    deleteContextConfirmText = "<spring:message code="mapping.deleteContextConfirm" />";
    deleteExtensionConfirmText = "<spring:message code="mapping.deleteExtensionConfirm" />";
    deleteScriptConfirmText = "<spring:message code="mapping.deleteScriptConfirm" />";
    
    contextNameText = "<spring:message code="mapping.contextName" />";
    extensionNameText = "<spring:message code="mapping.extensionName" />";
    scriptNameText = "<spring:message code="mapping.scriptName" />";
    nameColumnCaptionText = "<spring:message code="mapping.treeColumnDialplanName" />";
    scriptColumnCaptionText = "<spring:message code="mapping.treeColumnScriptName" />";
</script>

