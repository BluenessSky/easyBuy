
<div class="top">
    <div class="logo">
        <a href="http://localhost:8080"><img src="statics/images/logo.png"></a>
    </div>
    <div class="search">
        <form action="http://localhost:8081/item/search" method="post">
            <input type="text" value="${keyWord!}" name="query" class="s_ipt">
            <input type="submit" value="搜索" class="s_btn">
        </form>
        <!--推荐最热商品-->
    </div>
    <div class="i_car">
        <div class="car_t">
            购物车 [<span>空</span>]
        </div>
        <div class="car_bg">
            <!--End 购物车未登录 End-->
            <!--Begin 购物车已登录 Begin-->
            <ul class="cars">
            	<!-- 购物车中的商品列表 -->
            	<li  id="mycars"></li>
            </ul>
            <div class="price_sum">共计&nbsp;<font color="#ff4e00">￥</font><span></span></div>
            <div class="price_a"><a href="http://localhost:8084/login">去登录</a></div>
            <!--End 购物车已登录 End-->
        </div>
    </div>
</div>
