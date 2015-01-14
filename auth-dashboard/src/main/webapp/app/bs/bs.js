Ext.namespace("com.zz91.zzwork.bs");

var BS = new function(){
	this.BS_FORM="bsform";
	this.BS_WIN = "bswindows";
	this.BS_GRID = "bsgrid";
	this.BS_USER_GRID="bsusergrid";
}

com.zz91.zzwork.bs.StatusMap=["试","正","离"];
com.zz91.zzwork.bs.StatusColorMap=["green","blue","red"];

com.zz91.zzwork.bs.Field=[
	{name:"id",mapping:"id"},
	{name:"code",mapping:"code"},
	{name:"password",mapping:"password"},
	{name:"name",mapping:"name"},
	{name:"rightCode",mapping:"rightCode"},
	{name:"url",mapping:"url"},
	{name:"avatar",mapping:"avatar"},
	{name:"note",mapping:"note"},
	{name:"types",mapping:"types"},
	{name:"versions",mapping:"versions"},
	{name:"gmtCreated",mapping:"gmtCreated"},
	{name:"gmtModified",mapping:"gmtModified"}
];

com.zz91.zzwork.bs.TypeMap=["软件产品","业务系统","客户网站"];

com.zz91.zzwork.bs.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store = new Ext.data.JsonStore({
				root:"records",
				totalProperty:'totalRecords',
				remoteSort:true,
				fields:com.zz91.zzwork.bs.Field,
				url:Context.ROOT +  "/bs/queryBs.htm",
				autoLoad:true
		});
		
		var grid = this;
		var _sm=new Ext.grid.CheckboxSelectionModel({});
		
		var _cm=new Ext.grid.ColumnModel( [_sm,{
			header : "代号",
			width : 250,
			sortable : false,
			dataIndex : "code"
		},{
			header : "密码",
			sortable : false,
			dataIndex : "password" 
		},{
			header :"项目名称",
			sortable : false,
			width : 200,
			dataIndex:"name",
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				var url=record.get("url");
				if(url!=null && typeof(url)!="undefined" && url.length>0){
					return "<a href='"+url+"' target='_blank' >"+value+"</a>" ;
				}
				return value;
			}
		},{
			header : "类型",
			sortable : false,
			dataIndex : "types",
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				if(value==null || typeof(value)=="undefined"){
					value=0;
				}
				return com.zz91.zzwork.bs.TypeMap[value];
			}
		},{
			header : "版本",
			sortable : false,
			dataIndex : "versions"
		}
		]);
		
		var c={
			loadMask:MESSAGE.loadmask,
			iconCls:"icon-grid",
			store:_store,
			sm:_sm,
			cm:_cm,
			tbar:this.mytoolbar,
			bbar: new Ext.PagingToolbar({
				pageSize : Context.PAGE_SIZE,
				store : _store,
				displayInfo: true,
				displayMsg: MESSAGE.paging.displayMsg,
				emptyMsg : MESSAGE.paging.emptyMsg,
				beforePageText : MESSAGE.paging.beforePageText,
				afterPageText : MESSAGE.paging.afterPageText,
				paramNames : MESSAGE.paging.paramNames
			})
		};
		
		com.zz91.zzwork.bs.Grid.superclass.constructor.call(this,c);
	},
	mytoolbar:[{
		text : '添加',
		tooltip : '添加',
		iconCls : 'add16',
		handler : function(btn){
			com.zz91.zzwork.bs.addbsWin();
		}
	}, {
		text : '编辑',
		tooltip : '编辑',
		iconCls : 'edit16',
		handler : function(btn){
//			// 实际情况需要判断选定的记录数,一般情况下一次只能更新一条记录,也可以按照需求做批量更新的功能
			var row = Ext.getCmp(BS.BS_GRID).getSelectionModel().getSelected();
			if(row!=null){
				com.zz91.zzwork.bs.editbsWin(row.get("id"));
			}
		}

	}, {
		text : '删除',
		tooltip : '删除记录',
		iconCls : 'delete16',
		handler : function(btn){
			var row = Ext.getCmp(BS.BS_GRID).getSelectionModel().getSelections();
			if (row.length > 0) {
				Ext.MessageBox.confirm(Context.MSG_TITLE, '是否要删除选中的 ' + row.length + '条记录?', function(_btn){
					if (_btn != "yes")
						return;
					com.zz91.zzwork.bs.DeleteBs(row);
				});
			}
		}
	},"->",{
		xtype : "textfield",
		id : "search-name",
		emptyText:"搜索软件名称",
		listeners:{
			"change":function(field){
				var _store = Ext.getCmp(BS.BS_GRID).getStore();
				var B = _store.baseParams;
				B = B||{};
				if(field.getValue()!=""){
					B["name"] = field.getValue();
				}else{
					B["name"]=undefined;
				}
				_store.baseParams = B;
				_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
			}
		}
	}]
});

com.zz91.zzwork.bs.DeleteBs = function(rows){
	
	for (var i = 0, len = rows.length; i < len; i++) {
//		var _id = row[i].get("id");
		Ext.Ajax.request({
	        url:Context.ROOT+"/bs/deleteBs.htm",
	        params:{
	            "id":rows[i].get("id")
	        },
	        success:function(response,opt){
	            var obj = Ext.decode(response.responseText);
	            if(obj.success){
	                Ext.getCmp(BS.BS_GRID).getStore().reload();
	            }else{
	                Ext.MessageBox.show({
	                    title:MESSAGE.title,
	                    msg : MESSAGE.saveFailure,
	                    buttons:Ext.MessageBox.OK,
	                    icon:Ext.MessageBox.ERROR
	                });
	            }
	        },
	        failure:function(response,opt){
	            Ext.MessageBox.show({
	                title:MESSAGE.title,
	                msg : MESSAGE.submitFailure,
	                buttons:Ext.MessageBox.OK,
	                icon:Ext.MessageBox.ERROR
	            });
	        }
	    });
	}
}

com.zz91.zzwork.bs.Form = Ext.extend(Ext.form.FormPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var c={
			region:"center",
			layout:"column",
			bodyStyle:'padding:5px 0 0',
			frame:true,
			labelAlign : "right",
			labelWidth : 80,
			items:[{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "代号",
					allowBlank : false,
					itemCls :"required",
					name : "code",
					id : "code"
				}, {
					fieldLabel : "项目名称",
					allowBlank : false,
					itemCls :"required",
					name : "name"
				}, {
					readOnly:true,
					fieldLabel:"权限根结点",
					name:"right",
					id:"right",
					listeners:{
						"focus":function(field){
							//选择权限根，弹出树选择框
							com.zz91.zzwork.auth.right.RightSelector(function(node,e){
								Ext.getCmp("right").setValue(node.text);
								Ext.getCmp("rightCode").setValue(node.attributes["data"]);
								node.getOwnerTree().ownerCt.close();
							})
						}
					}
				}, {
					xtype:"hidden",
					name:"rightCode",
					id:"rightCode"
				},{
					fieldLabel:"图标",
					readOnly:true,
					id:"avatarName",
					name:"avatarName",
					listeners:{
						"focus":function(field){
							//选择权限根，弹出树选择框
							com.zz91.zzwork.bs.AvatarSelector();
						}
					}
				}, {
					xtype:"hidden",
					name:"avatar",
					id:"avatar"
				}]
			},{
				columnWidth:0.5,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype : "hidden",
					name : "id",
					dataIndex : "id"
				}, {
					fieldLabel : "密码",
					allowBlank : false,
					itemCls :"required",
					name : "password",
					id : "password"
				},{
					xtype:"combo",
					allowBlank : false,
					itemCls :"required",
					fieldLabel:"类型",
					name:"typesStr",
					hiddenName:"types",
					mode:"local",
					triggerAction:"all",
					lazyRender:true,
					store:new Ext.data.ArrayStore({
						fields:["k","v"],
						data:[[0,"软件产品"],[1,"业务系统"],[2,"客户网站"]]
					}),
					valueField:"k",
					displayField:"v"
				},{
					fieldLabel : "版本",
					name : "versions"
				}]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel : "链接地址",
					name : "url"
				}]
			}
//			,{
//				columnWidth:0.5,
//				layout:"fit",
//				height:"100%",
//				buttons:[{
//					text:"上传图片",
//					handler:function(btn){
//						ast.ast1949.UploadConfig.uploadURL=Context.ROOT+"/admin/upload?model=bs&filetype=img";
//	
//						var win = new ast.ast1949.UploadWin({
//							title:"上传广告图片"
//						});
//						win.show();
//	
//						ast.ast1949.UploadConfig.uplobsuccess = function(f,o){
//							if(o.result.success){
//								ast.ast1949.utils.Msg("","图片已成功上传");
//								var uploadUrl=o.result.data[0].path+o.result.data[0].uploadedFilename;
//								Ext.get("image-preview").dom.src=resourceUrl+"/"+uploadUrl;
//								Ext.getCmp("picAddress").setValue(uploadUrl);
//								win.close();
//							}else{
//								Ext.MessageBox.show({
//									title:Context.MSG_TITLE,
//									msg : "出现错误，文件没有被上传",
//									buttons:Ext.MessageBox.OK,
//									icon:Ext.MessageBox.ERROR
//								});
//							}
//						}
//					}
//				}],
//				html:'<div class="thumb-wrap" id="img-wrap">'+
//					'<div class="thumb"><img id="image-preview" src="http://img1.zz91.com/bs/no_image.gif" title=""></div>'+
//					'</div>'+
//					'<div class="x-clear"></div>'
//			}
			],
			buttons:[{
				text:"保存",
				scope:this,
				handler:function(btn){
					var url=this.saveUrl;
					if (this.getForm().isValid()) {
						this.getForm().submit({
							url : url,
							method : "post",
							type:"json",
							success : function(_form,_action){
								com.zz91.zzwork.utils.Msg("","信息已保存");
								Ext.getCmp(BS.BS_GRID).getStore().reload();
								Ext.getCmp(BS.BS_WIN).close();
								var cp = _action.result.data.split("|");
								Ext.get("initCode").dom.value=cp[0];
								Ext.get("initPassword").dom.value=cp[1];
							},
							failure : function(_form,_action){
								Ext.MessageBox.show({
									title:MESSAGE.title,
									msg : MESSAGE.saveFailure,
									buttons:Ext.MessageBox.OK,
									icon:Ext.MessageBox.ERROR
								});
							}
						});
					} else {
						Ext.MessageBox.show({
							title:MESSAGE.title,
							msg : MESSAGE.submitFailure,
							buttons:Ext.MessageBox.OK,
							icon:Ext.MessageBox.ERROR
						});
					}
					
				}
			},{
				text:"关闭",
				handler:function(btn){
					Ext.getCmp(BS.BS_WIN).close();
				}
			}]
		};
		
		com.zz91.zzwork.bs.Form.superclass.constructor.call(this,c);
	},
	saveUrl:Context.ROOT+ "/bs/createBs.htm",
	loadOneRecord:function(id){
		var reader=[
		        	{name:"id",mapping:"bs.id"},
		        	{name:"code",mapping:"bs.code"},
		        	{name:"password",mapping:"bs.password"},
		        	{name:"name",mapping:"bs.name"},
		        	{name:"rightCode",mapping:"bs.rightCode"},
		        	{name:"url",mapping:"bs.url"},
		        	{name:"avatar",mapping:"bs.avatar"},
		        	{name:"note",mapping:"bs.note"},
		        	{name:"types",mapping:"bs.types"},
		        	{name:"versions",mapping:"bs.versions"},
		        	{name:"gmtCreated",mapping:"bs.gmtCreated"},
		        	{name:"gmtModified",mapping:"bs.gmtModified"},
		        	{name:"right",mapping:"rightName"}
		        ];
		
		var form = this;
		var _store = new Ext.data.JsonStore({
			root : "records",
			fields : reader,
			url : Context.ROOT+ "/bs/queryOneBs.htm",
			baseParams:{"id":id},
			autoLoad : true,
			listeners : {
				"datachanged" : function() {
					var record = _store.getAt(0);
					if (record != null) {
						form.getForm().loadRecord(record);
						Ext.getCmp("avatarName").setValue(record.get("avatar"));
//						Ext.get("image-preview").dom.src=resourceUrl+"/"+ record.get("picAddress");
					}
				}
			}
		});
	},
	initCodePassword:function(code, password){
		this.findById("code").setValue(code);
		this.findById("password").setValue(password);
	}
});

com.zz91.zzwork.bs.AvatarSelector=function(fn){
	
	var _myStore = new Ext.data.JsonStore({
	    url: Context.ROOT+"/bs/avatar.htm",
	    autoLoad:true,
	    fields: ["url","avatar"]
	});
	var _xtpl = new Ext.XTemplate('<tpl for=".">',
        '<div class="thumb-wrap">',
        '<div class="thumb"><img src="'+Context.ROOT+'{url}" title="{avatar}"></div>',
        '</div>',
	    '</tpl>',
	    '<div class="x-clear"></div>'
	);
	
	var win = new Ext.Window({
		id:"avatarselector" ,
		title:"选择图标",
		width:650,
		height:300,
		//autoHeight:true,
		modal:true,
		bodyCssClass:"avatar-view",
		autoScroll:true,
		items:new Ext.DataView({
			height:300,
			store:_myStore,
			tpl:_xtpl,
			multiSelect:false,
			overClass:'x-view-over',
			itemSelector:'div.thumb-wrap',
			listeners:{
				"dblclick":function(dv,idx,node,e){
					Ext.getCmp("avatarName").setValue(_myStore.getAt(idx).get("url"));
					Ext.getCmp("avatar").setValue(_myStore.getAt(idx).get("url"));
					dv.ownerCt.close();
				}
			}
		})
	});
	win.show();
}

com.zz91.zzwork.bs.addbsWin = function(){
	var form = new com.zz91.zzwork.bs.Form({
		id:BS.BS_FORM,
		saveUrl:Context.ROOT+ "/bs/createBs.htm",
		region:"center"
	});
	
	form.initCodePassword(Ext.get("initCode").dom.value,Ext.get("initPassword").dom.value);
	
	var win = new Ext.Window({
			id:BS.BS_WIN ,
			title:"添加项目信息",
			width:650,
			autoHeight:true,
			modal:true,
			items:[form]
	});
	win.show();
}

com.zz91.zzwork.bs.editbsWin = function(id){
	var form = new com.zz91.zzwork.bs.Form({
		id:BS.BS_FORM,
		saveUrl:Context.ROOT+ "/bs/updateBs.htm",
		region:"center"
	});
	
	form.loadOneRecord(id);
//	
	var win = new Ext.Window({
			id:BS.BS_WIN ,
			title:"修改项目信息",
			width:650,
			autoHeight:true,
			modal:true,
			items:[form]
	});
	win.show();
}

com.zz91.zzwork.bs.StaffGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _field=[
        	{name:"id",mapping:"staff.id"},
        	{name:"account",mapping:"staff.account"},
        	{name:"staffNo",mapping:"staff.staffNo"},
        	{name:"deptCode",mapping:"staff.deptCode"},
        	{name:"name",mapping:"staff.name"},
        	{name:"email",mapping:"staff.email"},
        	{name:"sex",mapping:"staff.sex"},
        	{name:"avatar",mapping:"staff.avatar"},
        	{name:"birthday",mapping:"staff.birthday"},
        	{name:"jobs",mapping:"staff.jobs"},
        	{name:"status",mapping:"staff.status"},
        	{name:"gmtEntry",mapping:"staff.gmtEntry"},
        	{name:"gmtLeft",mapping:"staff.gmtLeft"},
        	{name:"note",mapping:"staff.note"},
        	{name:"deptName",mapping:"deptName"},
        	{name:"bsId",mapping:"bsId"}
        	];
		
		var _store=new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totals",
			remoteSort:true,
			fields:_field,
			url:Context.ROOT+"/bs/queryBsStaff.htm",
			autoLoad:false
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel();
		
		var _cm=new Ext.grid.ColumnModel([_sm,{
			header:"编号",
			width:30,
			sortable:true,
			dataIndex:"id",
			hidden:true
		},{
			header:"部门",
			sortable:false,
			dataIndex:"deptName"
		},{
			header:"工号",
			sortable:false,
			dataIndex:"staffNo"
		},{
			header:"姓名",
			width:300,
			sortable:false,
			dataIndex:"name",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				var sex="";
				if(record.get("sex")=="0"){
					sex="b";
				}
				var status = "<span style='color:"+com.zz91.zzwork.bs.StatusColorMap[record.get("status")]+"' >"+com.zz91.zzwork.bs.StatusMap[record.get("status")]+"</span>";
				return "<img src='"+Context.ROOT+"/themes/boomy/user"+sex+"16.png' />"+" "+value+" "+status;
			}
		},{
			header:"入职时间",
			sortable:false,
			dataIndex:"gmtEntry",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
				else{
					return "";
				}
			}
		},{
			header:"转正/离职时间",
			sortable:false,
			dataIndex:"gmtLeft",
			renderer : function(value, metadata, record, rowIndex,colIndex, store) {
				if(value!=null){
					return Ext.util.Format.date(new Date(value.time), 'Y-m-d');
				}
				else{
					return "";
				}
			}
		},{
			header:"职务",
			sortable:false,
			dataIndex:"jobs"
		}]);
		
		var grid=this;
		
		var c={
			store:_store,
			sm:_sm,
			cm:_cm,
			loadMask:MESSAGE.loadmask,
			tbar:[{
				iconCls:"add16",
				text:"添加关联",
				handler:function(btn){
					var row = Ext.getCmp(BS.BS_GRID).getSelectionModel().getSelected();
					if(row!=null){
						com.zz91.zzwork.bs.AddBsStaff(row.get("id"));
					}
				}
			},{
				iconCls:"delete16",
				text:"删除关联",
				handler:function(btn){
					var row = grid.getSelectionModel().getSelected();
					if(row!=null){
						com.zz91.zzwork.bs.DeleteBsStaff(row.get("bsId"),row.get("id"));
					}
				}
			}]
		};
		
		com.zz91.zzwork.bs.StaffGrid.superclass.constructor.call(this, c);
	},
	loadStaff:function(bsId){
		var B = this.getStore().baseParams;
		B = B||{};
		B["bsId"] = bsId;
		
		this.getStore().baseParams = B;
		this.getStore().reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
	}
});

com.zz91.zzwork.bs.DeleteBsStaff = function(bsId, staffId){
	Ext.Ajax.request({
        url:Context.ROOT+"/bs/deleteBsStaff.htm",
        params:{
            "bsId":bsId,
            "staffId":staffId
        },
        success:function(response,opt){
            var obj = Ext.decode(response.responseText);
            if(obj.success){
            	Ext.getCmp(BS.BS_USER_GRID).getStore().reload();
            }else{
                Ext.MessageBox.show({
                    title:MESSAGE.title,
                    msg : MESSAGE.saveFailure,
                    buttons:Ext.MessageBox.OK,
                    icon:Ext.MessageBox.ERROR
                });
            }
        },
        failure:function(response,opt){
            Ext.MessageBox.show({
                title:MESSAGE.title,
                msg : MESSAGE.submitFailure,
                buttons:Ext.MessageBox.OK,
                icon:Ext.MessageBox.ERROR
            });
        }
    });
}

com.zz91.zzwork.bs.AddBsStaff = function(bsId){
	var form=new Ext.form.FormPanel({
		region:"center",
		layout:"form",
		frame:true,
		labelAlign : "right",
		labelWidth : 80,
		defaults:{
			anchor:"100%",
			xtype:"textfield",
			labelSeparator:""
		},
		items:[{
			xtype:"hidden",
			name:"bsId",
			id:"bsId",
			value:bsId
		},{
			fieldLabel:"用户账号",
			name:"account"
		}],
		buttons:[{
			text:"保存",
			scope:this,
			handler:function(btn){
				if(form.getForm().isValid()){
					form.getForm().submit({
						url:Context.ROOT+"/bs/createBsStaff.htm",
						method:"post",
						type:"json",
						success:function(_form,_action){
							var obj = _action.result;
							if(obj.success){
								Ext.getCmp(BS.BS_USER_GRID).getStore().reload();
								Ext.getCmp(BS.BS_WIN).close();
							}else{
								Ext.MessageBox.show({
				                    title:MESSAGE.title,
				                    msg : MESSAGE.saveFailure,
				                    buttons:Ext.MessageBox.OK,
				                    icon:Ext.MessageBox.ERROR
				                });
							}
						},
						failure:function(_form,_action){
							Ext.MessageBox.show({
								title:MESSAGE.title,
								msg : MESSAGE.saveFailure,
								buttons:Ext.MessageBox.OK,
								icon:Ext.MessageBox.ERROR
							});
						}
					});
				}else{
					Ext.MessageBox.show({
						title:MESSAGE.title,
						msg : MESSAGE.submitFailure,
						buttons:Ext.MessageBox.OK,
						icon:Ext.MessageBox.ERROR
					});
				}
			}
		},{
			text:"关闭",
			handler:function(btn){
				Ext.getCmp(BS.BS_WIN).close();
			}
		}]
	});
	
	var win = new Ext.Window({
		id:BS.BS_WIN ,
		title:"添加关联用户",
		width:300,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	win.show();
}

com.zz91.zzwork.bs.UpdateBsDept = function(bsId, deptId, checked){
	Ext.Ajax.request({
        url:Context.ROOT+"/bs/updateBsDept.htm",
        params:{
            "bsId":bsId,
            "deptId":deptId,
            "checked":checked
        },
        success:function(response,opt){
            var obj = Ext.decode(response.responseText);
            if(obj.success){
            	com.zz91.zzwork.utils.Msg("",MESSAGE.saveSuccess);
            }else{
                Ext.MessageBox.show({
                    title:MESSAGE.title,
                    msg : MESSAGE.saveFailure,
                    buttons:Ext.MessageBox.OK,
                    icon:Ext.MessageBox.ERROR
                });
            }
        },
        failure:function(response,opt){
            Ext.MessageBox.show({
                title:MESSAGE.title,
                msg : MESSAGE.submitFailure,
                buttons:Ext.MessageBox.OK,
                icon:Ext.MessageBox.ERROR
            });
        }
    });
}

