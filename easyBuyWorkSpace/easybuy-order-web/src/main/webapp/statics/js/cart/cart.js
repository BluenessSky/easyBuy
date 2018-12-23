/**
 * Created by bdqn on 2016/5/3.
 */
/**
 * 商品详情页添加到购物车
 */
function addCart(){
    var entityId=$("input[name='entityId']").val();
    var quantity=$("input[name='quantity']").val();
    //添加到购物车
    addCartByParam(entityId,quantity);
}
/**
 * 商品列表页添加到购物车
 * @param entityId
 * @param quantity
 */
function addCartByParam(entityId,quantity){
    //添加到购物车
    $.ajax({
        url: contextPath + "/Cart",
        method: "post",
        data: {
            entityId: entityId,
            quantity: quantity
        },
        success: function (jsonStr) {
        	 var result = eval("(" + jsonStr + ")");
             //状态判断
             if (result.status == 200) {
            	 showMessage("操作成功");
            	 refreshCart();
             }else{
            	 showMessage(result.message);
             }
        }
    } )
}
/**
 * 刷新购物车 searchBar DIV
 */
function refreshCart(){
    $.ajax({
        url: "/cart/refreshCart",
        method: "post",
        dataType:"jsonp",
        data: null,
        success: function (data) {
            //$("#mycars li").html("");
            var $li = $("#mycars");
            for(var i = 0; i < data.items.length; i++){
            	$li.append("<div class='img'><a href='http:localhost:8082/item/queryProductDeatil?id="+data.items[i].id+"'><img src='"+data.items[i].filename+"' width='58' height='58' /></a></div>");
            	$li.append("<div class='name'><a href='http:localhost:8082/item/queryProductDeatil?id="+data.items[i].id+"'>"+data.items[i].name+"</a></div>");
            	$li.append("<div class='price'><font color='#ff4e00'>￥"+data.items[i].price+"</font> X"+data.items[i].stock+"</div>");
            }
            //购物车中的商品数量
            if(data.items.length > 0){
            	$(".car_t").html("购物车 [<span>"+data.items.length+"</span>]");
            } else {
            	$(".car_t").html("购物车 [<span>空</span>]");
            }
            //修改商品总的价格
            $(".price_sum span").html(data.sum);
        }
    })
}
/**
 * 结算 加载购物车列表
 */
function settlement1(){
    $.ajax({
        url:"/cart/settlement1",
        method: "post",
        data: null,
        dataType:"html",
        success: function (result) {
        	refreshCart();
            $("#settlement").html(result);
        }
    });
}
/**
 * 结算 形成预备订单
 */
function settlement2(){
    $.ajax({
        url: "/cart/settlement2",
        method: "post",
        data:null,
        dataType:"html",
        success: function (result) {
            $("#settlement").html(result);
        }
    });
}
/**
 * 结算 订单支付提醒
 */
function settlement3(){
    //判断地址
    var addressId=$("input[name='selectAddress']:checked").val();
    var newAddress=$("input[name='address']").val();
    var newRemark=$("input[name='remark']").val();
    if(addressId=="" || addressId==null){
        showMessage("请选择收货地址");
        return;
    }else if(addressId=="-1"){
        if(newAddress=="" || newAddress==null){
            showMessage("请填写新的收货地址");
            return;
        }else if(newAddress.length<=2 || newAddress.length>=50){
            showMessage("收货地址为2-50个字符");
            return;
        }
    }
    
    $("#addressId").val(addressId);
    $("#newAddress").val(newAddress);
    $("#newRemark").val(newRemark);
    $("#orderform").attr("action","/order/settlement3");
    $("#orderform").submit();
    /*
    $.ajax({
        url: "/cart/settlement3",
        method: "post",
        data: {
            addressId:addressId,
            newAddress:newAddress,
            newRemark:newRemark
        },
        success: function (result) {
        	$("#settlement").html(result);
        	
        }
    });*/
}
/**
 * 商品详情页的 数量加
 * @param obj
 * @param entityId
 */
function addQuantity(obj,entityId,stock){
	var quantity=Number(getPCount(jq(obj)))+1;
	if(stock<quantity){
		showMessage("商品数量不足");
		return;
	}
    modifyCart(entityId,quantity,jq(obj));
}
/**
 * 减去 数量减
 * @param obj
 * @param entityId
 */
function subQuantity(obj,entityId){
    var quantity=getPCount(jq(obj))-1;
    if(quantity==0) quantity=1;
    modifyCart(entityId,quantity,jq(obj));
}
/**
 * 修改购物车列表
 * @param entityId
 * @param quantity
 */
function modifyCart(entityId,quantity,obj){
	$.ajax({
        url: contextPath + "/Cart",
        method: "post",
        data: {
            action: "modCart",
            entityId:entityId,
            quantity:quantity
        },
        success: function (jsonStr) {
        	var result = eval("(" + jsonStr + ")");
            //状态判断
            if (result.status == 1) {
                obj.parent().find(".car_ipt").val(quantity);
                settlement1();
            }else{
           	 	showMessage(result.message);
            }
        }
    });
}
/**
 * 清空购物车
 */
function clearCart(){
    $.ajax({
        url: contextPath + "/Cart",
        method: "post",
        data: {
            action: "clearCart"
        },
        success: function (jsonStr) {
            $("#settlement").html(jsonStr);
            showMessage("操作成功");
        }
    });
}
/**
 * 删除购物车
 * @param entityId
 */
function removeCart(entityId){
	$.ajax({
        url: contextPath + "/Cart",
        method: "post",
        data: {
            action: "modCart",
            entityId:entityId,
            quantity:0
        },
        success: function (jsonStr) {
        	var result = eval("(" + jsonStr + ")");
            //状态判断
            if (result.status == 1) {
            	settlement1();
            }else{
           	 	showMessage(result.message);
            }
        }
    });
}
/**
 * 喜欢的列表
 */
function favoriteList(){
    $.ajax({
        url: contextPath + "/Favorite",
        method: "post",
        data: {
            action: "toFavoriteList"
        },
        success: function (jsonStr) {
            $("#favoriteList").html(jsonStr);
        }
    });
}
/**
 * 添加到收藏列表
 * @param productId
 */
function addFavorite(productId){
    $.ajax({
        url: contextPath + "/Favorite",
        method: "post",
        data: {
            action: "addFavorite",
            id:productId
        },
        success: function (jsonStr) {
            favoriteList();
        }
    });
}