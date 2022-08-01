/**
 * 
 */
$(document).ready(function() {
	$('#summernote').summernote('disable');
	var errorMessage =/*[[${errorMessage}]]*/"";
	var successMessage = /*[[${successMessage}]]*/"";
	if(errorMessage != "") {
		alert(errorMessage);
	}
	if(successMessage != "") {
		alert(successMessage);
	}

});

	$(document).ready(function() {
		var errorMessage =/*[[${errorMessage}]]*/"";
		if(errorMessage != "") {
			alert(errorMessage);
		}

		bindDomEvent();

	});

	function bindDomEvent() {
		$(".custom-file-input").on("change",function() {
					var fileName = $(this).val().split("\\")
							.pop(); //이미지 파일명
					var fileExt = fileName.substring(fileName
							.lastIndexOf(".") + 1); // 확장자 추출
					fileExt = fileExt.toLowerCase(); //소문자 변환

					if (fileExt != "jpg" && fileExt != "jpeg"
							&& fileExt != "gif"
							&& fileExt != "png"
							&& fileExt != "bmp") {
						alert("이미지 파일만 등록이 가능합니다.");
						return;
					}

					$(this).siblings(".custom-file-label")
							.html(fileName);
				});
	}
	
	
	$(function(){
		$("#btn-del").click(function(){
			  var token = $("meta[name='_csrf']").attr("content");
	            var header = $("meta[name='_csrf_header']").attr("content");
			var r = confirm("정말로 삭제하시겠습니까?");
			//확인 :true 취소: false
			if(!r) return ;
			var faqNo =$("#faqNo").val();
			$.ajax({
				  beforeSend : function(xhr){
	                    /* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
	                    xhr.setRequestHeader(header, token);
	                },
			url:"/faq/delete/"+faqNo,
				type:"delete",
				//data:{"_method":"delete"},
				success:function(result){
					alert("삭제완료");
					location.href="/faq";
				}
			});
		});
		$("#btn-edit").click(function(){
			$('#summernote').summernote('enable');
				$("input").attr('disabled',false);
			$("#btn-edit").hide();
			$("#btn-del").show();
			$("#btn-write").show();
			alert("수정이 가능합니다.");
		
		});
		$("#btn-cancel").click(function(){
			alert("취소 되었습니다.");
			$("input").attr('disabled',true);
		});
	});
