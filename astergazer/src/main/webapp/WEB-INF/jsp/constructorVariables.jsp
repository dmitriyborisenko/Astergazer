<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script>
  restControllerUrl = "<c:url value="/constructor/rest" />";
  constructorControllerUrl = "<c:url value="/constructor" />";
  scriptId = ${script.id};
  addCaseText = "<spring:message code="constructor.addCase" />";

  crossConnectionWarningText = "<spring:message code="constructor.crossConnectionWarning" />";
  duplicateNameWarningText = "<spring:message code="constructor.duplicateNameWarning" />";
  cannotRemoveErrorText = "<spring:message code="constructor.cannotRemoveError" />";
  noSelectedBlockErrorText = "<spring:message code="constructor.noSelectedBlockError" />";
  cannotCloneErrorText = "<spring:message code="constructor.cannotCloneError" />";
  deleteAllConfirmText = "<spring:message code="constructor.deleteAllConfirm" />";
  concurrentModificationConfirmText = "<spring:message code="common.concurrentModificationConfirm" />";

  contextMenuCopyText = "<spring:message code="constructor.contextMenu.copy" />";
  contextMenuPasteText = "<spring:message code="constructor.contextMenu.paste" />";
  contextMenuDeleteText = "<spring:message code="constructor.contextMenu.delete" />";
  contextMenuAddCaseText = "<spring:message code="constructor.contextMenu.addCase" />";

  blockParams = [];
  // AddQueueMember block
  var params = [];
  params.push("<spring:message code="blocks.addQueueMember.queueName" />");
  params.push("<spring:message code="blocks.addQueueMember.interface" />");
  params.push("<spring:message code="blocks.addQueueMember.penalty" />");
  params.push("<spring:message code="blocks.addQueueMember.options" />");
  params.push("<spring:message code="blocks.addQueueMember.memberName" />");
  params.push("<spring:message code="blocks.addQueueMember.stateInterface" />");
  blockParams["AddQueueMember"] = params;
  // Agi block
  var params = [];
  params.push("<spring:message code="blocks.agi.command" />");
  blockParams["Agi"] = params;
  // Amd block
  var params = [];
  params.push("<spring:message code="blocks.amd.initialSilence" />");
  params.push("<spring:message code="blocks.amd.greeting" />");
  params.push("<spring:message code="blocks.amd.afterGreetingSilence" />");
  params.push("<spring:message code="blocks.amd.totalAnalysisTime" />");
  params.push("<spring:message code="blocks.amd.minWordLength" />");
  params.push("<spring:message code="blocks.amd.betweenWordSilence" />");
  params.push("<spring:message code="blocks.amd.maxNumberOfWords" />");
  params.push("<spring:message code="blocks.amd.silenceThreshold" />");
  params.push("<spring:message code="blocks.amd.maxWordLength" />");
  blockParams["Amd"] = params;
  // Answer block
  var params = [];
  params.push("<spring:message code="blocks.answer.delay" />");
  blockParams["Answer"] = params;
  // Background block
  var params = [];
  params.push("<spring:message code="blocks.background.filename" />");
  params.push("<spring:message code="blocks.background.options" />");
  params.push("<spring:message code="blocks.background.langoverride" />");
  params.push("<spring:message code="blocks.background.context" />");
  blockParams["Background"] = params;
  // Busy block
  var params = [];
  params.push("<spring:message code="blocks.busy.timeout" />");
  blockParams["Busy"] = params;
  // ChannelRedirect block
  var params = [];
  params.push("<spring:message code="blocks.channelRedirect.channel" />");
  params.push("<spring:message code="blocks.channelRedirect.context" />");
  params.push("<spring:message code="blocks.channelRedirect.extension" />");
  params.push("<spring:message code="blocks.channelRedirect.priority" />");
  blockParams["ChannelRedirect"] = params;
  // ChanSpy block
  var params = [];
  params.push("<spring:message code="blocks.chanSpy.chanPrefix" />");
  params.push("<spring:message code="blocks.chanSpy.options" />");
  blockParams["ChanSpy"] = params;
  // Checklist block
  var params = [];
  params.push("<spring:message code="blocks.checklist.list" />");
  params.push("<spring:message code="blocks.checklist.expression" />");
  blockParams["Checklist"] = params;
  // ConfBridge block
  var params = [];
  params.push("<spring:message code="blocks.confBridge.conference" />");
  params.push("<spring:message code="blocks.confBridge.bridgeProfile" />");
  params.push("<spring:message code="blocks.confBridge.userProfile" />");
  params.push("<spring:message code="blocks.confBridge.menu" />");
  blockParams["ConfBridge"] = params;
  // Congestion block
  var params = [];
  params.push("<spring:message code="blocks.congestion.timeout" />");
  blockParams["Congestion"] = params;
  // Custom block
  var params = [];
  for (i = 0; i < 15; i++) {
    params.push("<spring:message code="blocks.custom.param" />");
  }
  blockParams["Custom"] = params;
  // DbDel block
  var params = [];
  params.push("<spring:message code="blocks.dbDel.family" />");
  params.push("<spring:message code="blocks.dbDel.key" />");
  blockParams["DbDel"] = params;
  // DbDelTree block
  var params = [];
  params.push("<spring:message code="blocks.dbDelTree.family" />");
  params.push("<spring:message code="blocks.dbDelTree.keyTree" />");
  blockParams["DbDelTree"] = params;
  // DbSet block
  var params = [];
  params.push("<spring:message code="blocks.dbSet.family" />");
  params.push("<spring:message code="blocks.dbSet.key" />");
  params.push("<spring:message code="blocks.dbSet.value" />");
  blockParams["DbSet"] = params;
  // Dial block
  var params = [];
  params.push("<spring:message code="blocks.dial.techResource" />");
  params.push("<spring:message code="blocks.dial.timeout" />");
  params.push("<spring:message code="blocks.dial.options" />");
  params.push("<spring:message code="blocks.dial.url" />");
  blockParams["Dial"] = params;
  // DumpChan block
  var params = [];
  params.push("<spring:message code="blocks.dumpChan.level" />");
  blockParams["DumpChan"] = params;
  // Gosub block
  var params = [];
  params.push("<spring:message code="blocks.gosub.context" />");
  params.push("<spring:message code="blocks.gosub.extension" />");
  params.push("<spring:message code="blocks.gosub.priority" />");
  params.push("<spring:message code="blocks.gosub.arguments" />");
  blockParams["Gosub"] = params;
  // Goto block
  var params = [];
  params.push("<spring:message code="blocks.goto.context" />");
  params.push("<spring:message code="blocks.goto.extension" />");
  params.push("<spring:message code="blocks.goto.priority" />");
  blockParams["Goto"] = params;
  // GotoIf block
  var params = [];
  params.push("<spring:message code="blocks.gotoIf.param" />");
  blockParams["GotoIf"] = params;
  // GotoIfTime block
  var params = [];
  params.push("<spring:message code="blocks.gotoIfTime.times" />");
  params.push("<spring:message code="blocks.gotoIfTime.weekdays" />");
  params.push("<spring:message code="blocks.gotoIfTime.mdays" />");
  params.push("<spring:message code="blocks.gotoIfTime.months" />");
  params.push("<spring:message code="blocks.gotoIfTime.timezone" />");
  blockParams["GotoIfTime"] = params;
  // Hangup block
  var params = [];
  params.push("<spring:message code="blocks.hangup.causeCode" />");
  blockParams["Hangup"] = params;
  // Macro block
  var params = [];
  params.push("<spring:message code="blocks.macro.name" />");
  params.push("<spring:message code="blocks.macro.arguments" />");
  blockParams["Macro"] = params;
  // MeetMe block
  var params = [];
  params.push("<spring:message code="blocks.meetMe.confNumber" />");
  params.push("<spring:message code="blocks.meetMe.options" />");
  params.push("<spring:message code="blocks.meetMe.pin" />");
  blockParams["MeetMe"] = params;
  // MixMonitor block
  var params = [];
  params.push("<spring:message code="blocks.mixMonitor.filename" />");
  params.push("<spring:message code="blocks.mixMonitor.options" />");
  params.push("<spring:message code="blocks.mixMonitor.command" />");
  blockParams["MixMonitor"] = params;
  // Monitor block
  var params = [];
  params.push("<spring:message code="blocks.monitor.fileFormat" />");
  params.push("<spring:message code="blocks.monitor.filenameBase" />");
  params.push("<spring:message code="blocks.monitor.options" />");
  blockParams["Monitor"] = params;
  // MusicOnHold block
  var params = [];
  params.push("<spring:message code="blocks.musicOnHold.class" />");
  params.push("<spring:message code="blocks.musicOnHold.duration" />");
  blockParams["MusicOnHold"] = params;
  // NoOp block
  var params = [];
  params.push("<spring:message code="blocks.noOp.text" />");
  blockParams["NoOp"] = params;
  // Pickup block
  var params = [];
  params.push("<spring:message code="blocks.pickup.targets" />");
  blockParams["Pickup"] = params;
  // Playback block
  var params = [];
  params.push("<spring:message code="blocks.playback.filename" />");
  params.push("<spring:message code="blocks.playback.options" />");
  blockParams["Playback"] = params;
  // Queue block
  var params = [];
  params.push("<spring:message code="blocks.queue.queueName" />");
  params.push("<spring:message code="blocks.queue.options" />");
  params.push("<spring:message code="blocks.queue.url" />");
  params.push("<spring:message code="blocks.queue.announceOverride" />");
  params.push("<spring:message code="blocks.queue.timeout" />");
  params.push("<spring:message code="blocks.queue.agi" />");
  params.push("<spring:message code="blocks.queue.macro" />");
  params.push("<spring:message code="blocks.queue.gosub" />");
  params.push("<spring:message code="blocks.queue.rule" />");
  params.push("<spring:message code="blocks.queue.position" />");
  blockParams["Queue"] = params;
  // Read block
  var params = [];
  params.push("<spring:message code="blocks.read.variable" />");
  params.push("<spring:message code="blocks.read.filename" />");
  params.push("<spring:message code="blocks.read.maxDigits" />");
  params.push("<spring:message code="blocks.read.options" />");
  params.push("<spring:message code="blocks.read.attempts" />");
  params.push("<spring:message code="blocks.read.timeout" />");
  blockParams["Read"] = params;
  // Record block
  var params = [];
  params.push("<spring:message code="blocks.record.filename" />");
  params.push("<spring:message code="blocks.record.silence" />");
  params.push("<spring:message code="blocks.record.maxDuration" />");
  params.push("<spring:message code="blocks.record.options" />");
  blockParams["Record"] = params;
  // RemoveQueueMember block
  var params = [];
  params.push("<spring:message code="blocks.removeQueueMember.queueName" />");
  params.push("<spring:message code="blocks.removeQueueMember.interface" />");
  blockParams["RemoveQueueMember"] = params;
  // Return block
  var params = [];
  params.push("<spring:message code="blocks.return.value" />");
  blockParams["Return"] = params;
  // SayDigits block
  var params = [];
  params.push("<spring:message code="blocks.sayDigits.digits" />");
  blockParams["SayDigits"] = params;
  // SayNumber block
  var params = [];
  params.push("<spring:message code="blocks.sayNumber.number" />");
  params.push("<spring:message code="blocks.sayNumber.gender" />");
  blockParams["SayNumber"] = params;
  // SendDtmf block
  var params = [];
  params.push("<spring:message code="blocks.sendDtmf.digits" />");
  params.push("<spring:message code="blocks.sendDtmf.timeout" />");
  params.push("<spring:message code="blocks.sendDtmf.duration" />");
  params.push("<spring:message code="blocks.sendDtmf.channel" />");
  blockParams["SendDtmf"] = params;
  // Set block
  var params = [];
  params.push("<spring:message code="blocks.set.name" />");
  params.push("<spring:message code="blocks.set.value" />");
  blockParams["Set"] = params;
  // SetGlobal block
  var params = [];
  params.push("<spring:message code="blocks.setGlobal.name" />");
  params.push("<spring:message code="blocks.setGlobal.value" />");
  blockParams["SetGlobal"] = params;
  // SipAddHeader block
  var params = [];
  params.push("<spring:message code="blocks.sipAddHeader.headerName" />");
  params.push("<spring:message code="blocks.sipAddHeader.headerValue" />");
  blockParams["SipAddHeader"] = params;
  // SipRemoveHeader block
  var params = [];
  params.push("<spring:message code="blocks.sipRemoveHeader.headerMask" />");
  blockParams["SipRemoveHeader"] = params;
  // StartMusicOnHold block
  var params = [];
  params.push("<spring:message code="blocks.startMusicOnHold.class" />");
  blockParams["StartMusicOnHold"] = params;
  // StopMixMonitor block
  var params = [];
  params.push("<spring:message code="blocks.stopMixMonitor.mixMonitorId" />");
  blockParams["StopMixMonitor"] = params;
  // Switch block
  var params = [];
  params.push("<spring:message code="blocks.switch.param" />");
  blockParams["Switch"] = params;
  // System block
  var params = [];
  params.push("<spring:message code="blocks.system.command" />");
  blockParams["System"] = params;
  // Transfer block
  var params = [];
  params.push("<spring:message code="blocks.transfer.techDestination" />");
  blockParams["Transfer"] = params;
  // UserEvent block
  var params = [];
  params.push("<spring:message code="blocks.userEvent.eventName" />");
  params.push("<spring:message code="blocks.userEvent.body" />");
  blockParams["UserEvent"] = params;
  // VoiceMenu block
  var params = [];
  params.push("<spring:message code="blocks.voiceMenu.menuPrompt" />");
  params.push("<spring:message code="blocks.voiceMenu.invalidPrompt" />");
  params.push("<spring:message code="blocks.voiceMenu.timeoutPrompt" />");
  params.push("<spring:message code="blocks.voiceMenu.timeout" />");
  params.push("<spring:message code="blocks.voiceMenu.timeoutAttempts" />");
  params.push("<spring:message code="blocks.voiceMenu.invalidAttempts" />");
  blockParams["VoiceMenu"] = params;
  // Wait block
  var params = [];
  params.push("<spring:message code="blocks.wait.delay" />");
  blockParams["Wait"] = params;
  // WaitExten block
  var params = [];
  params.push("<spring:message code="blocks.waitExten.seconds" />");
  params.push("<spring:message code="blocks.waitExten.options" />");
  blockParams["WaitExten"] = params;
</script>

