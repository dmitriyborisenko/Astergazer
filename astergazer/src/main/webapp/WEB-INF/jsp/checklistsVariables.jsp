<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script>
  treeControllerUrl = "<c:url value="/checklists/tree" />";
  restControllerUrl = "<c:url value="/checklists/rest" />";
  imageUrl = "<c:url value="/images" />";

  unknownNodeTypeErrorText = "<spring:message code="common.unknownNodeTypeError" />";
  deleteChecklistConfirmText = "<spring:message code="checklists.deleteChecklistConfirm" />";
  deleteEntryConfirmText = "<spring:message code="checklists.deleteEntryConfirm" />";

  checklistNameText = "<spring:message code="checklists.listName" />";
  entryText = "<spring:message code="checklists.entry" />";
  entryControlValueText = "<spring:message code="checklists.entryControlValue" />";
  entryReturnValueText = "<spring:message code="checklists.entryReturnValue" />";
</script>

