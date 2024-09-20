<template>
  <div class="wrapper">
    <header>
      <Back>
      </Back>
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p >
      <p>成为商家</p >
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p >
    </header>
    <div class="content">
      <input type="text" v-model="businessId" placeholder="请输入businessId口令">
      <button @click="verifyBusinessId">验证</button>
    </div>
  </div>
</template>

<script>
import Back from "@/components/Back.vue";

export default {
  name: 'BecomeMerchant',
  components: {Back},
  data() {
    return {
      businessId: ''
    }
  },
  methods: {
    verifyBusinessId() {
      // 获取当前用户的userId和password
      let user = this.$getSessionStorage('user');
      console.log('User from session storage:', user); // 调试代码

      if (!user) {
        this.$message.error('请先登录');
        console.log('User is not logged in'); // 调试代码
        return;
      }

      let userId = user.userId;
      console.log('User ID:', userId); // 调试代码

      // 记录 businessId 的值
      console.log('Business ID:', this.businessId); // 调试代码

      // 发送请求到后端验证
      this.$axios.post('BusinessController/verifyBusinessId', this.$qs.stringify({
        userId: userId,
        businessId: this.businessId
      })).then(resp => {
        console.log('Response from server:', resp); // 调试代码

        if (resp.data == true) {
          this.$message.success('验证成功，即将跳转到商家信息页面');
          console.log('Verification successful, redirecting to business control page'); // 调试代码
          this.$router.push({
            path: '/businessControl',
            query: {
              businessId: this.businessId
            }
          });
        } else {
          this.$message.error('验证失败，请检查输入信息');
          console.log('Verification failed'); // 调试代码
        }
      }).catch(err => {
        console.log('Error during verification:', err); // 调试代码
        this.$message.error('验证失败，请稍后再试');
      });
    }
  }
}
</script>

<style scoped>
/*************** 总容器 ***************/
.wrapper {
  width: 100%;
  height: 100%;
  background-color: #F5F5F5;
}

/*************** header ***************/
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

/*************** content ***************/
.wrapper .content {
  width: 100%;
  padding-top: 12vw;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.wrapper .content input {
  width: 80%;
  height: 10vw;
  margin-top: 5vw;
  padding: 0 2vw;
  font-size: 4vw;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.wrapper .content button {
  width: 80%;
  height: 10vw;
  margin-top: 5vw;
  background-color: #0097FF;
  color: #fff;
  font-size: 4vw;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.wrapper .content button:hover {
  background-color: #007ACC;
}
</style>