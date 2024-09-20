<template>
  <div class="wrapper">
    <heade>
      <p>我的</p>
    </heade>
    <!-- header部分 -->
    <header>
      <img :src="user.userImg" @error="handleImageError" alt="User avatar">
      <p>{{ user.userName }}</p>
    </header>

    <!-- 个人信息部分 -->
    <ul class="userinfoa">
      <li @click="changeImg(user.userId)">
        <img src="../assets/user.png"><p>我的头像</p>
      </li>
      <li @click="toMyWallet">
        <img src="../assets/wallet.png"><p>我的钱包</p>
      </li>
      <li @click="toMyCredit">
        <img src="../assets/points.jpg"><p>我的吃货豆</p>
      </li>
    </ul>
    <ul class="userinfob">
      <li @click="changePwd">
        <p><i class="fa fa-expeditedssl"></i>修改密码</p>
        <i class="fa fa-angle-right"></i>
      </li>
      <li>
        <p><i class="fa fa-gratipay"></i>我的收藏</p>
        <i class="fa fa-angle-right"></i>
      </li>
      <li @click="toBecomeMerchant">
        <p><i class="fa fa-sign-out"></i>成为商家</p>
        <i class="fa fa-angle-right"></i>
      </li>
      <li @click="logOut()">
        <p><i class="fa fa-sign-out"></i>退出登录</p>
        <i class="fa fa-angle-right"></i>
      </li>

    </ul>
    <Footer></Footer>
  </div>
</template>

<script>
import Footer from '../components/Footer.vue'
export default {
  name: 'MyInfo',
  data: function() {
    return {
      user: {
        userImg: require('../assets/user.png') // 初始化图片路径
      }
    }
  },
  methods: {
    // 跳转到修改密码页面
    changePwd() {
      this.$router.push('/changePwd');
    },
    // 跳转到头像修改页面
    changeImg: function(userId) {
      this.$router.push({
        path: '/myImg',
        query: {
          userId: userId
        }
      });
    },
    toMyCredit: function() {
      this.$router.push('/credit');
    },
    toMyWallet: function() {
      this.$router.push('/myWallet');
    },
    toBecomeMerchant: function() {
      this.$router.push('/becomeMerchant');
    },
    // 获取用户信息
    getUserById: function(userId) {
      let that = this;
      let url = 'UserController/getUserById';
      this.$axios.post(url, this.$qs.stringify({
        userId
      }))
          .then(resp => {
            that.user = resp.data;
            if (that.user.userImg) {
              that.user.userImg = that.user.userImg; // 直接使用服务器返回的图片路径
            }
          })
          .catch(err => {
            console.log(err);
          })
    },
    // 退出登录 (实际退出操作,依赖响应拦截器)
    logOut(user) {
      this.$removeSessionStorage('user');
      let result = this.$getSessionStorage('user');
      if (result == null) {
        alert('退出成功');
        this.$router.push('/index');
      } else {
        alert('退出失败');
      }
    },
    // 处理图片加载错误
    handleImageError() {
      this.user.userImg = require('../assets/user.png'); // 加载失败时使用默认头像
    }
  },
  components: {
    Footer: Footer
  },
  created: function() {
    let user = this.$getSessionStorage('user');
    if (user != null) {
      this.getUserById(user.userId);
    }
  }
}
</script>

<style scoped="scoped">
/*************** 总容器 ***************/
.wrapper {
  width: 100%;
  height: 100%;
  overflow-x: hidden;
  background-color: #F5F5F5;
}

.wrapper heade{
  box-sizing: border-box;
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

/*************** header ***************/
.wrapper header {
  width: 100%;
  box-sizing: border-box;
  height: 30vw;
  padding-top: 12vw;
  background-color: #0097FF;
  display: flex;
  align-items: center;
  color: #fff;
  font-size: 4.8vw;
}

.wrapper header img {
  width: 16vw;
  height: 16vw;
  border-radius: 8vw;
  margin-left: 5vw;
}

.wrapper header p {
  margin-left: 5vw;
  color: #fff;
  font-size: 5.6vw;
  font-weight: 700;
}

/*************** userinfo ***************/

.wrapper .userinfob {
  width: 100%;
  box-sizing: border-box;
  padding-bottom: 14vw;
}

.wrapper .userinfob li {
  width: 100%;
  height: 12vw;
  box-sizing: border-box;
  padding: 0 4vw;
  margin-top: 3.6vw;
  background-color: #fff;

  display: flex;
  justify-content: space-between;
  align-items: center;

  user-select: none;
  cursor: pointer;
}

.wrapper .userinfob li p {
  font-size: 4.2vw;
  color: #666;
}

.wrapper .userinfob li i {
  /*左右两边的图标样式一样*/
  font-size: 5vw;
  color: #0097FF;
  margin-right: 3vw;
}
.wrapper .userinfoa {
  width: 100%;
  height: 20vw;
  border:solid 1px #cecece;
  background-color: #fff;
  left: 0;
  bottom: 0;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.wrapper .userinfoa li {
  /*li本身的尺寸完全由内容撑起*/
  box-sizing: content-box;
  width: 33%;
  border-right: solid #9f9f9f 1px;
  padding:9px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: #999;
  user-select: none;
  cursor: pointer;
}

.wrapper .userinfoa li p {
  font-size: 3.5vw;
}

.wrapper .userinfoa li img {
  width: 12vw;
  /*视频讲解时高度设置为12vw，实际上设置为10.3vw更佳*/
  height: 12vw;
}
</style>