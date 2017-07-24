<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="title" scope="request">
	<spring:message code="constructor.title"/>
</c:set>
<jsp:include page="header.jsp"/>
<jsp:include page="constructorVariables.jsp"/>
<script src="<c:url value="/js/jquery-2.2.3.min.js" />"></script>
<script src="<c:url value="/js/jquery-ui.js" />"></script>
<script src="<c:url value="/js/jsPlumb-2.1.0.js" />"></script>
<script src="<c:url value="/js/jquery.contextMenu.js" />"></script>
<script src="<c:url value="/js/jquery.ui.position.js" />"></script>
<script src="<c:url value="/js/spin.min.js" />"></script>
<script src="<c:url value="/js/messageDialog.js" />"></script>
<script src="<c:url value="/js/constructorCreateBlocks.js" />"></script>
<script src="<c:url value="/js/constructorPlumbInit.js" />"></script>
<script src="<c:url value="/js/constructorSelector.js" />"></script>
<script src="<c:url value="/js/constructorContextMenu.js" />"></script>
<script src="<c:url value="/js/constructorBlocks.js" />"></script>
<script src="<c:url value="/js/constructor.js" />"></script>
<div class="div-sub-menu-wrapper">
	<div class="div-sub-menu">
		<ul id="ul-menu">
			<li id="li-mm-settings"><a href="<c:url value="/settings" />"><spring:message code="common.settings"/></a></li>
			<li id="li-mm-map"><a href="<c:url value="/mapping" />"><spring:message code="common.dialplanMap"/></a></li>
			<li id="li-mm-checklists"><a href="<c:url value="/checklists" />"><spring:message code="common.checklists"/></a></li>

		</ul>
	</div>
	<div class="div-actions-menu">
		<ul id="ul-actions-menu">
			<li id="li-am-save"><span onclick="saveScriptData()"><spring:message code="constructor.mainMenu.Save"/></span></li>
			<li id="li-am-translate"><a href="<c:url value="/translator/${script.id}" />" target="_blank">
					<spring:message code="constructor.mainMenu.Translate"/></a></li>
			<li id="li-am-clean"><span onclick="cleanAll()"><spring:message code="constructor.mainMenu.Clean"/></span></li>
		</ul>
	</div>
	<div class="div-additional-info">
		<label class="label-application"><spring:message code="constructor.ivrScript"/>:</label> <label
					class="label-application-value">${script.name}</label>
	</div>
</div>
<div class="div-middle-wrapper">
	<div class="div-repository">
		<spring:message code="constructor.dialplanBlocks"/>
		<table>
			<tr>
				<td>
					<div type="GotoIfComplex" class="block-GotoIf div-repository-block draggable" ondblclick="changeCurrentBlock(createBlockComplex('GotoIf'))"
							 title="GotoIf"></div>
				</td>
				<td>
					<div type="SwitchComplex" class="block-Switch div-repository-block draggable" ondblclick="changeCurrentBlock(createBlockComplex('Switch'))"
							 title="Switch"></div>
				</td>
				<td>
					<div type="GotoIfTimeComplex" class="block-GotoIfTime div-repository-block draggable"
							 ondblclick="changeCurrentBlock(createBlockComplex('GotoIfTime'))" title="GotoIfTime"></div>
				</td>
				<td>
					<div type="ChecklistComplex" class="block-Checklist div-repository-block draggable"
							 ondblclick="changeCurrentBlock(createBlockComplex('Checklist'))" title="Checklist"></div>
				</td>

			</tr>
			<tr>
				<td>
					<div type="VoiceMenuComplex" class="block-VoiceMenu div-repository-block draggable"
							 ondblclick="changeCurrentBlock(createBlockComplex('VoiceMenu'))" title="VoiceMenu"></div>
				</td>
				<td>
					<div type="Busy" class="block-Busy div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Busy'))" title="Busy"></div>
				</td>
				<td>
					<div type="Congestion" class="block-Congestion div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Congestion'))"
							 title="Congestion"></div>
				</td>
				<td>
					<div type="Progress" class="block-Progress div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Progress'))"
							 title="Progress"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div type="Ringing" class="block-Ringing div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Ringing'))"
							 title="Ringing"></div>
				</td>
				<td>
					<div type="Answer" class="block-Answer div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Answer'))"
							 title="Answer"></div>
				</td>
				<td>
					<div type="Playback" class="block-Playback div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Playback'))"
							 title="Playback"></div>
				</td>
				<td>
					<div type="Background" class="block-Background div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Background'))"
							 title="Background"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div type="SayDigits" class="block-SayDigits div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('SayDigits'))"
							 title="SayDigits"></div>
				</td>
				<td>
					<div type="SayNumber" class="block-SayNumber div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('SayNumber'))"
							 title="SayNumber"></div>
				</td>
				<td>
					<div type="ChannelRedirect" class="block-ChannelRedirect div-repository-block draggable"
							 ondblclick="changeCurrentBlock(createBlock('ChannelRedirect'))" title="ChannelRedirect"></div>
				</td>
				<td>
					<div type="ChanSpy" class="block-ChanSpy div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('ChanSpy'))"
							 title="ChanSpy"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div type="Set" class="block-Set div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Set'))" title="Set"></div>
				</td>
				<td>
					<div type="SetGlobal" class="block-SetGlobal div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('SetGlobal'))"
							 title="SetGlobal"></div>
				</td>
				<td>
					<div type="SipAddHeader" class="block-SipAddHeader div-repository-block draggable"
							 ondblclick="changeCurrentBlock(createBlock('SipAddHeader'))" title="SIPAddHeader"></div>
				</td>
				<td>
					<div type="SipRemoveHeader" class="block-SipRemoveHeader div-repository-block draggable"
							 ondblclick="changeCurrentBlock(createBlock('SipRemoveHeader'))" title="SIPRemoveHeader"></div>
				</td>

			</tr>
			<tr>
				<td>
					<div type="Queue" class="block-Queue div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Queue'))"
							 title="Queue"></div>
				</td>
				<td>
					<div type="Custom" class="block-Custom div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Custom'))"
							 title="Custom"></div>
				</td>
				<td>
					<div type="System" class="block-System div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('System'))"
							 title="System"></div>
				</td>
				<td>
					<div type="Agi" class="block-Agi div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Agi'))" title="AGI"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div type="Transfer" class="block-Transfer div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Transfer'))"
							 title="Transfer"></div>
				</td>
				<td>
					<div type="AddQueueMember" class="block-AddQueueMember div-repository-block draggable"
							 ondblclick="changeCurrentBlock(createBlock('AddQueueMember'))" title="AddQueueMember"></div>
				</td>
				<td>
					<div type="RemoveQueueMember" class="block-RemoveQueueMember div-repository-block draggable"
							 ondblclick="changeCurrentBlock(createBlock('RemoveQueueMember'))" title="RemoveQueueMember"></div>
				</td>
				<td>
					<div type="DumpChan" class="block-DumpChan div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('DumpChan'))"
							 title="DumpChan"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div type="Monitor" class="block-Monitor div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Monitor'))"
							 title="Monitor"></div>
				</td>
				<td>
					<div type="StopMonitor" class="block-StopMonitor div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('StopMonitor'))"
							 title="StopMonitor"></div>
				</td>
				<td>
					<div type="MixMonitor" class="block-MixMonitor div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('MixMonitor'))"
							 title="MixMonitor"></div>
				</td>
				<td>
					<div type="StopMixMonitor" class="block-StopMixMonitor div-repository-block draggable"
							 ondblclick="changeCurrentBlock(createBlock('StopMixMonitor'))" title="StopMixMonitor"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div type="ConfBridge" class="block-ConfBridge div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('ConfBridge'))"
							 title="ConfBridge"></div>
				</td>
				<td>
					<div type="MeetMe" class="block-MeetMe div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('MeetMe'))"
							 title="MeetMe"></div>
				</td>
				<td>
					<div type="Read" class="block-Read div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Read'))" title="Read"></div>
				</td>
				<td>
					<div type="Wait" class="block-Wait div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Wait'))" title="Wait"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div type="UserEvent" class="block-UserEvent div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('UserEvent'))"
							 title="UserEvent"></div>
				</td>
				<td>
					<div type="MusicOnHold" class="block-MusicOnHold div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('MusicOnHold'))"
							 title="MusicOnHold"></div>
				</td>
				<td>
					<div type="StartMusicOnHold" class="block-StartMusicOnHold div-repository-block draggable"
							 ondblclick="changeCurrentBlock(createBlock('StartMusicOnHold'))" title="StartMusicOnHold"></div>
				</td>
				<td>
					<div type="StopMusicOnHold" class="block-StopMusicOnHold div-repository-block draggable"
							 ondblclick="changeCurrentBlock(createBlock('StopMusicOnHold'))" title="StopMusicOnHold"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div type="Record" class="block-Record div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Record'))"
							 title="Record"></div>
				</td>
				<td>
					<div type="SendDtmf" class="block-SendDtmf div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('SendDtmf'))"
							 title="SendDTMF"></div>
				</td>
				<td>
					<div type="Pickup" class="block-Pickup div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Pickup'))"
							 title="Pickup"></div>
				</td>
				<td>
					<div type="WaitExten" class="block-WaitExten div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('WaitExten'))"
							 title="WaitExten"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div type="Amd" class="block-Amd div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Amd'))" title="AMD"></div>
				</td>
				<td>
					<div type="DbSet" class="block-DbSet div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('DbSet'))"
							 title="DBset"></div>
				</td>
				<td>
					<div type="DbDel" class="block-DbDel div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('DbDel'))"
							 title="DBdel"></div>
				</td>
				<td>
					<div type="DbDelTree" class="block-DbDelTree div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('DbDelTree'))"
							 title="DBdeltree"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div type="Dial" class="block-Dial div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Dial'))" title="Dial"></div>
				</td>
				<td>
					<div type="Goto" class="block-Goto div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Goto'))" title="Goto"></div>
				</td>
				<td>
					<div type="Gosub" class="block-Gosub div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Gosub'))"
							 title="Gosub"></div>
				</td>
				<td>
					<div type="Macro" class="block-Macro div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Macro'))"
							 title="Macro"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div type="Return" class="block-Return div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Return'))"
							 title="Return"></div>
				</td>
				<td>
					<div type="Hangup" class="block-Hangup div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('Hangup'))"
							 title="Hangup"></div>
				</td>
				<td>
					<div type="NoCdr" class="block-NoCdr div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('NoCdr'))"
							 title="NoCDR"></div>
				</td>
				<td>
					<div type="NoOp" class="block-NoOp div-repository-block draggable" ondblclick="changeCurrentBlock(createBlock('NoOp'))" title="NoOp"></div>
				</td>
			</tr>

		</table>
	</div>
	<div class="div-canvas-wrapper">
		<div class="div-canvas-subwrapper droppable">
			<div id="canvas" class="div-canvas">
			</div>
		</div>
		<div class="div-block-properties">
			<label class="label-current-block-name"><spring:message code="constructor.blockName"/>:</label>
			<input type="text" id="current-block-name" class="input-current-block-name" maxlength="50"></input>
			<button id="button-apply" class="ui-button" onclick="applyBlockChanges(window.currentBlock)"><spring:message
							code="constructor.applyChanges"/></button>
			<button id="button-clone" class="ui-button" onclick="cloneBlock(window.currentBlock)"><spring:message code="constructor.clone"/></button>
			<button id="button-delete" class="ui-button" onclick="deleteBlock(window.currentBlock)"><spring:message code="constructor.delete"/></button>
			<button id="button-addcase" class="ui-button" onclick="addCase(window.currentBlock)"><spring:message code="constructor.addCase"/></button>
			<div id="div-command-list">
				<table>
					<tr>
						<td><label id="block-param-key-0" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-0" type="text" class="input-block-param-value" maxlength="1024" tabindex="1"></input></td>
						<td><label id="block-param-key-5" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-5" type="text" class="input-block-param-value" maxlength="1024" tabindex="6"></input></td>
						<td><label id="block-param-key-10" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-10" type="text" class="input-block-param-value" maxlength="1024" tabindex="11"></input></td>
					</tr>
					<tr>
						<td><label id="block-param-key-1" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-1" type="text" class="input-block-param-value" maxlength="1024" tabindex="2"></input></td>
						<td><label id="block-param-key-6" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-6" type="text" class="input-block-param-value" maxlength="1024" tabindex="7"></input></td>
						<td><label id="block-param-key-11" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-11" type="text" class="input-block-param-value" maxlength="1024" tabindex="12"></input></td>
					</tr>
					<tr>
						<td><label id="block-param-key-2" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-2" type="text" class="input-block-param-value" maxlength="1024" tabindex="3"></input></td>
						<td><label id="block-param-key-7" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-7" type="text" class="input-block-param-value" maxlength="1024" tabindex="8"></input></td>
						<td><label id="block-param-key-12" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-12" type="text" class="input-block-param-value" maxlength="1024" tabindex="13"></input></td>
					</tr>
					<tr>
						<td><label id="block-param-key-3" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-3" type="text" class="input-block-param-value" maxlength="1024" tabindex="4"></input></td>
						<td><label id="block-param-key-8" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-8" type="text" class="input-block-param-value" maxlength="1024" tabindex="9"></input></td>
						<td><label id="block-param-key-13" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-13" type="text" class="input-block-param-value" maxlength="1024" tabindex="14"></input></td>
					</tr>
					<tr>
						<td><label id="block-param-key-4" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-4" type="text" class="input-block-param-value" maxlength="1024" tabindex="5"></input></td>
						<td><label id="block-param-key-9" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-9" type="text" class="input-block-param-value" maxlength="1024" tabindex="10"></input></td>
						<td><label id="block-param-key-14" class="label-block-param-key"></label></td>
						<td><input id="block-param-value-14" type="text" class="input-block-param-value" maxlength="1024" tabindex="15"></input></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
<div id="dialog-message"></div>
<div id="dialog-confirmation"></div>
<div id="dialog-addcase">
	<label><spring:message code="constructor.expressionValue"/>:</label>
	<input id="input-expression-value" type="text"/>
</div>
<div id="dialog-adddigitcase">
	<label><spring:message code="constructor.allowedDigits"/>:</label><br/>
	<label class="label-digit">1</label><input type="checkbox" id="input-digit-1" class="input-digit" digit="1">
	<label class="label-digit">2</label><input type="checkbox" id="input-digit-2" class="input-digit" digit="2">
	<label class="label-digit">3</label><input type="checkbox" id="input-digit-3" class="input-digit" digit="3">
	<br/>
	<label class="label-digit">4</label><input type="checkbox" id="input-digit-4" class="input-digit" digit="4">
	<label class="label-digit">5</label><input type="checkbox" id="input-digit-5" class="input-digit" digit="5">
	<label class="label-digit">6</label><input type="checkbox" id="input-digit-6" class="input-digit" digit="6">
	<br/>
	<label class="label-digit">7</label><input type="checkbox" id="input-digit-7" class="input-digit" digit="7">
	<label class="label-digit">8</label><input type="checkbox" id="input-digit-8" class="input-digit" digit="8">
	<label class="label-digit">9</label><input type="checkbox" id="input-digit-9" class="input-digit" digit="9">
	<br/>
	<label class="label-digit">*</label><input type="checkbox" id="input-digit-star" class="input-digit" digit="*">
	<label class="label-digit">0</label><input type="checkbox" id="input-digit-0" class="input-digit" digit="0">
	<label class="label-digit">#</label><input type="checkbox" id="input-digit-pound" class="input-digit" digit="#">
</div>
<div id="div-selection">
	<span></span>
</div>
<jsp:include page="footer.jsp"/>

