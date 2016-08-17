<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div id="messages">

</div>


<script type="text/javascript">
	
	var timer;
	
	function showReply(i){
		stopTimer();
		$("#form"+i).toggle();
		
	}
	
	function sendMessage(i, name, email){
		var text=$("#textbox"+i).val();
		$.ajax({
			"type": 'POST',
			"url": '<c:url value="/sendMessage"/>',
			"data": JSON.stringify({"target":i, "text":text, "name":name,"email":email }),
			"success": success,
			"error": error,
			contentType: "application/json",
			dataType: "json"
		});
		
	}
	
	function showMessages(data) {
		$("div#messages").html("");

		for (var i = 0; i < data.messageList.length; i++) {
			var message = data.messageList[i];
			
			var messageDiv = document.createElement("div");
			messageDiv.setAttribute("class","message")
			
			var subjectSpan = document.createElement("span");
			subjectSpan.setAttribute("class", "subject");
			subjectSpan.appendChild(document.createTextNode(message.subject));
			
			var contentSpan = document.createElement("span");
			contentSpan.setAttribute("class", "messageBody");
			contentSpan.appendChild(document.createTextNode(message.content));
			
			var nameSpan = document.createElement("span");
			nameSpan.setAttribute("class", "name");
			nameSpan.appendChild(document.createTextNode(message.name+" ("));
			
			 
			var link = document.createElement("a");
			link.setAttribute("class", "replyLink");
			link.setAttribute("href", "#");
			link.setAttribute("onclick", "showReply("+i+")");
			link.appendChild(document.createTextNode(message.email));
			 
			nameSpan.appendChild(link);
			nameSpan.appendChild(document.createTextNode(")"));
			
			var alertSpan = document.createElement("span");
			alertSpan.setAttribute("class", "alert");
			alertSpan.setAttribute("id", "alert"+i);
		//	alertSpan.appendChild(document.createTextNode("Message Sent"));
						
			
			
			var replyForm = document.createElement("form");
			replyForm.setAttribute("class", "replyForm");
			replyForm.setAttribute("id", "form"+i);
			
			var textArea = document.createElement("textarea");
			textArea.setAttribute("class","replyArea");
			textArea.setAttribute("id","textbox"+i);
			
			
			var replyButton = document.createElement("input");
			replyButton.setAttribute("class","replyButton");
			replyButton.setAttribute("type","button");
			replyButton.setAttribute("value","Reply");
			replyButton.onclick= function(j, name, email){
				return function(){
					sendMessage(j, name, email);
				}
				
			}(i, message.name, message.email);
			
			
			replyForm.appendChild(textArea);
			replyForm.appendChild(replyButton);
			
			messageDiv.appendChild(subjectSpan);
			messageDiv.appendChild(contentSpan);
			messageDiv.appendChild(nameSpan);
			messageDiv.appendChild(alertSpan);
			messageDiv.appendChild(replyForm);
			
			
			$("div#messages").append(messageDiv);
			
		}
	}
	
	function success(data){
		startTimer();
		$("#form"+data.target).toggle();
		$("#alert"+data.target).text("Message Sent");
	}

	function error(data){
		alert("Error sending message!");
	}

	function onLoad() {
		updatePage();
		startTimer();
	}
	
	function startTimer(){
		timer = window.setInterval(updatePage, 10000);
	}

	function stopTimer(){
		window.clearInterval(timer);
	}

	function updatePage() {
		$.getJSON("<c:url value='/getMessages'/>", showMessages);
	}

	$(document).ready(onLoad());
</script>
