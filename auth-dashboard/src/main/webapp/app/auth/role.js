Ext.namespace("com.zz91.zzwork.auth.role");

var ROLE = new function(){
	this.GRID="roleeditgrid";
	this.RIGHTGRID="rightgrid";
	this.RIGHTWIN="rightwin";
}

com.zz91.zzwork.auth.role.RoleEditGrid = Ext.extend(Ext.grid.EditorGridPanel,{
    constructor:function(config){
        config = config || {};
        Ext.apply(this,config);
        
        var _fields = this.roleRecord;
        var _url = this.storeUrl;
        
        var _store = new Ext.data.JsonStore({
            root:"records",
            totalProperty:'totals',
            remoteSort:false,
            fields:_fields,
            url:_url,
            autoLoad:true
        });
        
        var _sm=new Ext.grid.CheckboxSelectionModel({singleSelect:true});
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
            dataIndex:'name',
            editor:{
                xtype:'textfield',
                allowBlank:false
            }
        },{
            id:"edit-remark",
            header:'备注',
            sortable:true,
            dataIndex:'remark',
            editor:{
                xtype:"textarea"
            }
        }]);
        
        var c={
            id:ROLE.GRID,
            iconCls:"icon-grid",
            viewConfig:{
                autoFill:true
            },
            store:_store,
            sm:_sm,
            cm:_cm,
            tbar:[{
                iconCls:"add16",
                text:"新增",
                scope:this,
                handler:function(){
                    var r=new this.roleRecord({
                        id:0,
                        name:"",
                        remark:""
                    });
                    this.stopEditing();
                    _store.insert(_store.getCount(),r);
                    this.startEditing(_store.getCount()-1,2);
                }
            },{
                iconCls:"delete16",
                text:"删除角色",
                scope:this,
                handler:function(){
                    this.deleteRole();
                }
            },{
                iconCls:"refresh16",
                text:"刷新",
                handler:function(){
                    _store.reload();
                }
            }]
        };
        
        com.zz91.zzwork.auth.role.RoleEditGrid.superclass.constructor.call(this,c);
        
        this.on("afteredit",function(e){
            var _url;
            if(e.record.get("id")>0){
                _url=Context.ROOT+"/auth/role/updateRole.htm";
            }else{
                _url=Context.ROOT+"/auth/role/createRole.htm";
            }
            this.saveRole(e.record,_url);
        });
    },
    storeUrl:Context.ROOT+"/auth/role/listRole.htm",
    roleRecord:Ext.data.Record.create(["id","name","remark"]),
    saveRole:function(record,_url){
        Ext.Ajax.request({
            url:_url,
            params:{
                "id":record.get("id"),
                "name":record.get("name"),
                "remark":record.get("remark")
            },
            scope:this,
            success:function(response,opt){
                var obj = Ext.decode(response.responseText);
                if(obj.success){
                    this.getStore().reload();
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
    },
    deleteRole:function(){
//          Ext.MessageBox.confirm(MESSAGE.title,MESSAGE.confirmDelete,this.deleteRole);
        
        var record = this.getSelectionModel().getSelected();
        Ext.Ajax.request({
            url:Context.ROOT+"/auth/role/deleteRole.htm",
            params:{
                "id":record.get("id")
            },
            scope:this,
            success:function(response,opt){
                var obj = Ext.decode(response.responseText);
                if(obj.success){
                    this.getStore().remove(record);
                    com.zz91.zzwork.utils.Msg(MESSAGE.picTitleInfo,MESSAGE.deleteSuccess);
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
});

com.zz91.zzwork.auth.role.UpdateRoleRight = function(roleId, rightId, checked){
	Ext.Ajax.request({
        url:Context.ROOT+"/auth/role/updateRoleRight.htm",
        params:{
            "roleId":roleId,
            "rightId":rightId,
            "checked":checked
        },
        success:function(response,opt){
            var obj = Ext.decode(response.responseText);
            if(obj.success){
                com.zz91.zzwork.utils.Msg(MESSAGE.picTitleInfo,MESSAGE.saveSuccess);
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