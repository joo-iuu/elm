<template>
  <div class="wrapper">

    <!-- header部分 -->
    <header>
      <Back>
      </Back>
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p >
      <p>商家信息</p >
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p >

    </header>

    <!-- 商家logo部分 -->
    <div class="business-logo">
      <img :src="business.businessImg">
    </div>

    <!-- 商家信息部分 -->
    <div class="business-info">
      <h1>{{business.businessName}}</h1>
      <p>&#165;{{business.starPrice}}起送 &#165;{{business.deliveryPrice}}配送</p>
      <p>{{business.businessExplain}}</p>
    </div>
    <div class="remark" @click="toRemark">
      <button>评论</button>
    </div>

    <!-- 食品列表部分 -->
    <ul class="food">
      <li v-for="(item, index) in foodArr" :key="item.foodId">
        <div class="food-left">
          <img :src="item.foodImg">
          <div class="food-left-info">
            <h3>{{ item.foodName }}</h3>
            <h4>{{ item.foodExplain }}</h4>
            <p>&#165;{{ item.foodPrice }}</p>
          </div>
        </div>
        <div class="food-right">
          <div>
            <i class="fa fa-minus-circle" @click="minus(index)" v-show="item.quantity != 0"></i>
          </div>
          <p><span v-show="item.quantity != 0">{{ item.quantity }}</span></p>
          <div>
            <i class="fa fa-plus-circle" @click="add(index)"></i>
          </div>
        </div>
      </li>
    </ul>
    <!-- 购物车部分 -->
    <div class="cart">
      <div class="cart-left" @click="toggleCart">
        <div class="cart-left-icon" :style="totalQuantity==0?'background-color:#505051;':'background-color:#3190E8;'">
          <i class="fa fa-shopping-cart"></i>
          <div class="cart-left-icon-quantity" v-show="totalQuantity!=0">{{totalQuantity}}</div>
        </div>
        <div class="cart-left-info">
          <p>&#165;{{totalPrice}}</p>
          <p>另需配送费{{business.deliveryPrice}}元</p>
        </div>
      </div>

      <div class="cart-right">
        <!-- 不够起送费 -->
        <div class="cart-right-item" v-show="totalSettle<business.starPrice" style="background-color: #535356;cursor: default;">
          &#165;{{business.starPrice}}起送
        </div>
        <!-- 达到起送费 -->
        <div class="cart-right-item" @click="toOrder" v-show="totalSettle>=business.starPrice">
          去结算
        </div>
        <div class="content-right" @click.stop.prevent="payment">
        </div>
      </div>

      <!-- 购物车蒙层 -->
      <div v-show="showCart" class="overlay" @click="toggleCart"></div>

      <!-- 购物车详细列表 -->
      <div v-show="showCart" class="cart-details">
        <div class="clear-cart" @click="clearCart">
          <p>已选商品</p>
          <button>清空</button>
        </div>
        <ul>
          <li v-for="(item, index) in filteredFoodArr" :key="item.foodId">
            <div class="cart-left">
              <img :src="item.foodImg">
              <div class="cart-item">
                <h3>{{ item.foodName }}</h3>
                <h5>￥{{ (item.foodPrice * item.quantity).toFixed(2) }}</h5>
              </div>
            </div>
            <div class="cart-item-right-actions">
              <i class="fa fa-minus-circle" @click="minus(index)"></i>
              <p>{{ item.quantity }}</p>
              <i class="fa fa-plus-circle" @click="add(index)"></i>
            </div>
          </li>
        </ul>
      </div>

  </div>
  </div>

</template>
<script>
import Footer from '../components/Footer.vue';
import Back from '@/components/Back.vue';
import payment from "@/views/Payment.vue";
export default {
  name: 'BusinessInfo',
  data() {
    return {
      businessId: this.$route.query.businessId,
      business: {},
      foodArr: [],
      user: {},
      showCart: false, // 控制购物车详情的显示与隐藏
    }
  },
  created() {
    this.user = this.$getSessionStorage('user');

    //根据businessId查询商家信息
    this.$axios.post('BusinessController/getBusinessById', this.$qs.stringify({
      businessId: this.businessId
    })).then(response => {
      this.business = response.data;
    }).catch(error => {
      console.error(error);
    });

    //根据businessId查询所属食品信息
    this.$axios.post('FoodController/listFoodByBusinessId', this.$qs.stringify({
      businessId: this.businessId
    })).then(response => {
      this.foodArr = response.data;
      for (let i = 0; i < this.foodArr.length; i++) {
        this.foodArr[i].quantity = 0;
      }

      //如果已登录，那么需要去查询购物车中是否已经选购了某个食品
      if (this.user != null) {
        this.listCart();
      }
    }).catch(error => {
      console.error(error);
    });
  },
  components: { Back, Footer },
  methods: {

    toggleCart() {
      this.showCart = !this.showCart; // 切换购物车展开/收起状态
    },
    listCart() {
      this.$axios.post('CartController/listCart', this.$qs.stringify({
        businessId: this.businessId,
        userId: this.user.userId
      })).then(response => {
        let cartArr = response.data;
        //遍历所有食品列表
        for (let foodItem of this.foodArr) {
          for (let cartItem of cartArr) {
            if (cartItem.foodId == foodItem.foodId) {
              foodItem.quantity = cartItem.quantity;
              break;
            }
          }
        }
        this.foodArr.sort();
      }).catch(error => {
        console.error(error);
      });
    },
    toRemark() {
      this.$router.push({
        path: '/remark', //跳转路径
        query: {
          businessId: this.businessId //参数
        }
      });
    },

    add(index) {
      this.checkLogin(() => {
        if (this.foodArr[index].quantity == 0) {
          //做insert
          this.saveCart(index);
        } else {
          //做update
          this.updateCart(index, 1);
        }
      });
    },

    minus(index) {
      this.checkLogin(() => {
        if (this.foodArr[index].quantity > 1) {
          //做update
          this.updateCart(index, -1);
        } else {
          //做delete
          this.removeCart(index);
        }
      });
    },

    checkLogin(callback) {
      if (this.user == null) {
        this.$router.push({ path: '/login' });
        return;
      }
      callback();
    },

    saveCart(index) {
      this.$axios.post('CartController/saveCart', this.$qs.stringify({
        businessId: this.businessId,
        userId: this.user.userId,
        foodId: this.foodArr[index].foodId
      })).then(response => {
        if (response.data == 1) {
          //此食品数量要更新为1；
          this.foodArr[index].quantity = 1;
          this.foodArr.sort();
        } else {
          alert('向购物车中添加食品失败！');
        }
      }).catch(error => {
        console.error(error);
      });
    },

    updateCart(index, num) {
      this.$axios.post('CartController/updateCart', this.$qs.stringify({
        businessId: this.businessId,
        userId: this.user.userId,
        foodId: this.foodArr[index].foodId,
        quantity: this.foodArr[index].quantity + num
      })).then(response => {
        if (response.data == 1) {
          //此食品数量要更新为1或-1；
          this.foodArr[index].quantity += num;
          this.foodArr.sort();
        } else {
          alert('向购物车中更新食品失败！');
        }
      }).catch(error => {
        console.error(error);
      });
    },

    removeCart(index) {
      this.$axios.post('CartController/removeCart', this.$qs.stringify({
        businessId: this.businessId,
        userId: this.user.userId,
        foodId: this.foodArr[index].foodId
      })).then(response => {
        if (response.data == 1) {
          //此食品数量要更新为0；视图的减号和数量要消失
          this.foodArr[index].quantity = 0;
          this.foodArr.sort();
        } else {
          alert('从购物车中删除食品失败！');
        }
      }).catch(error => {
        console.error(error);
      });
    },

    // 清空购物车
    clearCart() {
      this.$axios.post('CartController/clearCart', this.$qs.stringify({
        userId: this.user.userId,
        businessId: this.businessId

      })).then(response => {
        if (response.data == 1) {
          for (let i = 0; i < this.foodArr.length; i++) {
            this.foodArr[i].quantity = 0;
          }
          this.foodArr.sort();
        } else {
          alert('清空购物车失败！');
        }
      }).catch(error => {
        console.error(error);
      });
    },

    toOrder() {
      this.$router.push({ path: '/orders', query: { businessId: this.business.businessId } });
    }
  },
  computed: {
    filteredFoodArr() {
      return this.foodArr.filter(item => item.quantity > 0);
    },

    payment() {
      return payment
    },
    //食品总价格
    totalPrice() {
      return this.foodArr.reduce((total, item) => total + item.foodPrice * item.quantity, 0).toFixed(2);
    },

    //食品总数量
    totalQuantity() {
      return this.foodArr.reduce((quantity, item) => quantity + item.quantity, 0);
    },

    //结算总价格
    totalSettle() {
      return this.totalPrice + this.business.deliveryPrice;
    },
  }
}
</script>
<style scoped>
/****************** 总容器 ******************/
.wrapper{
  width: 100%;
  height: 100%;
}

/****************** header部分 ******************/
.wrapper header{
  width: 100%;
  height: 12vw;
  background-color: #0097FF;
  color: #fff;
  font-size: 4.8vw;

  position: fixed;
  left: 0;
  top: 0;
  z-index: 1000;

  display: flex;
  justify-content: center;
  align-items: center;
}

/****************** 商家logo部分 ******************/
.wrapper .business-logo{
  width: 100%;
  height: 35vw;
  /*使用上外边距避开header部分*/
  margin-top: 12vw;

  display: flex;
  justify-content: center;
  align-items: center;
}
.wrapper .business-logo img{
  width: 40vw;
  height: 30vw;
  border-radius: 5px;
}

/****************** 商家信息部分 ******************/
.wrapper .business-info{
  width: 100%;
  height: 20vw;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.wrapper .business-info h1{
  font-size: 5vw;
}
.wrapper .business-info p{
  font-size: 3vw;
  color: #666;
  margin-top: 1vw;
}
/****************** 评论部分 ******************/
.wrapper .remark {
  display: flex;
  align-items: center;
  margin: 1vw 0 5vw 3vw;
}

.wrapper .remark button {
  background-color: #0084ff;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.wrapper .remark button:hover {
  background-color: #005cbf;
}

/****************** 食品列表部分 ******************/
.wrapper .food{
  width: 100%;
  /*使用下外边距避开footer部分*/
  padding-bottom: 14vw;
}
.wrapper .food li{
  width: 100%;
  box-sizing: border-box;
  padding: 2.5vw;
  user-select: none;

  display: flex;
  justify-content: space-between;
  align-items: center;
}
.wrapper .food li .food-left{
  display: flex;
  align-items: center;
}
.wrapper .food li .food-left img{
  width: 20vw;
  height: 20vw;
}
.wrapper .food li .food-left .food-left-info{
  margin-left: 3vw;
}
.wrapper .food li .food-left .food-left-info h3{
  font-size: 3.8vw;
  color: #555;
}
.wrapper .food li .food-left .food-left-info h4{
  font-size: 3vw;
  color: #888;
  margin-top: 2vw;
}
.wrapper .food li .food-left .food-left-info p{
  font-size: 4vw;
  color: red;
  margin-top: 2vw;
}
.wrapper .food li .food-right{
  width: 16vw;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.wrapper .food li .food-right .fa-minus-circle{
  font-size: 5.5vw;
  color: #999;
  cursor: pointer;
}
.wrapper .food li .food-right p{
  font-size: 3.6vw;
  color: #333;
}
.wrapper .food li .food-right .fa-plus-circle{
  font-size: 5.5vw;
  color: #0097EF;
  cursor: pointer;
}

/****************** 购物车部分 ******************/
.wrapper .cart{
  width: 100%;
  height: 14vw;

  position: fixed;
  left: 0;
  bottom: 0;

  display: flex;

}
.wrapper .cart .cart-left{
  flex: 2;
  background-color: #505051;
  display: flex;
}
.wrapper .cart .cart-left .cart-left-icon{
  width: 14vw;
  height: 14vw;
  box-sizing: border-box;
  border: solid 1.6vw #444;
  border-radius: 8vw;
  background-color: #3190E8;
  font-size: 7vw;
  color: #fff;

  display: flex;
  justify-content: center;
  align-items: center;

  margin-top: 0;
  margin-left: 3vw;

  position: relative;
}
.wrapper .cart .cart-left .cart-left-icon-quantity{
  width: 5vw;
  height: 5vw;
  border-radius: 2.5vw;
  background-color: red;
  color: #fff;
  font-size: 3.6vw;

  display: flex;
  justify-content: center;
  align-items: center;

  position: absolute;
  right: -1.5vw;
  top: -1.5vw;
}
.wrapper .cart .cart-left .cart-left-info p:first-child{
  font-size: 4.5vw;
  color: #fff;
  margin-top: 1vw;
}
.wrapper .cart .cart-left .cart-left-info p:last-child{
  font-size: 2.8vw;
  color: #AAA;
}

.wrapper .cart .cart-right{
  flex: 1;
}
/*达到起送费时的样式*/
.wrapper .cart .cart-right .cart-right-item{
  width: 100%;
  height: 100%;
  background-color: #38CA73;
  color: #fff;
  font-size: 4.5vw;
  font-weight: 700;
  user-select: none;
  cursor: pointer;

  display: flex;
  justify-content: center;
  align-items: center;
}

/*不够起送费时的样式（只有背景色和鼠标样式的区别）*/
/*
.wrapper .cart .cart-right .cart-right-item{
  width: 100%;
  height: 100%;
  background-color: #535356;
  color: #fff;
  font-size: 4.5vw;
  font-weight: 700;
  user-select: none;

  display: flex;
  justify-content: center;
  align-items: center;
}
*/
/****************** 蒙层样式  ******************/

.wrapper .overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  bottom: 14vw;
  background-color: rgba(0, 0, 0, 0.5); /* 半透明黑色背景 */
  z-index: 999; /* 保证在购物车背后 */

}

/****************** 购物车详细列表部分 ******************/
.wrapper .cart-details {
  width: 100%;
  max-height: 40vh; /* 限制购物车详情的最大高度 */
  background-color: #fff; /* 背景颜色为白色 */
  box-shadow: 0 -2vw 5vw rgba(0, 0, 0, 0.1); /* 顶部阴影 */
  border-radius: 2vw 2vw 0 0; /* 顶部圆角 */
  position: fixed;
  bottom: 14vw; /* 位于购物车上方，避开底部固定的购物车 */
  left: 0;
  z-index: 1000; /* 保证购物车详细列表显示在最上方 */
  overflow-y: auto; /* 超出部分可滚动 */
  padding: 2vw 0; /* 内边距 */
  transition: transform 0.3s ease; /* 添加平滑展开/收起的动画效果 */
}

/* 当购物车为空时，隐藏列表 */
.wrapper .cart-details .hidden {
  transform: translateY(100%); /* 初始状态为收起 */
}

.wrapper .cart-details ul {
  list-style:  none;
  padding: 2px;
  margin: 2px;
  color: #00abf5;
}

.wrapper .cart-details ul li {
  padding: 3vw 0;
  border-bottom: 1px solid #f0f0f0; /* 分隔线 */
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.wrapper .cart-details li .cart-left{
  display: flex;
  align-items: center;
  background-color: white;
}
.wrapper .cart-details li .cart-left img{
  margin-left: 2vw;
  width: 15vw;
  height: 15vw;
}
.wrapper .cart-details .cart-item {
  margin-left: 3vw;

}

/* 商品名称部分 */
.wrapper .cart-details .cart-item h3{
  flex: 1;
  font-size: 3.5vw;
  color: #333;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis; /* 商品名称过长时显示省略号 */
  margin-top: 1vw;
  margin-bottom: 4vw;
}

/* 商品价格部分 */
.wrapper .cart-details .cart-item h5 {
  font-size: 3vw;
  color: red;
  margin-top: 2vw;
}

/* 数量操作按钮部分 */
.wrapper .cart-details li .cart-item-right-actions {
  width: 16vw;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-right: 2vw;
}
.wrapper .cart-details .cart-item-right-actions .fa-minus-circle{
  font-size: 5.5vw;
  color: #999;
  cursor: pointer;
}

.wrapper .cart-details .cart-item-right-actions .fa-plus-circle {
  font-size: 5vw;
  color: #0097EF;
  cursor: pointer;
}

.wrapper .cart-details .cart-item-right-actions p {
  font-size: 4vw;
  margin: 0 2vw;
  color: #333;
}

/* 购物车为空时的提示样式 */
.wrapper .cart-details .empty-cart {
  text-align: center;
  color: #999;
  font-size: 4vw;
  padding: 20vw 0;
}

/****************** 清空购物车按钮样式 ******************/
.wrapper .cart-details .clear-cart {
  display: flex;
  justify-content: space-between;
  padding: 3vw 0;
  border-bottom: 1px solid #f0f0f0;
  margin-right: 4vw;

}
.wrapper .cart-details .clear-cart p{

  border: none;
  padding: 1.5vw 4vw;
  font-size: 4vw;
  color: #333;

  margin-left: 2vw;}

.wrapper .cart-details .clear-cart button {
  background-color: #ff4d4f;
  color: #fff;
  border: none;
  border-radius: 2vw;
  padding: 1.5vw 4vw;
  font-size: 4vw;
  cursor: pointer;
  transition: background-color 0.3s;
}

.wrapper .cart-details .clear-cart button:hover {
  background-color: #d9363e;
}

</style>
