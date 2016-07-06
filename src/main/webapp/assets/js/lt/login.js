/**
 * 
 */
$(function(){
	
	
	$("#login-but").click(function(){
		var param = $("#user-login-form").serializeArray();
		$.ajax({
			type : 'POST',
			url : appPath+'/index/userLogin',
			data : param,  
			success : function(msg) {
				if(msg.success){
					//window.location = appPath+"/index/index?loginType="+$("input[name='loginType'][type='radio']:checked").val();
					alert(1);
				}
				else{
					// $.messager.alert('提示',msg.errorMsg,'error');
					alert(msg.errorMsg);
				}
			}
		});
	})
	
	
	$("#register-but").click(function(){
//		var userName = $("input[name='userName']").val();
//		if(userName.length < 5){
//			$("#userNameError").show();
//			return false;
//		}
		
		
		var param = $("#user-register-form").serializeArray();
		$.ajax({
			type : 'POST',
			url : appPath+'/index/userRegister',
			data : param,  
			success : function(msg) {
				if(msg.success){
					//window.location = appPath+"/index/index?loginType="+$("input[name='loginType'][type='radio']:checked").val();
					alert(msg.successMsg);
				}
				else{
					// $.messager.alert('提示',msg.errorMsg,'error');
					alert(msg.errorMsg);
				}
			}
		});
	})
})