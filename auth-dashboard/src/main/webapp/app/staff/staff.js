Ext.namespace("com.zz91.zzwork.staff");

var STAFF=new function(){
	this.STAFFWIN = "staffwin";
	this.GRID="staffgrid";
}

com.zz91.zzwork.staff.StatusMap=["试","正","离"];
com.zz91.zzwork.staff.StatusColorMap=["green","blue","red"];

com.zz91.zzwork.staff.DtoFields=[
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
	{name:"deptName",mapping:"deptName"},
	{name:"note",mapping:"staff.note"},
	"roleArr","role"];

com.zz91.zzwork.staff.Grid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _store=new Ext.data.JsonStore({
			root:"records",
			totalProperty:"totalRecords",
			remoteSort:true,
			fields:com.zz91.zzwork.staff.DtoFields,
			url:Context.ROOT+"/staff/queryStaff.htm",
			autoLoad:true
		});
		
		var grid=this;
		
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
		},{//显示信息包括：姓名，性别，头像
			header:"姓名",
			width:300,
			sortable:false,
			dataIndex:"name",
			renderer:function(value, metadata, record, rowIndex, colIndex, store){
				var sex="";
				if(record.get("sex")=="0"){
					sex="b";
				}
				var status = "<span style='color:"+com.zz91.zzwork.staff.StatusColorMap[record.get("status")]+"' >"+com.zz91.zzwork.staff.StatusMap[record.get("status")]+"</span>";
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
		
		var c={
			id:STAFF.GRID,
			store:_store,
			sm:_sm,
			cm:_cm,
			loadMask:MESSAGE.loadmask,
			tbar:[{
				iconCls:"edit16",
				text:"编辑",
				handler:function(btn){
					var row = grid.getSelectionModel().getSelected();
					if(row!=null){
						com.zz91.zzwork.staff.UpdateStaffWin(row.get("account"));
					}
				}
			},"-",{
				iconCls:"add16",
				text:"入职",
				handler:function(btn){
					com.zz91.zzwork.staff.EntryWin();
				}
			},{
				text:"转正",
				handler:function(btn){
					
					var row = grid.getSelectionModel().getSelected();
					if(row!=null && row.get("status")=="0"){
						com.zz91.zzwork.staff.RegularWin(row.get("id"),row.get("note"));
					}
				}
			},{
				text:"离职",
				handler:function(btn){
					
					var row = grid.getSelectionModel().getSelected();
					if(row!=null && (row.get("status")=="1" || row.get("status")=="0")){
						com.zz91.zzwork.staff.LeftWin(row.get("id"),row.get("note"),row.get("gmtLeft"));
					}
				}
			}
			,"->",{
				xtype:"textfield",
				id:"searchStaffNo",
				emptyText:"请输入员工工号",
				listeners:{
					"change":function(field, newValue, oldValue){
						var B = _store.baseParams;
						B = B||{};
						if(field.getValue()!=""){
							B["staffNo"] = field.getValue();
						}else{
							B["staffNo"]=undefined;
						}
						_store.baseParams = B;
						_store.reload({params:{"start":0, "limit":Context.PAGE_SIZE}});
					}
				}
			}," ",{
				xtype:"textfield",
				id:"searchName",
				emptyText:"请输入员工姓名",
				listeners:{
					"change":function(field, newValue, oldValue){
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
			},"-",{
				xtype:"checkbox",
				boxLabel:"显示离职员工",
				handler:function(btn){
					var B=_store.baseParams||{};
					if(btn.getValue()){
						B["status"]="2";
					}else{
						B["status"]="1";
					}
					_store.baseParam=B;
					_store.reload({params:{"start":0,"limit":Context.PAGE_SIZE}})
				}
			}
//			," ",{
//				xtype:"combo",
//				id:"searchDept",
//				emptyText:"请选择部门",
//				displayField:"name",
//				valueField:"code",
//				hiddenId:"searchDeptCode",
//				store:new Ext.data.JsonStore({
//					root:"records",
//					fields:["code","name"],
//					autoLoad:true,
//					url:Contexxt.ROOT + "/staff/queryDept.htm"
//				})
//			}
			],
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
		
		com.zz91.zzwork.staff.Grid.superclass.constructor.call(this, c);
	}
});

com.zz91.zzwork.staff.UpdateStaffWin=function(account){
	var form = new com.zz91.zzwork.staff.StaffInfo({
		isEdit:true,
		saveUrl:Context.ROOT+"/staff/updateStaff.htm",
		region:"center"
	});
	
	form.loadRecord(account);
	
	var win = new Ext.Window({
		id:STAFF.STAFFWIN,
		title:"员工信息",
		width:700,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
}

com.zz91.zzwork.staff.EntryWin=function(){
	var form = new com.zz91.zzwork.staff.StaffInfo({
		saveUrl:Context.ROOT+"/staff/createStaff.htm",
		region:"center"
	});
	
	var win = new Ext.Window({
		id:STAFF.STAFFWIN,
		title:"员工入职",
		width:700,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
}

com.zz91.zzwork.staff.RegularWin=function(id,note){
	var form = new com.zz91.zzwork.staff.StatusInfo({
		saveUrl:Context.ROOT+"/staff/regular.htm",
		region:"center"
	});
	
	form.initForm(id,note,null);
	
	var win = new Ext.Window({
		id:STAFF.STAFFWIN,
		title:"员工转正",
		width:500,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
}

com.zz91.zzwork.staff.LeftWin=function(id,note,gmtLeft){
	var form = new com.zz91.zzwork.staff.StatusInfo({
		saveUrl:Context.ROOT+"/staff/left.htm",
		isLeft:true,
		region:"center"
	});
	
	form.initForm(id,note,gmtLeft);
	
	var win = new Ext.Window({
		id:STAFF.STAFFWIN,
		title:"员工离职",
		width:500,
		autoHeight:true,
		modal:true,
		items:[form]
	});
	
	win.show();
}

com.zz91.zzwork.staff.StaffInfo = Ext.extend(Ext.form.FormPanel,{
	isEdit:false,
	saveUrl:Context.ROOT+"/staff/createStaff.htm",
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _isEdit=this.isEdit;
		var _url=this.saveUrl;
		
		var form=this;
		
		var c={
			labelAlign:"right",
			labelWidth:80,
			layout:"column",
			frame:true,
			items:[{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					fieldLabel:"工号",
					readOnly:_isEdit,
					allowBlank:false,
					itemCls:"required",
					name:"staffNo"
				},{
					fieldLabel:"姓名",
					allowBlank:false,
					itemCls:"required",
					name:"name"
				},{
					fieldLabel:"部门",
					allowBlank:false,
					itemCls:"required",
					name:"deptName",
					id:"deptName",
					listeners:{
						"focus":function(field){
							com.zz91.zzwork.dept.DeptSelector(function(node,e){
								//alert(node.text+" "+node.attributes["data"]);
								Ext.getCmp("deptName").setValue(node.text);
								Ext.getCmp("deptCode").setValue(node.attributes["data"]);
								node.getOwnerTree().ownerCt.close();
							});
						}
					}
				},{
					xtype:"hidden",
					name:"deptCode",
					id:"deptCode"
				},{
					fieldLabel:"email",
					name:"email"
				},{
					fieldLabel:"职位",
					name:"jobs"
				},{
					xtype:"combo",
					fieldLabel:"性别",
					name:"sexStr",
					hiddenName:"sex",
					mode:"local",
					triggerAction:"all",
					lazyRender:true,
					store:new Ext.data.ArrayStore({
						fields:["k","v"],
						data:[[0,"女"],[1,"男"]]
					}),
					valueField:"k",
					displayField:"v"
				},{
					xtype:"datefield",
					fieldLabel:"入职时间",
					format:"Y-m-d",
					name:"gmtEntryDate",
					id:"gmtEntryDate",
					allowBlank:false,
					itemCls:"required"
				}]
			},{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor:"100%",
					xtype:"textfield",
					labelSeparator:""
				},
				items:[{
					xtype:"hidden",
					id:"id",
					name:"id"
				},{
					fieldLabel:"账号",
					readOnly:_isEdit,
					allowBlank:false,
					itemCls:"required",
					name:"account"
				},{
					fieldLabel:"密码",
					name:"password"
				},{
					xtype:"hidden",
					id:"roleArr",
					name:"roleArr"
				},{
					fieldLabel:"角色",
					name:"role",
					id:"role",
					readOnly:true,
					listeners:{
						"focus":function(field){
							com.zz91.zzwork.staff.RoleSelector();
						}
					}
				}]
			},{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"100%",
					labelSeparator:""
				},
				items:[{
					xtype:"textarea",
					fieldLabel:"备注",
					name:"note"
				}]
			}],
			buttons:[{
				text:"保存",
				itemCls:"floppy16",
				handler:function(btn){
					if(form.getForm().isValid()){
						form.getForm().submit({
							url:_url,
							method:"post",
							type:"json",
							success:function(_form,_action){
								Ext.getCmp(STAFF.GRID).getStore().reload();
								Ext.getCmp(STAFF.STAFFWIN).close();
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
				itemCls:"close16",
				handler:function(btn){
					Ext.getCmp(STAFF.STAFFWIN).close();
				}
			}]
		};
		
		com.zz91.zzwork.staff.StaffInfo.superclass.constructor.call(this,c);
	},
	loadRecord:function(account){
		var form = this;
		var store= new Ext.data.JsonStore({
			root:"records",
			fields:com.zz91.zzwork.staff.DtoFields,
			url:Context.ROOT+"/staff/queryOneStaff.htm",
			baseParams:{"account":account},
			autoLoad:true,
			listeners:{
				"datachanged":function(s){
					var record=s.getAt(0);
					if(record!=null){
						form.getForm().loadRecord(record);
						form.findById("gmtEntryDate").setValue(Ext.util.Format.date(new Date(record.get("gmtEntry").time), 'Y-m-d'));
					}
				}
			}
		});
	}
});

com.zz91.zzwork.staff.StatusInfo = Ext.extend(Ext.form.FormPanel,{
	saveUrl:Context.ROOT+"/staff/regular.htm",
	isLeft:false,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		
		var _txt="转正";
		if(this.isLeft){
			_txt="离职";
		}
		var form=this;
		var _url=this.saveUrl;
		
		var c={
			labelAlign:"right",
			labelWidth:80,
			layout:"column",
			frame:true,
			items:[{
				columnWidth:1,
				layout:"form",
				defaults:{
					anchor:"100%",
					labelSeparator:""
				},
				items:[{
					xtype:"datefield",
					format:"Y-m-d",
					fieldLabel:_txt+"时间",
					name:"gmtLeftDate",
					id:"gmtLeftDate",
					allowBlank:false,
					itemCls:"required"
				},{
					xtype:"textarea",
					fieldLabel:"备注",
					name:"note",
					id:"note"
				},{
					xtype:"hidden",
					name:"staffId",
					id:"staffId"
				}]
			}],
			buttons:[{
				text:"保存",
				itemCls:"floppy16",
				handler:function(btn){
					if(form.getForm().isValid()){
						form.getForm().submit({
							url:_url,
							method:"post",
							type:"json",
							success:function(_form,_action){
								Ext.getCmp(STAFF.GRID).getStore().reload();
								Ext.getCmp(STAFF.STAFFWIN).close();
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
				itemCls:"close16",
				handler:function(btn){
					Ext.getCmp(STAFF.STAFFWIN).close();
				}
			}]
		};
		
		com.zz91.zzwork.staff.StatusInfo.superclass.constructor.call(this,c);
	},
	initForm:function(staffId, note, gmtLeft){
		this.findById("staffId").setValue(staffId);
		this.findById("note").setValue(note);
		if(gmtLeft!=null){
			this.findById("gmtLeftDate").setValue(Ext.util.Format.date(new Date(gmtLeft.time), 'Y-m-d'));
		}
	}
});

com.zz91.zzwork.staff.RoleSelector = function(account){
	var rolegrid = new com.zz91.zzwork.staff.RoleGrid({
		region:"center",
		layout:"fit"
	});

	var win = new Ext.Window({
		id:"roleselector",
		title:"选择角色",
		width:600,
		height:500,
		modal:true,
		layout:"border",
		items:[rolegrid]
	});
	
	win.show();
}

com.zz91.zzwork.staff.RoleGrid = Ext.extend(Ext.grid.GridPanel,{
	constructor:function(config){
		config = config || {};
		Ext.apply(this,config);
		
		var _fields = this.roleRecord;
		
		var _store = new Ext.data.JsonStore({
			root:"records",
			totalProperty:'totalRecords',
			remoteSort:false,
			fields:_fields,
			url:Context.ROOT+"/staff/queryRole.htm",
			autoLoad:true
		});
		
		var _sm=new Ext.grid.CheckboxSelectionModel();
		var _cm=new Ext.grid.ColumnModel([_sm,{
			id:"edit-id",
			header:'编号',
			width:10,
			sortable:true,
			dataIndex:'id',
			hidden:true
		},{
			id:"edit-name",
			header:'角色名',
			sortable:true,
			dataIndex:'name'
		},{
			id:"edit-remark",
			header:'备注',
			sortable:true,
			dataIndex:'remark'
		}]);
		
		var grid=this;
		
		var c={
			viewConfig:{
				autoFill:true
			},
			store:_store,
			sm:_sm,
			cm:_cm,
			buttons:[{
				text:"选择角色",
				handler:function(btn){
					var row = grid.getSelectionModel().getSelections();
					if(row.length>0){
						var nameArr=new Array();
						var idArr=new Array();
						for(var i=0;i<row.length;i++){
							nameArr.push(row[i].get("name"));
							idArr.push(row[i].get("id"));
						}
						Ext.getCmp("roleArr").setValue(idArr.join(","));
						Ext.getCmp("role").setValue(nameArr.join(","));
					}else{
						Ext.getCmp("roleArr").setValue("");
						Ext.getCmp("role").setValue("");
					}
					Ext.getCmp("roleselector").close();
				}
			},{
				text:"关闭",
				handler:function(btn){
					Ext.getCmp("roleselector").close();
				}
			}]
		};
		
		com.zz91.zzwork.staff.RoleGrid.superclass.constructor.call(this,c);

	},
	roleRecord:Ext.data.Record.create(["id","name","remark"])
});


