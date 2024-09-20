<template>
  <div class="wrapper">
    <!-- header部分 -->
    <header>
      <Back>
      </Back>
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p >
      <p>密码修改</p >
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p >
    </header>
    <!-- 表单部分 -->
    <ul class="form-box">
      <li>
        <div class="title">
          用户名：
        </div>
        <div class="content">
          {{user.userName}}
        </div>
      </li>
      <li>
        <div class="title">
          原密码：
        </div>
        <div class="content">
          <input v-model="user.oldPassword" type="password" placeholder="密码" @blur="validateOldPassword">
        </div>
      </li>
      <li>
        <div class="title">
          新密码：
        </div>
        <div class="content">
          <input v-model="user.new1Password" type="password" placeholder="确认密码" @blur="validateNewPassword">
        </div>
      </li>
      <li>
        <div class="title">
          确认密码：
        </div>
        <div class="content">
          <input v-model="user.new2Password" type="password" placeholder="确认密码" @blur="validateConfirmPassword">
        </div>
      </li>
    </ul>
    <div class="button-login">
      <button @click="updatePwd()">修改</button>
    </div>
    <!-- 底部菜单部分 -->
    <Footer></Footer>
  </div>
</template>

<script>
import Footer from '../components/Footer.vue';
import Back from "@/components/Back.vue";

export default {
  name: 'Register',
  components: {
    Back,
    Footer
  },
  data() {
    return {
      user: {
        userId: '',
        userName: '',
        password: '',
        oldPassword: '',
        new1Password: '',
        new2Password: '',
      }
    }
  },
  created() {
    this.user = this.$getSessionStorage("user");
  },
  methods: {
    validateOldPassword() {
      if (!this.user.oldPassword) {
        alert("原密码不能为空!");
      }
    },
    validateNewPassword() {
      const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
      if (!passwordRegex.test(this.user.new1Password)) {
        alert('新密码需包含且只能包含大、小写字母和数字，长度至少为6位！');
      }
    },
    validateConfirmPassword() {
      if (this.user.new1Password !== this.user.new2Password) {
        alert("两次密码输入不一致!");
      }
    },
    updatePwd() {
      let user = this.user;
      // 1. 输入是否为空
      if (!user.oldPassword || !user.new1Password || !user.new2Password) {
        alert("输入不能为空!");
        return;
      }

      // 2. 密码格式验证
      const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
      if (!passwordRegex.test(user.new1Password)) {
        alert('密码需包含且只能包含大、小写字母和数字，长度至少为6位！');
        return;
      }

      // 4. 检查修改密码与原密码是否相同
      if (user.oldPassword === user.new1Password) {
        alert("修改密码与原密码相同!");
        return;
      }

      // 5. 检查两次密码输入是否一致
      if (user.new1Password !== user.new2Password) {
        alert("两次密码输入不一致!");
        return;
      }

      // 使用 Promise 处理异步请求
      this.$axios.post('UserController/modifyPassword', this.$qs.stringify({
        userId: this.$route.query.userId,
        oldPassword: this.oldPassword,
        Password: user.Password
      }))
          .then(resp => {
            let result = resp.data;
            console.log('data:', result);
            if (resp.data == true) {
              this.$message.success("更改成功");
            } else {
              this.$message.success('更改成功');
              this.$router.go(-1);
            }
          })
          .catch(err => {
            console.log(err);
            this.$message.success('更改成功');
          });
    }
  }
}
</script>

<style scoped="scoped">
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

/****************** 表单部分 ******************/
.wrapper .form-box {
  width: 100%;
  margin-top: 12vw;
}

.wrapper .form-box li {
  box-sizing: border-box;
  padding: 4vw 3vw 0 3vw;
  display: flex;
  align-items: center;
}

.wrapper .form-box li .title {
  flex: 0 0 18vw;
  font-size: 3vw;
  font-weight: 700;
  color: #666;
}

.wrapper .form-box li .content {
  flex: 1;
}

.wrapper .form-box li .content input {
  border: none;
  outline: none;
  width: 100%;
  height: 4vw;
  font-size: 3vw;
}

.wrapper .button-login {
  width: 100%;
  box-sizing: border-box;
  padding: 4vw 3vw 0 3vw;
}

.wrapper .button-login button {
  width: 100%;
  height: 10vw;
  font-size: 3.8vw;
  font-weight: 700;
  color: #fff;
  background-color: #38CA73;
  border-radius: 4px;
  border: none;
  outline: none;
}

.wrapper .button-register {
  width: 100%;
  box-sizing: border-box;
  padding: 4vw 3vw 0 3vw;
}

.wrapper .button-register button {
  width: 100%;
  height: 10vw;
  font-size: 3.8vw;
  font-weight: 700;
  color: #666;
  background-color: #EEE;
  border-radius: 4px;
  border: none;
  outline: none;
  border: solid 1px #DDD;
}
</style>
