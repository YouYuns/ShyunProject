/**
 * 
 */
 
 $(function(){
		var apiURI = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=a40f7fbefba44352991bd677431a2f10";
		$.ajax({
			url:apiURI,
			dataType:"json",
			success:function(result){
					console.log(result);
					console.log("절대온도 : " + result.main.temp);
					console.log("섭씨온도 : " + parseInt(result.main.temp-273.15));
					console.log("날씨아이콘 : " + result.weather[0].icon);
					
					var imgURL = "https://openweathermap.org/img/w/" + result.weather[0].icon + ".png";
					$("#w_icon").css("background-image", 'url('+ imgURL + ')');
					$("#temp").text(parseInt(result.main.temp-273.15));
		}
	});
});