<template>
  <div class="wrapper">
    <header>
      <Back>
      </Back>
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p >
      <p>确认订单</p >
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p >
    </header>


    <div class="order-info">
      <h5>订单配送至：</h5>
      <div class="order-info-address" @click="toUserAddress">

        <p>{{ address.address }}</p>

        <i class="fa fa-angle-right"></i>
      </div>

        <p>{{ address.contactName }} {{ address.contactSex == 1 ? '先生' : '女士' }} {{ address.contactTel }}</p>


    </div>


    <h3>{{business.businessName}}</h3>

    <!-- 订单明细部分 -->
    <!-- 订单明细部分 -->
    <ul class="order-detailed" v-if="cartArr.length > 0">
      <li v-for="item in cartArr" :key="item.food.foodId">
        <div class="order-detailed-left">
          <img :src="item.food.foodImg" :alt="item.food.foodName">
          <p>{{ item.food.foodName }} x {{ item.quantity }}</p>
        </div>
        <p>&#165;{{(item.food.foodPrice * item.quantity).toFixed(2)}}</p>
      </li>
    </ul>
    <div class="order-deliveryfee">
      <p>配送费</p>
      <p>&#165;{{business.deliveryPrice}}</p>
    </div>
    <!-- 		<div class="order-deliveryfee">
          <p>是否使用积分抵消金额</p>
          <p><input type="checkbox" v-model="open" :checked="open == 1"/></p>
        </div> -->
    <!-- 合计部分 -->
    <div class="total">
      <div class="total-left">
        &#165;{{totalPrice}}
      </div>
      <div class="total-right" @click="toPayment">
        去支付
      </div>
    </div>
  </div>
</template>

<script>
import Back from '@/components/Back.vue';
import Footer from '../components/Footer.vue';

export default {

  name: 'Orders',
  data() {
    return {
      businessId: this.$route.query.businessId,
      address: JSON.parse(this.$route.query.address || '{}'),

      business: {},
      daId: this.$route.query.daId,
      deliveryaddress: {},
      user: {},
      cartArr: [],
      open: 0,
    }
  },
  created() {
    this.user = this.$getSessionStorage('user');
    this.deliveryaddress = this.$getLocalStorage(this.user.userId);
    const storedAddress = this.$getLocalStorage(this.user.userId);
    if (storedAddress) {

    this.address = JSON.parse(storedAddress);}

    // 使用 Promise.all 等待所有异步请求完成
    Promise.all([
      this.$axios.post('DeliveryAddressController/getDeliveryAddressById', this.$qs.stringify({
        daId: this.daId
      })),
      this.$axios.post('BusinessController/getBusinessById', this.$qs.stringify({
        businessId: this.businessId
      })),
      this.$axios.post('CartController/listCart', this.$qs.stringify({
        userId: this.user.userId,
        businessId: this.businessId
      }))
    ]).then(([deliveryAddressResponse, businessResponse, cartResponse]) => {
      this.deliveryaddress = deliveryAddressResponse.data;
      this.business = businessResponse.data;
      this.cartArr = cartResponse.data;
    }).catch(error => {
      console.error(error);
    });
    //查询当前用户名
    this.$axios.post('DeliveryAddressController/getDeliveryAddressById', this.$qs.stringify({
      daId: this.daId,

    })).then(response => {
      this.deliveryaddress = response.data;
    }).catch(error => {
      console.error(error);
    });

    //查询当前商家
    this.$axios.post('BusinessController/getBusinessById', this.$qs.stringify({
      businessId: this.businessId
    })).then(response => {
      this.business = response.data;
    }).catch(error => {
      console.error(error);
    });

    //查询当前用户在购物车中的当前商家食品列表
    this.$axios.post('CartController/listCart', this.$qs.stringify({
      userId: this.user.userId,
      businessId: this.businessId
    })).then(response => {
      this.cartArr = response.data;
    }).catch(error => {
      console.error(error);
    });
  },
  computed: {
    totalPrice() {
      let totalPrice = 0;
      for (let cartItem of this.cartArr) {
        totalPrice += cartItem.food.foodPrice * cartItem.quantity;
      }
      totalPrice += this.business.deliveryPrice;
      return totalPrice.toFixed(2);
    },
  },
  methods: {
    toUserAddress() {
      this.$router.push({path: '/userAddress', query: {businessId: this.businessId}});
    },
      toPayment() {
        if (this.address == null) {
          alert('请选择送货地址！');
          return;
        }

        if (!this.user.userId) {
          alert('用户ID无效！');
          return;
        }

        if (!this.businessId) {
          alert('商家ID无效！');
          return;
        }

        if (!this.address.daId) {
          alert('请选择送货地址！');
          return;
        }

        if (!this.totalPrice || this.totalPrice <= 0) {
          alert('订单总价无效！');
          return;
        }

        // 创建订单
        this.$axios.post('OrdersController/createOrders', this.$qs.stringify({
          userId: this.user.userId,
          businessId: this.businessId,
          daId: this.address.daId,
          orderTotal: this.totalPrice,
          opens: this.open
        })).then(response => {
          let orderId = response.data;
          if (orderId > 0) {
            this.$router.push({path: '/payment', query: {orderId: orderId}});
          } else {
            alert('创建订单失败！');
          }
        }).catch(error => {
          console.error(error);
          if (error.response && error.response.data) {
            alert(`创建订单失败，错误信息：${error.response.data.message}`);
          } else {
            alert('创建订单失败，请稍后再试！');
          }
        });
      },
},
  components: {
    Back,Footer
  },}

</script>

<style scoped>
/****************** 总容器 ******************/
.wrapper {
  width: 100%;
  height: 100%;
}

/****************** header部分 ******************/
.wrapper header {
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

/****************** 订单信息部分 ******************/
.wrapper .order-info {
  /*注意这里，不设置高，靠内容撑开。因为地址有可能折行*/
  width: 100%;
  margin-top: 12vw;
  background-color: #0097EF;
  box-sizing: border-box;
  padding: 2vw;
  color: #fff;
}

.wrapper .order-info h5 {
  font-size: 3vw;
  font-weight: 300;
}

.wrapper .order-info .order-info-address {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;

  font-weight: 700;
  user-select: none;
  cursor: pointer;
  margin: 1vw 0;
}

.wrapper .order-info .order-info-address p {
  width: 90%;
  font-size: 5vw;
}

.wrapper .order-info .order-info-address i {
  font-size: 6vw;
}

.wrapper .order-info p {
  font-size: 3vw;
}

.wrapper h3 {
  box-sizing: border-box;
  padding: 3vw;
  font-size: 4vw;
  color: #666;
  border-bottom: solid 1px #DDD;
}

/****************** 订单明细部分 ******************/
.wrapper .order-detailed {
  width: 100%;
}

.wrapper .order-detailed li {
  width: 100%;
  height: 16vw;
  box-sizing: border-box;
  padding: 3vw;
  color: #666;

  display: flex;
  justify-content: space-between;
  align-items: center;
}

.wrapper .order-detailed li .order-detailed-left {
  display: flex;
  align-items: center;
}

.wrapper .order-detailed li .order-detailed-left img {
  width: 10vw;
  height: 10vw;
}

.wrapper .order-detailed li .order-detailed-left p {
  font-size: 3.5vw;
  margin-left: 3vw;
}

.wrapper .order-detailed li p {
  font-size: 3.5vw;
}

.wrapper .order-deliveryfee {
  width: 100%;
  height: 16vw;
  box-sizing: border-box;
  padding: 3vw;
  color: #666;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 3.5vw;
}

/****************** 订单合计部分 ******************/
.wrapper .total {
  width: 100%;
  height: 14vw;

  position: fixed;
  left: 0;
  bottom: 0;

  display: flex;
}

.wrapper .total .total-left {
  flex: 2;
  background-color: #505051;
  color: #fff;
  font-size: 4.5vw;
  font-weight: 700;
  user-select: none;

  display: flex;
  justify-content: center;
  align-items: center;
}

.wrapper .total .total-right {
  flex: 1;
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
</style>
点击去支付显示      创建订单失败，请稍后再试！
