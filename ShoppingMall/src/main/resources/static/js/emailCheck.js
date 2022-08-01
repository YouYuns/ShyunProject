/**
 * 
 */
 $(function(){
			$("#email").keyup(function(){
				  var token = $("meta[name='_csrf']").attr("content");
		            var header = $("meta[name='_csrf_header']").attr("content");
				var in_email=$(this).val();	
				//비동기로 전송할게요
				$.ajax({
					url:"/members/emailCheck",
					data: {email:in_email},
					type:"POST",
					 beforeSend : function(xhr){
		                    /* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
		                    xhr.setRequestHeader(header, token);
		                },
					success: function(result){
						if(!result){//이미존재
							$("#email").siblings(".msg")
							.text("사용불가").css("color","red");
						}
					}
				});
			});
		});