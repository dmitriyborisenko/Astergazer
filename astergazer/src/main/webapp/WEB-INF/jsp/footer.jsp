<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="div-main-footer">
	<label class="label-version"><spring:message code="common.version"/>:
		<spring:eval expression="@applicationProperties.projectVersion" />
	</label>
	<label class="label-author">Dmitriy Borisenko</label>
</div>
</body>
</html>
    