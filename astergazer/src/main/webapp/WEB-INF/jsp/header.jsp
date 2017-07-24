<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8"/>
	<meta http-equiv="Content-Style-Type" content="text/css"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/favicon.svg" />"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css" />"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/constructor.css" />"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/block.css" />"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/configuration.css" />"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/mapping.css" />"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/checklists.css" />"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui/smoothness_custom/jquery-ui.css" />"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/jstree/default/style.min.css" />"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.contextMenu.css" />"/>
	<jsp:include page="commonVariables.jsp"/>
	<title>Astergazer: ${title}</title>
</head>
<body>
<div class="div-main-header">
	<div class="div-main-menu">
		<a href="<c:url value="/" />"><img src="<c:url value="/images/astergazer.svg" />" class="img-logo"></a>
		<label class="label-caption">${title}</label>
	</div>
	<div class="div-lang-menu">
		<ul id="ul-lang">
			<li id="li-eng"><a href="?language=en">ENG</a></li>
			|
			<li id="li-rus"><a href="?language=ru">RUS</a></li>
		</ul>
	</div>
</div>
