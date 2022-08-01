/**
 * 
 */
 $(function(){
		 $.ajax({
			url: "/common/visuals",
			 success: function(result){
					$("#visual>.wrap").html(result);
  				}
  		});
});