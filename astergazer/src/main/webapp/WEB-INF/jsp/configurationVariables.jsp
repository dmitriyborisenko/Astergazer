<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script>
    restControllerUrl = "<c:url value="/configuration/rest" />";
    
    concurrentModificationConfirmText = "<spring:message code="common.concurrentModificationConfirm" />";
</script>

