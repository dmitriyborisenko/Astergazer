<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script>
  var treeControllerUrl = "<c:url value="/checklists/tree" />";
  var restControllerUrl = "<c:url value="/checklists/rest" />";
  var imageUrl = "<c:url value="/images" />";

  var unknownNodeTypeErrorText = "<spring:message code="common.unknownNodeTypeError" />";
  var deleteChecklistConfirmText = "<spring:message code="checklists.deleteChecklistConfirm" />";
  var deleteEntryConfirmText = "<spring:message code="checklists.deleteEntryConfirm" />";

  var checklistNameText = "<spring:message code="checklists.listName" />";
  var entryText = "<spring:message code="checklists.entry" />";
  var entryControlValueText = "<spring:message code="checklists.entryControlValue" />";
  var entryReturnValueText = "<spring:message code="checklists.entryReturnValue" />";
</script>

