Ext.namespace('com.zz91.zzwork');

var LOGIN = new function(){
	this.LOGINWINDOW="loginwindow";
}

com.zz91.zzwork.LoginForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		var c={
			layout:'form',
			frame:true,
			labelAlign:'right',
			labelWidth:60,
			defaults:{
				anchor:"95%",
				xtype:"textfield"
			},
			items:[{
				fieldLabel:'用户名',
				name:'account',
				id:'account',
				allowBlank:false
			},{
				inputType:'password',
				fieldLabel:'密码',
				name:'password',
				id:'password',
				allowBlank:false
			}]
		};
		
		com.zz91.zzwork.LoginForm.superclass.constructor.call(this,c);
		
	},
	initFocus:function(){
		this.findById("account").focus(true,100);
	}
});

/**
 * 用户登录动作
 * form:登录的表单对象
 * onSuccess:登录成功后要做的事
 * */
com.zz91.zzwork.UserLogin = function(form,onSuccess){
	form.getForm().submit({
		url:Context.ROOT+"/checkuser.htm",
		method:"post",
		type:"json",
		waitMsg : "正在验证用户名和密码,请耐心等待!",
		success:onSuccess,
		failure:function(_form,_action){
			Ext.MessageBox.show({
				title:MESSAGE.title,
				msg : _action.result.data,
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	});
}

com.zz91.zzwork.UserLoginWin = function(doSuccess){
	var form=new com.zz91.zzwork.LoginForm({
		region:"center"
	});
	
	var win=new Ext.Window({
		id:LOGIN.LOGINWINDOW,
		layout:'border',
		iconCls:"lock16",
		width:300,
		height:150,
		closable:false,
		title:"工作平台 - 登录",
		modal:true,
		items:[form],
		keys:[{
			key:[10,13],
			fn:function(){
				com.zz91.zzwork.UserLogin(form,doSuccess);
			}
		}],
		buttons:[{
			text:'登录',
			handler:function(btn){
				com.zz91.zzwork.UserLogin(form,doSuccess);
			}
		}]
	});
	
	win.show();
	
	form.initFocus();
}

