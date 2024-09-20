<template>
  <div class="wrapper">

    <!-- header部分 -->
    <header>
      <Back></Back>
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p>
      <p>商家信息</p>
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p>
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

    <!-- 商家管理部分 -->
    <div class="business-management">
      <h2>商家管理</h2>

      <!-- 配送费和起送价修改部分 -->
      <div class="delivery-management">
        <div class="delivery-item">
          <label>起送价:</label>
          <input type="number" v-model="business.starPrice" @change="updateStarPrice">
          <button @click="confirmUpdateStarPrice">确认修改</button>
        </div>
        <div class="delivery-item">
          <label>配送费:</label>
          <input type="number" v-model="business.deliveryPrice" @change="updateDeliveryPrice">
          <button @click="confirmUpdateDeliveryPrice">确认修改</button>
        </div>
      </div>
      <ul class="food-management">
        <li v-for="(item,index) in foodArr">
          <div class="food-left">
            <img :src="item.foodImg">
            <div class="food-left-info">
              <h3>{{item.foodName}}</h3>
              <h4>{{item.foodExplain}}</h4>
              <div class="price-input">
                <input type="number" v-model="item.foodPrice" @change="updateFoodPrice(index)">
                <button @click="confirmUpdatePrice(index)">确认修改</button>
              </div>
            </div>
          </div>
        </li>
      </ul>

    </div>

  </div>
</template>

<script>
import Footer from '../components/Footer.vue';
import Back from '@/components/Back.vue';

export default {
  name: 'BusinessControl',
  data() {
    return {
      businessId: this.$route.query.businessId, // 确保使用 this.$route.query
      business: {},
      foodArr: [],
      user: {},
    }
  },
  created() {
    this.user = this.$getSessionStorage('user');

    // 确保 businessId 不为 undefined
    if (!this.businessId) {
      console.error('businessId is undefined');
      return;
    }

    // 根据businessId查询商家信息
    this.$axios.post('BusinessController/getBusinessById', this.$qs.stringify({
      businessId: this.businessId
    })).then(response => {
      this.business = response.data;
    }).catch(error => {
      console.error(error);
    });

    // 根据businessId查询所属食品信息
    this.$axios.post('FoodController/listFoodByBusinessId', this.$qs.stringify({
      businessId: this.businessId
    })).then(response => {
      this.foodArr = response.data;
    }).catch(error => {
      console.error(error);
    });
  },
  components: { Back, Footer },
  methods: {
    // 更新食品价格
    updateFoodPrice(index) {
      // 这里可以添加一些逻辑，比如实时更新价格但不保存到数据库
    },
    // 确认修改价格
    confirmUpdatePrice(index) {
      this.$axios.post('FoodController/updateFoodPrice', this.$qs.stringify({
        foodId: this.foodArr[index].foodId,
        foodPrice: this.foodArr[index].foodPrice
      })).then(response => {
        if (response.data == 1) {
          this.$message.success('食品价格更新成功！');
        } else {
          this.$message.error('食品价格更新失败！');
        }
      }).catch(error => {
        console.error(error);
      });
    },
    // 更新起送价
    updateStarPrice() {
      // 这里可以添加一些逻辑，比如实时更新起送价但不保存到数据库
    },
    // 确认修改起送价
    confirmUpdateStarPrice() {
      this.$axios.post('BusinessController/updateStarPrice', this.$qs.stringify({
        businessId: this.businessId,
        starPrice: this.business.starPrice
      })).then(response => {
        if (response.data == 1) {
          this.$message.success('起送价更新成功！');
        } else {
          this.$message.error('起送价更新失败！');
        }
      }).catch(error => {
        console.error(error);
      });
    },
    // 更新配送费
    updateDeliveryPrice() {
      // 这里可以添加一些逻辑，比如实时更新配送费但不保存到数据库
    },
    // 确认修改配送费
    confirmUpdateDeliveryPrice() {
      if (this.handlePayment(order).order.orderState === 0) { // 假设 'UNPAID' 表示未支付状态
        this.$axios.post('BusinessController/updateDeliveryPrice', this.$qs.stringify({
          businessId: this.businessId,
          deliveryPrice: this.business.deliveryPrice
        })).then(response => {
          if (response.data == 1) {
            this.$message.success('配送费更新成功！');
          } else {
            this.$message.error('配送费更新失败！');
          }
        }).catch(error => {
          console.error(error);
        });
      }
    }
  }
}
</script>

<style scoped>
/****************** 总容器 ******************/
.wrapper {
  width: 100%;
  height: 100%;
  background-color: #f5f5f5;
  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
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
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/****************** 商家logo部分 ******************/
.wrapper .business-logo {
  box-sizing: border-box;
  width: 100%;
  height: 35vw;
  margin-top: 12vw;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fff;
  padding: 2vw 0;
}

.wrapper .business-logo img {
  width: 40vw;
  height: 30vw;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/****************** 商家信息部分 ******************/
.wrapper .business-info {
  box-sizing: border-box;
  width: 100%;
  padding: 4vw 5vw;
  background-color: #fff;
  margin-bottom: 2vw;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.wrapper .business-info h1 {
  font-size: 5vw;
  color: #333;
  margin-bottom: 2vw;
}

.wrapper .business-info p {
  font-size: 3.5vw;
  color: #666;
  margin-top: 1vw;
}

/****************** 商家管理部分 ******************/
.wrapper .business-management {
  width: 100%;
  padding: 4vw 5vw;
  background-color: #fff;
  margin-bottom: 2vw;
  box-sizing: border-box;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.wrapper .business-management h2 {
  font-size: 5vw;
  color: #333;
  margin-bottom: 4vw;
}

.wrapper .business-management .food-management li {
  width: 100%;
  padding: 4vw 0;
  border-bottom: 1px solid #eee;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.wrapper .business-management .food-management li:last-child {
  border-bottom: none;
}

.wrapper .business-management .food-management li .food-left {
  display: flex;
  align-items: center;
}

.wrapper .business-management .food-management li .food-left img {
  width: 20vw;
  height: 20vw;
  border-radius: 10px;
  margin-right: 4vw;
}

.wrapper .business-management .food-management li .food-left-info {
  display: flex;
  flex-direction: column;
}

.wrapper .business-management .food-management li .food-left-info h3 {
  font-size: 4vw;
  color: #333;
  margin-bottom: 1vw;
}

.wrapper .business-management .food-management li .food-left-info h4 {
  font-size: 3.5vw;
  color: #666;
  margin-bottom: 1vw;
}

.wrapper .business-management .food-management li .food-left-info .price-input {
  display: flex;
  align-items: center;
}

.wrapper .business-management .food-management li .food-left-info .price-input input {
  width: 20vw;
  height: 6vw;
  font-size: 3.5vw;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 1vw;
  margin-right: 2vw;
}

.wrapper .business-management .food-management li .food-left-info .price-input button {
  background-color: #0097FF;
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 1vw 2vw;
  font-size: 3.5vw;
  cursor: pointer;
  transition: background-color 0.3s;
}

.wrapper .business-management .food-management li .food-left-info .price-input button:hover {
  background-color: #007acc;
}

/****************** 配送费和起送价修改部分 ******************/
.wrapper .business-management .delivery-management {
  width: 100%;
  padding:0 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.wrapper .business-management .delivery-management .delivery-item {
  display: flex;
  align-items: center;
}

.wrapper .business-management .delivery-management .delivery-item label {
  font-size: 2.5vw;
  color: #333;
  margin-right: 2vw;
}

.wrapper .business-management .delivery-management .delivery-item input {
  width: 10vw;
  height: 6vw;
  font-size: 2.5vw;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 1vw;
  margin-right: 2vw;
}

.wrapper .business-management .delivery-management .delivery-item button {
  background-color: #0097FF;
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 1vw 2vw;
  font-size: 2.5vw;
  cursor: pointer;
  transition: background-color 0.3s;
}

.wrapper .business-management .delivery-management .delivery-item button:hover {
  background-color: #007acc;
}
</style>