<template>
  <div class="wrapper">

    <!-- header部分 -->
    <header>
      <Back>
      </Back>
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p >
      <p>头像管理</p >
      <p>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</p >

    </header>
    <div class="userinfo">
      <img v-bind:src="imgSrc" class="userinfo-img">


      <ul id="userAlternateImgArrBox">
        <li @click="changeImglocal(1)"><img src="../assets/user1.png"></li>
        <li @click="changeImglocal(2)"><img src="../assets/user2.png"></li>
        <li @click="changeImglocal(3)"><img src="../assets/user3.png"></li>
        <li @click="changeImglocal(4)"><img src="../assets/user4.png"></li>
        <li @click="changeImglocal(5)"><img src="../assets/user5.png"></li>

      </ul>
      <div>
        <input type="file" @change="getBase64">

      </div>
    </div>
    <Footer></Footer>
  </div>
</template>
//js代码
<script>
import Footer from '../components/Footer.vue'
import Back from '@/components/Back.vue';

export default {
  name: 'MyImg',
  data: function() {
    return {
      user: {},
      userId: this.$route.query.userId,
      base64: '',
      imgSrc: require('../assets/user.png')
    }
  },

  methods: {
    getBase64(e) {
      let file = e.target.files[0];
      if (window.FileReader) {
        let reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = e => {
          let base64String = e.target.result;
          this.imgSrc = base64String;
          this.saveImg(base64String);
        }
      }
    },
			saveImg(base64String) {
				this.$axios.post('UserController/changeUserPortrait', this.$qs.stringify({
						userId: this.$route.query.userId,
						base64: base64String

					}))
					.then(resp => {

						let result = resp.data;
						if (result == 0) {
              this.$message.error("头像更改失败");
						} else {
              this.$message.success('更改成功');
						}
					})
					.catch(err => {
						console.log(err);
					})

			},
    changeImglocal: function(index) {
      let data = require("../assets/user.png");
      if (index == 1)
        data = require("../assets/user1.png");
      else if (index == 2)
        data = require("../assets/user2.png");
      else if (index == 3)
        data = require("../assets/user3.png");
      else if (index == 4)
        data = require("../assets/user4.png");
      else if (index == 5)
        data = require("../assets/user5.png");
      this.imgSrc = data;
      this.saveImg(data);
    },

    getUserById(){

				this.$axios.post('UserController/getUserById', this.$qs.stringify({
						userId: this.$route.query.userId
					}))
					.then(resp => {
						this.user = resp.data;
						return this.imgSrc = user.userImg;
					})
					.catch(err => {
						console.log(err);
					})
			},
		},

		components: {
			Footer: Footer,Back
		},
	}
</script>

<style scoped="scoped">
	/*************** 总容器 ***************/
	.wrapper {
		width: 100%;
		height: 100%;
	}

	/*************** header ***************/
	.wrapper header {
		width: 100%;
		height: 12vw;
		background-color: #0097FF;
		display: flex;
		justify-content: space-around;
		align-items: center;
		color: #fff;
		font-size: 4.8vw;
		position: fixed;
		left: 0;
		top: 0;
		/*保证在最上层*/
		z-index: 1000;
	}

	/*************** userimg ***************/
	.wrapper .userinfo {
		width: 100%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;

		box-sizing: border-box;
		padding: 4vw;

		padding-top: 14vw;
		padding-bottom: 14vw;
	}

	.wrapper .userinfo .userinfo-img {
		display: block;
		width: 30vw;
		height: 30vw;
		border-radius: 15vw;
		margin-bottom: 8vw;
	}

	.wrapper .userinfo ul {
		width: 100%;
		display: flex;
		align-items: center;
		flex-wrap: wrap;
	}

	.wrapper .userinfo ul li {
		width: 16vw;
		height: 16vw;
		margin: 0 0 4vw 6vw;
	}

	.wrapper .userinfo ul li img {
		display: block;
		width: 16vw;
		height: 16vw;
		border-radius: 8vw;
		cursor: pointer;
	}

	.wrapper .userinfo button {
		margin-top: 1vw;
		width: 100%;
		height: 10vw;
		font-size: 3.8vw;
		font-weight: 700;

		border: none;
		outline: none;
		border-radius: 4px;

		color: #fff;
		background-color: #38CA73;
	}
</style>