<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script>
  var restControllerUrl = "<c:url value="/configuration/rest" />";
  var concurrentModificationConfirmText = "<spring:message code="common.concurrentModificationConfirm" />";
</script>

