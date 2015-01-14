Ext.namespace("com.zz91.zzwork");

Ext.onReady(function(){
	var west=new Ext.tree.TreePanel({
        id:'forum-tree',
        region:'west',
        title:'工作平台',
        split:true,
        width: 220,
        minSize: 175,
        maxSize: 400,
        collapsible: true,
        margins:'2,0,2,2',
        cmargins:'2,2,2,2',
        collapseMode:'mini',
        dataUrl:Context.ROOT+'/mymenu.htm',
        rootVisible:true,
        lines:false,
        autoScroll:true,
        root: new Ext.tree.AsyncTreeNode({
          	text: '系统导航',
          	expanded:true
      	})
    });
    
    var center=new Ext.TabPanel({
    	region:'center',
        margins:'0 5 5 0',
        resizeTabs: true,
        minTabWidth: 100,
        tabWidth: 100,
        enableTabScroll: true,
        activeTab: 0,
        items:[{
        	id:'welcome-panel',
        	iconCls:"home16",
        	title: '我的桌面',
        	closable: true,
        	autoScroll:true,
         	layout : 'fit',
         	html:'<iframe src="' + Context.ROOT+ '/welcome.htm" frameBorder=0 scrolling = "auto" style = "width:100%;height:100%"></iframe>'
        },new com.zz91.zzwork.FeedbackForm({
    		id:"feedback-panel",
            title:"意见反馈",
            closable: true
    	})]
    });
	
	var north={
		region:'north',
		height:0,
		bbar:[{
        	iconCls:"web16",
        	text:"推荐用Chrome访问我",
        	tooltip:"chrome或者firefox3+都不错，打倒IE6 >.<",
        	handler:function(btn){
        		window.open("http://www.google.com/chrome/index.html?hl=zh_cn&brand=CHMA&utm_campaign=zh_cn&utm_source=zh_cn-ha-apac-zh_cn-bk&utm_medium=ha");
        	}
        },'->',{ //new Ext.ux.ThemeChange(),
			iconCls:'userid16',
			text:'<b>'+Ext.get("logineduser").dom.value+'</b>',
			handler:function(btn){
//				caiban.utils.Msg(MESSAGE.title, "个人信息暂不能被查看和修改！");
			}
		},{
			text:'修改密码',
			iconCls:'key16',
			handler:function(btn){
				com.zz91.zzwork.ChangePasswordWin();
			}
		},{
			text:'退出',
			iconCls:'close16',
			handler:function(btn){
				window.location.href = Context.ROOT+"/logout.htm";
			}
		}],
		html:''
	};
	
	
	west.on('click',function(node, e){
		if(node.isLeaf()){
			e.stopEvent();
			var id = 'contents-' + node.attributes.data;
	        var tab = this.getComponent(id);
	        if(tab){
	            center.setActiveTab(tab);
	        }else{
	            var p = center.add(new Ext.Panel({
	                id: id,
	                title:node.text,
	                closable: true,
	                autoScroll:true,
	                layout : 'fit', 
					html : '<iframe src="' + Context.ROOT+ node.attributes.url + '" frameBorder=0 scrolling = "auto" style = "width:100%;height:100%"></iframe> '
	            }));
	            center.setActiveTab(p);
	        }
		}
	});
	
	west.getLoader().on("beforeload",function(treeLoader,node){
		this.baseParams.parentCode= node.attributes.data;  //根据data返回的JSON数据获取
	},west.getLoader());
	
	var viewport=new Ext.Viewport({
		layout:'border',
		items:[west,center,north]
	});
	
//	var todo=new Ext.Panel({
//		title:"TODO",
//		id:"todopanel",
//		height:300,
//		width:280,
//		titleCollapse:true,
//		collapsible: true,
////		animCollapse: true,
//		collapsed:false,
////		closable:true,
//		style: 'position:absolute;right:6;bottom:30;z-index:101',
//		html:'<iframe src="'+Context.ROOT+'/todo/mini.htm" frameBorder=0 scrolling = "auto" style = "width:100%;height:100%"></iframe> '
//	});
//	
//	todo.render(Ext.getBody());
//	todo.resizer= new Ext.Resizable(todo.el, {
//		minHeight:50,
//		minWidth:100,
//		handles: "n",
//		pinned: true,
//		transparent:true,
//		resizeElement : function(){
//			var box = this.proxy.getBox();
//			this.proxy.hide();
//			todo.setHeight(box.height);
//			return box;
//		}
//	});
	
});

/**
 * 密码修改窗口
 * */
com.zz91.zzwork.ChangePasswordWin = function(){
	var form = new com.zz91.zzwork.PasswordForm({
		region:"center"
	});
	
	var win = new Ext.Window({
		id:"changepasswordwin",
		title:"修改密码",
		iconCls:"key16",
		width:300,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	win.show();
}

/**
 * 密码修改表单
 * */
com.zz91.zzwork.PasswordForm = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			labelAlign : "right",
			labelWidth : 80,
			region:"center",
			layout:"form",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			defaults:{
				anchor:"90%",
				xtype:"textfield",
				labelSeparator:""
			},
			items:[{
				xtype:"textfield",
				inputType:"password",
				id:"originalPassword",
				name:"originalPassword",
				fieldLabel:"旧密码",
				itemCls:"required",
				allowBlank:false
			},{
				xtype:"textfield",
				inputType:"password",
				id:"newPassword",
				name:"newPassword",
				fieldLabel:"新密码",
				itemCls:"required",
				allowBlank:false
			},{
				xtype:"textfield",
				inputType:"password",
				id:"verifyPassword",
				name:"verifyPassword",
				fieldLabel:"确认密码",
				itemCls:"required",
				allowBlank:false
			}],
			buttons:[{
				text:"保存",
				scope:this,
				handler:this.saveForm
			},{
				text:"关闭",
				handler:function(){
					Ext.getCmp("changepasswordwin").close();
				}
			}]
		};
		
		com.zz91.zzwork.PasswordForm.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+"/changePassword.htm",
	saveForm:function(){
		if(this.getForm().isValid()){
			this.getForm().submit({
				url:this.saveUrl,
				method:"post",
				type:"json",
				success:this.onSaveSuccess,
				failure:this.onSaveFailure,
				scope:this
			});
		}else{
			Ext.MessageBox.show({
				title:MESSAGE.title,
				msg : MESSAGE.unValidate,
				buttons:Ext.MessageBox.OK,
				icon:Ext.MessageBox.ERROR
			});
		}
	},
	onSaveSuccess:function(_form,_action){
		Ext.MessageBox.show({
			title:MESSAGE.title,
			msg : MESSAGE.saveSuccess,
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO,
			fn:function(buttonId,text,opt){
				Ext.getCmp("changepasswordwin").close();
			}
		});
	},
	onSaveFailure:function(_form,_action){
		Ext.MessageBox.show({
			title:MESSAGE.title,
			msg : MESSAGE.saveFailure,
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
});

com.zz91.zzwork.FeedbackForm=Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
	config = config||{};
	Ext.apply(this,config);
	
	var c={
		labelAlign : "right",
		labelWidth : 60,
		layout:"form",
		bodyStyle:'padding:5px 0 0',
		frame:true,
		defaults:{
			anchor:"100%",
			xtype:"textfield",
			labelSeparator:""
		},
		items:[{
			id:"feedbackTopic",
			name:"topic",
			fieldLabel:"什么问题",
			itemCls:"required",
			allowBlank:false
		},{
			xtype:"textarea",
			id:"feedbackContent",
			name:"content",
			height:300,
			fieldLabel:"详细点说"
		}],
		buttons:[{
			text:"写好了，告诉开发",
			scope:this,
			handler:this.saveForm
		}]
	};
	
	com.zz91.zzwork.FeedbackForm.superclass.constructor.call(this,c);
},
saveUrl:Context.ROOT+"/feedback.htm",
saveForm:function(){
	if(this.getForm().isValid()){
		this.getForm().submit({
			url:this.saveUrl,
			method:"post",
			type:"json",
			success:this.onSaveSuccess,
			failure:this.onSaveFailure,
			scope:this
		});
	}else{
		Ext.MessageBox.show({
			title:MESSAGE.title,
			msg : MESSAGE.unValidate,
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.ERROR
		});
	}
},
onSaveSuccess:function(_form,_action){
	Ext.MessageBox.show({
		title:MESSAGE.title,
		msg : "您的问题我们收下了，如果有需要我们会及时联系您，谢谢您 :)",
		buttons:Ext.MessageBox.OK,
		icon:Ext.MessageBox.INFO,
		fn:function(buttonId,text,opt){
			Ext.getCmp("feedbackTopic").setValue("");
			Ext.getCmp("feedbackContent").setValue("");
		}
	});
},
onSaveFailure:function(_form,_action){
	Ext.MessageBox.show({
		title:MESSAGE.title,
		msg : MESSAGE.saveFailure,
		buttons:Ext.MessageBox.OK,
		icon:Ext.MessageBox.ERROR
	});
}
});