window.dhtmlXTooltip = {
    version: 0.1
};
dhtmlXTooltip.config = {
    className: "dhtmlXTooltip tooltip",
    timeout_to_display: 50,
    delta_x: 15,
    delta_y: -20
};
dhtmlXTooltip.tooltip = document.createElement("div");
dhtmlXTooltip.tooltip.className = dhtmlXTooltip.config.className;
dhtmlXTooltip.show = function(D, G) {
    dhtmlXTooltip.tooltip.className = dhtmlXTooltip.config.className;
    var H = this.position(D);
    var E = D.target || D.srcElement;
    if (this.isTooltip(E)) {
        return
    }
    var C = H.x + dhtmlXTooltip.config.delta_x || 0;
    var B = H.y - dhtmlXTooltip.config.delta_y || 0;
    this.tooltip.style.visibility = "hidden";
    if (this.tooltip.style.removeAttribute) {
        this.tooltip.style.removeAttribute("right");
        this.tooltip.style.removeAttribute("bottom")
    } else {
        this.tooltip.style.removeProperty("right");
        this.tooltip.style.removeProperty("bottom")
    }
    this.tooltip.style.left = "0px";
    this.tooltip.style.top = "0px";
    this.tooltip.innerHTML = G;
    scheduler._obj.appendChild(this.tooltip);
    var A = this.tooltip.offsetWidth;
    var F = this.tooltip.offsetHeight;
    if (document.body.offsetWidth - C - A < 0) {
        if (this.tooltip.style.removeAttribute) {
            this.tooltip.style.removeAttribute("left")
        } else {
            this.tooltip.style.removeProperty("left")
        }
        this.tooltip.style.right = (document.body.offsetWidth - C + 2 * dhtmlXTooltip.config.delta_x || 0) + "px"
    } else {
        if (C < 0) {
            this.tooltip.style.left = (H.x + Math.abs(dhtmlXTooltip.config.delta_x || 0)) + "px"
        } else {
            this.tooltip.style.left = C + "px"
        }
    }
    if (document.body.offsetHeight - B - F < 0) {
        if (this.tooltip.style.removeAttribute) {
            this.tooltip.style.removeAttribute("top")
        } else {
            this.tooltip.style.removeProperty("top")
        }
        this.tooltip.style.bottom = (document.body.offsetHeight - B - 2 * dhtmlXTooltip.config.delta_y || 0) + "px"
    } else {
        if (B < 0) {
            this.tooltip.style.top = (H.y + Math.abs(dhtmlXTooltip.config.delta_y || 0)) + "px"
        } else {
            this.tooltip.style.top = B + "px"
        }
    }
    this.tooltip.style.visibility = "visible"
};
dhtmlXTooltip.hide = function() {
    if (this.tooltip.parentNode) {
        this.tooltip.parentNode.removeChild(this.tooltip)
    }
};
dhtmlXTooltip.delay = function(D, B, C, A) {
    if (this.tooltip._timeout_id) {
        window.clearTimeout(this.tooltip._timeout_id)
    }
    this.tooltip._timeout_id = setTimeout(function() {
        var E = D.apply(B, C);
        D = obj = C = null;
        return E
    },
    A || this.config.timeout_to_display)
};
dhtmlXTooltip.isTooltip = function(B) {
    var A = false;
    while (B && !A) {
        A = (B.className == this.tooltip.className);
        B = B.parentNode
    }
    return A
};
dhtmlXTooltip.position = function(A) {
    var A = A || window.event;
    if (A.pageX || A.pageY) {
        return {
            x: A.pageX,
            y: A.pageY
        }
    }
    var B = ((dhtmlx._isIE) && (document.compatMode != "BackCompat")) ? document.documentElement: document.body;
    return {
        x: A.clientX + B.scrollLeft - B.clientLeft,
        y: A.clientY + B.scrollTop - B.clientTop
    }
};
scheduler.attachEvent("onMouseMove",
function(D, F) {
    var C = F || window.event;
    var E = C.target || C.srcElement;
    if (D || dhtmlXTooltip.isTooltip(E)) {
        var B = scheduler.getEvent(D) || scheduler.getEvent(dhtmlXTooltip.tooltip.event_id);
        dhtmlXTooltip.tooltip.event_id = B.id;
        var G = scheduler.templates.tooltip_text(B.start_date, B.end_date, B);
        if (_isIE) {
            var A = document.createEventObject(C)
        }
        dhtmlXTooltip.delay(dhtmlXTooltip.show, dhtmlXTooltip, [A || C, G])
    } else {
        dhtmlXTooltip.delay(dhtmlXTooltip.hide, dhtmlXTooltip, [])
    }
});
scheduler.templates.tooltip_text = function(C, A, B) {
	var str="";
	if(typeof B.staffName != "undefined" && B.staffName!=null){
		str=str+"<b>拥有者</b>："+B.staffName+"<br />";
	}
	if(typeof B.assignStaffName != "undefined" && B.assignStaffName!=null){
		str=str+"<b>授予人</b>："+B.assignStaffName+"<br />";
	}
    return str+"<b>事件:</b> " + B.text;
};