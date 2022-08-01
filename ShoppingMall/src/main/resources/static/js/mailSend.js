/**
 * 
 */
 
 
 //var mailKey ="";
	
var createdDate;
$(function(){
	$("#key-wrap").hide();
	
	$("#btn-mail").click(function(){
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			beforeSend: function(xhr){xhr.setRequestHeader(header, token);},
			url:"/request-key/mail",
			type: "post",
			data: {email:$("#email").val()},
			success:function(result){
				$("#key-wrap").show();
				start(result+(1000*60*5));
				
			}
		});
	});
	var myTimeout;
	function start(targetTime){
		
		//var targetTime=ctime+(1000*60*5);
		//var currTime=new Date();
		//console.log(targetTime);
		//console.log(currTime.getTime());
		var seconds=(targetTime-new Date().getTime())/1000;
		var minute=Math.floor(seconds/60);
		var second=Math.floor(seconds%60);
		
		console.log(minute +":"+second);
		//console.log("차이:"+(targetTime-currTime));//300,000
		if(seconds>1){
			$(".time").text(minute +":"+second);
			myTimeout = setTimeout(start, 1000,targetTime);
			
		}else{
			clearTimeout(myTimeout);
			$(".time").text("00:00");
			$.get("/request-key/remove",function(){});
		}
	}
	
	$("#key").blur(function(){
		//console.log("입력한 코드: "+$(this).val());
		//var scode=[[${session.mailKey}]];//하시면 않되요: 페이지로딩시 결정되는 session 정보입니다.
		//name이 생략된 get 요청방법
		var inputKey=$(this).val();
		
		$.get("/request-key/getKey",function(code){
			
			if(code==inputKey){
				$(".mail-msg").text("인증성공").css("color","green");
				$("#key-wrap").hide();
				clearTimeout(myTimeout);
				$.get("/request-key/remove",function(){});
			}else{
				$(".mail-msg").text("*인증실패").css("color","red");
			}
			
		});
	});
});


