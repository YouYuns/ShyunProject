/**
 * 
 */
		var check=[false,false,false,false];
		
		$(function(){
			$("#email").blur(emailBlurred);
			$("#password").blur(passBlurred);
			$("#password-check").blur(passCheckBlurred);
			$("#name").blur(nameBlurred);
		});
		function submitCheck(){
			for(i=0; i<check.length; i++){
				if(check[i]==false){
					$("#btn-join").attr("disabled", true);
					return;
				}
			}
			//모두 true인경우
			$(".submitJoin").attr("disabled", false);
		}
		function nameBlurred(){
			var nameRexp=/^[가-힣]{2,4}$/;
			var in_name=$.trim( $(this).val() );//input태그에 입력된 값(value)을 읽어올때
			var text;
			var target=$(this).siblings(".msg");
			
			if( nameRexp.test(in_name) ){
				text="이름 입력 완료!";
				target.css("color","green");
				check[3]=true;
			}else{
				text="* 이름은 한글2~4글자로 작성해주세요";
				target.css("color","red");
				check[3]=false;
			}
			target.html(text);
			submitCheck();
		}
		function passCheckBlurred(){
			//pass==pass-ckeck
			var text;
			var target=$(this).siblings(".msg");
			if($(this).val() == $("#password").val()){
				text="비밀번호 재확인 완료!";
				target.css("color", "green");
				check[2]=true;
			}else{
				text="비밀번호가 일치하지 않아요!";
				target.css("color", "red");
				check[2]=false;
			}
			target.html(text);
			submitCheck();
		}
		function passBlurred(){
			var passRexp=/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[/@!%*#?&])[A-Za-z\d/@!%*#?&]{8,}$/
			var in_pass=$(this).val();
			var text;
			var target=$(this).siblings(".msg");
			if(in_pass.length < 8){
				text="8자리 이상으로 입력해주세요.";
				target.css("color", "red");
				target.html(text);
				check[1]=false;
				return;//함수 종료...
			}
			//8자리 이상인경우 아래도 실행:패턴 체크
			if( passRexp.test(in_pass) ){
				text="비밀번호 입력 완료!";
				target.css("color", "green");
				check[1]=true;
			}else{
				text="비밀번호 형식에 맞지 않습니다.";
				target.css("color", "red");
				check[1]=false;
			}
			target.html(text);
			submitCheck();
		}
		function emailBlurred(){
			var emailRexp=/^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
			var in_email=$("#email").val();//input태그에 입력된 값(value)을 읽어올때
			var text;
			var target=$(this).siblings(".msg");
			
			if(emailRexp.test(in_email.trim()) ){
				text="이메일 입력 완료!";
				target.css("color","green");
				check[0]=true;
			}else{
				text="* 이메일 형식으로 입력하여야합니다.";
				target.css("color","red");
				check[0]=false;
			}
			target.html(text);
			submitCheck();
		}