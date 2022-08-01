/**
 * 
 */
 
$(function(){
    $("#btn-payment").click(payment);
});
function payment(){
	var r = confirm("100원 결제 하실래요?");
	if(!r){
		return;
	}
	var IMP = window.IMP; // 생략 가능
    IMP.init("imp15615813"); // 예: imp00000000
    
    
    //결재금액
    //주문자
  
       IMP.request_pay({ // param
          pg: "html5_inicis.INIpayTest",
          pay_method: "card",
          merchant_uid: "ORD" + new Date().getTime(),
          name: $(".itemDtlName").text(),
          amount: $("#totalPrice").text(),
          buyer_email: $("#buyerEmail").val(),
          buyer_name: $("#buyerName").val(),
          buyer_tel: "010-4479-6511",
          buyer_addr: "서울특별시 강남구 신사동",
          buyer_postcode: "01181"
      }, function (rsp) { // callback
        msg="";
          if (rsp.success) {
              // 결제 성공 시 로직,
              console.log(rsp);
              msg="결제가 완료 되었습니다.";
          } else {
              // 결제 실패 시 로직,
              msg="결제에 실패하였습니다.";
          }
          alert(msg);
      });
}


