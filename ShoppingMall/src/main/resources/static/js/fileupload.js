/**
 * 
 */
 
$(function(){
	//파일 업로드처리하기 위한 이벤트
	$(".fileInput").change(fileUpload);
}); 

function fileUpload(){
	target=$(this);
	//파일데이터 전송
	file=target[0].files[0];
	fileName=file.name;
	if(!file.type.includes("image")){
		alert("이미지 파일이 아닙니다.");
		return;	
	}
	if(file.size> (1024*1024*5) ){
		alert("이미지 용량은 1MB 까지만 적용하세요");
		return;}
	data=new FormData();
	data.append("file",file );
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$.ajax({
		beforeSend:function(xhr){
			xhr.setRequestHeader(header, token);
		},////csrf 적용한경우..아니면 제거
		data: data,
		type: "POST",
		url: "/admin/goods/fileupload",
		contentType: false,
		//enctype: 'multipart/form-data',
		processData: false,
		success: function(fileUrl) {
			
			target.next().css("background-image", "url("+fileUrl+")" );
			//style="background-image: url('');"
			target.siblings("[type=hidden]").val(fileName);
		},
		error: function(err){
			alert("파일업로드 오류 서버의 용량이나 경로를 확인하세요");
			//console.log(err);
		}
	});
	
	
}